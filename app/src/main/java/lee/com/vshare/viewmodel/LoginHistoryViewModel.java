package lee.com.vshare.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import lee.com.vshare.BasicApp;
import lee.com.vshare.DataRepository;
import lee.com.vshare.model.db.entity.LoginHistoryEntity;

/**
 * CreateDate：19-1-11 on 下午4:39
 * Describe:
 * Coder: lee
 */
public class LoginHistoryViewModel extends AndroidViewModel {

    private final DataRepository mRepository;
    private final MediatorLiveData<List<LoginHistoryEntity>> mObservableHistories;

    public LoginHistoryViewModel(Application application) {
        super(application);

        mObservableHistories = new MediatorLiveData<>();

        mObservableHistories.setValue(null);

        mRepository = ((BasicApp) application).getRepository();

        LiveData<List<LoginHistoryEntity>> loginHistories = mRepository.loadLoginHistory();

        // observe the changes of the products from the database and forward them
        mObservableHistories.addSource(loginHistories, mObservableHistories::setValue);
    }

    public MediatorLiveData<List<LoginHistoryEntity>> getmObservableHistories() {
        return mObservableHistories;
    }

}
