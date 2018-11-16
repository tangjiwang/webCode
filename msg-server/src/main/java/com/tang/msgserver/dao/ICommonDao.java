package com.tang.msgserver.dao;

import com.tang.msgserver.domain.Page;
import com.tang.msgserver.exception.SPTException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;
import java.util.Map;

/** 
* @Description: 为所有Service层提供访问数据库接口
* @Param:  
* @return:  
* @Author: tangjiwang
* @Date: 2018/11/16 
*/ 
public interface ICommonDao {

    /**
     * 方法描述:查询出唯一的一条数据，查询不到返回空，查询出多条抛出异常
     * @param sql 执行的sql语句
     * @param paramMap 参数注入的HashMap<String,Object>对象
     * @return 查询到的结果集
     */
    public Map<String,Object> queryForMap(String sql, Map<String, ?> paramMap) throws SPTException;

    /**
     * 方法描述:查询出唯一的一条数据，并且将这条数据自动映射成指定的类型返回，查询不到返回空，查询出多条抛出异常.
     * @param sql 执行的sql语句
     * @param paramMap 参数注入的HashMap<String,Object>对象
     * @param clazz 需要自动映射类型的class
     * @return 查询到的对象
     * date:2017-07-31
     * add by: lurufeng
     */
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> clazz) throws SPTException;

    /**
     * 方法描述:不带参数查询出唯一的一条数据，并且将这条数据自动映射成指定的类型返回，查询不到返回空，查询出多条抛出异常.
     * @param sql 执行的sql语句
     * @param clazz 需要自动映射类型的class
     * @return 查询到的对象
     * date:2017-07-31
     * add by: lurufeng
     */
    public <T> T queryForObject(String sql, Class<T> clazz) throws SPTException;

    /**
     * 方法描述:列表查询.
     * @param sql 执行的sql语句
     * @param paramMap 参数注入的HashMap<String,Object>对象
     * @return 查询到的结果集
     */
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) throws SPTException;

    /**
     * 方法描述:不带参数的列表查询
     * @param sql 执行的sql语句
     * @return 查询到的结果集
     */
    public List<Map<String,Object>> queryForList(String sql) throws SPTException;

    /**
     * 方法描述:列表查询(带分页，page为空时则不进行分页).
     * @param sql 执行的sql语句
     * @param paramMap 参数注入的HashMap<String,Object>对象
     * @return 查询到的结果集
     */
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap, Page page) throws SPTException;

    /**
     * 方法描述:列表查询
     * @param sql
     * @param parameterSource
     * @return
     * @throws SPTException
     */
    public List<Map<String, Object>> queryForList(String sql, MapSqlParameterSource parameterSource) throws SPTException;

    /**
     * 方法描述:列表查询(带分页，page为空时则不进行分页).
     * @param sql
     * @param parameterSource
     * @param page
     * @return
     * @throws SPTException
     */
    public List<Map<String, Object>> queryForList(String sql, MapSqlParameterSource parameterSource, Page page) throws SPTException;

    /**
     * 方法描述:列表查询，并且将每行数据自动映射成对象，形成一个对象列表返回
     * @param sql 执行的sql语句
     * @param paramMap 参数注入的HashMap<String,Object>对象
     * @param clazz 需要自动映射类型的class
     * @return 查询到的结果集
     */
    public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> clazz) throws SPTException;

    public <T> List<T> queryForListNew(String sql, Map<String, ?> paramMap, Class<T> clazz) throws SPTException, Exception;
    /**
     * 方法描述:不带参数的列表查询，并且将每行数据自动映射成对象，形成一个对象列表返回
     * @param sql 执行的sql语句
     * @param clazz 需要自动映射类型的class
     * @return 查询到的结果集
     */
    public <T> List<T> queryForList(String sql, Class<T> clazz) throws SPTException;

    /**
     * 方法描述:列表查询(带分页，page为空时则不进行分页)，并且将每行数据自动映射成对象，形成一个对象列表返回，。
     * @param selectSql - 结果查询语句
     * @param paramMap - 查询参数
     * @param page - 分页信息
     * @param clazz 需要自动映射类型的class
     * @return 查询到的结果集
     */
    public <T> List<T> queryForList(String selectSql, Map<String, ?> paramMap, Page page, Class<T> clazz) throws SPTException;

    /**
     * 方法描述:普通DML操作,如insert,update,delete
     * @param sql - 查询语句
     * @param paramMap - 参数注入的对象
     * @return 普通DML操作影响到的条数
     */
    public int update(String sql, Map<String, ?> paramMap) throws SPTException;

    /**
     * 方法描述:批量普通DML操作,如insert,update,delete
     * @param sql 执行的sql语句
     * @param paramListMap 批量参数
     * @return 批量执行普通DML操作时每条语句影响的条数数组，数组中每个int数字含义解释：0：没有影响到记录，-2有影响（批量执行不能精确知道影响的条数）
     */
    public int[] batchUpdate(String sql, List<Map<String, ?>> paramListMap) throws SPTException;

    /**
     * 方法描述: 批量获取sequence值
     * @param sequenceName	- sequence名称
     * @param size			- 一次获取多少个?
     * @return 批量的sequence值列表
     */
    public List<Long> querySequenceNextValues(String sequenceName, final int size) throws SPTException;

    /**
     * 方法描述:count,sum,max等查询
     * @param sql - 查询语句
     * @param paramMap - 参数注入的对象
     * @return 查询到的count,sum,max等结果
     */
    public int queryForInt(String sql, Map<String, ?> paramMap) throws SPTException;

    /**
     * 方法描述:count,sum,max等查询
     * @param sql - 查询语句
     * @param parameterSource - 参数注入的对象
     * @return 查询到的count,sum,max等结果
     */
    public int queryForIntByMapSqlParameterSource(String sql, MapSqlParameterSource parameterSource) throws SPTException;

    /**
     * 方法描述:调用存储过程
     * @param name 存储过程名称，区分大小写，创建存储过程时用的名称
     * @param params 存储过程所需的in参数值，key为参数名，value为参数值
     * @return 存储过程执行后的out参数获取值
     * @throws SPTException
     */
    public Map<String,Object> execProcedure(String name, Map<String, Object> params) throws SPTException;


    /**
     * 获取表格最后插入数据的id
     * @param tableName
     * @param idColume
     * @return
     * @throws SPTException
     */
    public int getLastId(String tableName, String idColume) throws SPTException;
}