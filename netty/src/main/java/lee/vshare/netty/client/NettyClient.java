package lee.vshare.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
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
//    private static final int port = 9090; // 端口
    private static final int port = 9876;
    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup group = new NioEventLoopGroup();

    private NettyClientFilter nettyClientFilter;

    private SocketChannel socketChannel;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     **/
    public void run() {
        nettyClientFilter = new NettyClientFilter();
        doConnect(new Bootstrap(), group);

    }

    /**
     * 重连
     */
    public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {

        try {
            bootstrap
                .channel(NioSocketChannel.class)
                .group(eventLoopGroup)
                .option(ChannelOption.TCP_NODELAY, true) // 不延迟，直接发送
                .option(ChannelOption.SO_KEEPALIVE, true) // 保持长连接状态
                .handler(nettyClientFilter)
                .connect(new InetSocketAddress(host, port))
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        System.out.println("客户端连接成功");
                        // 连接成功
                        socketChannel = (SocketChannel) future.channel();
                    } else {
                        System.out.println("客户端连接失败 : " + future.toString());
                        // 这里一定要关闭，不然一直重试会引发OOM
                        future.channel().close();
                        group.shutdownGracefully();
                    }
                });
        } catch (Exception e) {
            System.out.println("客户端连接失败!" + e.getMessage());
        }
    }
}
