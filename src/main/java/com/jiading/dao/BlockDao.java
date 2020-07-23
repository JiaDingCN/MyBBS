package com.jiading.dao;


import com.jiading.model.Block;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 查询论坛版块
 * @Param:
 * @return:
 * @Author: JiaDing
 * @Date: 2020/7/16
 */
@Repository
public interface BlockDao {
    @Select("select * from BLOCKS")
    public List<Block> findAll();

    @Select("select * from BLOCKS where BID=#{bid}")
    public Block findByBid(@Param("bid") int bid);

    @Select("select * from BLOCKS where BLOCKNAME=#{name}")
    public Block findByName(@Param("name") String name);
}
