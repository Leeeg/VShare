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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lee.com.vshare.R;
import lee.com.vshare.config.GlideImageLoader;
import lee.com.vshare.databinding.FragmentVideoBinding;
import lee.com.vshare.databinding.FragmentVideoBinding;
import lee.com.vshare.design.GridSpacingItemDecoration;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Video;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.VideoAdapter;
import lee.com.vshare.ui.adapter.VideoAdapter;
import lee.com.vshare.viewmodel.VideoViewModel;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class VideoFragment extends BaseFragment {

    public static final String TAG = "VideoFragment";

    private FragmentVideoBinding mBinding;
    private VideoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);

        mAdapter = new VideoAdapter(itemClickListener);
        mBinding.videoRecycle.setAdapter(mAdapter);
        mBinding.videoRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mBinding.videoRecycle.setHasFixedSize(true);
        mBinding.videoRecycle.setNestedScrollingEnabled(false);
        mBinding.videoRecycle.addItemDecoration(new GridSpacingItemDecoration(2, 21, true));

        mBinding.videoBanner.setImageLoader(new GlideImageLoader());
        mBinding.videoBanner.setDelayTime(5000);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final VideoViewModel viewModel = ViewModelProviders.of(this).get(VideoViewModel.class);

        subscribeBanner(viewModel.getBanner());
        subscribeUi(viewModel.getVideo());

    }

    private void subscribeBanner(LiveData<List<Integer>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (imageList) -> {
            if (imageList != null) {
                mBinding.videoBanner.setImages(imageList);
                mBinding.videoBanner.start();
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private void subscribeUi(LiveData<List<Video>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (recreation) -> {
            if (recreation != null) {
                mAdapter.setVideoList(recreation);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static VideoFragment newInstance() {
        final VideoFragment fragment = new VideoFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private final ItemClickListener<Video> itemClickListener = (recreation) -> Log.d(TAG, "id = " + recreation.getVideoId());

    @Override
    public void onResume() {
        super.onResume();
        mBinding.videoBanner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.videoBanner.stopAutoPlay();
    }
}
