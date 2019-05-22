package com.lee.vsahre.speex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mars_ma on 2017/3/16.
 */

public class AudioUtils {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private NativeLib nativeBridge;

    public AudioUtils() {
        nativeBridge = new NativeLib();
    }

    public boolean recordAndPlayPCM(final boolean enable1, final boolean enable2) {
        if (!nativeBridge.isRecordingAndPlaying()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    nativeBridge.recordAndPlayPCM(enable1, enable2);
//                    nativeBridge.initJni();
                }
            });
            return true;
        } else {
            return false;
        }
    }

    public boolean stopRecordAndPlay() {
        if (!nativeBridge.isRecordingAndPlaying()) {
            return false;
        } else {
            nativeBridge.stopRecordingAndPlaying();
            return true;
        }
    }

    public void initSpeex() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nativeBridge.initJni();
            }
        });
    }

    public void startRecord() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nativeBridge.startRecord();
            }
        });
    }

    public void stopRecord() {
        nativeBridge.stopRecord();
    }

    public void startPlay() {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    nativeBridge.startTrack(new String(Common.DEFAULT_PCM_FILE_PATH2.getBytes(),"UTF-8"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void stopPlay() {
        nativeBridge.stopTrack();
    }

    public void release() {
        nativeBridge.release();
    }

}
