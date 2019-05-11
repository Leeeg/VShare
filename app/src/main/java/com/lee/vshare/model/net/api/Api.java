package com.lee.vshare.model.net.api;

import com.lee.vshare.model.net.entity.BlogEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author ：mp5a5 on 2019/1/16 10：08
 * @describe
 * @email：wwb199055@126.com
 */
public interface Api {

    @GET("weather/index")
    Observable<BlogEntity> getWeather(@QueryMap Map<String, Object> map);


    @GET("notes/getAllNotes")
    Observable<BlogEntity> getBlog();
}