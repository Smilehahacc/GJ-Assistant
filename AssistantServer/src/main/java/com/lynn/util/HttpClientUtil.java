package com.lynn.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

public class HttpClientUtil {

    static HttpClientUtil instance;
    // 获取连接客户端工具
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String entityStr = null;
    CloseableHttpResponse response = null;

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    /**
     * 请求后端python的web服务（get）
     */
    public String getHttpResponse(String url) {
        try {
            HttpGet get = new HttpGet("http://127.0.0.1:5000/" + url);
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
            System.err.println("请求结果为："+entityStr);
            return entityStr;
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
