package com.example.manage.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.io.IOException;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.config
 * @Description 数据库配置
 * @Date 2017/11/30
 */
@Configuration
@MapperScan(basePackages = { "com.example.manage.mapper" }, annotationClass = MybatisRepository.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfig implements TransactionManagementConfigurer{

     private String driverClassName = "com.mysql.jdbc.Driver";
     private String url =
     "";
     private String username = "";
     private String password = "";
     private boolean defaultAutoCommit = true;
     private int maxActive = 2;
     private int maxIdle = 2;
     private int minIdle = 1;
     private int maxWait = 60000;

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource getDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(120);
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        dataSource.setNumTestsPerEvictionRun(30);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean getSqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        // 不知道为何注解扫描无效(生产状态)
        // sqlSessionFactory.setTypeAliasesPackage("com.chaney.bigata.security.entity");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:META-INF/configuration.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:META-INF/mybatis/*Mapper.xml"));

        return sqlSessionFactory;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
