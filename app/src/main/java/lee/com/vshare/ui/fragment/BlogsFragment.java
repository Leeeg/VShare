package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentBlogsBinding;
import lee.com.vshare.design.taglayout.FlowLayout;
import lee.com.vshare.design.taglayout.TagAdapter;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Blogs;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.BlogsAdapter;
import lee.com.vshare.viewmodel.BlogViewModel;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class BlogsFragment extends BaseFragment {

    public static final String TAG = "BlogsFragment";

    private FragmentBlogsBinding mBinding;
    private BlogsAdapter mAdapter;

    private BlogViewModel viewModel;

    private String[] tags = {"Java", "Android", " C ", "C++", "Object C", "Kotlin", "PHP", "Java Script"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blogs, container, false);

        mBinding.blogsTags.setAdapter(new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) inflater.inflate(R.layout.tv_tag, mBinding.blogsTags, false);
                tv.setText(s);
                return tv;
            }
        });
        mBinding.blogsTags.setOnSelectListener(selectPosSet -> Log.d(TAG, "choose:" + selectPosSet.toString()));

        mAdapter = new BlogsAdapter(itemClickListener);
        mBinding.blogsRecycle.setAdapter(mAdapter);
        mBinding.blogsRecycle.setHasFixedSize(true);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BlogViewModel.class);

        subscribeUi(viewModel.getBlog());
    }

    private void subscribeUi(LiveData<List<Blogs>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (blogList) -> {
            if (blogList != null) {
                mAdapter.setBlogsList(blogList);
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

    private final ItemClickListener<Blogs> itemClickListener = (blogs) -> {
        Log.d("Lee_Blog", "id = " + blogs.getBlogId());
    };

}
