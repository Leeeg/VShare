package com.lee.vshare;


import com.lee.vshare.model.db.AppDatabase;
import com.lee.vshare.model.db.entity.LoginHistoryEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<LoginHistoryEntity>> loadLoginHistory() {
        return mDatabase.loginHistoryDao().loadLoinHistory();
    }
}
