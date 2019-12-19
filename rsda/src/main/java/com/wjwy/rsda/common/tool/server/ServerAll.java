/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 16:06:38
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 14:13:35
 */
package com.wjwy.rsda.common.tool.server;

import com.wjwy.rsda.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 服务器监控
 */
@Controller
@RequestMapping("/monitor")
public class ServerAll {

    /**
     * 本机配置
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/server")
    public ModelAndView server(ModelAndView model) throws Exception {
        Server server = new Server();
        server.copyTo();
        model.addObject("server", server);
        model.setViewName("webview/system/server/server");
        return model;
    }


    /**
     * 数据源
     * @param 
     * @return
     * @throws Exception
     */
    @GetMapping("/data")
    public String druid() {
        return  StringUtils.format("redirect:{}", "/druid");
    }


    /**
     * Swagger
     * @return
     */
    @GetMapping("/swagger")
    public String Swagger() {
        return  StringUtils.format("redirect:{}", "/swagger-ui.html");
    }
}
