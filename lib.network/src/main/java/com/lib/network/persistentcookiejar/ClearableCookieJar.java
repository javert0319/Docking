package com.lib.network.persistentcookiejar;

import okhttp3.CookieJar;

/**
 * @ClassName: ClearableCookieJar
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public interface ClearableCookieJar extends CookieJar {

    void clear();
}
