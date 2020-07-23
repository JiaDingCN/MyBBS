package com.jiading.model;

import java.io.Serializable;


/**
 * @program: bbs
 * @description: 帖子的对象
 * @author: JiaDing
 * @create: 2020-07-16 10:40
 **/
public class Post implements Serializable {
    private int pid;//pid是指帖子的id
    private int uid;//uid是值用户的id
    private int bid;//bid是所属的类别的id
    private String summary;//摘要
    private String content;//文章
    private String title;//标题
    private String time;//文章发布时间
    private boolean isCanShow;//是否显示
    private int view;//浏览量
    private int liked;//收藏量

    public Post() {
    }

    public Post(int pid, int uid, int bid, String summary, String content, String title, String time, boolean isCanShow, int view, int liked) {
        this.pid = pid;
        this.uid = uid;
        this.bid = bid;
        this.summary = summary;
        this.content = content;
        this.title = title;
        this.time = time;
        this.isCanShow = isCanShow;
        this.view = view;
        this.liked = liked;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCanShow() {
        return isCanShow;
    }

    public void setCanShow(boolean canShow) {
        isCanShow = canShow;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", bid=" + bid +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", isCanShow=" + isCanShow +
                ", view=" + view +
                ", liked=" + liked +
                '}';
    }
}
