package com.qinjie.demo.utils;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;


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
     * 功能描述: get方法，访问目标地址，得到字符串返回值
     *
     * @param: url，目标地址, token 标识符
     * @return: 返回的字符串
     * @auther: 秦杰
     * @date: 2019/8/29 11:38
     */
    public static String doGet(String url, String token) {
        String result = null;
        Request.Builder builder = new Request.Builder().url(url);
        if(!Strings.isNullOrEmpty(token)){
            builder.header("token", token);
        }
        //发送请求
        try {
            Response response = okhttp.newCall(builder.build()).execute();
            //打印服务端传回的数据
            result = response.body().string();
        } catch (IOException e) {
            Log.i("ERROR", e.getMessage());
        }
        return result;
    }


    private static OkHttpClient okhttp = new OkHttpClient();

    public static String doPost(String url, Map<String, Object> paramMap, String token) {
        String result = null;
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(paramMap));
        //创建一个请求对象
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);
        if(!Strings.isNullOrEmpty(token)){
            builder.header("token", token);
        }
        //发送请求获取响应
        try {
            Response response=okhttp.newCall(builder.build()).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                result =  response.body().string();
            }
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        }

        return result;
    }

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
        Log.d("Timeout", "ERR");
        okhttp.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("Timeout", "超时超时");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("错误", response.message());
                if (response.isSuccessful()) {
                    JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                    Log.e("返回结果", jsonObject.toJSONString());
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

        Log.d("Timeout", "ERR");

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

