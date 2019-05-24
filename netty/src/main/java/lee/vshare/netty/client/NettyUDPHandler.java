package lee.vshare.netty.client;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * @Title: NettyServerHandler
 * @Description: 服务端业务逻辑
 * @Version:1.0.0
 */
public class NettyUDPHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String TAG = "NettyUDPHandler";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG,"channelActive");
        super.channelActive(ctx);
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d(TAG,"exceptionCaught :  出现异常 : " + cause);
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 业务逻辑处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String ip = datagramPacket.sender().getAddress().toString();
        Log.d(TAG,"channelRead0 : " + ip);
        ByteBuf byteBuf = datagramPacket.copy().content();
        Log.d(TAG,"byteBuf : " + byteBuf.readableBytes());
    }

}