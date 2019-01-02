package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentBlogsBinding;
import lee.com.vshare.ui.BaseFragment;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class BlogsFragment extends BaseFragment {

    public static final String TAG = "BlogsFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentBlogsBinding blogsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        blogsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blogs, container, false);

        return blogsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static BlogsFragment newInstance() {
        final BlogsFragment fragment = new BlogsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


}
