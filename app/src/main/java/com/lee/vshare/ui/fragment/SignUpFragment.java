package com.lee.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.R;
import com.lee.vshare.databinding.FragmentSignupBinding;
import com.lee.vshare.ui.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class SignUpFragment extends BaseFragment {

    public static final String TAG = "SignUpFragment";

    private FragmentSignupBinding mBinding;

    public static SignUpFragment newInstance() {
        final SignUpFragment fragment = new SignUpFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);

        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
