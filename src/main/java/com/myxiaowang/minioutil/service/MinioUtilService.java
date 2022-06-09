package com.myxiaowang.minioutil.service;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年06月09日 17:35:00
 */
public interface MinioUtilService {

    /**
     * 创建存储桶
     * @param bucketName 桶文件
     * @return boolean 结果
     */
    boolean createBucket(String bucketName);

    /**
     * 删除存储桶
     * @param bucketName 桶名称
     * @return String 结果
     */
    String removeBucket(String bucketName);
}
