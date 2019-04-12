package lee.com.vshare.model.network.service;

import android.os.Build;
import android.util.ArrayMap;


import java.util.Map;

import androidx.annotation.RequiresApi;
import chat.ctyon.com.netlibrary.use.RetrofitFactory;
import io.reactivex.Observable;
import lee.com.vshare.model.network.api.Api;
import lee.com.vshare.model.network.entity.BlogEntity;

/**
 * @describe
 */
public class NetService {

    private Api api;

    private NetService() {
        api = RetrofitFactory.getInstance().create(Api.class);
    }

    public static NetService getInstance() {
        return Service1Holder.S_INSTANCE;
    }

    private static class Service1Holder {
        private static final NetService S_INSTANCE = new NetService();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Observable<BlogEntity> getNBAInfo(String key) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("key", key);
        return api.getBlog(map);
    }

}
