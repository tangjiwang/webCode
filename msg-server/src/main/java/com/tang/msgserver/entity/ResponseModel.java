package com.tang.msgserver.entity;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

/**
 * @program: gitCode
 * @description: 返回实体
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 13:40
 **/
public class ResponseModel<T> implements Serializable {

    private int respCode;//返回码

    private String respDesc;//返回描述

    private T respData;//返回数据

    public ResponseModel(int respCode, String respDesc, T respData) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.respData = respData;
    }

    public ResponseModel(int respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }

    public ResponseModel() {
    }

    public static <T> ResponseEntity<ResponseModel<T>> success() {
        return new ResponseEntity<ResponseModel<T>>(new ResponseModel<T>(0, "success"), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseModel<T>> success(T result) {
        return new ResponseEntity<ResponseModel<T>>(new ResponseModel<T>(0, "success", result), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseModel<T>> error(HttpStatus httpStatus, int respCode, String respDesc) {
        return new ResponseEntity<ResponseModel<T>>(new ResponseModel<T>(respCode, respDesc), httpStatus);
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "respCode=" + respCode +
                ", respDesc='" + respDesc + '\'' +
                ", respData=" + respData +
                '}';
    }
}
