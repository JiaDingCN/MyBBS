package com.jiading.controller;

import com.jiading.model.ResultInfo;
import com.jiading.model.User;
import com.jiading.service.FavouriteUserService;
import com.jiading.service.UserService;
import com.jiading.util.QiniuyunUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")//使用通配符匹配多个方法
public class UserServlet extends BaseServlet {
    //调用Service查询
    @Autowired
    private UserService service;
    @Autowired
    private FavouriteUserService favouriteUserService;

    /**
     *
     * @Description: 尝试注册，给前端返回false或者true.如果注册失败（也就是用户名重复）则回滚
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/register")
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取用户名和密码
        Map<String, String[]> parameterMap = req.getParameterMap();
        //2.封装user对象
        //实体类就直接new
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ResultInfo resultInfo = new ResultInfo();
        if (service.register(user)) {
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
        }
        //7.响应数据
        writeValue(resultInfo, resp);
    }

    @RequestMapping("/active")
    public void active(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取激活码
        String code = req.getParameter("code");
        if (code != null) {
            boolean flag = service.active(code);
            String msg;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='/#/login'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }
    }

    @RequestMapping("/exit")
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.销毁session
        req.getSession().invalidate();
        //2.跳转
        /*
        vue不需要后端进行跳转
         */
        //resp.sendRedirect(req.getContextPath() + "/login.html");
    }

    @RequestMapping("/findUser")
    public void findUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //从session中获取登录用户
        Object user = req.getSession().getAttribute("user");
        writeValue(user, resp);
    }

    @RequestMapping("/login")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取用户名和密码
        Map<String, String[]> parameterMap = req.getParameterMap();
        //2.封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        ResultInfo resultInfo = new ResultInfo();

        User u = service.login(user);

        //4.判断用户名或密码是否正确
        if (u == null) {
            //用户名密码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        //5.判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您尚未激活，请登录邮箱激活");
        }
        //6.判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            //登录成功
            resultInfo.setFlag(true);
            resultInfo.setData(u.getUid());
            req.getSession().setAttribute("user", u);
        }
        writeValue(resultInfo, resp);
    }

    /**
     * @Description: 返回该用户所有关注的用户
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/likedPeople")
    public void allLikedPeople(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object objectUser = req.getSession().getAttribute("user");
        User user = (User) objectUser;
        List<User> list = favouriteUserService.allLikedPeople(user);
        writeValue(list, resp);
    }

    /**
     * @Description: 添加用户到关注列表
     * @Param: [req, resp]
     * @return: void
     * @Author: JiaDing
     * @Date: 2020/7/19
     */
    @RequestMapping("/addFavourite")
    public void addFavourite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");
        User user = (User) req.getSession().getAttribute("user");
        favouriteUserService.add(Integer.parseInt(uid), user.getUid());
        service.likedUserAddOneToUserBean(uid);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }



    /**
     * @Description: 把一个目标用户从本用户的关注列表中移除
     * @Param: 只接受post请求
     * uid待处理的用户对象
     * 本用户id从session中获取,不用传入
     * @return: void
     * @Author: Yishiyu
     * @Date: 2020/7/19
     */
    @RequestMapping(value = "/deleteFavourite", method = RequestMethod.POST)
    public void deleteFavourite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");
        User user = (User) req.getSession().getAttribute("user");
        favouriteUserService.delete(Integer.parseInt(uid), user.getUid());
        service.likedUserSubOneToUserBean(uid);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }


    /**
     * @Description: 根据提供的用户id查找特定用户
     * @Param: 只接受post请求
     * uid ==> 用户id
     * @return: 以json形式返回用户
     * @Author: Yishiyu
     * @Date: 2020/7/19
     */
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.POST)
    public void getUserInfoById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int uid = Integer.parseInt(req.getParameter("uid"));
        User user = service.findByUserId(uid);
        writeValue(user, resp);
    }


    /**
     * @Description: 修改用户的签名
     * @Param: 只接受post请求
     *          用户的id根据session得到,不用传入
     *          新签名封装在comment参数中
     * @return: void
     * @Author: Yishiyu
     * @Date: 2020/7/19
     */
    @RequestMapping(value = "/modifyUserComment", method = RequestMethod.POST)
    public void modifyUserComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        String comment = req.getParameter("comment");
        user.setComment(comment);
        service.updateUser(user);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }


    /**
     * @Description: 查看一个用户是否已关注
     * @Param: 只接受post请求
     * uid待查询的用户对象
     * 本用户id从session中获取,不用传入
     * @return: 以json形式返回 "本用户" 是否已关注 "uid代表的用户"
     * @Author: Yishiyu
     * @Date: 2020/7/19
     */
    @RequestMapping(value = "/isFavourite", method = RequestMethod.POST)
    public void isFavourite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        int uid = Integer.parseInt(req.getParameter("uid"));
        boolean isFavourite = favouriteUserService.isFavouriteUser(uid, user.getUid());
        writeValue(isFavourite, resp);
    }


    /**
     * @Description: 修改用户密码
     * @Param: 只接受post请求
     * 本用户id从session中获取,不用传入
     * password为新密码
     * @return:
     * @Author: Yishiyu
     * @Date: 2020/7/19
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public void modifyPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String password = req.getParameter("password");
        User user = (User) req.getSession().getAttribute("user");
        user.setPassword(password);
        service.updateUser(user);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }


    /**
     * @Description: 修改用户头像
     * @前端示例代码: <form action="http://localhost:8080/user/uploadHeadPortrait" method="post" enctype="multipart/form-data">
     * 选择文件:<input type="file" name="file" width="120px">
     * <input type="submit" value="上传">
     * </form>
     * @Param: 只接受post请求
     * 本用户id从session中获取,不用传入
     * 不需要上传文件名称
     * @return: void
     * @Author: Yishiyu
     * @Date: 2020/7/19
     */
    @RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST)
    public void uploadHeadPortrait(MultipartFile file, HttpServletRequest req,HttpServletResponse resp) throws IOException {
        InputStream inputStream = file.getInputStream();
        String head_portrait = QiniuyunUtil.uploadObject(inputStream);
        User user = (User) req.getSession().getAttribute("user");
        user.setHead_portrait(head_portrait);
        service.updateUser(user);
        writeValue(ResultInfo.getTrueResultInfo(),resp);
    }

}
