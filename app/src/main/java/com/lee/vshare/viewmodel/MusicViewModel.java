package com.lee.vshare.viewmodel;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class MusicViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<com.lee.vshare.model.ex.Music>> mObservableMusic;

    public MusicViewModel(@NonNull Application application) {
        super(application);

        mObservableMusic = new MediatorLiveData<>();

        mObservableMusic.setValue(null);

        List<com.lee.vshare.model.ex.Music> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            com.lee.vshare.model.MusicModel music = new com.lee.vshare.model.MusicModel();
            music.setMusicId(i);
            music.setMusicTitle("music : " + i);
            music.setDescribe("this is music " + i);
            list.add(music);
        }

        MediatorLiveData<List<com.lee.vshare.model.ex.Music>> musicList = new MediatorLiveData<>();
        musicList.setValue(list);

        mObservableMusic.addSource(musicList, mObservableMusic::setValue);
    }

    public MediatorLiveData<List<com.lee.vshare.model.ex.Music>> getMusic() {
        return mObservableMusic;
    }

}
