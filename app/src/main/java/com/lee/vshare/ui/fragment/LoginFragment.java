package com.lee.vshare.ui.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.R;
import com.lee.vshare.binding.bean.LoginBean;
import com.lee.vshare.databinding.FragmentLoginBinding;
import com.lee.vshare.listener.ItemClickListener;
import com.lee.vshare.model.db.entity.LoginHistoryEntity;
import com.lee.vshare.model.db.entity.ex.LoginHistory;
import com.lee.vshare.ui.BaseFragment;
import com.lee.vshare.ui.activity.LoginActivity;
import com.lee.vshare.ui.activity.MainActivity;
import com.lee.vshare.ui.presenter.LoginPresenter;
import com.lee.vshare.viewmodel.LoginHistoryViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";

    private FragmentLoginBinding mBinding;

    private com.lee.vshare.ui.adapter.LoginHistoryAdapter mAdapter;

    public static LoginFragment newInstance() {
        final LoginFragment fragment = new LoginFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        mBinding.setHistoryShow(false);
        mBinding.setCleanShow(false);
        mBinding.setLoginBean(new LoginBean());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAdapter = new com.lee.vshare.ui.adapter.LoginHistoryAdapter(itemClickListener);
        mBinding.loginHistoryRecycle.setLayoutManager(layoutManager);
        mBinding.loginHistoryRecycle.setAdapter(mAdapter);

        mBinding.setLoginPresenter(presenter);

        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBinding.getHistoryShow()) {
            mBinding.setHistoryShow(false);
        }
        ((LoginActivity)getActivity()).setTitle(LoginActivity.TITLE_SIGNIN);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LoginHistoryViewModel viewModel = ViewModelProviders.of(this).get(LoginHistoryViewModel.class);

        subscribeUi(viewModel.getmObservableHistories());
    }

    private void subscribeUi(LiveData<List<LoginHistoryEntity>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (loginHistories) -> {
            if (loginHistories != null) {
                Log.d("Lee_Login", "setLoginHistoryList");
                mAdapter.setLoginHistoryList(loginHistories);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private final LoginPresenter presenter = new LoginPresenter() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.d("Lee_Login", "onFocusChange : hasFocus = " + hasFocus);
            if (!hasFocus) {
                mBinding.setHistoryShow(false);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("Lee_Login", "onTextChanged : s = " + s);
            if (mBinding.getCleanShow() && s.toString().equals("")) {
                mBinding.setCleanShow(false);
            } else if (!mBinding.getCleanShow() && !s.toString().equals("")) {
                mBinding.setCleanShow(true);
            }
        }

        @Override
        public void onLoginButtonClick() {
            String addr = mBinding.getLoginBean().getAddr();
            String password = mBinding.getLoginBean().getPassword();
            Log.d("Lee_Login", "onLoginButtonClick :   addr = " + addr + "  password = " + password);

            ((LoginActivity) getActivity()).startActivity(new Intent((LoginActivity) getActivity(), MainActivity.class));
            ((LoginActivity) getActivity()).finish();

        }

        @Override
        public void onShowHistoryClick() {
            Log.d("Lee_Login", "onShowHistoryClick");
            mBinding.setHistoryShow(!mBinding.getHistoryShow());
            if (mBinding.getHistoryShow()) {
                mBinding.email.requestFocus();
            }
        }

        @Override
        public void onRetrieveClick() {
            Log.d("Lee_Login", "onRetrieveClick");
            ((LoginActivity)getActivity()).showRetrieve();
        }

        @Override
        public void onSingUpClick() {
            Log.d("Lee_Login", "onSingUpClick");
            ((LoginActivity)getActivity()).showRegister();
        }
    };

    private final ItemClickListener<LoginHistory> itemClickListener = (history) -> {
        Log.d("Lee_Login", "userId = " + history.getUserId());
    };


}
