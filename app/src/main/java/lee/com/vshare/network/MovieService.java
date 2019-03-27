package lee.com.vshare.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * CreateDate：19-3-27 on 下午8:07
 * Describe:
 * Coder: lee
 */
public interface HttpService{
    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("/x3/weather")
    Call<String> getWeather(@Field("cityId") String cityId, @Field("key") String key);
}
