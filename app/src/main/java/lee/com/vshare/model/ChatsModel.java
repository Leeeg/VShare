package lee.com.vshare.model;

import java.util.Date;

import lee.com.vshare.model.ex.Chats;

/**
 * CreateDate：19-1-3 on 上午10:43
 * Describe:
 * Coder: lee
 */
public class ChatsModel implements Chats {
    
    int type;

    long chatsId;

    String chatsTitle;

    long chatsAuthorId;

    String chatsAuthorName;

    Date chatsCreateTime;

    Date chatsModifiedTime;

    int chatsWords;

    int chatsReads;

    int chatsLikes;

    String describe;

    int share;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public long getChatsId() {
        return chatsId;
    }

    public void setChatsId(long chatsId) {
        this.chatsId = chatsId;
    }

    @Override
    public String getChatsTitle() {
        return chatsTitle;
    }

    public void setChatsTitle(String chatsTitle) {
        this.chatsTitle = chatsTitle;
    }

    @Override
    public long getChatsAuthorId() {
        return chatsAuthorId;
    }

    public void setChatsAuthorId(long chatsAuthorId) {
        this.chatsAuthorId = chatsAuthorId;
    }

    @Override
    public String getChatsAuthorName() {
        return chatsAuthorName;
    }

    public void setChatsAuthorName(String chatsAuthorName) {
        this.chatsAuthorName = chatsAuthorName;
    }

    @Override
    public Date getChatsCreateTime() {
        return chatsCreateTime;
    }

    public void setChatsCreateTime(Date chatsCreateTime) {
        this.chatsCreateTime = chatsCreateTime;
    }

    @Override
    public Date getChatsModifiedTime() {
        return chatsModifiedTime;
    }

    public void setChatsModifiedTime(Date chatsModifiedTime) {
        this.chatsModifiedTime = chatsModifiedTime;
    }

    @Override
    public int getChatsWords() {
        return chatsWords;
    }

    public void setChatsWords(int chatsWords) {
        this.chatsWords = chatsWords;
    }

    @Override
    public int getChatsReads() {
        return chatsReads;
    }

    public void setChatsReads(int chatsReads) {
        this.chatsReads = chatsReads;
    }

    @Override
    public int getChatsLikes() {
        return chatsLikes;
    }

    public void setChatsLikes(int chatsLikes) {
        this.chatsLikes = chatsLikes;
    }

    @Override
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
