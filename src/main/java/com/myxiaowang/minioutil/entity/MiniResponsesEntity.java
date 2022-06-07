package com.myxiaowang.minioutil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年06月07日 10:46:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniResponsesEntity {

    /**
     * minio返回的地址
     */
    private String minioUrl;

    /**
     * 域名返回的地址
     */
    private String nginxUrl;
}
