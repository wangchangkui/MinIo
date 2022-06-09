package com.myxiaowang.minioutil.service.impl;

import com.myxiaowang.minioutil.entity.MiniResponsesEntity;
import com.myxiaowang.minioutil.service.MinioUtilService;
import com.myxiaowang.minioutil.util.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年06月09日 17:34:00
 */

@Service
public class MinioUtilServiceImpl implements MinioUtilService {

    @Resource
    private MinioUtil minioUtil;

    @Override
    public boolean createBucket(String bucketName) {
        return minioUtil.createBucket(bucketName);
    }

    @Override
    public String removeBucket(String bucketName) {
        try {
            minioUtil.removeBucket(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
        return "删除成功";
    }

    @Override
    public MiniResponsesEntity uploadFile(String bucketName, MultipartFile file) {
     return minioUtil.uploadFile(file, bucketName);
    }


}
