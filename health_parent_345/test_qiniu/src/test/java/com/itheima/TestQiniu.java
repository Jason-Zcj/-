package com.itheima;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestQiniu {

    public static void main(String[] args) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

        String accessKey = "UhhUkME8ISmWuHK0JFHtXDZEXOkbEFwQWKAkv9_x";
        String secretKey = "SMxYSLdjHNZc18Uw599QqRPSOQam9dbdGDMQfdGX";

        String bucket = "hm_345";
        String key = "FqSyWf5vdudjlGbEIo6Fg5seW4SM";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }

    public static void main1(String[] args) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证(鉴权（秘钥）)，然后准备上传
        String accessKey = "UhhUkME8ISmWuHK0JFHtXDZEXOkbEFwQWKAkv9_x";
        String secretKey = "SMxYSLdjHNZc18Uw599QqRPSOQam9dbdGDMQfdGX";
        //存储空间的名称
        String bucket = "hm_345";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "F:\\hm_345\\7_项目一--传智健康\\第4章\\资源\\图片资源\\ee7dcf84-8a3a-4ab9-b981-9c5d272fd58d3.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }



}
