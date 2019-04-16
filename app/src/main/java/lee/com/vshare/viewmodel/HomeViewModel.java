package lee.com.vshare.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import chat.ctyon.com.netlibrary.use.BaseObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lee.com.vshare.model.net.entity.BlogEntity;
import lee.com.vshare.model.net.service.NetService;

/**
 * CreateDate：19-4-13 on 上午11:38
 * Describe:
 * Coder: lee
 */
public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<BlogEntity> blogLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        blogLiveData = new MediatorLiveData<>();

    }

    public MutableLiveData<BlogEntity> getBlogLiveData() {
        return blogLiveData;
    }

    public void getWeather(String cityName) {
        NetService.getInstance().getWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BlogEntity>() {
                    @Override
                    public void onSuccess(BlogEntity blogEntity) {
                        blogLiveData.postValue(blogEntity);
                    }
                });

    }

    public void getBlog() {
        NetService.getInstance().getBlog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BlogEntity>() {
                    @Override
                    public void onSuccess(BlogEntity blogEntity) {
                        blogLiveData.setValue(blogEntity);
                    }
                });

    }
}
