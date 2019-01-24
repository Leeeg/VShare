package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentRetrieveBinding;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.activity.LoginActivity;


/**
 * CreateDate：19-1-10 on 下午3:15
 * Describe:
 * Coder: lee
 */
public class RetrieveFragment extends BaseFragment {

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

        return mBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity)getActivity()).setTitle(LoginActivity.TITLE_RETRIEVE);
    }

}
