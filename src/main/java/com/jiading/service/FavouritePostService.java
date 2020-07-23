package com.jiading.service;

import com.jiading.model.PageBean;
import com.jiading.model.Post;
import com.jiading.model.User;


public interface FavouritePostService {
    boolean isFavouritePost(String pid, int uid);

    void add(int pid, int uid);


    PageBean<Post> allLinkedPosts(int intCurrentPage, int intPageSize, User user);

    void cancelLike(Integer valueOf, int uid);
}
