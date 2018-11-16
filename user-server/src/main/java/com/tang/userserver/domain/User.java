package com.tang.userserver.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: gitCode
 * @description: 用户bean
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 16:26
 **/
public class User implements Serializable{
        @ApiModelProperty("用户名")
        private String userName;
        @ApiModelProperty("手机号码")
        private String phoneNo;
        @ApiModelProperty("登录账号密码")
        private String passwd;
        @ApiModelProperty("身份证")
        private String idCard;//身份证
        @ApiModelProperty("居住地")
        private String addRess;//居住地
        @ApiModelProperty("邮箱地址")
        private String emailAddress;//邮件
        @ApiModelProperty("注册渠道 '0' : ios, '1' : anrioid , '2' : 'pc'")
        private String registChannel;//注册渠道
        @ApiModelProperty("注册时间")
        private String registTime;
        @ApiModelProperty("首次登录时间")
        private String firstLoginTime;
        @ApiModelProperty("最后一次登录时间")
        private String lastLoginTime;
        @ApiModelProperty("验证码")
        private String verifyCode;

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

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getAddRess() {
            return addRess;
        }

        public void setAddRess(String addRess) {
            this.addRess = addRess;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getRegistChannel() {
            return registChannel;
        }

        public void setRegistChannel(String registChannel) {
            this.registChannel = registChannel;
        }

        public String getRegistTime() {
            return registTime;
        }

        public void setRegistTime(String registTime) {
            this.registTime = registTime;
        }

        public String getFirstLoginTime() {
            return firstLoginTime;
        }

        public void setFirstLoginTime(String firstLoginTime) {
            this.firstLoginTime = firstLoginTime;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", passwd='" + passwd + '\'' +
                ", idCard='" + idCard + '\'' +
                ", addRess='" + addRess + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", registChannel='" + registChannel + '\'' +
                ", registTime='" + registTime + '\'' +
                ", firstLoginTime='" + firstLoginTime + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
