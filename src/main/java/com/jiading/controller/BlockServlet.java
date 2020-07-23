package com.jiading.controller;


import com.jiading.model.Block;
import com.jiading.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/block")
public class BlockServlet extends BaseServlet {
    @Autowired
    BlockService service;

    List<Block> all;

    /**
     * 获取所有板块
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        all = service.findAll();
        writeValue(all, response);
    }

    /**
     * 获取bid指定的板块
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findByBid")
    public void findOByBid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bid = request.getParameter("bid");
        int bidInt = Integer.parseInt(bid);
        writeValue(service.findByBid(bidInt), response);
    }

    /**
     * 获取name指定的板块
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findByName")
    public void findByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        writeValue(service.findByName(name), response);
    }

}
