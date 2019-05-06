package com.lee.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.ui.activity.LoginActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.lee.vshare.R;
import com.lee.vshare.databinding.FragmentRegisterBinding;


/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class RegisterFragment extends com.lee.vshare.ui.BaseFragment {

    public static final String TAG = "RegisterFragment";

    private FragmentRegisterBinding mBinding;

    public static RegisterFragment newInstance() {
        final RegisterFragment fragment = new RegisterFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        mBinding.setRegisterPresenter(registerPresenter);

        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity)getActivity()).setTitle(LoginActivity.TITLE_REGISTER);
    }

    private final com.lee.vshare.ui.presenter.RegisterPresenter registerPresenter = new com.lee.vshare.ui.presenter.RegisterPresenter() {
        @Override
        public void onNextClick() {
            ((LoginActivity)getActivity()).showSignUp();
        }
    };

}
