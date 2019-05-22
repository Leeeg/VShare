package com.lee.vsahre.speex;

import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ma.xuanwei on 2017/3/7.
 */

public class NativeLib {

    private AtomicBoolean isRecording = new AtomicBoolean(false);
    private AtomicBoolean isPlaying = new AtomicBoolean(false);
    private AtomicBoolean isRecordAndPlay = new AtomicBoolean(false);
    private BlockingDeque<byte[]> blockingDeque = new LinkedBlockingDeque<>();

    static {
        System.loadLibrary("native");
    }

    public void setIsRecording(boolean v) {
        isRecording.set(v);
        LogUtils.DEBUG("setIsRecording " + v);
    }

    public void setIsRecordingAndPlaying(boolean v) {
        isRecordAndPlay.set(v);
        LogUtils.DEBUG("setIsRecordingAndPlaying " + v);
    }

    public boolean isRecording() {
        return isRecording.get();
    }

    public void setIsPlaying(boolean b) {
        isPlaying.set(b);
    }

    public boolean isPlaying() {
        return isPlaying.get();
    }

    public boolean isRecordingAndPlaying() {
        return isRecordAndPlay.get();
    }

    public void onDataCallback(byte[] data) {
        Log.d("onDataCallback", "data : " + (data == null ? "null" : Arrays.toString(data)));
        blockingDeque.offer(data.clone());
        int count = blockingDeque.size();
        Log.d("onDataCallback", "count = " + count);
        if (count == 300) {
            stopRecord();
            for (int i = 0; i < count; i++) {
                byte[] speex = blockingDeque.poll();
                if (null != speex) {
                    Log.d("onDataCallback", "speex = " + speex.length);
                    if (speex.length > 0) {
                        Log.d("onDataCallback", "speex = " + Arrays.toString(speex));
                        startTrack(speex);
                    }
                }
            }
        }
    }

    public void onDataCallback(int data) {
        Log.d("onDataCallback", "data : " + data);
    }

    public native void startRecording(int sampleRate, int period, int channels, String path);

    public native void stopRecording();

    public native void playRecording(int sampleRate, int period, int channels, String path);

    public native void stopPlaying();

    public native int encode(String pcm, String speex);

    public native int decode(String speex, String pcm);

    public native int recordAndPlayPCM(boolean enableProcess, boolean enableEchoCancel);

    public native int stopRecordingAndPlaying();

    public native int initJni();

    public native short[] startRecord();

    public native void stopRecord();

    public native void startTrack(byte[] data);

    public native void stopTrack();

    public native void release();

}
