package com.myxiaowang.minioutil.util;


import cn.hutool.core.util.IdUtil;
import com.myxiaowang.minioutil.entity.MinIoEntity;
import com.myxiaowang.minioutil.entity.MiniResponsesEntity;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年06月07日 10:49:00
 */

@Component
@Slf4j
public class MinioUtil {

    @Autowired
    private MinIoEntity minIoEntity;

    @Autowired
    private MinioClient minioClient;

    /**
     * 删除桶的生命周期
     * @param bucketName 桶名称
     * @throws ServerException serverException
     * @throws InsufficientDataException insufficientDataException
     * @throws ErrorResponseException errorResponseException
     * @throws IOException ioException
     * @throws NoSuchAlgorithmException     noSuchAlgorithmException
     * @throws InvalidKeyException    invalidKeyException
     * @throws InvalidResponseException invalidResponseException
     * @throws XmlParserException xmlParserException
     * @throws InternalException internalException
     */
    public void deleteBuketLifecycle(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 官网说这是删除桶的加密信息
     * @param bucketName 桶
     * @throws Exception ex
     */
    public void deleteBucketEncryption(String bucketName) throws Exception {
      minioClient.deleteBucketEncryption(DeleteBucketEncryptionArgs.builder().bucket(bucketName).build());
    }

    /**
     * 查询通是否能存在
     * @param bucketName 桶名称
     * @return  Boolean
     */
    public boolean hasBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }
    /**
     * 创建桶
     * @param bucketName 桶名
     */
    public boolean createBucket(String bucketName) {
        // 判断桶是否存在
        try {
            if (hasBucket(bucketName)) {
                log.info("桶{}已经存在！",bucketName);
                return false;
            }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("创建bucket失败，bucketName:{}", bucketName, e);
            throw new RuntimeException(e.getMessage());
        }
        log.info("创建成功：{}",bucketName);
        return true;
    }


    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @param bucketName    需要往那个桶上传
     * @return MiniResponsesEntity
     */
    public MiniResponsesEntity uploadFile(MultipartFile multipartFile, String bucketName){
        if(multipartFile == null || multipartFile.isEmpty()){
            throw new RuntimeException("上传文件不能为空");
        }
        createBucket(bucketName);
        // 获取文件名称
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + IdUtil.nanoId() + fileName.substring(fileName.lastIndexOf("."));
        // 开始上传文件
        log.info("开始上传文件");
        try{
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(newFileName).stream(
                                    multipartFile.getInputStream(), multipartFile.getSize(), -1)
                            .contentType(multipartFile.getContentType())
                            .build());
            String url = minIoEntity.getEndpoint() + "/" + bucketName + "/" + newFileName;
            String urlHost = minIoEntity.getNginxHost() + "/" + bucketName + "/" + newFileName;
            return new MiniResponsesEntity(url,urlHost);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * 获取全部bucket
     *
     * @return List
     */
    public List<String> getAllBuckets() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets.stream().map(Bucket::name).collect(Collectors.toList());
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    public Optional<Bucket> getBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, ErrorResponseException, ServerException, XmlParserException, ServerException {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return url
     */
    public String getObjectUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 获取⽂件
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return ⼆进制流
     */
    public InputStream getObject(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }



    /***
     * 上传视频
     * @param file 文件
     * @param bucketName 桶
     * @return MiniResponsesEntity
     * @throws Exception Exception
     */
    public MiniResponsesEntity uploadVideo(MultipartFile file, String bucketName) throws Exception {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) {
            return null;
        }
        //判断存储桶是否存在  不存在则创建
        createBucket(bucketName);
        //文件名
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + IdUtil.nanoId() + fileName.substring(fileName.lastIndexOf("."));
        //开始上传
        log.info("file大小:{}",file.getSize());
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                file.getInputStream(), file.getSize(), -1)
                        .contentType("video/mp4")
                        .build());
        String url = minIoEntity.getEndpoint() + "/" + bucketName + "/" + newFileName;
        String urlHost = minIoEntity.getNginxHost() + "/" + bucketName + "/" + newFileName;
        return new MiniResponsesEntity(url,urlHost);
    }

}
