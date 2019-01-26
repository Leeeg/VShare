package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentResetpasswordBinding;
import lee.com.vshare.ui.BaseFragment;


/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class ResetPasswordFragment extends BaseFragment {

    public static final String TAG = "ResetPasswordFragment";

    private FragmentResetpasswordBinding mBinding;

    public static ResetPasswordFragment newInstance() {
        final ResetPasswordFragment fragment = new ResetPasswordFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_resetpassword, container, false);

        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
