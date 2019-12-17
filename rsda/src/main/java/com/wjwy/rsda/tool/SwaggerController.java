/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 15:08:12
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-17 16:06:49
 */
package com.wjwy.rsda.tool;

import com.wjwy.rsda.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * swagger 接口
 * 
 * @author ZHANGQI
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController {
    @GetMapping()
    public String index() {
        return  StringUtils.format("redirect:{}", "/swagger-ui.html");
    }
}
