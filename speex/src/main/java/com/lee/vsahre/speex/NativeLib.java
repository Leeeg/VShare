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

    static {
        System.loadLibrary("native");
    }

    private AtomicBoolean isRecording = new AtomicBoolean(false);
    private AtomicBoolean isPlaying = new AtomicBoolean(false);
    private AtomicBoolean isRecordAndPlay = new AtomicBoolean(false);

    private SpeexCallback speexCallback;

    public void setSpeexCallback(SpeexCallback speexCallback) {
        this.speexCallback = speexCallback;
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
        if (null != speexCallback){
            speexCallback.audioData(data);
        }
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
