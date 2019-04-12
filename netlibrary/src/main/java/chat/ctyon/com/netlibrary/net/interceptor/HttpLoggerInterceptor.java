package chat.ctyon.com.netlibrary.net.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @describe：
 */
public class HttpLoggerInterceptor {

    public static HttpLoggingInterceptor getLoggerInterceptor() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            Log.e("-->", message.toString());
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

}
