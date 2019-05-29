package lee.vshare.netty.callback;

/**
 * Created by Lee on 2019/5/26.
 */

public interface NettyUdpCallback {

    void receiveUdp(byte[] bytes);

}
