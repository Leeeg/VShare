package com.lee.vshare.model;

import com.lee.vshare.model.ex.Video;

import java.util.Date;


/**
 * CreateDate：19-1-3 on 上午10:43
 * Describe:
 * Coder: lee
 */
public class VideoModel implements Video {
    
    int type;

    long videoId;

    String videoTitle;

    long videoAuthorId;

    String videoAuthorName;

    Date videoCreateTime;

    Date videoModifiedTime;

    int videoWords;

    int videoReads;

    int videoLikes;

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
    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    @Override
    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    @Override
    public long getVideoAuthorId() {
        return videoAuthorId;
    }

    public void setVideoAuthorId(long videoAuthorId) {
        this.videoAuthorId = videoAuthorId;
    }

    @Override
    public String getVideoAuthorName() {
        return videoAuthorName;
    }

    public void setVideoAuthorName(String videoAuthorName) {
        this.videoAuthorName = videoAuthorName;
    }

    @Override
    public Date getVideoCreateTime() {
        return videoCreateTime;
    }

    public void setVideoCreateTime(Date videoCreateTime) {
        this.videoCreateTime = videoCreateTime;
    }

    @Override
    public Date getVideoModifiedTime() {
        return videoModifiedTime;
    }

    public void setVideoModifiedTime(Date videoModifiedTime) {
        this.videoModifiedTime = videoModifiedTime;
    }

    @Override
    public int getVideoWords() {
        return videoWords;
    }

    public void setVideoWords(int videoWords) {
        this.videoWords = videoWords;
    }

    @Override
    public int getVideoReads() {
        return videoReads;
    }

    public void setVideoReads(int videoReads) {
        this.videoReads = videoReads;
    }

    @Override
    public int getVideoLikes() {
        return videoLikes;
    }

    public void setVideoLikes(int videoLikes) {
        this.videoLikes = videoLikes;
    }

    @Override
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
