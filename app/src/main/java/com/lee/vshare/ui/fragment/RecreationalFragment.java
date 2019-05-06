package com.lee.vshare.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.viewmodel.RecreationViewModel;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import com.lee.vshare.R;
import com.lee.vshare.databinding.FragmentRecreationBinding;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class RecreationalFragment extends com.lee.vshare.ui.BaseFragment {

    public static final String TAG = "RecreationalFragment";

    private FragmentRecreationBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recreation, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecreationViewModel viewModel = ViewModelProviders.of(this).get(RecreationViewModel.class);

        subscribeUi(viewModel.getFragments());
    }

    private void subscribeUi(LiveData<Map<String, Fragment>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (fragments) -> {
            if (fragments != null) {
                Log.d("Lee_Music", "setMusicList");
                initPage(fragments);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private void initPage(Map<String, Fragment> fragmentStringMap) {
        
        mBinding.viewpager.setAdapter(new com.lee.vshare.ui.adapter.ViewPagerAdapter(getChildFragmentManager(),
                new ArrayList<Fragment>(fragmentStringMap.values()),
                new ArrayList<String>(fragmentStringMap.keySet())));
        mBinding.tab.setupWithViewPager(mBinding.viewpager);
        mBinding.viewpager.setOffscreenPageLimit(2);
    }

    public static RecreationalFragment newInstance() {
        final RecreationalFragment fragment = new RecreationalFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
