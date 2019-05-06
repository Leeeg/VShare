package lee.vshare.audiotalkie.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

/**
 * CreateDate：19-2-25 on 下午8:32
 * Describe:
 * Coder: lee
 */
public class AudioConfig {

    AudioManager audioManager;
    Context context;

    private static final String TAG = "Lee_AudioConfig";

    public AudioConfig(Context context) {
        this.context = context;
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        int i = getHeadsetState();  //刚进来获取状态
        if(i == 1){
            Toast.makeText(context, "有线耳机处于连接状态", Toast.LENGTH_SHORT).show();
        }else if(i == 2){
            Toast.makeText(context, "蓝牙耳机处于连接状态", Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG, " 无耳机连接");

            audioManager.setMode(AudioManager.USE_DEFAULT_STREAM_TYPE);
            audioManager.stopBluetoothSco();
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(true);
//            Toast.makeText(context, "无耳机连接", Toast.LENGTH_SHORT).show();
        }
//        registerHeadsetPlugReceiver();
    }

    /**
     * 获取耳机的连接状态
     * @return 根据返回的int值进行自己的逻辑操作
     */
    public int getHeadsetState(){
        if(audioManager.isWiredHeadsetOn()){  //有限耳机是否连接
            return 1;
        }

        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();  //蓝牙耳机
        if (ba == null){ //若蓝牙耳机无连接
            return -1;
        } else if(ba.isEnabled()) {
            int a2dp = ba.getProfileConnectionState(BluetoothProfile.A2DP);              //可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
            int headset = ba.getProfileConnectionState(BluetoothProfile.HEADSET);        //蓝牙头戴式耳机，支持语音输入输出
            int health = ba.getProfileConnectionState(BluetoothProfile.HEALTH);          //蓝牙穿戴式设备

            //查看是否蓝牙是否连接到三种设备的一种，以此来判断是否处于连接状态还是打开并没有连接的状态
            int flag = -1;
            if (a2dp == BluetoothProfile.STATE_CONNECTED) {
                flag = a2dp;
            } else if (headset == BluetoothProfile.STATE_CONNECTED) {
                flag = headset;
            } else if (health == BluetoothProfile.STATE_CONNECTED) {
                flag = health;
            }
            //说明连接上了三种设备的一种
            if (flag != -1) {
                return 2;
            }
        }
        return -2;
    }

    /**
     * 注册监听
     */
    private void registerHeadsetPlugReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.registerReceiver(headsetPlugReceiver, intentFilter);
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        context.registerReceiver(headsetPlugReceiver, bluetoothFilter);
    }

    private BroadcastReceiver headsetPlugReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if(BluetoothProfile.STATE_DISCONNECTED == adapter.getProfileConnectionState(BluetoothProfile.HEADSET)) {
                    Toast.makeText(context, "蓝牙耳机断开", Toast.LENGTH_SHORT).show();

                    audioManager.setBluetoothScoOn(false);
                    audioManager.setMode(AudioManager.USE_DEFAULT_STREAM_TYPE);
                    audioManager.stopBluetoothSco();
                    audioManager.setSpeakerphoneOn(true);
                }else{
                    Toast.makeText(context, "蓝牙耳机连接", Toast.LENGTH_SHORT).show();

                    audioManager.setBluetoothScoOn(true);
                    audioManager.setMode(AudioManager.STREAM_VOICE_CALL);
                    audioManager.startBluetoothSco();
                    audioManager.setSpeakerphoneOn(false);
                }
            } else if ("android.intent.action.HEADSET_PLUG".equals(action)) {
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        Log.d(TAG, " 有线拔出");
//                        Toast.makeText(context, "有线拔出", Toast.LENGTH_SHORT).show();
                    }else if(intent.getIntExtra("state", 0) == 1){
                        Toast.makeText(context, "有线插入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    };

    public void release() {
        context.unregisterReceiver(headsetPlugReceiver);
    }


}
