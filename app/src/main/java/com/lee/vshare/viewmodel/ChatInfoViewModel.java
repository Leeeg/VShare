package com.lee.vshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.lee.vshare.model.ChatInfoModel;
import com.lee.vshare.model.ex.ChatInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import lee.vshare.netty.callback.NettyMsgCallback;
import lee.vshare.netty.task.NettyTask;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class ChatInfoViewModel extends AndroidViewModel implements NettyMsgCallback{

    private static final String TAG = "ChatInfoViewModel";

    private final MediatorLiveData<List<ChatInfo>> mObservableChatInfo;
    MediatorLiveData<List<ChatInfo>> chatInfoList;
    private final List<ChatInfo> list;

    public ChatInfoViewModel(@NonNull Application application) {
        super(application);

        mObservableChatInfo = new MediatorLiveData<>();
        mObservableChatInfo.setValue(null);

        list = new ArrayList<>();
        chatInfoList = new MediatorLiveData<>();
        chatInfoList.setValue(list);

        mObservableChatInfo.addSource(chatInfoList, mObservableChatInfo::setValue);

        NettyTask.getInstance().setMsgCallback(this::receiveMsg);
    }

    public MediatorLiveData<List<ChatInfo>> getChatInfo() {
        return mObservableChatInfo;
    }

    @Override
    public void receiveMsg(String message) {
        Log.d(TAG, "receiveMsg : " + message);
        ChatInfoModel chatInfoModel = new ChatInfoModel();
        chatInfoModel.setContent(message);
        list.add(chatInfoModel);
        chatInfoList.postValue(list);
    }

}
