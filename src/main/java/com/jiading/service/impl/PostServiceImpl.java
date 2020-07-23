package com.jiading.service.impl;

import com.jiading.dao.PostDao;
import com.jiading.dao.UserDao;
import com.jiading.model.PageBean;
import com.jiading.model.Post;
import com.jiading.model.Reply;
import com.jiading.model.User;
import com.jiading.service.PostService;
import com.jiading.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @program: bbs
 * @description: postService的实现类
 * @author: JiaDing
 * @create: 2020-07-18 16:53
 **/
@Service("postService")
public class PostServiceImpl implements PostService {
    @Autowired
    PostDao postDao;
    @Autowired
    UserDao userDao;

    @Override
    public PageBean<Post> pageQueryForViewByBlock(int bid, int currentPage, int pageSize) {
        //封装PageBean
        PageBean<Post> pb = new PageBean<Post>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = postDao.findTotalCountByBlock(bid);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Post> list = postDao.findByPageInBlockView(bid, start, pageSize);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * @Description: show默认设置为1，已在sql中调整
     * @Param: [uid, pid, text]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @Override
    public void writeComment(int uid, int pid, String text) {
        postDao.writeComment(uid, pid, text, TimeUtil.getStringTimeNow());
    }


    @Override
    public List<Reply> allCommentsInThisPost(String pid) {
        List<Reply> replies = postDao.allCommentsInThisPost(Integer.valueOf(pid));
        Iterator<Reply>iterator=replies.iterator();
        while(iterator.hasNext()){
            Reply reply=iterator.next();
            reply.setUser(userDao.findByUserId(reply.getUid()));
        }
        return replies;
    }


    @Override
    public Post findOne(String pid) {
        Integer pidInt = Integer.parseInt(pid);
        Post post = new Post();
        post.setPid(pidInt);
        Post one = postDao.findOne(post);
        return one;
    }


    @Override
    public void viewAddOne(String pid) {
        postDao.viewAddOne(Integer.valueOf(pid));
    }

    @Override
    public void likedSubOne(String pid) {
        postDao.likedSubOne(Integer.valueOf(pid));
    }

    @Override
    public void likedAddOne(String pid) {
        postDao.likedAddOne(Integer.valueOf(pid));
    }

    @Override
    public List<Post> getNMostPopularPosts(Integer n) {
        return postDao.nMostPopularPosts(n);
    }

    @Override
    public PageBean<Post> pageQueryForSearch(int bid, int currentPage, int pageSize, String postNameKeyWord) {
        //封装PageBean
        PageBean<Post> pb = new PageBean<Post>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        Post post = new Post();
        post.setBid(bid);
        post.setTitle("%"+postNameKeyWord+"%");
        //设置总记录数
        int totalCount = postDao.findTotalCountByTitleKeyWordAndBlock(post);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Post> list = postDao.findByPageInSearch(post.getBid(),post.getTitle(), start, pageSize);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public List<Post> findAllByUid(int uid) {
        return postDao.findAllByUid(String.valueOf(uid));
    }

    @Override
    public PageBean<Post> findAllByUidInPages(int uid, int currentPage, int pageSize) {
        //封装PageBean
        PageBean<Post> pb = new PageBean<Post>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = postDao.countAllByUid(uid);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;

        List<Post> list = postDao.findAllByUidInPages(uid, start, pageSize);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public void writePost(User user, String title, String summary, String content, String bid) {
        postDao.writePost(user.getUid(), Integer.valueOf(bid), title, summary, content, TimeUtil.getStringTimeNow());
    }

}
