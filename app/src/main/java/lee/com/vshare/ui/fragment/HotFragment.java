package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.ui.BaseFragment;

import lee.com.vshare.databinding.FragmentHotBinding;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class HotFragment extends BaseFragment {

    public static final String TAG = "MainFragmentViewModel";

    private FragmentHotBinding hotBinding;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        hotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);

        hotBinding.setIsLoading(true);

        return hotBinding.getRoot();
    }
}
