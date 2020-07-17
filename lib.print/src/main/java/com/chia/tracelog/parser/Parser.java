package com.chia.tracelog.parser;

/**
 * @ClassName: Parser
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public interface Parser<T> {
    String LINE_SEPARATOR = Constant.BR;

    Class<T> parseClassType();

    String parseString(T var1);
}
