package com.myxiaowang.minioutil.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年06月07日 10:30:00
 */
@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinIoEntity {

    /**
     * minio的 endpoint
     */
    private String endpoint;

    /**
     * minio的 accessKey 账号
     */
    private String accessKey;

    /**
     * minio的 secretKey 密码
     */
    private String secretKey;

    /**
     * nginx 的地址 域名
     */
    private String nginxHost;

    /**
     * 最大文件大小
     */
    private Long maxSize;
}
