package lee.com.vshare.viewmodel;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import lee.com.vshare.model.BlogsModel;
import lee.com.vshare.model.ex.Blogs;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class BlogViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Blogs>> mObservableBlog;

    public BlogViewModel(@NonNull Application application) {
        super(application);

        mObservableBlog = new MediatorLiveData<>();

        mObservableBlog.setValue(null);

        List<Blogs> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BlogsModel blog = new BlogsModel();
            blog.setBlogId(i);
            blog.setBlogTitle("Blogs : " + i);
            blog.setDescribe("this is the Blogs " + i);
            list.add(blog);
        }

        MediatorLiveData<List<Blogs>> blogList = new MediatorLiveData<>();
        blogList.setValue(list);

        mObservableBlog.addSource(blogList, mObservableBlog::setValue);
    }

    public MediatorLiveData<List<Blogs>> getBlog() {
        return mObservableBlog;
    }

}
