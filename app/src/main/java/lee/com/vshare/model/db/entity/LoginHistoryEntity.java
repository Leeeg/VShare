
package lee.com.vshare.model.db.entity;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import lee.com.vshare.model.db.entity.ex.LoginHistory;

@Entity(tableName = "loginhistory")
public class LoginHistoryEntity implements LoginHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long userId;
    private String userName;
    private String password;
    private String userImgUrl;
    private Date loginDate;

    public LoginHistoryEntity() {
    }

    @Ignore
    public LoginHistoryEntity(int id, long userId, String userName, String password, String userImgUrl, Date loginDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userImgUrl = userImgUrl;
        this.loginDate = loginDate;
    }

    @Override
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
