package com.lee.vshare.config;


import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by Lee on 2018/12/31.
 */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {

    int diskSize = 1024 * 1024 * 1024;
    String cacheFolderName = "VGlideCache";
    int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(3)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, cacheFolderName, diskSize))
                .setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()))
                .setLogLevel(Log.DEBUG);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
