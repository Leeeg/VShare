package com.lee.vshare.model;

import com.lee.vshare.model.ex.ChatInfo;

/**
 * CreateDate：19-1-3 on 上午10:43
 * Describe:
 * Coder: lee
 */
public class ChatInfoModel implements ChatInfo {
    
    int type;

    long fromId;

    long toId;

    long time;

    String content;

    @Override
    public int getType() {
        return type;
    }

    @Override
    public long getFromId() {
        return fromId;
    }

    @Override
    public long getToId() {
        return toId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public long getTime() {
        return time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
