package com.dysy.carttest.util;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String result = "";

    public static void get(String url, OkHttpCallback callback){
        callback.url = url;
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void post(String url, String json, OkHttpCallback callback){
        callback.url = url;
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static String post(final String url, final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                try (Response response = CLIENT.newCall(request).execute()) {
                    result = response.body().string().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return result;
    }
}
