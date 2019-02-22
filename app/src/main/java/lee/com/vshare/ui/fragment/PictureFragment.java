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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import lee.com.vshare.BasicApp;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentPictureBinding;
import lee.com.vshare.design.GridSpacingItemDecoration;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Picture;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.PictureAdapter;
import lee.com.vshare.viewmodel.PictureViewModel;

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
