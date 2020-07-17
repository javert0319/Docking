package com.lib.network;

import com.lib.network.bean.entry.base.BaseBean;
import com.lib.network.bean.entry.music.MusicMiguHomeBean;
import com.lib.network.bean.response.BaseListResponse;
import com.lib.network.bean.response.BaseResponse;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * @ClassName: APIService
 * @Description: 网络请求接口
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public interface APIService {
    @Multipart
    @POST("hopeApi/code/send")
    Observable<BaseResponse<String>> getSMSAuthCode(@PartMap Map<String, RequestBody> options);

    //咪咕首页信息查询
    @Multipart
    @POST("/hopeApi/music/listIndex")
    Flowable<BaseListResponse<BaseBean, MusicMiguHomeBean>> loadMiguHome(@PartMap Map<String, RequestBody> options);
}
