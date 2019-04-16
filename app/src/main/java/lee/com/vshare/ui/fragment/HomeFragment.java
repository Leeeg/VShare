package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import lee.com.vshare.R;
import lee.com.vshare.model.net.entity.BlogEntity;
import lee.com.vshare.ui.BaseFragment;

import lee.com.vshare.databinding.FragmentHomeBinding;
import lee.com.vshare.ui.presenter.HomePresenter;
import lee.com.vshare.viewmodel.HomeViewModel;


/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class HomeFragment extends BaseFragment {

    public static final String TAG = "HomeFragment";

    private FragmentHomeBinding mBinding;

    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        mBinding.setIsLoading(true);
        mBinding.setHomePresenter(presenter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        subscribeUi(viewModel.getBlogLiveData());
    }

    private void subscribeUi(LiveData<BlogEntity> liveData) {
        liveData.observe(this, blogEntity -> {
            Log.d(TAG, "code = " + blogEntity.code);
//            mBinding.setText(blogEntity.getResult().getToday().toString());
        });
    }

    public static HomeFragment newInstance() {
        final HomeFragment fragment = new HomeFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private final HomePresenter presenter = new HomePresenter() {
        @Override
        public void onTextClick() {
            viewModel.getWeather("深圳");
            viewModel.getBlog();
        }
    };

}
