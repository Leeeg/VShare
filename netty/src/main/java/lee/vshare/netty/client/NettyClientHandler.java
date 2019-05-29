
package lee.vshare.netty.client;

import android.util.Log;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lee.vshare.netty.protobuf.NettyMessage;
import lee.vshare.netty.task.NettyTask;

import static lee.vshare.netty.contain.NMsgContainer.MSG_TALK_APPLY_FAILED;
import static lee.vshare.netty.contain.NMsgContainer.MSG_TALK_APPLY_SUCCESS;
import static lee.vshare.netty.contain.NMsgContainer.MSG_TALK_RELEASE;
import static lee.vshare.netty.contain.NMsgContainer.MSG_USER_BUSINESS;
import static lee.vshare.netty.contain.NMsgContainer.MSG_USER_HEART_BEAT;
import static lee.vshare.netty.contain.NMsgContainer.MSG_USER_MESSAGE_SUCCESS;
import static lee.vshare.netty.contain.NMsgContainer.MSG_USER_TIME_OUT;

/**
 * @Title: NettyClientHandler
 * @Description: 客户端业务逻辑实现
 * @Version:1.0.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final String TAG = "NettyClientHandler";

    /**
     * 服务端与客户端连接建立
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG, "handlerAdded");
        /**
         * 调用channelGroup的writeAndFlush相当于channelGroup中的每个channel都writeAndFlush
         * 先去广播，再将自己加入到channelGroup中
         */
        super.handlerAdded(ctx);
    }

    /**
     * 有连接断开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG, "handlerRemoved");
        NettyTask.getInstance().releaseChannel();
        super.handlerRemoved(ctx);
    }

    /**
     * 连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG, "channelActive");
        super.channelActive(ctx);
    }

    /**
     * 连接关闭
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG, "channelInactive");
        super.channelInactive(ctx);
    }

    /**
     * 超时处理
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        Log.d(TAG, "userEventTriggered");
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) { // 如果读通道处于空闲状态，说明没有接收到心跳命令
                Log.d(TAG, "ping >>> ");
                NettyMessage.NettyMsg pingNettyMsg = NettyMessage.NettyMsg.newBuilder()
                        .setMsgType(MSG_USER_HEART_BEAT)
                        .build();

                NettyTask.getInstance().sendNettyMsg(pingNettyMsg);
            }
        }
        super.channelInactive(ctx);
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d(TAG, "exceptionCaught : 出现异常 " + cause);
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        Log.d(TAG, "收到 " + channel.remoteAddress() + "发来的消息");
        try {
            if (msg instanceof NettyMessage.NettyMsg) {
                NettyMessage.NettyMsg readNettyMsg = (NettyMessage.NettyMsg) msg;
                Log.d(TAG, "内容 " + readNettyMsg.toString());
                int type = readNettyMsg.getMsgType();
                if (type == MSG_USER_HEART_BEAT) {
                    Log.d(TAG, "心跳回复  remoteAddress : " + channel.remoteAddress() + " localAddress : " + channel.localAddress());
                    NettyTask.getInstance().callbackMessage("收到消息");
                } else if (type == MSG_USER_MESSAGE_SUCCESS) {
                    Log.d(TAG, "发送成功");
                } else if (type == MSG_USER_BUSINESS) {
                    Log.d(TAG, "业务消息 : " + readNettyMsg.getMsgType());
                } else if (type == MSG_USER_TIME_OUT) {
                    Log.d(TAG, "Time Out !!!");
                } else if (type == MSG_TALK_APPLY_SUCCESS) {//根据消息中的发送方区分自己主呼还是被呼(主呼则开始录音 被呼则上报地址)
                    Log.d(TAG, "申请话权成功");
                    NettyTask.getInstance().reportAddress();
                } else if (type == MSG_TALK_APPLY_FAILED) {
                    Log.d(TAG, "申请话权失败");
                } else if (type == MSG_TALK_RELEASE) {//根据消息中的发送方区分自己主呼还是被呼
                    Log.d(TAG, "释放话权");
                }
                else {
                    Log.d(TAG, "未知命令");
                }
            } else {
                Log.d(TAG, "无效消息");
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}

