package lee.vshare.netty.client;

import android.util.Log;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lee.vshare.netty.task.NettyTask;

import static lee.vshare.netty.config.Config.NETTY_CLIENT_HOST;
import static lee.vshare.netty.config.Config.NETTY_CLIENT_PORT;

/**
 * @author pancm
 * @Title: NettyClient
 * @Description: Netty客户端 心跳测试
 * @Version:1.0.0
 * @date 2017年10月8日
 */
public class NettyClient {

    private static final String TAG = "NettyClient";

    private Channel clientChannel;
    private Channel udpChannel;

    private EventLoopGroup clientGroup = new NioEventLoopGroup();
    private EventLoopGroup udpGroup = new NioEventLoopGroup(4);
    private Bootstrap clientBootstrap = new Bootstrap();
    private Bootstrap udpBootstrap = new Bootstrap();

    /**
     * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     **/
    public void init() {
        try {

            clientBootstrap
                    .group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true) // 不延迟，直接发送
                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持长连接状态
                    .handler(new NettyClientFilter());

            udpBootstrap.group(udpGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)//支持广播
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_SNDBUF, 1024) //设置发送数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 1024) //设置接受数据缓冲大小
                    .handler(new NettyUDPHandler());

            ChannelFuture future = clientBootstrap.connect(NETTY_CLIENT_HOST, NETTY_CLIENT_PORT)
                    .addListener(channelFuture -> {
                        if (channelFuture.isSuccess()) {
                            Log.d(TAG, "\n[ Netty Client 连接成功 >>>>>>> ]\n");
                        } else {
                            Log.d(TAG, "Netty Client 连接失败");
                        }
                    }).sync();

            Log.d(TAG, "setClientChannel");
            clientChannel = future.channel();
            NettyTask.getInstance().setClientChannel(clientChannel);

            ChannelFuture udpChannelFuture = udpBootstrap.bind(0).sync();
            Log.d(TAG, "\n[ udpChannel >>>>>> ]\n");
            udpChannel = udpChannelFuture.channel();
            NettyTask.getInstance().setUdpChannel(udpChannel);

            Log.d(TAG, "clientChannel closeFuture().sync()");
            clientChannel.closeFuture().sync();
            Log.d(TAG, "udpChannel closeFuture().sync()");
            udpChannel.closeFuture().sync();


        } catch (Exception e) {
            Log.e(TAG, "Netty 连接异常 ：" + e);
        } finally {
            Log.d(TAG, "shutdownGracefully");
            clientGroup.shutdownGracefully();
            udpGroup.shutdownGracefully();
        }
    }

}
