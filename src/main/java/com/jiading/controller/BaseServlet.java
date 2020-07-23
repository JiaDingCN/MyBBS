package com.jiading.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 优化servlet
 * 将现在的一个功能一个servlet优化为一个模块一个servlet
 * 数据库一张表对应一个servlet，在servlet中提供不同方法以完成不同的请求
 * 我们写的Servlet不再继承自HttpServlet，而是继承这个我们写的BaseServlet
 * BaseServlet继承自HttpServlet，对原有方法改造以完成方法的分发
 * 注意我们并不配置BaseServlet的注解，因为我们不希望它被直接访问到，它只是用来被继承的
 * checked:7/18
 */
@Controller
public class BaseServlet extends HttpServlet {
    /**
     * 这里我们没有选择springMVC的Conytroller和jsp之间的数据传递方法，也就是使用Model传递数据，而是依然使用了普通的json，
     * 使用jackson将对象转换为json,然后使用jackson的writeValue方法将json写回response中供前台jquery调用
     *
     * @param obj
     * @param response
     * @throws IOException
     */
    //序列化json方法,将传入的对象序列化并写回客户端
    public void writeValue(Object obj, HttpServletResponse response) throws IOException {
        //序列化json返回
        /*
        Jackson是著名的将java对象转换为json类型的工具
        ObjectMapper是jackson的核心对象
         */
        ObjectMapper mapper = new ObjectMapper();
        //手动设置返回值类型
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //  这里最好明确的写允许的域名
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,Authorization,ybg");
        mapper.writeValue(response.getOutputStream(), obj);
    }

    //将传入的对象序列化为json并返回给调用者
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(obj);
        return s;
    }
}
