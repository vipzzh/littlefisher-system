package com.littlefisher.core.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.github.pagehelper.PageInterceptor;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;

/**
 * Description:
 *
 * Created on 2017年4月18日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Configuration
public class SqlSessionFactoryBeanConfig {

    /**
     * dataSource
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Description: 创建SqlSessionFactoryBean
     *
     * @return SqlSessionFactoryBean
     * @throws IOException IOException
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 解析classpath
        Resource[] littlefisherResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:com/littlefisher/**/mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(littlefisherResources);
        Interceptor[] plugins = { pageInterceptor() };
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean;
    }

    /**
     * Description: 引入分页插件
     *
     * @return PageInterceptor
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("helperDialect", "mysql");
        properties.put("pageSizeZero", true);
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
