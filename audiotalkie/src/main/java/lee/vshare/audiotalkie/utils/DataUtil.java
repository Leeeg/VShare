package lee.vshare.audiotalkie.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

import lee.vshare.audiotalkie.model.RTPPackage;


/**
 * CreateDate：18-10-19 on 下午2:44
 * Describe:
 * Coder: lee
 */
public class DataUtil {

    private final static String TAG = "Lee_log_DataUtil";

    /**
     * ------------------------------------------------------- 协议用 -----------------------------------------------------
     */

    public static int randomSequenceNumber() {
        return new Random().nextInt(10);
    }

    public static RTPPackage.RTPPackageBase getRTPBase(RTPPackage.RTPPackageBase packageBases, byte[] b) {
        if (null == b || b.length < 4) {
            Log.e(TAG, "getSequenceNumber on a bad byteArray ! ");
            return null;
        }
        return null;
    }

    public static short getSequenceNumber(byte[] b) {
        if (null == b || b.length < 4) {
            Log.e(TAG, "getSequenceNumber on a bad byteArray ! ");
            return 0;
        }
        return ByteUtil.byteToShort(Arrays.copyOfRange(b, 2, 4));

    }

    public static int getSsrc(byte[] b) {
        if (null == b || b.length < 12) {
            Log.e(TAG, "getSsrc on a bad byteArray ! ");
            return 0;
        }
        return ByteUtil.byteToInt(Arrays.copyOfRange(b, 8, 12));

    }

    public static short getCallType(byte[] b) {
        if (null == b || b.length < 18) {
            Log.e(TAG, "getLength on a bad byteArray ! ");
            return 0;
        }
        return ByteUtil.byteToShort(Arrays.copyOfRange(b, 16, 18));

    }

    public static short getLength(byte[] b) {
        if (null == b || b.length < 20) {
            Log.e(TAG, "getLength on a bad byteArray ! ");
            return 0;
        }
        return ByteUtil.byteToShort(Arrays.copyOfRange(b, 18, 20));

    }

    public static long getUserId(byte[] b) {
        if (null == b || b.length < 28) {
            Log.e(TAG, "getUserId on a bad byteArray !");
            return 0;
        }
        return ByteUtil.byteToLong(Arrays.copyOfRange(b, 20, 28));
    }

    public static long getTargetId(byte[] b) {
        if (null == b || b.length < 36) {
            Log.e(TAG, "getTargetId on a bad byteArray !");
            return 0;
        }
        return ByteUtil.byteToLong(Arrays.copyOfRange(b, 28, 36));
    }

    public static int getOnline(byte[] b) {
        if (null == b || b.length < 38) {
            Log.e(TAG, "getOnline on a bad byteArray !");
            return 0;
        }

        int i = (((b[37] << 8) | b[36]));
//        System.out.println("i    " + i);
//        System.out.println("getOnline   (i | 0x000000FF)    " + (i & 0x000000FF));

        return (i & 0x000000FF);
    }

    public static int getMember(byte[] b) {
        if (null == b || b.length < 40) {
            Log.e(TAG, "getMember on a bad byteArray !");
            return 0;
        }
        int i = (((b[39] << 8) | b[38]));
//        System.out.println("i    " + i);
//        System.out.println("getMember   (i | 0x000000FF)    " + (i & 0x000000FF));
        return (i & 0x000000FF);
    }

    public static boolean getOpusData(byte[] opusBytes, byte[] b, int len) {
        if (null == b || b.length < len) {
            Log.e(TAG, "getOpusData on a bad byteArray !");
            return false;
        }
        System.arraycopy(b, b.length - len, opusBytes, 0, len);
        return true;
    }

    public static long checkIp(byte[] data) {
        long ip = 0x0;
        if (data[1] == 0xFF && data[6] == 0xFF) {
            ip |= data[2];
            ip <<= 8;
            ip |= data[3];
            ip <<= 8;
            ip |= data[4];
            ip <<= 8;
            ip |= data[5];
            return ip;
        } else {
            return -1;
        }
    }

    public static boolean isMessage(byte[] bytes) {
        String msg = new String(bytes);
        if (null != msg && !msg.isEmpty() && msg.contains("meg:")) {
            return true;
        }
        return false;
    }

    public static int getMessageKey(String msg) {
        int key = 0;
        try {
            if (msg.contains("talkback")){
                key = 22;
            }else {
                key = Integer.parseInt(msg.substring(4, 6));
                Log.d(TAG, "getMessageKey ： key = " + key);
            }
        }catch (Exception e){
            Log.e(TAG, "getMessageKey ： ERROR : " + e);
        }
        return key;
    }


    public static byte[] getBytes(int online, int groupNumber){
        byte[] req = new byte[40];
        req[36] = (byte) (online & 0x000000FF);
        req[37] = (byte) ((online >> 8) & 0x000000FF);
        req[38] = (byte) (groupNumber & 0x000000FF);
        req[39] = (byte) ((groupNumber >> 8) & 0x000000FF);
        System.out.println("req[36]    " + req[36]);
        System.out.println("req    " + Arrays.toString(req));
        System.out.println("online & 0x000000FF  =  " + (online & 0x000000FF));
        System.out.println("((online >> 8) & 0x000000FF)  =  " + ((online >> 8) & 0x000000FF));
        return req;
    }

    public static String[] getParms(String msg) {
        return msg.split("\\*");
    }

//    public static void main(String[] args) {
//
////        System.err.println(getMessageKey("meg:03*1020180405090001534*临时对讲"));
//
////        getOnline(getBytes(187, 234));
//        getMember(getBytes(187, 128));
//
//    }

}
