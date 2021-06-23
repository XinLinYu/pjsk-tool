package com.projectsekai.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName SmConfig
 * @description
 * @date 2021/6/23 0023 下午 3:54
 **/
@Configuration
@MapperScan(basePackages = "com.projectsekai.mapper")
@ComponentScan("com.projectsekai")
public class DataSourceConfig {
    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://47.108.207.77:3306/projectsekai?serverTimezone=GMT%2b8");
        ds.setUsername("root");
        ds.setPassword("zjp19971113106");
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        // 设置xml的映射文件位置
        sessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:sqlmapper/*.xml"));
        // 设置别名的包，可以在xml中直接使用user,
        sessionFactory.setTypeAliasesPackage("com.projectsekai.entity");
        return sessionFactory.getObject();
    }
}