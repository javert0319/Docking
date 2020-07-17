package com.lib.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lib.network.utils.DesUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ClassName: ParamsCreator
 * @Description: Params 生成器
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class ParamsCreator {
    private static final String TEXT_PLAIN = "text/plain";
    private static String KEY = "";//旧值306BE98143724C6481E5F2BB7F6951D5
    private static String CID = "";//旧值750832243333435392
    private static String SID = "";//旧值750837261197414400
    public static final String VER = "1.0";
    public static final int DEFAULT_PAGE_SIZE = 10;

    private static Gson gson = new Gson();

    @NonNull
    public static Map<String, String> generateParams(String dat) {
        if (TextUtils.isEmpty(KEY)||TextUtils.isEmpty(CID)||TextUtils.isEmpty(SID)) {
            throw new IllegalArgumentException("key,cid,sid must not null");
        }
        Map<String, String> params = new HashMap<>();
        params.put("key", KEY);
        params.put("cid", CID);
        params.put("sid", SID);
        params.put("ver", VER);
        params.put("des", calcDes(dat));
        params.put("dat", dat);
        params.put("len", String.valueOf(dat.length()));
        return params;
    }

    @NonNull
    public static Map<String, String> generateParams(int dat) {
        return generateParams(String.valueOf(dat));
    }


    @NonNull
    public static Map<String, String> generateParams(Object obj) {
        String dat = gson.toJson(obj);
        return generateParams(dat);
    }

    @NonNull
    public static Map<String, RequestBody> generateRequestBodyParams(String dat) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("key", createRequestBody(KEY));
        params.put("cid", createRequestBody(CID));
        params.put("sid", createRequestBody(SID));
        params.put("ver", createRequestBody(VER));
        params.put("des", createRequestBody(calcDes(dat)));
        params.put("dat", createRequestBody(dat));
        params.put("len", createRequestBody(String.valueOf(dat.length())));
        return params;
    }

    @NonNull
    public static RequestBody createRequestBodyParams(String dat) {
        RequestBody body = new FormBody.Builder()
                .add("key", KEY)
                .add("cid", CID)
                .add("sid", SID)
                .add("ver", VER)
                .add("des", calcDes(dat))
                .add("dat", dat)
                .add("len", String.valueOf(dat.length()))
                .build();
        return body;
    }

    public static RequestBody generateRequestBodyParamsWithImage(String dat, File image, String mediaType, String name){
        RequestBody fileBody = RequestBody.create(MediaType.parse(mediaType), image);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(name,image.getName(),fileBody)
                .addFormDataPart("key", KEY)
                .addFormDataPart("cid", CID)
                .addFormDataPart("sid", SID)
                .addFormDataPart("ver", VER)
                .addFormDataPart("des", calcDes(dat))
                .addFormDataPart("dat", dat)
                .addFormDataPart("len", String.valueOf(dat.length()))
                .build();
        return body;

    }

    @NonNull
    public static Map<String, RequestBody> generateRequestBodyParams(int dat) {
        return generateRequestBodyParams(String.valueOf(dat));
    }

    @NonNull
    public static Map<String, RequestBody> generateRequestBodyParams(Object obj) {
        String dat = gson.toJson(obj);
        return generateRequestBodyParams(dat);
    }

    @NonNull
    public static Map<String, RequestBody> generateRequestBodyParamsNew(Map obj) {
        String dat = (gson.toJson(obj));
        return generateRequestBodyParams(String.valueOf(obj));
    }

    @NonNull
    private static RequestBody createRequestBody(String content) {
        return RequestBody.create(MediaType.parse(TEXT_PLAIN), content);
    }

    private static String calcDes(String dat) {
        return DesUtil.calc(dat, KEY, CID, SID, VER);
    }

    public static String getKEY() {
        return KEY;
    }

    public static void setKEY(String KEY) {
        ParamsCreator.KEY = KEY;
    }

    public static String getCID() {
        return CID;
    }

    public static void setCID(String CID) {
        ParamsCreator.CID = CID;
    }

    public static String getSID() {
        return SID;
    }

    public static void setSID(String SID) {
        ParamsCreator.SID = SID;
    }
}
