package com.lee.vshare.util;

import android.os.Environment;
import android.util.Log;
import android.util.Property;

import com.lee.versioncheck.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

/**
 * CreateDate：19-3-21 on 下午2:58
 * Describe:
 * Coder: lee
 */
public class PropertyUtils {

    private static final String TAG = "Lee_PropertyUtils";
    private static final String SYMBOL = "123456#";

    public static void createProperty() {
        Properties p = new Properties();
        p.setProperty("name", "tinyfun");
        p.setProperty("age", "25");
        p.setProperty("sex", "man");
        p.setProperty("title", "software developer");

        try {

            File file = new File(getSDPath(), "PropertiesTest");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(SYMBOL.getBytes());
            fos.close();

            PrintStream fW = new PrintStream(file);
            p.list(fW);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, " e ： " + e);
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        String path = sdDir.toString();
        Log.d(TAG, "path = " + path);
        return path;
    }
}
