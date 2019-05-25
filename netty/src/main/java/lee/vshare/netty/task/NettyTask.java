package lee.vshare.netty.task;


import android.util.Log;

import io.netty.channel.Channel;
import lee.vshare.common.AppUtil;
import lee.vshare.netty.protobuf.NettyMessage;

/**
 * Created by Lee on 2019/5/24.
 */

public class NettyTask {

    private static final String TAG = "NettyTask";

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

    public void releaseChannel(){
        clientChannel.close();
        udpChannel.close();
    }

    public void sendNettyMsg(NettyMessage.NettyMsg nettyMsg) {
        if (null != clientChannel) {
            Log.d(TAG, "sendNettyMsg : " + nettyMsg.toString());
            clientChannel.writeAndFlush(nettyMsg);
        }
    }


}
