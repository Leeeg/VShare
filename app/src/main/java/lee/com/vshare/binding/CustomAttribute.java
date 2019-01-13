package lee.com.vshare.binding;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import lee.com.vshare.R;
import lee.com.vshare.glide.GlideApp;
import lee.com.vshare.util.StringUtils;

/**
 * Created by Lee on 2018/12/31.
 */

@SuppressLint("LongLogTag")
public class CustomAttribute {

    /**
     * 《说明》：
     * 1. 方法名可与注解名一样，也可不一样
     * 2. 第一个参数必须是View，就是自定义属性所在的View
     * 3. 第二个参数就是自定义属性的值，与注解值对应。这是数组，可多个
     */

    //图片加载
    @BindingAdapter({"bind:showImg"})
    public static void showImg(ImageView imageView, Drawable imgRes) {
        GlideApp.with(imageView.getContext()).load(imgRes).error(R.drawable.icon).into(imageView);
    }
    @BindingAdapter({"bind:showImgCircle"})
    public static void showImgCircle(ImageView imageView, Drawable imgRes) {
        GlideApp.with(imageView.getContext()).load(imgRes).error(R.drawable.icon).circleCrop().into(imageView);
    }
    @BindingAdapter({"bind:loadImg"})
    public static void loadImg(ImageView imageView, String imgUrl) {
        GlideApp.with(imageView.getContext()).load(imgUrl).error(R.drawable.icon).into(imageView);
    }
    @BindingAdapter({"bind:loadImgCircle"})
    public static void loadImgCircle(ImageView imageView, String imgUrl) {
        GlideApp.with(imageView.getContext()).load(imgUrl).error(R.drawable.icon).circleCrop().into(imageView);
    }


}
