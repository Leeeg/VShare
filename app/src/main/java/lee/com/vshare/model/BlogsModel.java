package lee.com.vshare.model;

import java.util.Date;

import lee.com.vshare.model.ex.Blogs;

/**
 * CreateDate：19-1-3 on 上午10:43
 * Describe:
 * Coder: lee
 */
public class BlogsModel implements Blogs {

    long blogId;

    String blogTitle;

    long blogAuthorId;

    String blogAuthorName;

    Date blogCreateTime;

    Date blogModifiedTime;

    int blogWords;

    int blogReads;

    int blogLikes;

    String describe;

    int share;

    @Override
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    @Override
    public long getBlogAuthorId() {
        return blogAuthorId;
    }

    public void setBlogAuthorId(long blogAuthorId) {
        this.blogAuthorId = blogAuthorId;
    }

    @Override
    public String getBlogAuthorName() {
        return blogAuthorName;
    }

    public void setBlogAuthorName(String blogAuthorName) {
        this.blogAuthorName = blogAuthorName;
    }

    @Override
    public Date getBlogCreateTime() {
        return blogCreateTime;
    }

    public void setBlogCreateTime(Date blogCreateTime) {
        this.blogCreateTime = blogCreateTime;
    }

    @Override
    public Date getBlogModifiedTime() {
        return blogModifiedTime;
    }

    public void setBlogModifiedTime(Date blogModifiedTime) {
        this.blogModifiedTime = blogModifiedTime;
    }

    @Override
    public int getBlogWords() {
        return blogWords;
    }

    public void setBlogWords(int blogWords) {
        this.blogWords = blogWords;
    }

    @Override
    public int getBlogReads() {
        return blogReads;
    }

    public void setBlogReads(int blogReads) {
        this.blogReads = blogReads;
    }

    @Override
    public int getBlogLikes() {
        return blogLikes;
    }

    public void setBlogLikes(int blogLikes) {
        this.blogLikes = blogLikes;
    }

    @Override
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
