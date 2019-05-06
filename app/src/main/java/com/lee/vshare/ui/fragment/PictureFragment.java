package com.lee.vshare.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.BasicApp;
import com.lee.vshare.R;
import com.lee.vshare.databinding.FragmentPictureBinding;
import com.lee.vshare.design.GridSpacingItemDecoration;
import com.lee.vshare.listener.ItemClickListener;
import com.lee.vshare.model.ex.Picture;
import com.lee.vshare.ui.BaseFragment;
import com.lee.vshare.ui.adapter.PictureAdapter;
import com.lee.vshare.viewmodel.PictureViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class PictureFragment extends BaseFragment {

    public static final String TAG = "PictureFragment";

    private FragmentPictureBinding mBinding;
    private PictureAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_picture, container, false);

        mAdapter = new PictureAdapter(BasicApp.getInstance().getApplicationContext(), itemClickListener);
        mBinding.pictureRecycle.setAdapter(mAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//去除空白自动滑动填充（空白通过固定寬高处理）
        mBinding.pictureRecycle.addItemDecoration(new GridSpacingItemDecoration(2, 21, true));
        mBinding.pictureRecycle.setLayoutManager(layoutManager);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final PictureViewModel viewModel = ViewModelProviders.of(this).get(PictureViewModel.class);

        subscribeUi(viewModel.getPicture());

    }
    
    private void subscribeUi(LiveData<List<Picture>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (recreation) -> {
            if (recreation != null) {
                mAdapter.setPictureList(recreation);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static PictureFragment newInstance() {
        final PictureFragment fragment = new PictureFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    private final ItemClickListener<Picture> itemClickListener = (recreation) -> {
        Log.d("Lee_Picture", "id = " + recreation.getPictureId());
    };
}
