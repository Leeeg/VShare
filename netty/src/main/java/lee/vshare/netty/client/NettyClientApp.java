package lee.vshare.netty.client;

/**
* @Title: NettyClientApp
* @Description: Netty 客户端主程序
* @Version:1.0.0  
*/
public class NettyClientApp {

    public NettyClientApp() {
        NettyClient nettyClient = new NettyClient();
        nettyClient.run();
    }

}
