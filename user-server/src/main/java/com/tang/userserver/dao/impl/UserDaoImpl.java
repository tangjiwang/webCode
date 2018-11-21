package com.tang.userserver.dao.impl;

import com.tang.userserver.dao.ICommonDao;
import com.tang.userserver.dao.UserDao;
import com.tang.userserver.domain.User;
import com.tang.userserver.exception.SPTException;
import com.tang.userserver.util.DateUtil;
import com.tang.userserver.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: gitCode
 * @description: 用户登录 增删改查操作
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-16 16:00
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserDaoImpl implements UserDao {
    @Autowired
    private ICommonDao commonDao;

    /**
     * 新增用户
     */
    private static String ADD_USER = "insert into t_user (name,passwd,phone,regist_time,identity,del_flag,regist_channel) " +
            "values (:name,:passwd,:phone,:regist_time,0,0,:regist_channel)";

    /**
     * 通过用户名查找密码
     */
    private static String QUERY_PASSWDBYNAME = "select passwd from t_user where name =:name and del_flag = 0";

    @Override
    public int addUser(User user) throws SPTException {

        StringBuilder sb = new StringBuilder(ADD_USER);
        String name = user.getUserName();
        String passwd = user.getPasswd();
        String phone = user.getPhoneNo();
        String registTime = user.getRegistTime();
        String registChannel = user.getRegistChannel();

        Map<String, Object> map = new HashMap();
        map.put("name", name);
        map.put("passwd", passwd);
        map.put("phone", phone);
        map.put("regist_time", registTime);
        map.put("regist_channel", registChannel);
        return commonDao.update(sb.toString(), map);

    }

    @Override
    public List<Map<String, Object>> queryPasswdByName(String name) throws SPTException {
        StringBuilder sb = new StringBuilder(QUERY_PASSWDBYNAME);
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        return commonDao.queryForList(sb.toString(),map);
    }
}
