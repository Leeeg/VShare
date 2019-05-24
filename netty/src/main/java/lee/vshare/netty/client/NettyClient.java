package lee.vshare.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author pancm
 * @Title: NettyClient
 * @Description: Netty客户端 心跳测试
 * @Version:1.0.0
 * @date 2017年10月8日
 */
public class NettyClient {

    private String host = "192.168.0.21"; // ip地址
    private static final int port = 9090;
    private static final int udpPort = 9091;
    // 通过nio方式来接收连接和处理连接

    private Channel udpChannel;
    private Channel clientChannel;

    private EventLoopGroup clientGoup = new NioEventLoopGroup();
    EventLoopGroup udpGroup = new NioEventLoopGroup(4);
    Bootstrap clientBootstrap = new Bootstrap();
    Bootstrap udpBootstrap = new Bootstrap();

    /**
     * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     **/
    public void init() {
        try {
            clientBootstrap
                    .group(clientGoup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true) // 不延迟，直接发送
                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持长连接状态
                    .handler(new NettyClientFilter());

            udpBootstrap.group(udpGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_SNDBUF, 1024) //设置发送数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 1024) //设置接受数据缓冲大小
                    .handler(new NettyUDPHandler());


            ChannelFuture udpChannelFuture = udpBootstrap.bind(9098).sync();// 服务器绑定端口监听
            udpChannel = udpChannelFuture.channel();

            ChannelFuture channelFuture = clientBootstrap.connect(host, port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        //连接成功
                        clientChannel = channelFuture.channel();
                    } else {
                        //连接失败
                    }
                }
            }).sync();

            udpChannel.closeFuture().sync();
            clientChannel.closeFuture().sync();

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            System.out.println("客户端连接失败!" + e.getMessage());
        }finally {

        }
    }

}
