package lee.com.vshare.network;

import android.graphics.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * CreateDate：19-3-27 on 下午8:13
 * Describe: https://www.jianshu.com/p/0ad99e598dba
 * Coder: lee
 */
public class MovieLoader extends ObjectLoader {
    private MovieService mMovieService;

    public MovieLoader() {
        mMovieService = RetrofitServiceManager.getInstance().create(MovieService.class);
    }

    /**
     * 获取电影列表
     *
     * @param start
     * @param count
     * @return
     */
    public Observable<List<Movie>> getMovie(int start, int count) {
        return observe(mMovieService.getTop250(start, count)).map(new Func1<MovieSubject, List<Movie>>() {
            @Override
            public List<Movie> call(MovieSubject movieSubject) {
                return movieSubject.subjects;
            }
        });
    }

    public Observable<String> getWeatherList(String cityId, String key) {
        return observe(mMovieService.getWeather(cityId, key)).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                //可以处理对应的逻辑后在返回
                return s;
            }
        });
    }

    public interface MovieService {
        //获取豆瓣Top250 榜单
        @GET("top250")
        Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);

        @FormUrlEncoded
        @POST("/x3/weather")
        Call<String> getWeather(@Field("cityId") String cityId, @Field("key") String key);
    }
}

/**
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 */
class ObjectLoader {
    /**
     * @param observable
     * @param <T>
     * @return
     */
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

