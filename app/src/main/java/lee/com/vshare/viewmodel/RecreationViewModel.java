package lee.com.vshare.viewmodel;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import lee.com.vshare.ui.fragment.MusicFragment;
import lee.com.vshare.ui.fragment.VideoFragment;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class RecreationViewModel extends AndroidViewModel {

    private final MediatorLiveData<Map<String, Fragment>> mObservableFragments;

    public RecreationViewModel(@NonNull Application application) {
        super(application);

        mObservableFragments = new MediatorLiveData<>();

        mObservableFragments.setValue(null);

        Map<String, Fragment> fragmentsMap = new HashMap<>();
        Fragment fragment0 = MusicFragment.newInstance();
        Fragment fragment1 = MusicFragment.newInstance();
        Fragment fragment2 = VideoFragment.newInstance();
        fragmentsMap.put("Picture", fragment0);
        fragmentsMap.put("Music", fragment1);
        fragmentsMap.put("Video", fragment2);

        MediatorLiveData<Map<String, Fragment>> fragmentList = new MediatorLiveData<>();
        fragmentList.setValue(fragmentsMap);

        mObservableFragments.addSource(fragmentList, mObservableFragments::setValue);
    }

    public MediatorLiveData<Map<String, Fragment>> getFragments() {
        return mObservableFragments;
    }
}
