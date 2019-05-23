/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lee.vshare;

import android.app.Application;
import android.net.TrafficStats;

import com.lee.vshare.model.db.AppDatabase;
import com.lee.vshare.test.NetObserver;

import lee.com.netlibrary.utils.ApiConfig;

import static com.lee.vshare.model.net.NetConfig.BASE_URL_TEST;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application {

    private AppExecutors mAppExecutors;
    private static BasicApp basicApp;

    @Override
    public void onCreate() {
        super.onCreate();

        basicApp = this;
        mAppExecutors = new AppExecutors();

        //初始化网络请求模块
        ApiConfig build = new ApiConfig.Builder()
                .setBaseUrl(BASE_URL_TEST)//BaseUrl，这个地方加入后项目中默认使用该url
                .setInvalidateToken(0)//Token失效码
                .setSucceedCode(0)//成功返回码  NBA的测试返回成功code为0  上传图片返回code为200 由于是不同接口 请大家注意
                .setFilter("com.mp5a5.quit.broadcastFilter")//失效广播Filter设置
                //.setDefaultTimeout(2000)//响应时间，可以不设置，默认为2000毫秒
//                .setHeads(headMap)//动态添加的header，也可以在其他地方通过ApiConfig.setHeads()设置
                //.setOpenHttps(true)//开启HTTPS验证
                //.setSslSocketConfigure(sslSocketConfigure)//HTTPS认证配置
                .build();
        build.init(this);
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

    public static BasicApp getInstance() {
        return basicApp;
    }

}
