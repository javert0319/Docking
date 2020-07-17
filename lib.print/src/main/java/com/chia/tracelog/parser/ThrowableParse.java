package com.chia.tracelog.parser;

import android.util.Log;

/**
 * @ClassName: ThrowableParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class ThrowableParse implements Parser<Throwable> {
    public ThrowableParse() {
    }

    public Class<Throwable> parseClassType() {
        return Throwable.class;
    }

    public String parseString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}
