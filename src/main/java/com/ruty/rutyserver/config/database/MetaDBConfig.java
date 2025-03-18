package com.ruty.rutyserver.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MetaDBConfig {

    // datasource를 선언할때 디비 개수마다 이를 작성함. 이때 @Primary를 통해서 중복방지.
    @Primary
    @Bean
    // application.properties의 변수값을 자동으로 불러오는 역할
    @ConfigurationProperties(prefix = "spring.datasource-meta")
    public DataSource meatDBSource() {
        // datasource-meta값이 우선순위가 되도록 등록되게 됨.
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager metaTransactionManager() {
        return new DataSourceTransactionManager(meatDBSource());
    }

}
