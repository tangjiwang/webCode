package com.tang.userserver.dao;

import com.tang.userserver.domain.User;
import com.tang.userserver.exception.SPTException;

import java.util.List;
import java.util.Map;

/**
 * 用户表操作接口 t_user表增删改查
 */
public interface UserDao {
     /**
      * 新增用户
      * @param user
      * @return
      * @throws SPTException
      */
     int addUser(User user) throws SPTException;

     /**
      * 通过用户名查找密码
      * @param name
      * @return
      * @throws SPTException
      */
     List<Map<String,Object>> queryPasswdByName(String name) throws  SPTException;

     /**
      * 通过用户名重置登陆密码
      * @param name
      * @return
      * @throws SPTException
      */
     int resetPasswd(String name,String passwd) throws SPTException;
}
