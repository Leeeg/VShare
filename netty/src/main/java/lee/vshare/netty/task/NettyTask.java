package lee.vshare.netty.task;


import android.util.Log;

import io.netty.channel.Channel;
import lee.vshare.netty.callback.NettyMsgCallback;
import lee.vshare.netty.protobuf.NettyMessage;

/**
 * Created by Lee on 2019/5/24.
 */

public class NettyTask {

    private static final String TAG = "NettyTask";

    private NettyMsgCallback msgCallback;

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

    public boolean isClientAlive(){
        return clientChannel == null? false : (clientChannel.isOpen() && clientChannel.isActive());
    }

    public void releaseChannel(){
        clientChannel.close();
        udpChannel.close();
    }

    public void sendNettyMsg(NettyMessage.NettyMsg nettyMsg) {
        if (isClientAlive()) {
            Log.d(TAG, "sendNettyMsg : " + nettyMsg.toString());
            clientChannel.writeAndFlush(nettyMsg);
            callbackMessage("发送消息");
        }
    }

    public void sendNettyTextMsg(String textMsg){
        if (isClientAlive()) {
            Log.d(TAG, "sendNettyTextMsg : " + textMsg);
            clientChannel.writeAndFlush(textMsg);
        }
    }

    public void callbackMessage(String message){
        if (null != msgCallback){
            msgCallback.receiveMsg(message);
        }
    }

}
