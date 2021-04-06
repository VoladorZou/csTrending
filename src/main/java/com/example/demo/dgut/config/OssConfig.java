package com.example.demo.dgut.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class OssConfig {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private String accessKeyId = "";
    private String accessKeySecret = "";

    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    // 关闭OSSClient。
//    ossClient.shutdown();
}
