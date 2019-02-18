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
import lee.com.vshare.databinding.FragmentMusicBinding;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Music;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.MusicAdapter;
import lee.com.vshare.viewmodel.MusicViewModel;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class MusicFragment extends BaseFragment {

    public static final String TAG = "MusicFragment";

    private FragmentMusicBinding mBinding;
    private MusicAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_music, container, false);

        mAdapter = new MusicAdapter(itemClickListener);
        mBinding.musicRecycle.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MusicViewModel viewModel = ViewModelProviders.of(this).get(MusicViewModel.class);

        subscribeUi(viewModel.getMusic());

    }
    
    private void subscribeUi(LiveData<List<Music>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (recreation) -> {
            if (recreation != null) {
                mAdapter.setMusicList(recreation);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static MusicFragment newInstance() {
        final MusicFragment fragment = new MusicFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    private final ItemClickListener<Music> itemClickListener = (recreation) -> {
        Log.d("Lee_Music", "id = " + recreation.getMusicId());
    };
}
