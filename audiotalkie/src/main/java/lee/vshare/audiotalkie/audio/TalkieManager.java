package lee.vshare.audiotalkie.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.util.List;

import androidx.annotation.RequiresApi;
import lee.vshare.audiotalkie.Jni;
import lee.vshare.audiotalkie.callBack.RecordDataCallback;
import lee.vshare.audiotalkie.callBack.SocketCallback;
import lee.vshare.audiotalkie.channel.TalkieChannel;
import lee.vshare.audiotalkie.model.OutCallInformation;
import lee.vshare.audiotalkie.model.RTPPackage;
import lee.vshare.audiotalkie.net.UdpClient;
import lee.vshare.audiotalkie.utils.AudioConfig;
import lee.vshare.audiotalkie.utils.DataUtil;
import lee.vshare.audiotalkie.utils.OutLimitList;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TalkieManager implements RecordDataCallback, SocketCallback {

    private final String TAG = "Lee_TalkieManager";

    private static TalkieManager talkieManager;
    private RecordThread recordThread;
    private TrackThread trackThread;
    private boolean isSpeaking, isListening;
    private UdpClient udpClient = null;
    private int state;
    private boolean hasHeartRecv;
    private Handler handler;
    private Runnable heartCheckRunnable;
    private boolean trackAble;
    private OnTalkieMessageCallback messageCallback;
    private int seqOld;

    private int doubleAble = 1;
    private int mqttCallId;
    private boolean isUdpControl = false;

    private AudioConfig audioConfig;

    HeadsetPlugReceiver headsetPlugReceiver;

    public interface OnTalkieMessageCallback {
        void onMessage(String var1);
    }

    public TalkieManager() {

    }

    public void setTrackAble(boolean trackAble) {
        this.trackAble = trackAble;
        if (!trackAble) {
            stopTrack();
        }
    }

    public void setMqttCallId(int mqttCallId) {
        this.mqttCallId = mqttCallId;
        this.seqOld = 0;
        Log.d(TAG, " ------->>>>   receive mqtt message  <<<<-------  mqttCallId = " + mqttCallId);
    }

    /**
     * 获取单例引用
     */
    public static TalkieManager getInstance() {
        if (talkieManager == null) {
            synchronized (TalkieManager.class) {
                if (talkieManager == null) {
                    talkieManager = new TalkieManager();
                }
            }
        }
        return talkieManager;
    }

    /**
     * 初始化AudioRecord & AudioTrack & udpClient
     */
    public int init(String filePath, String heartIp, String audioIp) {

        Log.d(TAG, " ------->>>>   init TalkieManager  start  <<<<-------  state = " + state);

        if (1 == state) return 1;

        Jni.close();
        int opusState = Jni.initOpus();
        Log.e(TAG, "OPUS init state = " + (opusState == 0 ? "success" : "failed"));
        if (0 != opusState) return 0;

        recordThread = new RecordThread(filePath);
        recordThread.start();

        trackThread = new TrackThread(filePath);
        trackThread.start();

        udpClient = new UdpClient(heartIp, audioIp, this::socketReceive);
        udpClient.initSocket();

        addRecordCallback(this::recordData);

        state = 1;
        Log.d(TAG, " ------->>>>   init TalkieManager over  <<<<-------  state = " + state);


//        RTPPackage rtpPackage = new RTPPackage();
//        rtpPackage.setType(0)
//                .setNeedRsp(0)
//                .setTimestamp(1543905877)
//                .setSeq((short) 1)
//                .setSsrc(45052540)
//                .setCallType((short) 1)
//                .setLen(312)
//                .setUserId(2951)
//                .setTargetId(Long.parseLong("1020180405090001391"));
//
//        System.out.println("Test  rtpPackage : " + rtpPackage.toString());
//
//        byte[] rtpHeardData = Jni.getHeaderBytes(rtpPackage.getCallType(),
//                rtpPackage.getTimestamp(),
//                rtpPackage.getSeq(),
//                rtpPackage.getSsrc(),
//                rtpPackage.getLen(),
//                rtpPackage.getUserId(),
//                rtpPackage.getTargetId());
//
//        System.out.println("Test  rtpHeardData : " + Arrays.toString(rtpHeardData));

        return 2;

    }

    public synchronized void startRecord() {
        Log.d(TAG, "startRecord : " + OutCallInformation.getString());
        if (0 == state) return;
        if (!OutCallInformation.isIntact()) return;
        if (isListening) stopTrack();
        if (!isSpeaking && createOutChannel()) {
            Log.d(TAG, TalkieChannel.getInstance().toString());
            isSpeaking = true;

            RTPPackage rtpPackage = new RTPPackage();
            rtpPackage.setType(2)
                    .setCallType(TalkieChannel.getInstance().getCallType())
                    .setSeq((short) 0)
                    .setSsrc(TalkieChannel.getInstance().getCallId())
                    .setUserId(TalkieChannel.getInstance().getUserId())
                    .setTargetId(TalkieChannel.getInstance().getTargetId());
            udpClient.clear();
            udpClient.addRTPPacket(rtpPackage);

            recordThread.begin("" + OutCallInformation.getVoiceIdentification());
        }
    }

    public void stopRecord() {
        Log.d(TAG, "stopRecord");
        if (0 == state) return;
        if (!isSpeaking) return;
        isSpeaking = false;

        RTPPackage rtpPackage = new RTPPackage();
        rtpPackage.setType(3)
                .setCallType(TalkieChannel.getInstance().getCallType())
                .setSeq((short) -1)
                .setSsrc(TalkieChannel.getInstance().getCallId())
                .setUserId(TalkieChannel.getInstance().getUserId())
                .setTargetId(TalkieChannel.getInstance().getTargetId());
        udpClient.addRTPPacket(rtpPackage);

        recordThread.over();
        if (!isListening) {
            releaseChannel();
        }
    }

    private void startTrack(byte[] data) {
        Log.d(TAG, "startTrack   isSpeaking = " + isSpeaking + "   isListening = " + isListening);
        if (!isSpeaking) {
            if (!isListening) {
                if (createInChannel(data)) {
                    Log.d(TAG, TalkieChannel.getInstance().toString());
                    isListening = true;
                    trackThread.begin("" + TalkieChannel.getInstance().getCallId());
                } else {
                    Log.e(TAG, "ERROR : createInChannel failed !");
                }
            } else {
                int callId = DataUtil.getSsrc(data);
                int seqNow = DataUtil.getSequenceNumber(data);

                Log.d(TAG, "seqOld = " + seqOld);
                Log.d(TAG, "seqNow = " + seqNow);
                Log.d(TAG, "callId = " + callId);
                Log.d(TAG, "mqttCallId = " + mqttCallId);
                Log.d(TAG, "TalkieChannelCallId = " + TalkieChannel.getInstance().getCallId());
                if (TalkieChannel.getInstance().isWorking() && callId == mqttCallId && seqNow > seqOld) {//只处理seqNumber增大的包
                    if (addIncoming(data)) {
                        seqOld = seqNow;
                    } else {
                        Log.e(TAG, "addIncoming : Failed !");
                    }
                } else {
                    Log.d(TAG, "addIncoming : invalid " + seqNow);
                }
            }
        } else {
            int callId = DataUtil.getSsrc(data);
            Log.d(TAG, "startTrack when isSpeaking: callId : " + callId + "     mqttCallId = " + mqttCallId);
            if (0 != mqttCallId && callId == mqttCallId) {
                stopRecord();
            }
        }
    }

    private void startTrackE(byte[] data) {
        Log.d(TAG, "startTrackE   isSpeaking = " + isSpeaking + "   isListening = " + isListening);

        if (isUdpControl) {
            int seqNow = DataUtil.getSequenceNumber(data);

            Log.d(TAG, "seqOld = " + seqOld);
            Log.d(TAG, "seqNow = " + seqNow);
            Log.d(TAG, "TalkieChannelCallId = " + TalkieChannel.getInstance().getCallId());
            if (TalkieChannel.getInstance().isWorking() && seqNow > seqOld) {//只处理seqNumber增大的包
                if (addIncoming(data)) {
                    seqOld = seqNow;
                } else {
                    Log.e(TAG, "addIncoming : Failed !");
                }
            } else {
                Log.d(TAG, "addIncoming : invalid " + seqNow);
            }
        } else {
            if (!isSpeaking) {
                if (!isListening) {
                    if (createInChannel(data)) {
                        Log.d(TAG, TalkieChannel.getInstance().toString());
                        isListening = true;
                        trackThread.begin("" + TalkieChannel.getInstance().getCallId());
                    } else {
                        Log.e(TAG, "ERROR : createInChannel failed !");
                    }
                } else {
                    int callId = DataUtil.getSsrc(data);
                    int seqNow = DataUtil.getSequenceNumber(data);

                    Log.d(TAG, "seqOld = " + seqOld);
                    Log.d(TAG, "seqNow = " + seqNow);
                    Log.d(TAG, "callId = " + callId);
                    Log.d(TAG, "mqttCallId = " + mqttCallId);
                    Log.d(TAG, "TalkieChannelCallId = " + TalkieChannel.getInstance().getCallId());
                    if (TalkieChannel.getInstance().isWorking() && callId == mqttCallId && seqNow > seqOld) {//只处理seqNumber增大的包
                        if (addIncoming(data)) {
                            seqOld = seqNow;
                        } else {
                            Log.e(TAG, "addIncoming : Failed !");
                        }
                    } else {
                        Log.d(TAG, "addIncoming : invalid " + seqNow);
                    }
                }
            } else {
                int callId = DataUtil.getSsrc(data);
                Log.d(TAG, "startTrack when isSpeaking: callId : " + callId + "     mqttCallId = " + mqttCallId);
                if (0 != mqttCallId && callId == mqttCallId) {
                    stopRecord();
                }
            }
        }
    }

    public void playFile(String fileName) {
        Log.d(TAG, "playFile");
        isListening = true;
        trackThread.play(fileName);
    }

    public void stopTrack() {
        Log.d(TAG, "stopTrack");
        isListening = false;
        seqOld = 0;
        trackThread.over();
        OutLimitList.getInstance().clean();
        if (!isSpeaking) {
            releaseChannel();
        }
    }

    private boolean addIncoming(byte[] data) {
        RTPPackage rtpPackage = new RTPPackage();
        if (rtpPackage.parse(data)) {
            Log.d(TAG, "-----addIncoming------->>>>>" + rtpPackage.toString());
            int isLost = OutLimitList.getInstance().add(rtpPackage.getSeq());
            return trackThread.addPacket(rtpPackage);
        }
        return false;
    }

    private boolean createOutChannel() {
        Log.d(TAG, "createOutChannel");
        if (TalkieChannel.getInstance().isWorking()) {
            System.out.println("Busying");
            return false;
        }
        Log.d(TAG, OutCallInformation.getString());

        return TalkieChannel.getInstance().reSetSeq()
                .setCallId(OutCallInformation.getVoiceIdentification())
                .setCallType(OutCallInformation.getCallType())
                .setUserId(OutCallInformation.getUserId())
                .setTargetId(OutCallInformation.getTargetId())
                .work();
    }

    private boolean createInChannel(byte[] data) {
        if (TalkieChannel.getInstance().isWorking()) {
            System.out.println("Busying");
            return false;
        }
        doubleAble = 1;
        RTPPackage rtpPackage = new RTPPackage();
        if (rtpPackage.parse(data)) {
            Log.e(TAG, "-----createInChannel------->>>>>" + rtpPackage.toString());
            trackThread.addPacket(rtpPackage);
            return TalkieChannel.getInstance().reSetSeq()
                    .setCallId(rtpPackage.getSsrc())
                    .setCallType(rtpPackage.getCallType())
                    .setUserId(rtpPackage.getUserId())
                    .setTargetId(rtpPackage.getTargetId())
                    .work();
        }
        return false;
    }

    public void release() {
        Log.d(TAG, "release");
//        if (null != recordThread){
//            recordThread.release();
//        }
//        if (null != trackThread){
//            trackThread.release();
//        }
//        udpClient = null;
//        state = 0;
        Jni.close();
    }

    public void sendHeartBeat(long userId, int needRsp, long timeOut) {
        if (state == 0) {
            return;
        }
        RTPPackage rtpPackage = new RTPPackage();
        rtpPackage.setType(1)
                .setUserId(userId)
                .setNeedRsp(needRsp);
        Log.d(TAG, "---------- ping  >>>>>  " + rtpPackage.toString());
        udpClient.addRTPPacket(rtpPackage);
        hasHeartRecv = false;
        checkHeartTimeOut(timeOut);
    }

    private void checkHeartTimeOut(long timeOut) {
        handler.postDelayed(heartCheckRunnable, timeOut);
    }

    @Override
    public void recordData(byte[] opusData) {
        RTPPackage rtpPackage = new RTPPackage();
        rtpPackage.setType(0)
                .setCallType(TalkieChannel.getInstance().getCallType())
                .setSeq(TalkieChannel.getInstance().getSeq())
                .setSsrc(TalkieChannel.getInstance().getCallId())
                .setUserId(TalkieChannel.getInstance().getUserId())
                .setTargetId(TalkieChannel.getInstance().getTargetId())
                .setLen(opusData.length)
                .setOpusData(opusData);

        udpClient.addRTPPacket(rtpPackage);
        udpClient.addRTPPacket(rtpPackage);
    }

    @Override
    public void socketReceive(byte[] data) {
        if (null == data || data.length < 4) return;
        if (20 == data.length) {//心跳回复
        } else if (DataUtil.isMessage(data)) {//业务通信
        } else if (40 == data.length) {
        } else if (36 == data.length) {
            int seq = DataUtil.getSequenceNumber(data);
            Log.d(TAG, "seq = " + seq);
        } else if (36 < data.length && trackAble) {//语音数据
            Log.d(TAG, "data lenth = " + data.length);
            startTrack(data);
        }
    }

    public boolean isListening() {
        return isListening;
    }

    public boolean isSpeaking() {
        return isSpeaking;
    }

    private void releaseChannel() {
        Log.d(TAG, "releaseChannel ");
        TalkieChannel.getInstance().clear();
    }

    private void addRecordCallback(RecordDataCallback recordDataCallback) {
        recordThread.addRecordDataCallback(recordDataCallback);
    }

    public void addMessageCallback(OnTalkieMessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    public void registerHeadsetPlugReceiver(Context context) {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        context.registerReceiver(headsetPlugReceiver, filter);

    }

    public void unRegisterHeadsetPlugReceiver(Context context) {
        if (headsetPlugReceiver == null) return;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.HEADSET_PLUG");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        if (resolveInfos != null && resolveInfos.size() > 0) {
            context.unregisterReceiver(headsetPlugReceiver);
        }
    }

    class HeadsetPlugReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    Log.d(TAG, "headset  disConnected");
                } else if (intent.getIntExtra("state", 0) == 1) {
                    Log.d(TAG, "headset  connected");
                }
                if (null != recordThread) {
                    recordThread.fixHeadset();
                }
            }
        }

    }
}