package com.lee.vshare.test;

import android.net.TrafficStats;
import android.os.FileObserver;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * CreateDate：19-5-15 on 下午8:12
 * Describe:
 * Coder: lee
 */
public class NetObserver extends FileObserver {


    public NetObserver(String path) {
        super(path, FileObserver.ALL_EVENTS);
        Log.d("Lee_NetObserver", "NetObserver : path = " + path);

        new Thread(){
            @Override
            public void run() {

                while (true){

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    long receive = TrafficStats.getTotalRxBytes();
                    long send = TrafficStats.getTotalTxBytes();
                    Log.d("Lee_NetObserver", "receive =  " + receive + " send = " + send);
                }

            }
        }.start();
    }

    @Override
    public void onEvent(int event, @Nullable String path) {
        Log.d("Lee_NetObserver", "event : " + event + " path : " + path);
    }
}
