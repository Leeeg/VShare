package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentRegisterBinding;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.activity.LoginActivity;
import lee.com.vshare.ui.presenter.RegisterPresenter;


/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class RegisterFragment extends BaseFragment {

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

    private final RegisterPresenter registerPresenter = new RegisterPresenter() {
        @Override
        public void onNextClick() {
            ((LoginActivity)getActivity()).showSignUp();
        }
    };

}
