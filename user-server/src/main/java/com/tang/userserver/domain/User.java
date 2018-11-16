package com.tang.userserver.domain;

import java.io.Serializable;

/**
 * @program: gitCode
 * @description: 用户bean
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 16:26
 **/
public class User implements Serializable {
    private String userName;
    private String phoneNo;
    private String passwd;
    private String id;//身份证
    private String addRess;//居住地
    private String email;//邮件
    private String verifiyCode;//手机验证码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddRess() {
        return addRess;
    }

    public void setAddRess(String addRess) {
        this.addRess = addRess;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifiyCode() {
        return verifiyCode;
    }

    public void setVerifiyCode(String verifiyCode) {
        this.verifiyCode = verifiyCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", passwd='" + passwd + '\'' +
                ", id='" + id + '\'' +
                ", addRess='" + addRess + '\'' +
                ", email='" + email + '\'' +
                ", verifiyCode='" + verifiyCode + '\'' +
                '}';
    }
}
