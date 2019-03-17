package lee.com.vshare.viewmodel;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import lee.com.vshare.model.PictureModel;
import lee.com.vshare.model.ex.Picture;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class PictureViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Picture>> mObservablePicture;

    public PictureViewModel(@NonNull Application application) {
        super(application);

        mObservablePicture = new MediatorLiveData<>();

        mObservablePicture.setValue(null);

        List<Picture> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PictureModel recreation = new PictureModel();
            recreation.setPictureId(i);
            recreation.setPictureTitle("picture : " + i);
            recreation.setDescribe("this is picture " + i);
            list.add(recreation);
        }

        MediatorLiveData<List<Picture>> recreationList = new MediatorLiveData<>();
        recreationList.setValue(list);

        mObservablePicture.addSource(recreationList, mObservablePicture::setValue);
    }

    public MediatorLiveData<List<Picture>> getPicture() {
        return mObservablePicture;
    }

}
