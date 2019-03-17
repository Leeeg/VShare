package lee.com.vshare.viewmodel;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import lee.com.vshare.model.MusicModel;
import lee.com.vshare.model.ex.Music;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class MusicViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Music>> mObservableMusic;

    public MusicViewModel(@NonNull Application application) {
        super(application);

        mObservableMusic = new MediatorLiveData<>();

        mObservableMusic.setValue(null);

        List<Music> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MusicModel music = new MusicModel();
            music.setMusicId(i);
            music.setMusicTitle("music : " + i);
            music.setDescribe("this is music " + i);
            list.add(music);
        }

        MediatorLiveData<List<Music>> musicList = new MediatorLiveData<>();
        musicList.setValue(list);

        mObservableMusic.addSource(musicList, mObservableMusic::setValue);
    }

    public MediatorLiveData<List<Music>> getMusic() {
        return mObservableMusic;
    }

}
