package lee.com.vshare.model.ex;

import java.util.Date;

/**
 * CreateDate：19-2-18 on 上午10:02
 * Describe:
 * Coder: lee
 */
public interface Video {
    
    int getType();

    long getVideoId();

    String getVideoTitle();

    long getVideoAuthorId();

    String getVideoAuthorName();

    Date getVideoCreateTime();

    Date getVideoModifiedTime();

    int getVideoWords();

    int getVideoReads();

    int getVideoLikes();

    String getDescribe();

    int getShare();

}
