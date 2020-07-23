package com.jiading.dao;


import com.jiading.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteUserDao {
    //查询该uid喜欢的uid
    @Select("select * from USER where UID in (select LIKEDUID from FAVORITE_UID where UID=#{uid})")
    public List<User> findByUid(@Param("uid") Integer uid);

    //查询该uid喜欢的用户人数
    @Select("select count(*) from FAVORITE_UID where UID=#{uid}")
    public int findCount(@Param("uid") Integer uid);

    @Update("insert into FAVORITE_UID(`UID`,`LIKEDUID`,`TIME`) values(#{uid},#{likedUid},#{date})")
    void add(@Param("uid") Integer uid, @Param("date") String date, @Param("likedUid") Integer likedUid);


    @Delete("delete  from FAVORITE_UID where UID=#{uid} and LIKEDUID=#{likedUid}")
    void delete(@Param("uid") Integer uid , @Param("likedUid") Integer likedUid);

//    @Select("select * from FAVORITE_UID where uid=#{uid} and linkeduid=#{linkedUid}")
//    public User findByLinkedUidAndUid(@Param("uid") int uid, @Param("linkedUid") int likedUid);

}
