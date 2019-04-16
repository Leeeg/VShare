package lee.com.vshare.model.net.service;

import android.util.ArrayMap;

import java.util.Map;

import chat.ctyon.com.netlibrary.use.RetrofitFactory;
import io.reactivex.Observable;
import lee.com.vshare.model.net.api.Api;
import lee.com.vshare.model.net.entity.BlogEntity;

import static lee.com.vshare.model.net.AppKey.KEY_WEATHR;

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

    public Observable<BlogEntity> getWeather(String cityName) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("cityname", cityName);
        map.put("key", KEY_WEATHR);
        return api.getWeather(map);
    }

    public Observable<BlogEntity> getBlog() {
        return api.getBlog();
    }
}
