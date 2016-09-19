package com.classic.simple.model;

public class News {

    /** 单图布局样式 */
    public static final int TYPE_SINGLE_PICTURE   = 0;
    /** 多图布局样式 */
    public static final int TYPE_MULTIPLE_PICTURE = 1;
    /** 无图布局样式 */
    public static final int TYPE_NONE_PICTURE     = 2;

    private String title;
    private String intro;
    private String coverUrl;
    private String author;
    private long   releaseTime;
    private int    newsType;

    public News(){}

    public News(int newsType, String author, String title, String intro) {
        this(newsType,author,title,intro,"");
    }

    public News(int newsType, String author, String title, String intro, String coverUrl) {
        this.newsType = newsType;
        this.author = author;
        this.title = title;
        this.intro = intro;
        this.coverUrl = coverUrl;
        this.releaseTime = System.currentTimeMillis();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
