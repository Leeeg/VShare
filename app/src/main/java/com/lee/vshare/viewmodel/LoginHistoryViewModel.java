package com.lee.vshare.viewmodel;

import android.app.Application;

import com.lee.vshare.BasicApp;
import com.lee.vshare.DataRepository;
import com.lee.vshare.model.db.entity.LoginHistoryEntity;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

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
