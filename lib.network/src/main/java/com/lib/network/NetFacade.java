package com.lib.network;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.util.ArrayMap;

import com.lib.network.observer.Observer;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @ClassName: NetFacade
 * @Description: 网络访问 门面(网络访问通过该类进行)
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class NetFacade {
    private String baseUrl = "http://deve.nbhope.cn:8889/";
    //private String baseUrl = "http://192.168.2.7:80/";
    //private String baseUrl = "http://192.168.2.4:8080/";
    private Map<String, Object> serviceMap;

    private NetFacade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            serviceMap = new ArrayMap<>();
        } else {
            serviceMap = new HashMap<>();
        }
    }

    public static NetFacade getInstance() {
        return SingleTonHolder.instance;
    }

    private static class SingleTonHolder {
        private static NetFacade instance = new NetFacade();
    }

    private Retrofit createRetrofit(String baseUrl) {
        return RetrofitFactory.getInstance().createRetrofit(baseUrl);
    }

    /**
     * 提供 ApiService
     *
     * @param baseUrl 基础Url 根据这个判断不同的ApiService
     * @param clz     ApiService class
     * @param <T>
     * @return 指定的ApiService
     */
    public synchronized <T> T provideService(String baseUrl, Class clz) {
        T service = (T) serviceMap.get(baseUrl);
        if (service == null) {
            service = (T) createRetrofit(baseUrl).create(clz);
            serviceMap.put(baseUrl, service);
        }
        return service;
    }

    /**
     * 提供默认 ApiService
     *
     * @return 指定的ApiService
     */
    public synchronized APIService provideDefaultService() {
        return provideService(baseUrl, APIService.class);
    }

    /**
     * 提供默认 ApiService
     *
     * @return 指定的ApiService
     */
    public APIService provideDefualtService() {
        APIService service = (APIService) serviceMap.get(baseUrl);
        if (service == null) {
            service = createRetrofit(baseUrl).create(APIService.class);
            serviceMap.put(baseUrl, service);
        }
        return service;
    }

    /**
     * 该方法已弃用，请使用provideDefaultService()
     *
     * @param a 是否是Debug环境 这个参数已启用
     * @return 指定的ApiService
     */
    @Deprecated
    public synchronized APIService provideDefaultService(boolean a) {
        return provideService(baseUrl, APIService.class);
    }

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(Application application) {
        if (RetrofitFactory.getContext() == null) {
            RetrofitFactory.init(application);
        }
    }


    public static void init(Application application, String baseUrl) {
        if (RetrofitFactory.getContext() == null) {
            RetrofitFactory.init(application);
        }
        NetFacade.getInstance().setBaseUrl(baseUrl);
    }

    public OkHttpClient okHttpClient() {
        return RetrofitFactory.getInstance().createOkhttp();
    }

    /**
     * 注册 响应观察者
     *
     * @param observer
     */
    public static void register(Observer observer) {
        RetrofitFactory.register(observer);
    }

    /**
     * 取消注册响应观察者
     * @param observer
     */
    public static void unRegister(Observer observer) {
        RetrofitFactory.unregister(observer);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        if (baseUrl.startsWith("http://") || baseUrl.startsWith("https://")) {
            this.baseUrl = baseUrl;
        } else {
            this.baseUrl = "http://" + baseUrl + "/";
        }
    }
}
