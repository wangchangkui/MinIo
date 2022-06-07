package com.myxiaowang.minioutil.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wck
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年06月07日 10:30:00
 */
@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinIoEntity {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String nginxHost;
}
