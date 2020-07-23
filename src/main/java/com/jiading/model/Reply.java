package com.jiading.model;

import java.io.Serializable;

/**
 * @program: bbs
 * @description: 评论的实体类
 * @author: JiaDing
 * @create: 2020-07-18 15:43
 **/
public class Reply implements Serializable {
    private User user;//评论的用户
    private int uid;
    private int pid;//评论的文章的id
    private String content;
    private String time;
    private boolean isCanShow;//是否显示

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Reply(int uid, int pid, String content, String time, boolean isCanShow) {
        this.uid = uid;
        this.pid = pid;
        this.content = content;
        this.time =time;
        this.isCanShow = isCanShow;
    }

    public Reply() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPid() {
        return pid;
    }



    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "Reply{" +
                "user=" + user +
                ", uid=" + uid +
                ", pid=" + pid +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", isCanShow=" + isCanShow +
                '}';
    }
}
