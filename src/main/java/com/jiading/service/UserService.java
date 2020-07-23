package com.jiading.service;


import com.jiading.model.User;


public interface UserService {

    boolean register(User user);

    boolean active(String code);

    User login(User user);

    User findByUserId(int uid);

    User findByUsername(String username);

    void updateUser(User user);

    void likedPostAddOneToUserBean(String pid);

    void likedPostSubOneToUserBean(String pid);

    void likedUserSubOneToUserBean(String uid);

    void likedUserAddOneToUserBean(String uid);
}
