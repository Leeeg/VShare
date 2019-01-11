package lee.com.vshare.ui.fragment;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentLoginBinding;
import lee.com.vshare.db.entity.LoginHistoryEntity;
import lee.com.vshare.db.entity.ex.LoginHistory;
import lee.com.vshare.listener.LoginHistoryItemClickListener;
import lee.com.vshare.listener.LoginListener;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.activity.LoginActivity;
import lee.com.vshare.ui.activity.MainActivity;
import lee.com.vshare.ui.adapter.LoginHistoryAdapter;
import lee.com.vshare.viewmodel.LoginHistoryViewModel;

/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";

    private FragmentLoginBinding mBinding;

    private LoginHistoryAdapter mAdapter;

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

        mBinding.setLoginListener(loginListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAdapter = new LoginHistoryAdapter(loginHistoryItemClickListener);
        mBinding.loginHistoryRecycle.setLayoutManager(layoutManager);
        mBinding.loginHistoryRecycle.setAdapter(mAdapter);

        return mBinding.getRoot();

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

    private final LoginListener loginListener = () ->{
        ((LoginActivity)getActivity()).startActivity(new Intent((LoginActivity)getActivity(), MainActivity.class));
        ((LoginActivity)getActivity()).finish();
    };

    private final LoginHistoryItemClickListener loginHistoryItemClickListener = (LoginHistory history)->{
        Log.d("Lee_Login", "userId = " + history.getUserId());
    };


}
