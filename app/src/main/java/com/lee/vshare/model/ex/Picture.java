package com.lee.vshare.model.ex;

import java.util.Date;

/**
 * CreateDate：19-2-18 on 上午10:02
 * Describe:
 * Coder: lee
 */
public interface Picture {
    
    int getType();

    long getPictureId();

    String getPictureTitle();

    long getPictureAuthorId();

    String getPictureAuthorName();

    Date getPictureCreateTime();

    Date getPictureModifiedTime();

    int getPictureWords();

    int getPictureReads();

    int getPictureLikes();

    String getDescribe();

    int getShare();

    String getPictureUrl();

}
