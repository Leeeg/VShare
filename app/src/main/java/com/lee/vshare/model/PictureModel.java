package com.lee.vshare.model;

import com.lee.vshare.model.ex.Picture;

import java.util.Date;


/**
 * CreateDate：19-1-3 on 上午10:43
 * Describe:
 * Coder: lee
 */
public class PictureModel implements Picture {
    
    int type;

    long pictureId;

    String pictureTitle;

    long pictureAuthorId;

    String pictureAuthorName;

    Date pictureCreateTime;

    Date pictureModifiedTime;

    int pictureWords;

    int pictureReads;

    int pictureLikes;

    String pictureUrl;

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
    public long getPictureId() {
        return pictureId;
    }

    public void setPictureId(long pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    @Override
    public long getPictureAuthorId() {
        return pictureAuthorId;
    }

    public void setPictureAuthorId(long pictureAuthorId) {
        this.pictureAuthorId = pictureAuthorId;
    }

    @Override
    public String getPictureAuthorName() {
        return pictureAuthorName;
    }

    public void setPictureAuthorName(String pictureAuthorName) {
        this.pictureAuthorName = pictureAuthorName;
    }

    @Override
    public Date getPictureCreateTime() {
        return pictureCreateTime;
    }

    public void setPictureCreateTime(Date pictureCreateTime) {
        this.pictureCreateTime = pictureCreateTime;
    }

    @Override
    public Date getPictureModifiedTime() {
        return pictureModifiedTime;
    }

    public void setPictureModifiedTime(Date pictureModifiedTime) {
        this.pictureModifiedTime = pictureModifiedTime;
    }

    @Override
    public int getPictureWords() {
        return pictureWords;
    }

    public void setPictureWords(int pictureWords) {
        this.pictureWords = pictureWords;
    }

    @Override
    public int getPictureReads() {
        return pictureReads;
    }

    public void setPictureReads(int pictureReads) {
        this.pictureReads = pictureReads;
    }

    @Override
    public int getPictureLikes() {
        return pictureLikes;
    }

    public void setPictureLikes(int pictureLikes) {
        this.pictureLikes = pictureLikes;
    }

    @Override
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    @Override
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
