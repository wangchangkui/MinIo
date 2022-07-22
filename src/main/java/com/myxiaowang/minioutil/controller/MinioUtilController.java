package com.myxiaowang.minioutil.controller;

import com.myxiaowang.minioutil.entity.MiniResponsesEntity;
import com.myxiaowang.minioutil.service.MinioUtilService;
import com.myxiaowang.minioutil.util.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wck
 * @version 1.0.0
 * @Description 其实不推荐使用这些接口 我想我应该弄到Service里面去
 * @createTime 2022年06月07日 11:53:00
 */
@Slf4j
@RestController
@RequestMapping("/minio")
@RequiredArgsConstructor
public class MinioUtilController {

    @Value("${minio.defualtBucket}")
    private String defualtBucket;
    private final MinioUtilService minioUtilService;

    private final MinioUtil minioUtil;

    @GetMapping("/create/{bucketName}")
    public boolean createBucket( @PathVariable String bucketName){
        return minioUtilService.createBucket(bucketName);
    }

    @DeleteMapping("/deleteBucket/{bucketName}")
    public String removeBucket(@PathVariable String bucketName){
        return minioUtilService.removeBucket(bucketName);
    }

    @GetMapping("/getBucketList")
    public List<String> getBucketList() throws Exception {
      return  minioUtil.getAllBuckets();
    }


    @PostMapping("/uploadFile")
    public MiniResponsesEntity uploadFile(String bucketName, MultipartFile file){
        return minioUtilService.uploadFile(bucketName,file);
    }

    @PostMapping("/defualtBucket/uploadFile")
    public MiniResponsesEntity uploadFile( MultipartFile file){
        return uploadFile(defualtBucket,file);
    }

    @PostMapping("/defualtBucket/uploadFileByPico")
    public String uploadFileByPico( MultipartFile file){
        return uploadFile(defualtBucket,file).getMinioUrl();
    }

}
