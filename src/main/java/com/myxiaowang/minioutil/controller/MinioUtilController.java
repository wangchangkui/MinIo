package com.myxiaowang.minioutil.controller;

import com.myxiaowang.minioutil.util.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wck
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年06月07日 11:53:00
 */
@Slf4j
@RestController
@RequestMapping("/minio")
public class MinioUtilController {

    @Autowired
    private MinioUtil minioUtil;


    @GetMapping("/create/{bucketName}")
    public JSONObject createBucket( @PathVariable String bucketName){
        JSONObject jsonObject = new JSONObject();
        try{
            if (minioUtil.createBucket(bucketName)) {
                jsonObject.put("code",200);
                jsonObject.put("message","创建成功");
            }
        }catch (RuntimeException e){
            jsonObject.put("code",500);
            jsonObject.put("message",e.getMessage());
        }
        return jsonObject;
    }

}
