package com.wjwy.rsda.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import com.wjwy.rsda.enums.MessageConstant;

/**
 * @author xmpu
 * @version 1.0
 * @title ResponseWrapper
 * @projectName ResponseWrapper
 * @description http返回状态
 * @date 2019/7/7 17:07
 */
public class ResponseWrapper {

    /**
     * 状态
     */
    private StatusEnum status;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 传输数据
     */
    private Object data;
    /**
     * 指向路径
     */
    private String indexUrl;

    //页码条数
    private Integer count;


    private ResponseWrapper() {

    }

    /**
     * @param status   状态
     * @param code 响应码
     * @param msg      响应信息
     * @param data     传输数据
     */
    private ResponseWrapper(StatusEnum status, Integer code, String msg, Object data, String indexUrl,Integer count) {
        this.msg = msg;
        this.status = status;
        this.code = code;
        this.data = data;
        this.indexUrl = indexUrl;
        this.count = count;
    }


    public static ResponseWrapper success(Integer code, String info, Object data, String indexUrl,Integer count) {
        return new ResponseWrapper(StatusEnum.SUCCESS, code, info, data, indexUrl,count);
    }

    public static ResponseWrapper success(Object data) {
        return new ResponseWrapper(StatusEnum.SUCCESS, 200, MessageConstant.MSG_SUCCESS, data, "",null);
    }

    public static ResponseWrapper success(Object data, String indexUrl) {
        return new ResponseWrapper(StatusEnum.SUCCESS, 200, MessageConstant.MSG_SUCCESS, data, indexUrl,null);
    }

    public static ResponseWrapper success(String info) {
        return new ResponseWrapper(StatusEnum.SUCCESS, 200, info, "", "",null);
    }

    public static ResponseWrapper success() {
        return new ResponseWrapper(StatusEnum.SUCCESS, 200, MessageConstant.MSG_SUCCESS, "", "",null);
    }

    public static ResponseWrapper error(Integer code, String info, Object data) {
        return new ResponseWrapper(StatusEnum.ERROR, code, info, data, "",null);
    }

    public static ResponseWrapper error(String info) {
        return new ResponseWrapper(StatusEnum.ERROR, 500, info, "", "",null);
    }

    public static ResponseWrapper error() {
        return new ResponseWrapper(StatusEnum.ERROR, 500, MessageConstant.MSG_ERROR, "", "",null);
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum StatusEnum {
        SUCCESS, ERROR
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount(){
        return count;
    }
    /**
     * 修改,插入 数据校验
     * 
     * @return
     */
    public static ResponseWrapper validateFail(List<ObjectError> errorList) {
        List<String> list = new ArrayList<>();
        for (ObjectError objectError : errorList) {
            list.add(objectError.getDefaultMessage());
        }
        return new ResponseWrapper(StatusEnum.ERROR, 412, StringUtils.join(list, "<br/>"), "", "",null);
    }

    /**
     * 修改,插入 数据校验
     * 
     * @return
     */
    public static ResponseWrapper validateFail(List<ObjectError> errorList, String separate) {
        List<String> list = new ArrayList<>();
        for (ObjectError objectError : errorList) {
            list.add(objectError.getDefaultMessage());
        }
        return new ResponseWrapper(StatusEnum.ERROR, 412, StringUtils.join(list, separate), "", "",null);
    }

    /**
     * 根据返回的记录数目来决定返回成功还是失败,一般用于新增,修改,删除
     *
     * @return
     */
    public static ResponseWrapper choose(int num) {
        if (num > 0) {
            return success(200, MessageConstant.MSG_SUCCESS, null, "",null);
        } else {
            return fail();
        }
    }

    /**
     * 400 参数错误或数据不完整,缺少必填项,格式错误等等
     *
     * @return
     */
    public static ResponseWrapper invalid() {
        return new ResponseWrapper(StatusEnum.ERROR, 400, MessageConstant.MSG_INVALID_DATA, "", "",null);
    }

    /**
     * 403 无权限访问的接口,数据或者数据对当前用户是不允许操作
     *
     * @return
     */
    public static ResponseWrapper deny() {
        return new ResponseWrapper(StatusEnum.ERROR, 403, MessageConstant.MSG_DENY_MODIFY, "", "",null);
    }

    /**
     * 405 数据重复了
     * 
     * @return
     */
    public static ResponseWrapper duplicate() {
        return new ResponseWrapper(StatusEnum.ERROR, 405, MessageConstant.MSG_DENY_DUPLICATE, "", "",null);
    }

    /**
     * 404 预期可以得到数据却未得到,数据可能已经被删除或不在可操作的状态
     *
     * @return
     */
    public static ResponseWrapper notFound() {
        return new ResponseWrapper(StatusEnum.ERROR, 404, MessageConstant.MSG_NOT_FOUND, "", "",null);
    }

    /**
     * 修改,插入,删除等修改记录条数为0
     *
     * @return
     */
    public static ResponseWrapper fail() {
        return new ResponseWrapper(StatusEnum.ERROR, 404, MessageConstant.MSG_FAIL, "", "",null);
    }

    public boolean isSuccess() {
        return this != null && this.code == 200;
    }

}
