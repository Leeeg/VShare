package lee.com.vshare.model.ex;

import java.util.Date;

/**
 * CreateDate：19-2-18 on 上午10:02
 * Describe:
 * Coder: lee
 */
public interface Chats {
    
    int getType();

    long getChatsId();

    String getChatsTitle();

    long getChatsAuthorId();

    String getChatsAuthorName();

    Date getChatsCreateTime();

    Date getChatsModifiedTime();

    int getChatsWords();

    int getChatsReads();

    int getChatsLikes();

    String getDescribe();

    int getShare();

}
