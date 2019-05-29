package com.lee.vsahre.speex;

import android.util.Log;

import lee.vshare.netty.callback.NettyUdpCallback;
import lee.vshare.netty.task.NettyTask;

/**
 * CreateDate：19-5-27 on 下午1:59
 * Describe:
 * Coder: lee
 */
public class SpeexTask {

    private final String TAG = "SpeexTask";

    private static NativeLib nativeLib;

    private static class SpeexTaskHolder {

        private static final SpeexTask INSTANCE = new SpeexTask();

    }

    public static final SpeexTask getInstance() {

        return SpeexTaskHolder.INSTANCE;

    }

    public void init(){
        nativeLib = new NativeLib();
        nativeLib.initJni();
        nativeLib.setSpeexCallback(speexCallback);
        NettyTask.getInstance().setUdpCallback(nettyUdpCallback);
        Log.d(TAG, "init SpeexTask : " + nativeLib);
    }

    public void release(){
        if (null != nativeLib) {
            nativeLib.release();
        }
    }

    public void startSpeak(){
        Log.d(TAG, "startSpeak : " + nativeLib);
        if (null != nativeLib){
            nativeLib.startRecord();
        }
    }

    public void stopSpeak(){
        Log.d(TAG, "stopSpeak : " + nativeLib);
        if (null != nativeLib){
            nativeLib.stopRecord();
        }
    }

    public void track(byte[] audioData) {
        if (!nativeLib.isRecording()){
            nativeLib.startTrack(audioData);
        } else {
            Log.e(TAG, "is recording !");
        }
    }

    private final SpeexCallback speexCallback = data -> {
        NettyTask.getInstance().sendAudio(data);
    };

    private final NettyUdpCallback nettyUdpCallback = bytes -> {
        track(bytes.clone());
    };

}
