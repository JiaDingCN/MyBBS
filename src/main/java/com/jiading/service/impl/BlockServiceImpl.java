package com.jiading.service.impl;


import com.jiading.dao.BlockDao;

import com.jiading.model.Block;

import com.jiading.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blockService")
public class BlockServiceImpl implements BlockService {
    @Autowired
    private BlockDao blockDao;

    /**
     * 1. 从redis查询
     * 2. 如果获取的集合为空，说明是第一次访问，去查询数据库；如果不是空，就直接返回
     *
     * @return
     */
    @Override
    public List<Block> findAll() {
        List<Block> all = blockDao.findAll();
        return all;
    }

    /**
     * 根据bid查询block
     */
    @Override
    public Block findByBid(int bid) {
        return blockDao.findByBid(bid);
    }

    /**
     * 根据name查询block
     */
    @Override
    public Block findByName(String name) {
        return blockDao.findByName(name);
    }

}
