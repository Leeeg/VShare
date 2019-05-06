package lee.vshare.audiotalkie.model;

import lee.vshare.audiotalkie.utils.ByteUtil;
import lee.vshare.audiotalkie.utils.DataUtil;

/**
 * CreateDate：18-11-22 on 下午2:04
 * Describe:
 * Coder: lee
 */
public class RTPPackage {

    private int type;//0: 语音   1： 心跳

    private int needRsp;

    private long timestamp = System.currentTimeMillis();

    private short seq;

    private int ssrc;

    private short callType;//0: 单呼   1： 群呼

    private short len;

    private long userId;

    private long targetId;

    private byte[] opusData;

    private RTPPackageBase packageBase;

    public int getType() {
        return type;
    }

    public RTPPackage setType(int type) {
        this.type = type;
        return this;
    }

    public int isNeedRsp() {
        return needRsp;
    }

    public RTPPackage setNeedRsp(int needRsp) {
        this.needRsp = needRsp;
        return this;
    }

    public short getSeq() {
        return seq;
    }

    public RTPPackage setSeq(short seq) {
        this.seq = seq;
        return this;
    }

    public int getSsrc() {
        return ssrc;
    }

    public RTPPackage setSsrc(int ssrc) {
        this.ssrc = ssrc;
        return this;
    }

    public short getCallType() {
        return callType;
    }

    public RTPPackage setCallType(short callType) {
        this.callType = callType;
        return this;
    }

    public short getLen() {
        return len;
    }

    public RTPPackage setLen(int len) {
        this.len = (short) len;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public RTPPackage setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getTargetId() {
        return targetId;
    }

    public RTPPackage setTargetId(long targetId) {
        this.targetId = targetId;
        return this;
    }

    public byte[] getOpusData() {
        return opusData;
    }

    public short[] getOpusDataByShort() {
        if (null == opusData) return null;
        return ByteUtil.toShortArray(opusData);
    }

    public RTPPackage setOpusData(byte[] opusData) {
        this.opusData = opusData;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public RTPPackage setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public static class RTPPackageBase{
        private int version;
        private int padding;
        private int csrcCount;
        private int extension;

        private int mark;
        private int playloadType;

        public RTPPackageBase(byte[] bytes) {
            DataUtil.getRTPBase(this, bytes);
        }
    }


    public RTPPackage() {
    }

    public boolean parse(byte[] bytes) {
//        this.packageBase = new RTPPackageBase(bytes);
        this.seq = DataUtil.getSequenceNumber(bytes);
        this.ssrc = DataUtil.getSsrc(bytes);
        this.callType = DataUtil.getCallType(bytes);
        this.len = DataUtil.getLength(bytes);
        this.userId = DataUtil.getUserId(bytes);
        this.targetId = DataUtil.getTargetId(bytes);
        this.opusData = new byte[len];

        System.out.println("bytes --- " + this.toString() + "     bytesLength = " + bytes.length);
//        System.out.println("bytes --- dataLength = " + bytes.length + "    \nbytes : " + Arrays.toString(bytes));

        return DataUtil.getOpusData(this.opusData, bytes, len);
    }

    @Override
    public String toString() {
        return "RTPPackage{" +
                "type=" + type +
                ", needRsp=" + needRsp +
                ", timestamp=" + timestamp +
                ", seq=" + seq +
                ", ssrc=" + ssrc +
                ", callType=" + callType +
                ", len=" + len +
                ", userId=" + userId +
                ", targetId=" + targetId +
//                ", opusData=" + Arrays.toString(opusData) +
                '}';
    }
}
