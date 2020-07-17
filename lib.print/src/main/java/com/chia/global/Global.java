package com.chia.global;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: Global
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class Global {
    private static Application application;
    private static AppInfo appInfo;
    private static Gson gson;
    private static Map<String, Object> dataMap;
    private static Handler mainHandler;

    public static void init(Application _application) {
        application = _application;
        appInfo = new AppInfo();
        gson = new Gson();
        dataMap = new ConcurrentHashMap<>();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static Application getContext() {
        return application;
    }

    public static AppInfo getAppInfo() {
        return appInfo;
    }

    public static void setAppInfo(AppInfo appInfo) {
        Global.appInfo = appInfo;
    }

    public static Gson gson() {
        return gson;
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 存储数据到全局Map中
     *
     * @param key
     * @param value
     */
    public static void putToDataMap(String key, Object value) {
        dataMap.put(key, value);
    }

    /**
     * 从全局Map中取出数据
     *
     * @param key
     * @return
     */
    public static Object getFromDataMap(String key) {
        return dataMap.get(key);
    }

    /**
     * 从全局Map中移除数据
     *
     * @param key
     * @return
     */
    public static boolean removeFromDataMap(String key) {
        if (dataMap.containsKey(key)) {
            dataMap.remove(key);
            return true;
        }
        return false;
    }

    /**
     * 清除全局Map的数据
     */
    public static void clearDataMap() {
        dataMap.clear();
    }

    /**
     * 在主线程执行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable){
        mainHandler.post(runnable);
    }
}
