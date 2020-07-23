package com.jiading.service.impl;


import com.jiading.dao.FavouritePostDao;
import com.jiading.model.PageBean;
import com.jiading.model.Post;
import com.jiading.model.User;
import com.jiading.service.FavouritePostService;
import com.jiading.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("favouritePostService")
public class FavouritePostServiceImpl implements FavouritePostService {
    @Autowired
    private FavouritePostDao favouritePostDao;

    @Override
    public boolean isFavouritePost(String pid, int uid) {
        Post byRidAndUid = favouritePostDao.findByPidAndUid(uid, Integer.parseInt(pid));
        if (byRidAndUid == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void add(int pid, int uid) {
        favouritePostDao.add(uid, pid, TimeUtil.getStringTimeNow());
    }

    @Override
    public PageBean<Post> allLinkedPosts(int currentPage, int pageSize, User user) {
        //封装PageBean
        PageBean<Post> pb = new PageBean<Post>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int uid = user.getUid();
        //设置总记录数
        int totalCount = favouritePostDao.findTotalCountByUser(uid);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Post> list = favouritePostDao.findByUidInPages(uid, start, pageSize);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * @Description: 撤销对文章的收藏
     * @Param: [valueOf, uid]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @Override
    public void cancelLike(Integer pid, int uid) {
        favouritePostDao.cancelLike(pid, uid);
    }


}
