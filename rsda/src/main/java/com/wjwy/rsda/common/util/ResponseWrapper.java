package com.wjwy.rsda.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import com.wjwy.rsda.common.ResponseMessageConstant;

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
    private Integer infoCode;
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



    private ResponseWrapper() {

    }

    /**
     * @param status   状态
     * @param infoCode 响应码
     * @param msg      响应信息
     * @param data     传输数据
     */
    private ResponseWrapper(StatusEnum status, Integer infoCode, String msg, Object data,String indexUrl) {
        this.msg = msg;
        this.status = status;
        this.infoCode = infoCode;
        this.data = data;
        this.indexUrl = indexUrl;
    }

    public static ResponseWrapper success(Integer infoCode, String info, Object data,String indexUrl) {
        return new ResponseWrapper(StatusEnum.SUCCESS, infoCode
                , info, data,indexUrl);
    }

    public static ResponseWrapper success(Object data) {
        return new ResponseWrapper( StatusEnum.SUCCESS, 200
                , ResponseMessageConstant.MSG_SUCCESS, data,"");
    }
    public static ResponseWrapper success(Object data,String indexUrl) {
        return new ResponseWrapper( StatusEnum.SUCCESS, 200
                , ResponseMessageConstant.MSG_SUCCESS, data,indexUrl);
    }
    public static ResponseWrapper success(String info) {
        return new ResponseWrapper( StatusEnum.SUCCESS, 200
                , info, "","");
    }

    public static ResponseWrapper success() {
        return new ResponseWrapper( StatusEnum.SUCCESS, 200
                , ResponseMessageConstant.MSG_SUCCESS, "","");
    }

    public static ResponseWrapper error(Integer infoCode, String info, Object data) {
        return new ResponseWrapper(StatusEnum.ERROR, infoCode
                , info, data,"");
    }


    public static ResponseWrapper error(String info){
        return new ResponseWrapper( StatusEnum.ERROR, 500, info, "","");
    }

    public static ResponseWrapper error() {
        return new ResponseWrapper( StatusEnum.ERROR, 500
                , ResponseMessageConstant.MSG_ERROR, "","");
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Integer getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(Integer infoCode) {
        this.infoCode = infoCode;
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
        SUCCESS,
        ERROR
    }

    /**
     * 修改,插入  数据校验
     * @return
     */
    public static ResponseWrapper validateFail(List<ObjectError> errorList) {
        List<String> list=new ArrayList<>();
        for(ObjectError objectError:errorList){
            list.add(objectError.getDefaultMessage());
        }
        return new ResponseWrapper( StatusEnum.ERROR, 412
                , StringUtils.join(list,"<br/>"), "", "");
    }

    /**
     * 修改,插入  数据校验
     * @return
     */
    public static ResponseWrapper validateFail(List<ObjectError> errorList,String separate) {
        List<String> list=new ArrayList<>();
        for(ObjectError objectError:errorList){
            list.add(objectError.getDefaultMessage());
        }
        return new ResponseWrapper( StatusEnum.ERROR, 412
                , StringUtils.join(list,separate), "","");
    }

    /**
     * 根据返回的记录数目来决定返回成功还是失败,一般用于新增,修改,删除
     *
     * @return
     */
    public static ResponseWrapper choose(int num) {
        if (num > 0) {
            return success(200, ResponseMessageConstant.MSG_SUCCESS, null, "");
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
        return new ResponseWrapper(StatusEnum.ERROR, 400
                , ResponseMessageConstant.MSG_INVALID_DATA, "","");
    }

    /**
     * 403 无权限访问的接口,数据或者数据对当前用户是不允许操作
     *
     * @return
     */
    public static ResponseWrapper deny() {
        return new ResponseWrapper(StatusEnum.ERROR, 403
                , ResponseMessageConstant.MSG_DENY_MODIFY, "","");
    }

    /**
     * 405 数据重复了
     * @return
     */
    public static ResponseWrapper duplicate() {
        return new ResponseWrapper( StatusEnum.ERROR, 405
                , ResponseMessageConstant.MSG_DENY_DUPLICATE, "","" );
    }

    /**
     * 404 预期可以得到数据却未得到,数据可能已经被删除或不在可操作的状态
     *
     * @return
     */
    public static ResponseWrapper notFound() {
        return new ResponseWrapper(StatusEnum.ERROR, 404
                , ResponseMessageConstant.MSG_NOT_FOUND, "","");
    }

    /**
     * 修改,插入,删除等修改记录条数为0
     *
     * @return
     */
    public static ResponseWrapper fail() {
        return new ResponseWrapper(StatusEnum.ERROR, 404
                , ResponseMessageConstant.MSG_FAIL, "","");
    }

    public boolean isSuccess() {
        return this!=null&&this.infoCode==200;
    }

}

