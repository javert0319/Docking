package com.lib.utils.print;

import android.content.Context;
import android.text.TextUtils;

import com.chia.tracelog.TraceLog;
import com.chia.tracelog.logs.DiskLogTrace;
import com.chia.tracelog.parser.Constant;

/**
 * @ClassName: L
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class L {
    private static TraceLog traceLog;

    public L() {}

    public static void init() {
        TraceLog traceLog = (new TraceLog.Builder()).addTrace(new LTrace()).build();
        init(traceLog);
    }

    public static void init(String defTag) {
        TraceLog traceLog = (new TraceLog.Builder()).addTrace(new LTrace()).setDefaultTag(defTag).build();
        init(traceLog);
    }

    public static void init(Context context, String defTag, boolean saveToDisk) {
        TraceLog.Builder builder = (new TraceLog.Builder()).addTrace(new LTrace());
        if (!TextUtils.isEmpty(defTag)) {
            builder.setDefaultTag(defTag);
        }
        if (saveToDisk) {
            builder.addTrace(new DiskLogTrace(context));
        }
        TraceLog traceLog = builder.build();
        init(traceLog);
    }

    public static void init(Context context, String defTag, boolean saveToDisk,boolean isLoggable) {
        TraceLog.Builder builder = (new TraceLog.Builder()).addTrace(new LTrace());
        if (!TextUtils.isEmpty(defTag)) {
            builder.setDefaultTag(defTag);
        }
        if (saveToDisk) {
            builder.addTrace(new DiskLogTrace(context));
        }
        Constant.ISLOGGABLE = isLoggable;
        TraceLog traceLog = builder.build();
        init(traceLog);
    }

    public static void init(TraceLog _traceLog) {
        traceLog = _traceLog;
    }

    public static void e(String msg) {
        traceLog.e(msg);
    }

    public static void e(String msg, Throwable tr) {
        traceLog.e(tr, msg, new Object[0]);
    }

    public static void w(String msg) {
        traceLog.w(msg);
    }

    public static void i(String msg) {
        traceLog.i(msg);
    }

    public static void d(String msg) {
        traceLog.d(msg);
    }

    public static void v(String msg) {
        traceLog.v(msg);
    }

    public static void json(String json) {
        traceLog.json(json);
    }

    public static void xml(String xml) {
        traceLog.xml(xml);
    }

    public static void v(Object object) {
        traceLog.v(object);
    }

    public static void i(Object object) {
        traceLog.i(object);
    }

    public static void d(Object object) {
        traceLog.d(object);
    }

    public static void w(Object object) {
        traceLog.w(object);
    }

    public static void e(Object object) {
        traceLog.e(object);
    }

    public static PrinterWrap t(String tag) {
        return getPrintWrap(traceLog, tag);
    }

    private static PrinterWrap getPrintWrap(TraceLog traceLog, String tag) {
        return new PrinterWrap(traceLog.t(tag));
    }

    public static void e(String tag, String msg) {
        traceLog.t(tag).e(msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        traceLog.t(tag).e(tr, msg, new Object[0]);
    }

    public static void w(String tag, String msg) {
        traceLog.t(tag).w(msg);
    }

    public static void i(String tag, String msg) {
        traceLog.t(tag).i(msg);
    }

    public static void d(String tag, String msg) {
        traceLog.t(tag).d(msg);
    }

    public static void v(String tag, String msg) {
        traceLog.t(tag).v(msg);
    }

    public static void json(String tag, String json) {
        traceLog.t(tag).json(json);
    }

    public static void xml(String tag, String xml) {
        traceLog.t(tag).xml(xml);
    }

    public static void v(String tag, Object object) {
        traceLog.t(tag).v(object);
    }

    public static void i(String tag, Object object) {
        traceLog.t(tag).i(object);
    }

    public static void d(String tag, Object object) {
        traceLog.t(tag).d(object);
    }

    public static void w(String tag, Object object) {
        traceLog.t(tag).w(object);
    }

    public static void e(String tag, Object object) {
        traceLog.t(tag).e(object);
    }
}
