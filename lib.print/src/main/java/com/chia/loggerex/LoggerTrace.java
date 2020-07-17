package com.chia.loggerex;

import com.chia.logbase.Trace;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @ClassName: LoggerTrace
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class LoggerTrace implements Trace {

    public LoggerTrace() {
        this(2, 5);
    }

    public LoggerTrace(int methodCount, int methodOffset) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().showThreadInfo(true).methodCount(methodCount).methodOffset(methodOffset).tag("Logger").build();
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public LoggerTrace(FormatStrategy formatStrategy) {
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public boolean isLoggable(String tag, int priority,boolean isLoggable) {
        return isLoggable;
    }

    public void log(int priority, String tag, String message) {
        Logger.log(priority, tag, message, (Throwable)null);
    }
}
