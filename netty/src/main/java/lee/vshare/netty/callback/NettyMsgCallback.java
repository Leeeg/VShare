package lee.vshare.netty.callback;

/**
 * Created by Lee on 2019/5/26.
 */

public interface NettyMsgCallback {

    String TAG = "NettyMsgCallback";

    void receiveMsg(String message);

}
