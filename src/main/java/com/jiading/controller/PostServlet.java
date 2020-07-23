package com.jiading.controller;

import com.jiading.dao.BlockDao;
import com.jiading.model.*;
import com.jiading.service.FavouritePostService;
import com.jiading.service.PostService;
import com.jiading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
/*
所有没有返回对象的方法均返回一个resultInfo，用其中的flag字段表示操作是否完成
 */
/**
 * @program: bbs
 * @description: 与帖子相关的servlet
 * @author: JiaDing
 * @create: 2020-07-16 16:44
 **/
@Controller
@RequestMapping("/post")
public class PostServlet extends BaseServlet {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlockDao blockDao;
    @Autowired
    private FavouritePostService favouritePostService;
    public static int NOLOGIN = -1;
    public static int FAVOURITED = 1;
    public static int NOTFAVOURITED = 0;

    /**
     * @Description: 分类查看，分页显示
     * 前端需要传入的参数有三个
     * * currentPage:现在到第几页了
     * * pageSize:一页显示多少个结果
     * * bid:属于哪个分类
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/pageQueryForViewByBlock")
    public void pageQueryForViewByBlock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 接受参数
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String bid = req.getParameter("bid");
        int intBid = 0;
        int intCurrentPage = 1;
        int intPageSize = 5;//每页显示条数，默认为5条
        if (bid != null && bid.length() > 0) {
            intBid = Integer.parseInt(bid);
        }
        if (currentPage != null && currentPage.length() > 0) {
            intCurrentPage = Integer.parseInt(currentPage);
        }
        if (pageSize != null && pageSize.length() > 0) {
            intPageSize = Integer.parseInt(pageSize);
        }
        //调用service查询PageBean对象
        PageBean<Post> pb = postService.pageQueryForViewByBlock(intBid, intCurrentPage, intPageSize);
        writeValue(pb, resp);
    }


    /**
     * @Description: 用于搜索功能，给出关键字进行搜索，返回结果列表和总页数，查询的时候会根据当前页面来在sql中设置只返回对应结果
     * 前端需要传入的参数有四个
     * currentPage:现在到第几页了
     * pageSize:一页显示多少个结果
     * blockName:分类的名称
     * postName:搜索的关键字
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/pageQueryForSearch")
    public void pageQueryForSearch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 接受参数
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String blockName = req.getParameter("blockName");
        Block block = blockDao.findByName(blockName);
        String bid=String.valueOf(block.getBid());
        //这里postname输入中文会产生乱码，需要处理乱码问题
        String postName = req.getParameter("postName");
        /*
        if (postName != null)
            //重新编码
            postName = new String(postName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        */
        int intBid = 0;
        int intCurrentPage = 1;
        int intPageSize = 5;//每页显示条数，默认为5条
        if (bid != null && bid.length() > 0) {
            intBid = Integer.parseInt(bid);
        }
        if (currentPage != null && currentPage.length() > 0) {
            intCurrentPage = Integer.parseInt(currentPage);
        }
        if (pageSize != null && pageSize.length() > 0) {
            intPageSize = Integer.parseInt(pageSize);
        }

