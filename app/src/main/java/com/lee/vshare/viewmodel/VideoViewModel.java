package com.lee.vshare.viewmodel;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import com.lee.vshare.R;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class VideoViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<com.lee.vshare.model.ex.Video>> mObservableVideo;
    private final MediatorLiveData<List<Integer>> mObservableBanner;

    public VideoViewModel(@NonNull Application application) {
        super(application);

        mObservableVideo = new MediatorLiveData<>();
        mObservableBanner = new MediatorLiveData<>();

        mObservableVideo.setValue(null);
        mObservableBanner.setValue(null);

        List<com.lee.vshare.model.ex.Video> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            com.lee.vshare.model.VideoModel recreation = new com.lee.vshare.model.VideoModel();
            recreation.setVideoId(i);
            recreation.setVideoTitle("video : " + i);
            recreation.setDescribe("this is the video " + i);
            list.add(recreation);
        }

        Integer[] banners = {
                R.mipmap.banner1,
                R.mipmap.banner2,
                R.mipmap.banner3,
                R.mipmap.banner4,
                R.mipmap.banner5
        };

        MediatorLiveData<List<com.lee.vshare.model.ex.Video>> videoList = new MediatorLiveData<>();
        videoList.setValue(list);
        MediatorLiveData<List<Integer>> bannerList = new MediatorLiveData<>();
        bannerList.setValue(Arrays.asList(banners));

        mObservableVideo.addSource(videoList, mObservableVideo::setValue);
        mObservableBanner.addSource(bannerList, mObservableBanner::setValue);
    }

    public MediatorLiveData<List<com.lee.vshare.model.ex.Video>> getVideo() {
        return mObservableVideo;
    }

    public MediatorLiveData<List<Integer>> getBanner() {
        return mObservableBanner;
    }
}
