package com.jiading.dao;


import com.jiading.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    @Select("select * from USER where UID=#{uid}")
    public User findByUserId(@Param("uid") Integer uid);

    @Select("select * from USER where USERNAME=#{username}")
    public User findByUsername(@Param("username") String username);

    @Insert("insert into USER(`USERNAME`,`PASSWORD`,`HEAD_PORTRAIT`,`ADMIN_PERMISSION`,`EMAIL`,`STATUS`,`CODE`,`COMMENT`)values(#{username},#{password},#{head_portrait},#{admin_permission},#{email},#{status},#{code},#{comment})")
    public void saveUser(User user);

    @Update("update USER set USERNAME=#{username},PASSWORD=#{password},HEAD_PORTRAIT=#{head_portrait},ADMIN_PERMISSION=#{admin_permission},COMMENT=#{comment} where UID=#{uid}")
    public void updateUser(User user);

    @Select("select * from USER where CODE=#{code}")
    User findByCode(@Param("code") String code);

    @Update("update USER set STATUS ='Y' where UID=#{uid}")
    void updateStatus(User user);

    @Select("select * from USER where USERNAME=#{username} and PASSWORD=#{password}")
    User findByUsernameAndPassword(User user);

    @Select("select * from USER where CODE=#{code} and USERNAME=#{username}")
    User findByCodeAndUsername(@Param("code") String code, @Param("username") String username);

    @Update("update USER set MYPOSTLIKEDTIME=MYPOSTLIKEDTIME+1 where UID=(select UID from POSTS where PID=#{pid})")
    void likedPostAddOneToUserBean(@Param("pid") Integer pid);

    @Update("update USER set MYPOSTLIKEDTIME=MYPOSTLIKEDTIME-1 where UID=(select UID from POSTS where PID=#{pid})")
    void likedPostSubOneToUserBean(@Param("pid")Integer pid);

    @Update("update USER set LIKEDTIME=LIKEDTIME-1 where UID=#{uid}")
    void likedUserSubOneToUserBean(@Param("uid") Integer uid);

    @Update("update USER set LIKEDTIME=LIKEDTIME+1 where UID=#{uid}")
    void likedUserAddOneToUserBean(@Param("uid") Integer uid);
}
