package com.lzumetal.open.source.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaosi on 2017/7/29.
 */
public class HttpClientTest {

    private static final String TEST_GET_URL = "http://localhost:8080/testHttpClient";

    @Test
    public void testGet() {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            //1.HttpClients 是用于创建 httpClient 实例的工厂，createDefault()方法可以创建一个默认配置的实例。
            httpClient = HttpClients.createDefault();

            //创建一个 http Get 方法
            HttpGet httpGet = new HttpGet(TEST_GET_URL);
            System.out.println("get请求的地址：" + httpGet.getURI());

            //执行请求，得到返回响应的数据，通过它我们可以获取拿到返回的状态码、首部和响应实体等
            response = httpClient.execute(httpGet);

            //获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("响应状态码：" + statusCode);

            //获取响应头中信息
            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                System.out.println(String.format("请求头信息：%s ----> %s", header.getName(), header.getValue()));
            }

            //获取响应实体
            HttpEntity entity = response.getEntity();
            /*EntityUtils.toString(entity)方法读取实体的内容，并以字符串的形式返回。
                默认使用实体内容中自带的字符编码格式，如果没有则使用"ISO-8859-1"编码
             */
            //String result = EntityUtils.toString(entity);
            //也可以自定编码格式
            String result = EntityUtils.toString(entity, Charset.forName("utf-8"));
            System.out.println("接口返回：" + result);

            System.out.println("Response content length: " + entity.getContentLength());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testGet2() {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            //设置cookie
            BasicCookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookie = new BasicClientCookie("token1", "ab37gbfv65eeq19o54cd986lefg");
            cookie.setVersion(0);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            cookieStore.addCookie(cookie);

            //HttpClients.custom()方法创建一个builder对象用于CloseableHttpClient对象的创建
            httpClient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore) //在客户端添加多个cookie
                    .build();


            //get请求设置参数，可以直接将参数键值对进行url编码后加载请求路径后即可
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("province", "广东省"));
            nameValuePairs.add(new BasicNameValuePair("city", "深圳市"));

            String reqUrl = TEST_GET_URL + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, Charset.forName("utf-8")));
            System.out.println("请求的URL:" + reqUrl);

            HttpGet httpGet = new HttpGet(reqUrl);
            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            //如果知道返回的数据是字符串，我们直接使用缓冲字符流来获取结果
//            inputStream = entity.getContent();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            System.out.println(stringBuilder.toString());

            //也可以使用一种更简单的方法获取返回数据：
            System.out.println(new BasicResponseHandler().handleEntity(entity));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Test
    public void testPost() {

    }
}
