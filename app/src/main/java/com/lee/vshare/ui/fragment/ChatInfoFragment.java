package com.lee.vshare.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lee.vshare.R;
import com.lee.vshare.databinding.FragmentChatInfoBinding;
import com.lee.vshare.listener.ItemClickListener;
import com.lee.vshare.model.ex.ChatInfo;
import com.lee.vshare.ui.BaseFragment;
import com.lee.vshare.ui.adapter.ChatInfoAdapter;
import com.lee.vshare.viewmodel.ChatInfoViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class ChatInfoFragment extends BaseFragment {

    public static final String TAG = "ChatInfoFragment";

    private FragmentChatInfoBinding mBinding;
    private ChatInfoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_info, container, false);

        mAdapter = new ChatInfoAdapter(itemClickListener);
        mBinding.chatInfoRecycle.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ChatInfoViewModel viewModel = ViewModelProviders.of(this).get(ChatInfoViewModel.class);

        subscribeUi(viewModel.getChatInfo());

    }
    
    private void subscribeUi(LiveData<List<ChatInfo>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (recreation) -> {
            Log.d(TAG, "subscribeUi : " + recreation.size());
            if (recreation != null) {
                mAdapter.setChatInfoList(recreation);
                mBinding.chatInfoRecycle.smoothScrollToPosition(recreation.size());
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static ChatInfoFragment newInstance() {
        final ChatInfoFragment fragment = new ChatInfoFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    private final ItemClickListener<ChatInfo> itemClickListener = (recreation) -> {
        Log.d(TAG, "id = " + recreation.getTime());
    };

}
