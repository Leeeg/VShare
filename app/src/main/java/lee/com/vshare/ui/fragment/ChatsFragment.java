package lee.com.vshare.ui.fragment;

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
import lee.com.vshare.R;
import lee.com.vshare.databinding.FragmentChatsBinding;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Chats;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.ChatsAdapter;
import lee.com.vshare.viewmodel.ChatsViewModel;

/**
 * CreateDate：18-12-29 on 下午2:59
 * Describe:
 * Coder: lee
 */
public class ChatsFragment extends BaseFragment {

    public static final String TAG = "ChatsFragment";

    private FragmentChatsBinding mBinding;
    private ChatsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chats, container, false);

        mAdapter = new ChatsAdapter(itemClickListener);
        mBinding.chatsRecycle.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ChatsViewModel viewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);

        subscribeUi(viewModel.getChats());

    }
    
    private void subscribeUi(LiveData<List<Chats>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (recreation) -> {
            if (recreation != null) {
                mAdapter.setChatsList(recreation);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static ChatsFragment newInstance() {
        final ChatsFragment fragment = new ChatsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    private final ItemClickListener<Chats> itemClickListener = (recreation) -> {
        Log.d("Lee_Chats", "id = " + recreation.getChatsId());
    };
}
