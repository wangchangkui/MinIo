package com.myxiaowang.minioutil.config;

import com.myxiaowang.minioutil.entity.MinIoEntity;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年06月07日 10:44:00
 */
@Configuration
@EnableConfigurationProperties(MinIoEntity.class)
public class MinIoConfig {
    @Autowired
    private MinIoEntity minIoEntity;

    @Bean
     public MinioClient getMinioClient() {
         return  MinioClient.builder().endpoint(minIoEntity.getEndpoint()).credentials(minIoEntity.getAccessKey(), minIoEntity.getSecretKey()).build();
     }
}
