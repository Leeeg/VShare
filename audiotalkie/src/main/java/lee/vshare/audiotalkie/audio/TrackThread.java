package lee.vshare.audiotalkie.audio;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import androidx.annotation.RequiresApi;
import lee.vshare.audiotalkie.Jni;
import lee.vshare.audiotalkie.model.RTPPackage;
import lee.vshare.audiotalkie.model.RecordConfig;
import lee.vshare.audiotalkie.utils.ByteUtil;

/**
 * CreateDate：18-11-7 on 下午3:01
 * Describe:
 * Coder: lee
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("LongLogTag")
public class TrackThread extends Thread implements BaseAudio {

    private final String TAG = "Lee_TrackThread";

    private AudioTrack audioTrack;
    private int minTrackBufferSize;
    private boolean isPlaying = true;
    private BlockingDeque<RTPPackage> blockingDeque;
    private boolean isPark = true;
    private boolean isFilePlay;
    private File file;
    private FileInputStream fis;
    private FileOutputStream fos;
    private String filePath;
    private String playId;
    private int count;

    public boolean addPacket(RTPPackage rtpPackage) {
        return blockingDeque.offer(rtpPackage);
    }

    public TrackThread(String filePath) {
        this.filePath = filePath;
        init();
    }

    @Override
    public void init() {
        Log.d(TAG, "init");

        minTrackBufferSize = 960 * 6;

        final int trackBufferSize = AudioTrack.getMinBufferSize(RecordConfig.SAMPLE_RATE_INHZ.getValue(), RecordConfig.CHANNEL_OUT_CONFIG.getValue(),
                RecordConfig.AUDIO_FORMAT.getValue());
        Log.e(TAG, "AudioTrack trackBufferSize = " + trackBufferSize);
//        minTrackBufferSize = trackBufferSize;

        audioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setLegacyStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE)
                        .build(),
                new AudioFormat.Builder()
                        .setSampleRate(RecordConfig.SAMPLE_RATE_INHZ.getValue())
                        .setChannelMask(RecordConfig.CHANNEL_OUT_CONFIG.getValue())
                        .setEncoding(RecordConfig.AUDIO_FORMAT.getValue())
                        .build(),
                minTrackBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        int type = audioTrack.getStreamType();
        // AudioTrack.STATE_UNINITIALIZED：没有初始化成功
        // AudioTrack.STATE_INITIALIZED：初始化成功AudioTrack实例，等待被使用
        // AudioTrack.STATE_NO_STATIC_DATA：初始化成功一个使用静态数据的AudioTrack实例，但是该实例还没有获取到任何的数据。
        int trackState = audioTrack.getState();
        Log.e(TAG, "AudioTrack init state = " + trackState + "    streamType = " + type);

        blockingDeque = new LinkedBlockingDeque<>();
    }

    @Override
    public void begin(String voiceIdentification) {
        Log.d(TAG, "startTrack");
        count = 0;
        if (AudioTrack.STATE_INITIALIZED != audioTrack.getState()) {
            Log.e(TAG, "the audioTrack is uninitialized !  state = " + audioTrack.getState());
            return;
        }

        if (null != voiceIdentification && !voiceIdentification.isEmpty()) {
            playId = voiceIdentification;

            Log.i(TAG, "playId = " + playId);

            try {
                file = new File(filePath + File.separator + voiceIdentification + ".pcm");

                fos = new FileOutputStream(file);
            } catch (IOException e) {
                Log.i(TAG, "未能创建");
                throw new IllegalStateException("未能创建" + file.toString());
            }

        }

        if (null != audioTrack)
            audioTrack.play();

        Log.d(TAG, "begin   do isPark = " + isPark);
        if (isPark) {
            isPark = false;
            LockSupport.unpark(this);
        }

    }

    public void play(String voiceIdentification) {
        Log.d(TAG, "playFile");
        if (null != voiceIdentification && !voiceIdentification.isEmpty()) {
            isFilePlay = true;
            playId = voiceIdentification;
            file = new File(filePath + File.separator + voiceIdentification + ".pcm");
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            begin(null);
        }

    }

    @Override
    public void over() {
        Log.d(TAG, "stopTrack ");
        if (null != audioTrack) {
            audioTrack.flush();
            audioTrack.stop();
        }

        Log.d(TAG, "stopTrack   isPark = " + isPark);
        if (!isPark) {
            isPark = true;
            Log.d(TAG, "stopTrack   do isPark = " + isPark);
        }

        try {
            if (null != fos)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void release() {
        Log.d(TAG, "release");
        if (null != audioTrack) {
            isPlaying = false;
            isFilePlay = false;
            audioTrack.release();
            audioTrack = null;
            destroy();
        }
    }

    @Override
    public void run() {
        Log.d(TAG, "run");
        short[] shorts_pkg;
        short[] opusData;
        int opusLen;
        Log.d(TAG, "TrackThread running -------------- ");
        while (isPlaying) {
            try {
                Log.d(TAG, "Playing -------------- isPark = " + isPark);
                if (isPark) {
                    if (0 < blockingDeque.size()) {
                        blockingDeque.clear();
                        Log.i(TAG, "blockingDeque.size() = " + blockingDeque.size());
                    }
                    Log.d(TAG, "park ----------------- ");
                    LockSupport.park();
                }
                RTPPackage aPackage = blockingDeque.poll(50, TimeUnit.MILLISECONDS);
                Log.i(TAG, "blockingDeque.size() = " + blockingDeque.size());
                if (null != aPackage) {
                    count = 0;
                    opusData = aPackage.getOpusDataByShort();
                    Log.i(TAG, "recording ---- decode  >>");
                    opusLen = opusData.length;
                    Log.d(TAG, "shortBuffer ： " + opusLen);
                    shorts_pkg = Jni.opusDecode(opusData, opusLen, minTrackBufferSize);
                    if (null != shorts_pkg) {
                        Log.d(TAG, "shorts_pkg ： " + shorts_pkg.length);
                        Log.i(TAG, "recording ---- decode  << \n");
                        audioTrack.write(shorts_pkg, 0, shorts_pkg.length);
                        if (null != fos) {
                            Log.d(TAG, "fos.write");
                            fos.write(ByteUtil.shortArray2ByteArray(shorts_pkg));
                        }
                    } else {
                        Log.e(TAG, "recording ---- decode failed ! << \n");
                    }
                } else {
                    Log.e(TAG, "can not get the data  : " + count);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Playing ERROR : " + e);
            }
        }
    }

}
