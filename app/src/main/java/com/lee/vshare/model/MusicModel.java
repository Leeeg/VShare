package com.lee.vshare.model;

import com.lee.vshare.model.ex.Music;

import java.util.Date;

/**
 * CreateDate：19-1-3 on 上午10:43
 * Describe:
 * Coder: lee
 */
public class MusicModel implements Music {
    
    int type;

    long musicId;

    String musicTitle;

    long musicAuthorId;

    String musicAuthorName;

    Date musicCreateTime;

    Date musicModifiedTime;

    int musicWords;

    int musicReads;

    int musicLikes;

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
    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    @Override
    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    @Override
    public long getMusicAuthorId() {
        return musicAuthorId;
    }

    public void setMusicAuthorId(long musicAuthorId) {
        this.musicAuthorId = musicAuthorId;
    }

    @Override
    public String getMusicAuthorName() {
        return musicAuthorName;
    }

    public void setMusicAuthorName(String musicAuthorName) {
        this.musicAuthorName = musicAuthorName;
    }

    @Override
    public Date getMusicCreateTime() {
        return musicCreateTime;
    }

    public void setMusicCreateTime(Date musicCreateTime) {
        this.musicCreateTime = musicCreateTime;
    }

    @Override
    public Date getMusicModifiedTime() {
        return musicModifiedTime;
    }

    public void setMusicModifiedTime(Date musicModifiedTime) {
        this.musicModifiedTime = musicModifiedTime;
    }

    @Override
    public int getMusicWords() {
        return musicWords;
    }

    public void setMusicWords(int musicWords) {
        this.musicWords = musicWords;
    }

    @Override
    public int getMusicReads() {
        return musicReads;
    }

    public void setMusicReads(int musicReads) {
        this.musicReads = musicReads;
    }

    @Override
    public int getMusicLikes() {
        return musicLikes;
    }

    public void setMusicLikes(int musicLikes) {
        this.musicLikes = musicLikes;
    }

    @Override
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
