
package lee.vshare.netty.client;

import android.util.Log;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lee.vshare.netty.protobuf.UserInfo;

/**
 * @Title: NettyClientHandler
 * @Description: 客户端业务逻辑实现
 * @Version:1.0.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final String TAG = "NettyClientHandler";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.d(TAG, "建立连接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.d(TAG, "断开连接");
    }

    /**
     * 心跳请求处理
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        Log.d(TAG, "没有交互  检测心跳");
        Channel channel = ctx.channel();
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) { // 如果写通道处于空闲状态,就发送心跳命令
                UserInfo.UserMsg.Builder userState = UserInfo.UserMsg.newBuilder().setState(2);
                ctx.channel().writeAndFlush(userState);
                Log.d(TAG, "ping >>> ");
//                channel.writeAndFlush("ping.....");
            }
        }
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Log.d(TAG, "收到消息");
        // 如果不是protobuf类型的数据
        if (!(msg instanceof UserInfo.UserMsg)) {
            Log.d(TAG, "未知类型消息 ： " + msg);
            return;
        }
        try {
            // 得到protobuf的数据
            UserInfo.UserMsg userMsg = (UserInfo.UserMsg) msg;
            // 进行相应的业务处理。。。
            // 这里就从简了，只是打印而已
            System.out.println(
                    "客户端接受到的用户信息。编号:" + userMsg.getId() + ",姓名:" + userMsg.getName() + ",年龄:" + userMsg.getAge());

            // 这里返回一个已经接受到数据的状态
            UserInfo.UserMsg.Builder userState = UserInfo.UserMsg.newBuilder().setState(1);
            ctx.writeAndFlush(userState);
            System.out.println("成功发送给服务端!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}

