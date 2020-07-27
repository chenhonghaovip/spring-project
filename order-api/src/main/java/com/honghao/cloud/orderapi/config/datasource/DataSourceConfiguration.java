package com.honghao.cloud.orderapi.config.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenhonghao
 * @date 2020-06-09 20:37
 */
@Configuration
public class DataSourceConfiguration {
    @Value("${spring.application.name}")
    private String applicationId;


//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        return druidDataSource;
//    }
//
//    @Primary
//    @Bean("dataSource")
//    //6.1 创建DataSourceProxy
//    public DataSourceProxy dataSourceProxy(DataSource druidDataSource){
//        return new DataSourceProxy(druidDataSource);
//    }
//
//    @Bean
//    //6.2 将原有的DataSource对象替换为DataSourceProxy
//    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy)throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:/mapper/*.xml"));
//        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
//        return sqlSessionFactoryBean.getObject();
//    }
}
