package lee.com.vshare.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentMessageBinding;
import lee.com.vshare.ui.BaseFragment;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class MessageFragment extends BaseFragment {

    public static final String TAG = "MessageFragment";

    private FragmentMessageBinding messageBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        messageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);

        return messageBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static MessageFragment newInstance() {
        final MessageFragment fragment = new MessageFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
