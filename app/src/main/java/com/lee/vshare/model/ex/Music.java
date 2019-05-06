package com.lee.vshare.model.ex;

import java.util.Date;

/**
 * CreateDate：19-2-18 on 上午10:02
 * Describe:
 * Coder: lee
 */
public interface Music {
    
    int getType();

    long getMusicId();

    String getMusicTitle();

    long getMusicAuthorId();

    String getMusicAuthorName();

    Date getMusicCreateTime();

    Date getMusicModifiedTime();

    int getMusicWords();

    int getMusicReads();

    int getMusicLikes();

    String getDescribe();

    int getShare();

}
