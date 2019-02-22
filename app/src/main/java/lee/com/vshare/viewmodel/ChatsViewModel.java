package lee.com.vshare.viewmodel;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import lee.com.vshare.model.ChatsModel;
import lee.com.vshare.model.ex.Chats;

/**
 * CreateDate：19-1-3 on 下午3:41
 * Describe:
 * Coder: lee
 */
public class ChatsViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Chats>> mObservableChats;

    public ChatsViewModel(@NonNull Application application) {
        super(application);

        mObservableChats = new MediatorLiveData<>();

        mObservableChats.setValue(null);

        List<Chats> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChatsModel chats = new ChatsModel();
            chats.setChatsId(i);
            chats.setChatsTitle("chats : " + i);
            chats.setDescribe("this is chats " + i);
            list.add(chats);
        }

        MediatorLiveData<List<Chats>> chatsList = new MediatorLiveData<>();
        chatsList.setValue(list);

        mObservableChats.addSource(chatsList, mObservableChats::setValue);
    }

    public MediatorLiveData<List<Chats>> getChats() {
        return mObservableChats;
    }

}
