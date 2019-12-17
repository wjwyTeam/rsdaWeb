/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 16:06:38
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-17 17:09:52
 */
package com.wjwy.rsda.tool;

import com.wjwy.rsda.tool.server.Server;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 服务器监控
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController
{
    private String prefix = "monitor/server";

    @GetMapping()
    public String server(ModelMap mmap) throws Exception
    {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return prefix + "/server";
    }
}
