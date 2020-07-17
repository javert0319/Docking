package com.chia.logbase;

/**
 * @ClassName: Config
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public interface Config {
    Config setDefaultTag(String var1);

    Config addTrace(Trace var1);
}
