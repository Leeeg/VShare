package com.lee.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.R;
import com.lee.vshare.ui.activity.LoginActivity;
import com.lee.vshare.ui.presenter.RetrievePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.lee.vshare.databinding.FragmentRetrieveBinding;


/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class RetrieveFragment extends com.lee.vshare.ui.BaseFragment {

    public static final String TAG = "RetrieveFragment";

    private FragmentRetrieveBinding mBinding;

    public static RetrieveFragment newInstance() {
        final RetrieveFragment fragment = new RetrieveFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_retrieve, container, false);

        mBinding.setRetrievePresenter(retrievePresenter);

        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity) getActivity()).setTitle(LoginActivity.TITLE_RETRIEVE);
    }

    private final RetrievePresenter retrievePresenter = () -> ((LoginActivity) getActivity()).showResetPasswordFragment();

}
