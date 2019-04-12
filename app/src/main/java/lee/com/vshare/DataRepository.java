package lee.com.vshare;

import java.util.List;

import androidx.lifecycle.LiveData;
import lee.com.vshare.model.db.AppDatabase;
import lee.com.vshare.model.db.entity.LoginHistoryEntity;

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
