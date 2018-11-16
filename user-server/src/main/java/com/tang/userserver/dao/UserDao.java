package com.tang.userserver.dao;

import com.tang.userserver.domain.User;
import com.tang.userserver.exception.SPTException;

import java.util.List;
import java.util.Map;

/**
 * 用户表操作接口
 */
public interface UserDao {
     int addUser(User user) throws SPTException;
     List<Map<String,Object>> queryUserLogin(User user) throws  SPTException;
}
