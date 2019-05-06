package lee.vshare.audiotalkie.audio;

import android.annotation.SuppressLint;
import android.media.AudioRecord;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

import lee.vshare.audiotalkie.Jni;
import lee.vshare.audiotalkie.callBack.RecordDataCallback;
import lee.vshare.audiotalkie.model.RecordConfig;
import lee.vshare.audiotalkie.utils.ByteUtil;

/**
 * CreateDate：18-11-7 on 下午3:01
 * Describe:
 * Coder: lee
 */
@SuppressLint("LongLogTag")
public class RecordThread extends Thread implements BaseAudio {

    private final String TAG = "Lee_RecordThread";

    int fixCount = 0;
    boolean fixAble;

    private AudioRecord audioRecord;
    private int minRecordBufferSize;
    private boolean isRecording = true;
    private boolean isPark = true;
    private short[] recordBuffer;
    private LinkedList<short[]> linkedList;
    private RecordDataCallback recordDataCallback;
    private FileOutputStream fos;
    //    private BufferedOutputStream bos;
//    private DataOutputStream dos;
    private File file;
    private String filePath;
    private AtomicInteger atomicInteger;
    private volatile int count = 0;
    private int missCount = 0;

    public void addRecordDataCallback(RecordDataCallback recordDataCallback) {
        this.recordDataCallback = recordDataCallback;
    }

    public RecordThread(String filePath) {
        this.filePath = filePath;
        init();
    }

    @SuppressLint("NewApi")
    @Override
    public void init() {
        minRecordBufferSize = 960;
        final int recordBufferSize = AudioRecord.getMinBufferSize(RecordConfig.SAMPLE_RATE_INHZ.getValue(), RecordConfig.CHANNEL_IN_CONFIG.getValue(),
                RecordConfig.AUDIO_FORMAT.getValue());
        Log.e(TAG, "AudioRecord minRecordBufferSize = " + recordBufferSize);

        audioRecord = new AudioRecord(
                RecordConfig.MIC.getValue(),
                RecordConfig.SAMPLE_RATE_INHZ.getValue(),
                RecordConfig.CHANNEL_IN_CONFIG.getValue(),
                RecordConfig.AUDIO_FORMAT.getValue(),
                minRecordBufferSize * 4);

        //    STATE_INITIALIZED:1, 初始化成功，等待被使用；    STATE_UNINITIALIZED:0, 初始化失败。
        int recordState = audioRecord.getState();
        Log.e(TAG, "AudioRecord init state = " + recordState);

        recordBuffer = new short[minRecordBufferSize];
        linkedList = new LinkedList<>();

        atomicInteger = new AtomicInteger();

    }

    @Override
    public void begin(String voiceIdentification) {
        Log.d(TAG, "startRecord");
        if (AudioRecord.STATE_INITIALIZED != audioRecord.getState()) {
            Log.e(TAG, "the audioRecord is uninitialized !  state = " + audioRecord.getState());
            return;
        }

        try {
            file = new File(filePath + File.separator + voiceIdentification + ".pcm");
            Log.i(TAG, "生成文件");
            //如果存在，就先删除再创建
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Log.i(TAG, "创建文件");

            fos = new FileOutputStream(file);
        } catch (IOException e) {
            Log.i(TAG, "未能创建");
            throw new IllegalStateException("未能创建" + file.toString());
        }

        if (null != audioRecord) {
            audioRecord.startRecording();
            Log.d(TAG, "startRecording ------------");
        }
        if (isPark) {
            Log.d(TAG, "isPark = " + isPark);
            isPark = false;
            LockSupport.unpark(this);
        }

    }

    @Override
    public void over() {
        Log.d(TAG, "stopRecord");
        if (null != audioRecord)
            audioRecord.stop();

        if (!isPark)
            isPark = true;

        try {
            if (null != fos)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void release() {
        Log.d(TAG, "release");
        if (null != audioRecord) {
            isRecording = false;
            audioRecord.release();
            audioRecord = null;
            destroy();
        }
    }

    @Override
    public void run() {
        Log.d(TAG, "run");
        Log.d(TAG, "RecordThread running ------------");
        count = 0;
        atomicInteger.set(0);
        while (isRecording) {
            Log.d(TAG, "Recording ------------");
            if (isPark) {
                count = 0;
                atomicInteger.set(0);
//                LockSupport.park();
            }
            int bufferReadResult = audioRecord.read(recordBuffer, 0, minRecordBufferSize);
            Log.d(TAG, "bufferReadResult = " + bufferReadResult);
            short[] shorts_pkg;
            short[] encoderShort;
            // 如果读取音频数据没有出现错误
            if (AudioRecord.ERROR_INVALID_OPERATION != bufferReadResult && 0 != bufferReadResult) {
                if (missCount > 0){
                    missCount = 0;
                }
                Log.i(TAG, "recording ---- bufferReadResult = " + bufferReadResult);
                Log.i(TAG, "recording ---- length = " + recordBuffer.length + "\n");
                if (bufferReadResult == minRecordBufferSize) {
                    Log.i(TAG, "recording ---- encode  >>");
                    encoderShort = Jni.opusEncoder(recordBuffer, minRecordBufferSize);
                    if (null != encoderShort) {
                        Log.i(TAG, "recording ---- encode  << \n");
                        shorts_pkg = encoderShort.clone();
                        if (linkedList.size() >= 2) {
                            Log.d(TAG, "linkedList.size() = " + linkedList.size());
                            Log.d(TAG, "shorts_pkg.length = " + shorts_pkg.length);
                            if (null != recordDataCallback) {
                                byte[] bytes = ByteUtil.toByteArray(linkedList.removeFirst());
                                recordDataCallback.recordData(bytes);
                                System.err.println("count++ = " + atomicInteger.addAndGet(1));
                                try {
                                    byte[] pcm = ByteUtil.shortArray2ByteArray(recordBuffer);
                                    fos.write(pcm);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        linkedList.add(shorts_pkg);
                    }
                }
            } else {
                if (missCount++ == 10) {
                    System.err.println("missCount = " + missCount);
                    LockSupport.park();
                }
            }
        }
    }

    public synchronized void fixHeadset() {
        if (null != audioRecord) {
            fixCount = 0;
            fixAble = true;
            audioRecord.startRecording();
            new Thread() {
                @Override
                public void run() {
                    while (fixAble) {
                        int bufferReadResult = audioRecord.read(recordBuffer, 0, minRecordBufferSize);
                        Log.d(TAG, "fixHeadset ： bufferReadResult = " + bufferReadResult);
                        if (fixCount++ > 10) {
                            fixAble = false;
                        }
                    }
                }
            }.start();
            audioRecord.stop();
        }
    }

}
