package com.lee.vshare.model.ex;

/**
 * CreateDate：19-2-18 on 上午10:02
 * Describe:
 * Coder: lee
 */
public interface ChatInfo {

    int getType();

    long getFromId();

    long getToId();

    String getContent();

    long getTime();


}
