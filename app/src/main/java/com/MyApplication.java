package com;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.util.Log;

import androidx.annotation.NonNull;

/**
  * 运行时全局变量
  *
  * @author:
 **/
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
    private String position;
    /**
     * 用户信息
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        position = "重启";
        //设置默认异常处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        Log.e("线程异常："+t.getName(), e.getMessage());
    }
}
