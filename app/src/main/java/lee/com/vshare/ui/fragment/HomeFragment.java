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

import lee.com.vshare.databinding.FragmentHomeBinding;


/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class HomeFragment extends BaseFragment {

    public static final String TAG = "HomeFragment";

    private  FragmentHomeBinding homeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        homeBinding.setIsLoading(true);

        return homeBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static HomeFragment newInstance() {
        final HomeFragment fragment = new HomeFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
