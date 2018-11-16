package com.tang.msgserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/** 
* @Description: 数据库连接池配置 
* @Param:  
* @return:  
* @Author: tangjiwang
* @Date: 2018/11/16 
*/ 
@Configuration
@RefreshScope
public class DBConfig {

    private static final Logger LOGGER = LogManager.getLogger(DBConfig.class);

    @Value("${spring.druid.datasource.url}")
    private String url;

    @Value("${spring.druid.datasource.username}")
    private String username;

    @Value("${spring.druid.datasource.password}")
    private String password;

    @Value("${spring.druid.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.druid.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.druid.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.druid.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.druid.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.druid.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.druid.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.druid.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.druid.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.druid.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.druid.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.druid.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.druid.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.druid.datasource.filters}")
    private String filters;

    @Value("${spring.druid.datasource.connectionProperties}")
    private String connectionProperties;

    @Value("${spring.druid.datasource.maxCount}")
    private int maxCount;

    @Bean
    @RefreshScope
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            LOGGER.error("druid configuration initialization filter", e);
        }
        dataSource.setConnectionProperties(connectionProperties);

        return dataSource;
    }

    @Bean
    @RefreshScope
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", "127.0.0.1"); //设置白名单
        reg.addInitParameter("deny", ""); // 设置黑名单
        reg.addInitParameter("loginUsername", "admin");
        reg.addInitParameter("loginPassword", "admin");
        return reg;
    }

    @Bean
    @RefreshScope
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


}


