package com.jiading.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QiniuyunUtil {
    private static final String dataFile = "qiniuyun.properties";

    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    private static String BUCKET_NAME;
    private static String SERVER_DOMAIN;

    private static Configuration cfg;
    private static UploadManager uploadManager;
    private static Auth auth;
    private static String upToken;

    // 从配置文件中读取配置
    static {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = QiniuyunUtil.class.getClassLoader().getResourceAsStream(dataFile);
        // 使用properties对象加载输入流
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取key对应的value值
        ACCESS_KEY = properties.getProperty("access_key");
        SECRET_KEY = properties.getProperty("secret_key");
        BUCKET_NAME = properties.getProperty("bucket_name");
        SERVER_DOMAIN = properties.getProperty("server_domain");

        //构造一个带指定 Region 对象的配置类
        cfg = new Configuration(Region.huanan());
        // 配置信息(略)
        uploadManager = new UploadManager(cfg);
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        upToken = auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 获取云存储服务器域名
     *
     * @return 域名
     */
    public static String getServerDomain() {
        return SERVER_DOMAIN;
    }

    /**
     * 上传一个本地文件到服务器上
     *
     * @param : String ==> 绝对文件路径
     *          Byte[] ==> 字节数组
     *          InputStream ==> 输入流对象中的流
     * @return 如果成功, 返回文件名称, 如果失败, 返回空字符串
     */
    public static String uploadObject(Object object) {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        DefaultPutRet putRet = null;
        try {
            Response response = null;
            if (object instanceof String) {
                response = uploadManager.put((String) object, key, upToken);
            } else if (object instanceof byte[]) {
                response = uploadManager.put((byte[]) object, key, upToken);
            } else if (object instanceof InputStream) {
                response = uploadManager.put((InputStream) object, key, upToken, null, null);
            }

            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
            }
        }
        if (putRet == null) {
            return "";
        }
        return SERVER_DOMAIN + putRet.key;
    }

}
