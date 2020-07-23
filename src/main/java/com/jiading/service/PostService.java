package com.jiading.service;

import com.jiading.model.PageBean;
import com.jiading.model.Post;
import com.jiading.model.Reply;
import com.jiading.model.User;

import java.util.List;


public interface PostService {
    public PageBean<Post> pageQueryForSearch(int bid, int currentPage, int pageSize, String postName);

    Post findOne(String pid);

    List<Post> findAllByUid(int uid);

    PageBean<Post> findAllByUidInPages(int uid, int currentPage, int pageSize);

    void writePost(User user, String title, String summary, String content, String bid);

    PageBean<Post> pageQueryForViewByBlock(int bid, int currentPage, int pageSize);

    void writeComment(int uid, int pid, String text);

    List<Reply> allCommentsInThisPost(String pid);

    void viewAddOne(String pid);

    void likedSubOne(String pid);

    void likedAddOne(String pid);

    List<Post> getNMostPopularPosts(Integer valueOf);
}
