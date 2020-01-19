/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-17 15:35:16
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-19 16:12:58
 */
package com.wjwy.rsda.common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjwy.rsda.common.config.ServerConfig;
import com.wjwy.rsda.common.enums.AjaxResult;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.enums.MessageConstant;
import com.wjwy.rsda.common.util.FileUploadUtils;
import com.wjwy.rsda.common.util.FileUtils;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 通用请求处理
 */
@RequestMapping("/upload")
@RestController
@Api(value = "文件管理", tags = "A1-文件管理API维护")
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    /**
     * 用户中心业务
     */
    // @Autowired
    // private UserService userService;

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    @Log(title = "文件管理", businessType = EnumEntitys.ONLOAD)
    @ApiOperation(value = "通用下载请求", notes = "参数:fileName-对象 delete ")
    public void fileDownload(String fileName, Boolean delete, @ApiIgnore HttpServletResponse response,
            HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    @ResponseBody
    @Log(title = "文件管理", businessType = EnumEntitys.UPLOAD)
    @ApiOperation(value = "通用上传请求", notes = "参数:file-对象  ")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    @Log(title = "文件管理", businessType = EnumEntitys.ONLOAD)
    @ApiOperation(value = "本地资源通用下载", notes = "参数:resource-对象  ")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 本地资源路径
        String localPath = Global.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(resource, MessageConstant.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    // public void save() {
    //     // 创建实体
    //     User board = new User();
    //     // byte[] exportBoard;
    //     try {
    //         // exportBoard = InputStream2ByteArray("D:\\ceshi.png");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     // board.setImg(exportBoard);
    //     // 调用保存方法
    //     userService.insert(board);
    //     // 具体业务~略
    // }

    // 主要的工具方法
    public byte[] InputStream2ByteArray(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        return data;
    }

    public byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
 
}
