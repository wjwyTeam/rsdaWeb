/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 16:06:38
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-17 17:40:36
 */
package com.wjwy.rsda.tool;

import com.wjwy.rsda.tool.server.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 服务器监控
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController {

    @GetMapping()
    public ModelAndView server(ModelAndView model) throws Exception {
        Server server = new Server();
        server.copyTo();
        model.addObject("server", server);
        model.setViewName("webview/system/server/server");
        return model;
    }
}
