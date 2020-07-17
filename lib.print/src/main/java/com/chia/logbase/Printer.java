package com.chia.logbase;

/**
 * @ClassName: Printer
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public interface Printer {
    Printer t(String var1);

    void d(String var1, Object... var2);

    void e(String var1, Object... var2);

    void e(Throwable var1, String var2, Object... var3);

    void w(String var1, Object... var2);

    void i(String var1, Object... var2);

    void v(String var1, Object... var2);

    void wtf(String var1, Object... var2);

    void d(Object var1);

    void e(Object var1);

    void w(Object var1);

    void i(Object var1);

    void v(Object var1);

    void json(String var1);

    void xml(String var1);

    void log(int var1, String var2, String var3, Throwable var4);
}
