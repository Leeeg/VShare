package lee.vshare.common;

import android.os.Looper;

/**
 * CreateDate：19-5-25 on 上午10:15
 * Describe:
 * Coder: lee
 */
public class AppUtil {

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }

}
