package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentBlogsBinding;
import lee.com.vshare.listener.BlogsItemClickListener;
import lee.com.vshare.model.ex.Blogs;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.BlogsAdapter;
import lee.com.vshare.viewmodel.BlogsViewModel;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class BlogsFragment extends BaseFragment{

    public static final String TAG = "BlogsFragment";

    private FragmentBlogsBinding mBinding;
    private BlogsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blogs, container, false);

        mAdapter = new BlogsAdapter(blogsItemClickListener);
        mBinding.blogsRecycle.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final BlogsViewModel viewModel = ViewModelProviders.of(this).get(BlogsViewModel.class);

        subscribeUi(viewModel.getBlogs());
    }

    private void subscribeUi(LiveData<List<Blogs>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (blogsList) -> {
            if (blogsList != null) {
                Log.d("Lee_Blog", "setLoginHistoryList");
                mAdapter.setBlogsList(blogsList);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static BlogsFragment newInstance() {
        final BlogsFragment fragment = new BlogsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private final BlogsItemClickListener blogsItemClickListener = (Blogs blogs) ->{
        Log.d("Lee_Blog", "id = " + blogs.getBlogId());
    };

}