        //调用service查询PageBean对象
        PageBean<Post> pb = postService.pageQueryForSearch(intBid, intCurrentPage, intPageSize, postName);
        writeValue(pb, resp);
    }

    /**
     * @Description: 根据pid来获取某一篇文章，主要是用于浏览。每次操作都为浏览量加一
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/findOne")
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.接收参数id
        String pid = req.getParameter("pid");
        //2.调用service查询route对象
        Post post = postService.findOne(pid);
        postService.viewAddOne(pid);
        //3.转为json写回客户端
        writeValue(post, resp);
    }

    /**
     * @Description: 传入pid, 返回含有的true或false的flag的resultinfo, 查看是否是该用户已经收藏的帖子
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/isFavourite")
    public void isFavourite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int ans = NOTFAVOURITED;
        //1.获取帖子的id
        String pid = req.getParameter("pid");
        //2.获取当前登录的用户User
        User user = (User) req.getSession().getAttribute("user");
        int uid;//用户id
        if (user == null) {
            ans = NOLOGIN;
            //用户尚未登录
            writeValue(ans, resp);
            return;
        } else {
            uid = user.getUid();
        }
        //3.调用FavouriteService查询是否收藏
        boolean favourite = favouritePostService.isFavouritePost(pid, uid);
        if (favourite == true) {
            ans = FAVOURITED;
        } else {
            ans = NOTFAVOURITED;
        }
        //测试
        //System.out.println("isFavourite__user:"+user.getUsername()+",pid:"+pid+",ans:"+ans);
        //
        writeValue(ans, resp);

    }

    /**
     * @Description: 取消收藏，收藏量减一,post的用户的收藏量也减一
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/cancelFavourite")
    public void cancelFavourite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pid = req.getParameter("pid");
        User user = (User) req.getSession().getAttribute("user");
        favouritePostService.cancelLike(Integer.valueOf(pid), user.getUid());
        postService.likedSubOne(pid);
        userService.likedPostSubOneToUserBean(pid);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }

    /**
     * @Description: 添加收藏，该文章的收藏量同时加一，给该文章的发布用户的myPostsLikedTime字段也加一
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/addFavourite")
    public void addFavourite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pid = req.getParameter("pid");
        User user = (User) req.getSession().getAttribute("user");
        favouritePostService.add(Integer.parseInt(pid), user.getUid());
        postService.likedAddOne(pid);
        userService.likedPostAddOneToUserBean(pid);
        //测试
        //System.out.println("user:"+user.getUsername()+",pid:"+pid);
        //
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }
    /**
    * @Description: 输入n，返回n个收藏最多的帖子
    * @Param: [req, resp]
    * @return: void
    * @Author: JiaDing
    * @Date: 2020/7/20
    */
    @RequestMapping("/nMostPopularPosts")
    public void nMostPopularPosts(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String n=req.getParameter("n");
        List<Post> list=postService.getNMostPopularPosts(Integer.valueOf(n));
        writeValue(list,resp);
    }

    /**
     * @Description: 所有收藏的帖子
     * 传入：
     * 1. currentPage
     * 2. pageSize
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     **/
    @RequestMapping("/likedPosts")
    public void allLikedPostsPageBean(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 接受参数
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        int intCurrentPage = 1;
        int intPageSize = 5;//每页显示条数，默认为5条
        if (currentPage != null && currentPage.length() > 0) {
            intCurrentPage = Integer.parseInt(currentPage);
        }
        if (pageSize != null && pageSize.length() > 0) {
            intPageSize = Integer.parseInt(pageSize);
        }
        Object objectUser = req.getSession().getAttribute("user");
        User user = (User) objectUser;
        PageBean<Post> list = favouritePostService.allLinkedPosts(intCurrentPage, intPageSize, user);
        writeValue(list, resp);
    }

    /**
     * @Description: 分页显示我写的所有的帖子
     * @Param: [req, resp]
     * 输入
     * 1. currentPage
     * 2. pageSize
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/myPosts")
    public void myPostsPageBean(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 接受参数
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        int intCurrentPage = 1;
        int intPageSize = 5;//每页显示条数，默认为5条
        if (currentPage != null && currentPage.length() > 0) {
            intCurrentPage = Integer.parseInt(currentPage);
        }
        if (pageSize != null && pageSize.length() > 0) {
            intPageSize = Integer.parseInt(pageSize);
        }
        Object objectUser = req.getSession().getAttribute("user");
        User user = (User) objectUser;
        PageBean<Post> myPosts = postService.findAllByUidInPages(user.getUid(), intCurrentPage, intPageSize);
        writeValue(myPosts, resp);
    }

    /**
     * @Description: 发帖
     * 前端需要传入的参数
     * 1. title
     * 2. summary
     * 3. content
     * 4. block的id,也就是bid
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/writePost")
    public void writePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object objectUser = req.getSession().getAttribute("user");
        User user = (User) objectUser;
        String title = req.getParameter("title");
        String summary = req.getParameter("summary");
        String content = req.getParameter("content");
        String bid = req.getParameter("bid");
        postService.writePost(user,title, summary, content, bid);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }

    /**
     * @Description: 给某文章进行评论
     * 前端需要提供的数据
     * 1. pid
     * 2. text(即评论内容)
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/submitComment")
    public void submitComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object objectUser = req.getSession().getAttribute("user");
        User user = (User) objectUser;
        int pid = Integer.parseInt(req.getParameter("pid"));
        String text = req.getParameter("text");
        postService.writeComment(user.getUid(), pid, text);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }

    /**
     * @Description: 显示该文章的所有评论
     * @Param: 输入：
     * 1. pid
     * 输出:
     * List<Reply>
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/allCommentsInThisPost")
    public void allCommentsInThisPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pid = req.getParameter("pid");
        List<Reply> list = postService.allCommentsInThisPost(pid);
        writeValue(list, resp);
    }
    /**
    * @Description:
     * 1. currentPage
     * 2. pageSize
     * 3. uid
    * @Param: [req, resp]
    * @return: void
    * @Author: JiaDing
    * @Date: 2020/7/23
    */
    @RequestMapping("/allPostsForAnother")
    public void allPostsForAnother(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //1. 接受参数
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String uid=req.getParameter("uid");
        int intCurrentPage = 1;
        int intPageSize = 5;//每页显示条数，默认为5条
        if (currentPage != null && currentPage.length() > 0) {
            intCurrentPage = Integer.parseInt(currentPage);
        }
        if (pageSize != null && pageSize.length() > 0) {
            intPageSize = Integer.parseInt(pageSize);
        }
        PageBean<Post> myPosts = postService.findAllByUidInPages(Integer.valueOf(uid), intCurrentPage, intPageSize);
        writeValue(myPosts, resp);
    }

}
