package com.lzumetal.open.source.httpclient.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liaosi
 * @date 2020-07-09
 */
public class HttpRequest {

    public static void main(String[] args) {
        System.out.println(StandardCharsets.UTF_8.name());
    }


    private static final String DEFAULT_CHARSET_NAME = StandardCharsets.UTF_8.name();

    /* 请求头 */
    private Map<String, String> headers = new HashMap<>();

    /* 参数 */
    private Map<String, String> params = new HashMap<>();

    /* 请求配置 */
    private RequestConfig requestConfig;


    public HttpRequest addHead(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpRequest addHeads(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public HttpRequest setHeads(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequest addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public HttpRequest addParams(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public HttpRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }


    private List<NameValuePair> mapToNameValuePairList(Map<String, String> formParams) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

    public String get(String url, Map<String, String> params) throws IOException {
        this.params.putAll(params);
        return get(url);
    }


    public String get(String url) throws IOException {
        String urlTarget = url;
        //参数需要拼接
        if (params != null && params.size() > 0) {
            urlTarget = url + "?" + URLEncodedUtils.format(mapToNameValuePairList(params), DEFAULT_CHARSET_NAME);
        }
        HttpGet method = new HttpGet(urlTarget);
        return executeMethod(method);
    }

    private RequestConfig buildRequestConfig(int connectTimeout, int socketTimeout) {
        return RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(socketTimeout)
                .build();

    }


    public String post(String url, Map<String, String> params, int connectTimeout, int socketTimeout) throws IOException {
        this.params.putAll(params);
        this.requestConfig = buildRequestConfig(connectTimeout, socketTimeout);
        return post(url);
    }


    public String post(String url, Map<String, String> params) throws IOException {
        this.params.putAll(params);
        return post(url);
    }

    private String post(String url) throws IOException {
        HttpPost method = new HttpPost(url);
        if (params != null && params.size() > 0) {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(mapToNameValuePairList(params), DEFAULT_CHARSET_NAME);
            method.setEntity(uefEntity);
        }
        if (this.requestConfig != null) {
            method.setConfig(this.requestConfig);
        }
        return executeMethod(method);
    }

    public String postJson(String url, String json) throws IOException {
        HttpPost method = new HttpPost(url);
        method.setEntity(new StringEntity(json, DEFAULT_CHARSET_NAME));
        method.setHeader("content-type", ContentType.APPLICATION_JSON.getMimeType());
        return executeMethod(method);
    }


    public String postMultipartFile(String url, String fileParamName, MultipartFile multipartFile, Map<String, String> otherParams) throws IOException {
        HttpPost method = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setCharset(StandardCharsets.UTF_8)
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)//加上此行代码解决返回中文乱码问题
                .addBinaryBody(fileParamName, multipartFile.getBytes(), ContentType.MULTIPART_FORM_DATA, multipartFile.getOriginalFilename());// 文件流
        for (Map.Entry<String, String> e : otherParams.entrySet()) {
            builder.addTextBody(e.getKey(), e.getValue());//表单提交其他参数
        }
        HttpEntity entity = builder.build();
        method.setEntity(entity);
        return executeMethod(method);
    }


    private String executeMethod(HttpUriRequest request) throws IOException {
        if (this.headers != null && this.headers.size() > 0) {
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpClient httpClient = HttpConnectionManager.getInst().getHttpClient();
        HttpResponse response = httpClient.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity, DEFAULT_CHARSET_NAME) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }


}
