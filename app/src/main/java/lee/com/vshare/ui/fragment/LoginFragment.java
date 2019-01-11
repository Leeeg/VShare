package lee.com.vshare.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentLoginBinding;
import lee.com.vshare.listener.LoginListener;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.activity.LoginActivity;
import lee.com.vshare.ui.activity.MainActivity;

/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";

    private FragmentLoginBinding mBinding;

    public static LoginFragment newInstance() {
        final LoginFragment fragment = new LoginFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mBinding.setLoginListener(loginListener);

        return mBinding.getRoot();

    }

    private final LoginListener loginListener = () ->{
        ((LoginActivity)getActivity()).startActivity(new Intent((LoginActivity)getActivity(), MainActivity.class));
    };

}
