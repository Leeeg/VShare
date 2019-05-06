package lee.vshare.audiotalkie.channel;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CreateDate：18-11-23 on 下午3:39
 * Describe:
 * Coder: lee
 */
public class TalkieChannel {

    private AtomicInteger atomicInteger;

    public static TalkieChannel getInstance() {

        return TalkieChannelHolder.instance;
    }

    private static class TalkieChannelHolder {
        private static final TalkieChannel instance = new TalkieChannel();
    }

    public TalkieChannel() {
        super();
        atomicInteger = new AtomicInteger();
    }

    private boolean isWorking;

    private int callId;

    private long userId;

    private long targetId;

    private volatile short seq;

    private short callType;

    public boolean isWorking() {
        return isWorking;
    }

    public boolean work() {
        isWorking = true;
        return true;
    }

    public int getCallId() {
        return callId;
    }

    public TalkieChannel setCallId(int callId) {
        this.callId = callId;
        return getInstance();
    }

    public long getUserId() {
        return userId;
    }

    public TalkieChannel setUserId(long userId) {
        this.userId = userId;
        return getInstance();
    }

    public long getTargetId() {
        return targetId;
    }

    public TalkieChannel setTargetId(long targetId) {
        this.targetId = targetId;
        return getInstance();
    }

    public short getSeq() {
        int s = atomicInteger.addAndGet(1);
        Log.d("Lee_TalkieChannel", " seq = " + s);
        return (short) s;
    }

    public TalkieChannel reSetSeq() {
        this.seq = 0;
        atomicInteger.set(0);
        return getInstance();
    }

    public short getCallType() {
        return callType;
    }

    public TalkieChannel setCallType(short callType) {
        this.callType = callType;
        return getInstance();
    }

    public void clear() {
        isWorking = false;
        callId = 0;
        seq = 0;
        userId = 0;
        targetId = 0;
        callType = 0;
    }

    @Override
    public String toString() {
        return "TalkieChannel{" +
                "isWorking=" + isWorking +
                ", callId=" + callId +
                ", userId=" + userId +
                ", targetId=" + targetId +
                ", seq=" + seq +
                ", callType=" + callType +
                '}';
    }

}
