package lee.vshare.netty.task;


import android.util.Log;

import java.net.InetSocketAddress;
import java.util.Random;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import lee.vshare.netty.callback.NettyMsgCallback;
import lee.vshare.netty.callback.NettyUdpCallback;
import lee.vshare.netty.protobuf.NettyMessage;

import static lee.vshare.netty.config.Config.NETTY_UDP_PORT;
import static lee.vshare.netty.config.Config.NETTY_UDP_SERVER_HOST;
import static lee.vshare.netty.contain.NMsgContainer.MSG_TALK_APPLY;
import static lee.vshare.netty.contain.NMsgContainer.MSG_TALK_RELEASE;

/**
 * Created by Lee on 2019/5/24.
 */

public class NettyTask {

    private static final String TAG = "NettyTask";

    private NettyMsgCallback msgCallback;
    private NettyUdpCallback udpCallback;

    public NettyTask() {
    }

    private static class NettyTaskHolder {

        private static final NettyTask INSTANCE = new NettyTask();

    }

    public static final NettyTask getInstance() {

        return NettyTaskHolder.INSTANCE;

    }

    private Channel clientChannel;
    private Channel udpChannel;

    public void setClientChannel(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public void setUdpChannel(Channel udpChannel) {
        this.udpChannel = udpChannel;
    }

    public void setMsgCallback(NettyMsgCallback msgCallback) {
        this.msgCallback = msgCallback;
    }

    public void setUdpCallback(NettyUdpCallback udpCallback) {
        this.udpCallback = udpCallback;
    }

    public boolean isClientAlive() {
        return clientChannel == null ? false : (clientChannel.isOpen() && clientChannel.isActive());
    }

    public boolean isUdpAlive() {
        return udpChannel == null ? false : (udpChannel.isOpen() && udpChannel.isActive());
    }

    public void releaseChannel() {
        if (null != clientChannel) {
            clientChannel.close();
        }
        if (null != udpChannel) {
            udpChannel.close();
        }
    }

    /**
     * -------------------------------------------------- 普通消息 --------------------------------------
     */

    public void sendNettyMsg(NettyMessage.NettyMsg nettyMsg) {
        if (isClientAlive()) {
            Log.d(TAG, "sendNettyMsg : " + nettyMsg.toString());
            clientChannel.writeAndFlush(nettyMsg);
            callbackMessage("发送消息");
        }
    }

    public void callbackMessage(String message) {
        if (null != msgCallback) {
            msgCallback.receiveMsg(message);
        }
    }

    /**
     * -------------------------------------------------- 语音通话 --------------------------------------
     */
    public void applyTalk() {
        if (isClientAlive()) {
            Log.d(TAG, "applyTalk");
            NettyMessage.NettyMsg talkNettyMsg = NettyMessage.NettyMsg.newBuilder()
                    .setMsgType(MSG_TALK_APPLY)
                    .build();
            clientChannel.writeAndFlush(talkNettyMsg);
        }
    }

    public void reportAddress() {
        if (isUdpAlive()) {
            Log.d(TAG, "reportAddress");
            byte[] bytes = String.valueOf(new Random().nextInt(100)).getBytes();
            DatagramPacket dispensePacket = new DatagramPacket(Unpooled.copiedBuffer(bytes),
                    new InetSocketAddress(NETTY_UDP_SERVER_HOST, NETTY_UDP_PORT));
            udpChannel.writeAndFlush(dispensePacket);
        }
    }

    public void releaseTalk() {
        if (isClientAlive()) {
            Log.d(TAG, "releaseTalk");
            NettyMessage.NettyMsg talkNettyMsg = NettyMessage.NettyMsg.newBuilder()
                    .setMsgType(MSG_TALK_RELEASE)
                    .build();
            clientChannel.writeAndFlush(talkNettyMsg);
        }
    }

    public void sendAudio(byte[] audioData) {
        if (isUdpAlive()) {
            Log.d(TAG, "sendAudio");
            DatagramPacket dispensePacket = new DatagramPacket(Unpooled.copiedBuffer(audioData),
                    new InetSocketAddress(NETTY_UDP_SERVER_HOST, NETTY_UDP_PORT));
            udpChannel.writeAndFlush(dispensePacket);
        }
    }

    public void track(byte[] audioData) {
        if (null != udpCallback) {
            Log.d(TAG, "track");
            udpCallback.receiveUdp(audioData);
        }
    }

}
