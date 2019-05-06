package com.lee.vshare.viewmodel;

import android.app.Application;

import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class MessageViewModel extends AndroidViewModel {

    private final MediatorLiveData<Map<String, Fragment>> mObservableFragments;

    public MessageViewModel(@NonNull Application application) {
        super(application);

        mObservableFragments = new MediatorLiveData<>();

        mObservableFragments.setValue(null);

        Map<String, Fragment> fragmentsMap = new LinkedHashMap<>();
        Fragment fragmentContacts = com.lee.vshare.ui.fragment.MusicFragment.newInstance();
        Fragment fragmentChats = com.lee.vshare.ui.fragment.ChatsFragment.newInstance();
        fragmentsMap.put("Contacts", fragmentContacts);
        fragmentsMap.put("Chats", fragmentChats);

        MediatorLiveData<Map<String, Fragment>> fragmentList = new MediatorLiveData<>();
        fragmentList.setValue(fragmentsMap);

        mObservableFragments.addSource(fragmentList, mObservableFragments::setValue);
    }

    public MediatorLiveData<Map<String, Fragment>> getFragments() {
        return mObservableFragments;
    }
}
