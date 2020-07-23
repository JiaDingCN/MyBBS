package com.jiading.service.impl;


import com.jiading.dao.UserDao;
import com.jiading.model.User;
import com.jiading.service.UserService;
import com.jiading.util.MailUtils;
import com.jiading.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    /*
    注册用户
     */
    @Override
    public boolean register(User user) {
        //1.根据用户名查询用户对象
        User byUsername = userDao.findByUsername(user.getUsername());
        if (byUsername != null) {
            //用户名存在，注册失败
            return false;
        } else {
            //2.保存用户信息
            //设置激活码
            user.setCode(UuidUtil.getUuid());
            //设置激活状态
            user.setStatus("N");
            userDao.saveUser(user);
            //发送激活邮件
            String context = "点击激活【GitTub】:http://gittub.cc:8080/user/active?code=" + user.getCode();
            MailUtils.sendMail(user.getEmail(), context, "激活邮件");
            return true;
        }
    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        //2.调用dao的修改激活状态的方法
        if (user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user);
    }

    @Override
    public User findByUserId(int uid) {
        return userDao.findByUserId(uid);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    /**
    * @Description: 根据pid查到这个用户，给这个用户的myPostsLikedTime字段加一，当然在sql里都能完成
    * @Param: [pid]
    * @return: void
    * @Author: JiaDing
    * @Date: 2020/7/20
    */
    @Override
    public void likedPostAddOneToUserBean(String pid) {
        userDao.likedPostAddOneToUserBean(Integer.valueOf(pid));
    }

    @Override
    public void likedPostSubOneToUserBean(String pid) {
        userDao.likedPostSubOneToUserBean(Integer.valueOf(pid));
    }

    @Override
    public void likedUserSubOneToUserBean(String uid) {
        userDao.likedUserSubOneToUserBean(Integer.valueOf(uid));
    }

    @Override
    public void likedUserAddOneToUserBean(String uid) {
        userDao.likedUserAddOneToUserBean(Integer.valueOf(uid));
    }

}
