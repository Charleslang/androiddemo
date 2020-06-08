package com.qinjie.demo.utils;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 构建http请求，得到返回结果
 *
 * @author: qinjie
 **/
public class HttpClient1 {
    private static CloseableHttpClient httpClient = null;

    static {
        // 通过址默认配置创建一个httpClient实例
        httpClient = HttpClients.createDefault();
    }

    /**
     * 功能描述: get方法，访问目标地址，得到字符串返回值
     *
     * @param: url，目标地址
     * @return: 返回的字符串
     * @auther: 秦杰
     * @date: 2019/8/29 11:38
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 功能描述: post方法访问目标方法，得到结果
     *
     * @param: url目标地址，paramMap变量
     * @return: 字符串结果
     * @auther: 秦杰
     * @date: 2019/8/29 11:39
     */
    public static String doPost(String url, Map<String, Object> paramMap) {
        return doPost(url, paramMap, null);
    }


    /**
     * 功能描述: post方法访问目标方法，得到结果
     *
     * @param: url目标地址，paramMap变量, token 标识
     * @return: 字符串结果
     * @auther: 秦杰
     * @date: 2019/8/29 11:39
     */
    public static String doPost(String url, Map<String, Object> paramMap, String token) {
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");
        if (token != null) {
            httpPost.addHeader("token", token);
        }
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), "utf-8"));
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            Log.e("ROOOR", e.getMessage());
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 功能描述: get方法，访问目标地址，得到字符串返回值
     *
     * @param: url，目标地址, token 标识符
     * @return: 返回的字符串
     * @auther: 秦杰
     * @date: 2019/8/29 11:38
     */
    public static String doGet(String url, String token) {
//        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
//            // 设置请求头信息，鉴权
            if (token != null) {
                httpGet.setHeader("token", token);
            }
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    private static OkHttpClient okhttp = new OkHttpClient();

    public static void upLoadImg(String url, String imgPath, final Handler handler, String token) {
        final String imageType = "multipart/form-data";
        File file = new File(imgPath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", token)
                .post(requestBody)
                .build();
        okhttp.newBuilder().readTimeout(10000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("Timeout", "超时超时");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                    if (jsonObject.get("code").equals(200)) {
                        Message mes = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("profile", jsonObject.get("datas") + "");
                        mes.setData(bundle);
                        mes.what = 3;
                        handler.sendMessage(mes);
                    }
                }
            }
        });


    }

    public static String doPut(String url, HashMap params,String token) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(params));
        Request.Builder builder = new Request.Builder();
        if (token != null && !token.isEmpty()) {
            builder.addHeader("token", token);
        }
        builder.url(url)
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "keep-alive");
        String str = null;
        try {
            Response response = okhttp.newBuilder().readTimeout(10000, TimeUnit.MILLISECONDS).build().newCall(builder.build()).execute();
            str = response.body().string();
            // str为json字符串
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("ERROR", e.getMessage());
        }
        return str;
    }

    public static String doDelete(String url, String token, HashMap params) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(params));
        Request.Builder builder = new Request.Builder();
        if (token != null && !token.isEmpty()) {
            builder.addHeader("token", token);
        }
        builder.url(url)
                .delete(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "keep-alive");
        String str = null;
        try {
            Response response = okhttp.newBuilder().readTimeout(10000, TimeUnit.MILLISECONDS).build().newCall(builder.build()).execute();
            str = response.body().string();
            // str为json字符串
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
}

