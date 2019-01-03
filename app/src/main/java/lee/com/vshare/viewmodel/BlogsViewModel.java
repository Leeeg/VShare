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
public class BlogsViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Blogs>> mObservableBlogs;

    public BlogsViewModel(@NonNull Application application) {
        super(application);

        mObservableBlogs = new MediatorLiveData<>();

        mObservableBlogs.setValue(null);

        List<Blogs> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BlogsModel blogs = new BlogsModel();
            blogs.setBlogId(i);
            blogs.setBlogTitle("Blogs : " + i);
            list.add(blogs);
        }

        MediatorLiveData<List<Blogs>> blogsList = new MediatorLiveData<>();
        blogsList.setValue(list);

        mObservableBlogs.addSource(blogsList, mObservableBlogs::setValue);
    }

    public MediatorLiveData<List<Blogs>> getBlogs() {
        return mObservableBlogs;
    }
}
