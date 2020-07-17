package com.chia.logbase;

/**
 * @ClassName: Trace
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public interface Trace {
    boolean isLoggable(String var1, int var2, boolean isLoggable);

    void log(int var1, String var2, String var3);
}
