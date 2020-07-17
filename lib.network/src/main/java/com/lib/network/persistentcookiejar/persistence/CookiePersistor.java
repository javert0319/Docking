package com.lib.network.persistentcookiejar.persistence;

import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;

/**
 * @ClassName: CookiePersistor
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public interface CookiePersistor {

    List<Cookie> loadAll();

    /**
     * Persist all cookies, existing cookies will be overwritten.
     *
     * @param cookies cookies persist
     */
    void saveAll(Collection<Cookie> cookies);

    /**
     * Removes indicated cookies from persistence.
     *
     * @param cookies cookies to remove from persistence
     */
    void removeAll(Collection<Cookie> cookies);

    /**
     * Clear all cookies from persistence.
     */
    void clear();

}
