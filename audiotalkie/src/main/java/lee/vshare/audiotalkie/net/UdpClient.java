package lee.vshare.audiotalkie.net;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import lee.vshare.audiotalkie.Jni;
import lee.vshare.audiotalkie.callBack.SocketCallback;
import lee.vshare.audiotalkie.model.RTPPackage;
import lee.vshare.audiotalkie.utils.ByteUtil;

/**
 * CreateDate：18-10-25 on 上午10:17
 * Describe:
 * Coder: lee
 */
public class UdpClient {

    private final String TAG = "Lee_UdpClient";

    private String heartHost;
    private String audioHost;
    private DatagramSocket datagramSocket = null;
    private byte[] buffer = new byte[512];
    private InetAddress local = null;
    private InetAddress localAudio = null;
    private DatagramPacket outPacket, inPacket;
    private SocketCallback socketCallback;
    private BlockingDeque<RTPPackage> blockingDeque = new LinkedBlockingDeque<>(1000);
    private int inCount, outCount;

    public UdpClient(String heartHost, String audioHost, SocketCallback socketCallback) {
//        this.heartHost = "192.168.0.16";
//        this.port = 8887;
        this.heartHost = heartHost;
        this.audioHost = audioHost;
        this.socketCallback = socketCallback;
        Log.e(TAG, "heartHost = " + heartHost + "    audioHost = " + audioHost);
    }

    public void addRTPPacket(RTPPackage rtpPackage) {
        blockingDeque.offer(rtpPackage);
    }

    public void clear() {
        blockingDeque.clear();
    }

    public void initSocket() {

        try {
            datagramSocket = new DatagramSocket(Integer.parseInt(""));
            local = InetAddress.getByName(heartHost);
            localAudio = InetAddress.getByName(audioHost);
            inPacket = new DatagramPacket(buffer, buffer.length);

            new UdpSendThread().start();

            new UdpReceiveThread().start();

        } catch (SocketException e) {
            e.printStackTrace();
            Log.e(TAG, "UdpSendThread : ERROR : " + e);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.e(TAG, "UdpSendThread : ERROR : " + e);
        }

    }

    public void updateHost(String newHost) {
        if (!newHost.equals(heartHost)) {
            try {
                local = InetAddress.getByName(newHost);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendUdp(byte[] data, int type) {
        if (0 == type) {// 语音
            outPacket = new DatagramPacket(data, data.length, localAudio, 9966);
            Log.d(TAG, "on udp send :" + " dataLen = " + data.length + "   count = " + outCount++ + " ---- >>>>>  ip = " + localAudio.getHostAddress() + "   type = " + type);
        } else if (1 == type) { // 心跳
            outPacket = new DatagramPacket(data, data.length, local, 9999);
            Log.d(TAG, "on udp send :" + " dataLen = " + data.length + "   count = " + outCount++ + " ---- >>>>>  ip = " + local.getHostAddress() + "   type = " + type);
        } else if (2 == type || 3 == type) { // 起呼、止呼
            outPacket = new DatagramPacket(data, data.length, localAudio, 9966);
            Log.d(TAG, "on udp send :" + " dataLen = " + data.length + "   count = " + outCount++ + " ---- >>>>>  ip = " + localAudio.getHostAddress() + "   type = " + type);
            Log.d(TAG, "on udp send :" + " dataLen =  " + data.length + "\n" + "data : " + Arrays.toString(data));
        }
        try {
            datagramSocket.send(outPacket);
        } catch (IOException e) {
            if (null != e && e.toString().contains("")) {
                Log.d(TAG, "sendUdp failed : Network is unreachable !");
            } else {
                Log.e(TAG, "sendUdp ERROR : " + e);
                e.printStackTrace();
            }
        }
    }

    class UdpSendThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "UdpSendThread running ------------------ ");
            RTPPackage rtpPackage;
            byte[] rtpData = null;
            byte[] rtpHeardData;
            while (true) {
                try {
                    rtpPackage = blockingDeque.takeFirst();
                    Log.d(TAG, "on udp send : " + rtpPackage.getSeq());
                    if (null != rtpPackage) {
                        if (0 == rtpPackage.getType()) {
                            rtpHeardData = Jni.getHeaderBytes(rtpPackage.getCallType(),
                                    rtpPackage.getTimestamp(),
                                    rtpPackage.getSeq(),
                                    rtpPackage.getSsrc(),
                                    rtpPackage.getLen(),
                                    rtpPackage.getUserId(),
                                    rtpPackage.getTargetId());

                            Log.d(TAG, "on udp send :" + " heardLen " + rtpHeardData.length + "   payloadLen ： " + rtpPackage.getOpusData().length);
                            sendUdp(ByteUtil.byteMerger(rtpData, rtpHeardData, rtpPackage.getOpusData()), 0);
                        } else if (1 == rtpPackage.getType()) {
                            rtpData = Jni.getHeartBytes(rtpPackage.isNeedRsp(), rtpPackage.getUserId());
                            Log.d(TAG, "on udp send :" + " heart with  isNeedRsp : " + rtpPackage.isNeedRsp());
                            sendUdp(rtpData, 1);
                        } else if (2 == rtpPackage.getType()) {
                            rtpHeardData = Jni.getHeaderBytes(rtpPackage.getCallType(),
                                    rtpPackage.getTimestamp(),
                                    rtpPackage.getSeq(),
                                    rtpPackage.getSsrc(),
                                    rtpPackage.getLen(),
                                    rtpPackage.getUserId(),
                                    rtpPackage.getTargetId());
                            Log.d(TAG, "on udp send :" + " startCall : " + rtpHeardData.length);
                            sendUdp(ByteUtil.byteMerger(rtpData, rtpHeardData, new byte[4]), 2);
                        } else if (3 == rtpPackage.getType()) {
                            rtpHeardData = Jni.getHeaderBytes(rtpPackage.getCallType(),
                                    rtpPackage.getTimestamp(),
                                    rtpPackage.getSeq(),
                                    rtpPackage.getSsrc(),
                                    rtpPackage.getLen(),
                                    rtpPackage.getUserId(),
                                    rtpPackage.getTargetId());
                            Log.d(TAG, "on udp send :" + " endCall : " + rtpHeardData.length);
                            sendUdp(rtpHeardData, 3);
                        }
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "UdpSendThread : ERROR : " + e);
                    e.printStackTrace();
                }
            }
        }
    }

    class UdpReceiveThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "UdpReceiveThread running ----------------- ");
//            try {
//                WifiManager manager = (WifiManager) TalkieApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                manager.createMulticastLock("UDPWifi");
//                inetSocketAddress = new InetSocketAddress(port);
//                inDatagramSocket = new DatagramSocket(port);
//                inDatagramSocket.setBroadcast(true);
//            } catch (SocketException e) {
//                Log.e(TAG, "UdpSendThread : ERROR : " + e);
//                e.printStackTrace();
//            }
            byte[] data;
            while (true) {
                try {
                    datagramSocket.receive(inPacket);

                    System.out.println(" 收到数据 ： length = " + inPacket.getLength());

                    data = new byte[inPacket.getLength()];
                    System.arraycopy(inPacket.getData(), inPacket.getOffset(), data, 0, inPacket.getLength());
                    socketCallback.socketReceive(data);

                } catch (IOException e) {
                    Log.e(TAG, "UdpSendThread : ERROR : " + e);
                    e.printStackTrace();
                }
            }
        }
    }

}
