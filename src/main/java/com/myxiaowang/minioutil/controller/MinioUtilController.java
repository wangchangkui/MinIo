package com.myxiaowang.minioutil.controller;

import com.myxiaowang.minioutil.service.MinioUtilService;
import com.myxiaowang.minioutil.util.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private final MinioUtilService minioUtilService;

    @GetMapping("/create/{bucketName}")
    public boolean createBucket( @PathVariable String bucketName){
        return minioUtilService.createBucket(bucketName);
    }

    @DeleteMapping("/deleteBucket/{bucketName}")
    public String removeBucket(@PathVariable String bucketName){
        return minioUtilService.removeBucket(bucketName);
    }



}
