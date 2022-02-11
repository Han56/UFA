package com.han56.ufa.utils;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/2/9 上午9:07
 */
public class Test {

    private static final Logger log = Logger.getLogger(Test.class);

    @org.junit.Test
    public void testLog(){
        log.debug("debug");
        log.error("error");
    }

    @org.junit.Test
    public void aliTest(){
        String host = "http://xhuabu.market.alicloudapi.com";
        String path = "/quote/real";
        String method = "GET";
        String appcode = "92918db90a2b4a00b63e0485cf803f7e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("security_code", "SH.600000");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());
            //获取response的body
            //String outValue = EntityUtils.toString(response.getEntity());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
