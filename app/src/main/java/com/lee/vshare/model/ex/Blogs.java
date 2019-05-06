package com.lee.vshare.model.ex;

import java.util.Date;

/**
 * CreateDate：19-1-3 on 上午10:28
 * Describe:
 * Coder: lee
 */
public interface Blogs {

    long getBlogId();

    String getBlogTitle();

    long getBlogAuthorId();

    String getBlogAuthorName();

    Date getBlogCreateTime();

    Date getBlogModifiedTime();

    int getBlogWords();

    int getBlogReads();

    int getBlogLikes();

    String getDescribe();

    int getShare();

}
