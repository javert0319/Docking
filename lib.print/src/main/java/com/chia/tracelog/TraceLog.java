package com.chia.tracelog;

import android.text.TextUtils;

import com.chia.logbase.Config;
import com.chia.logbase.Printer;
import com.chia.logbase.Trace;
import com.chia.tracelog.logs.DefaultLog;
import com.chia.tracelog.printer.TraceLogImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: TraceLog
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class TraceLog implements Printer {
    private TraceLogImpl impl;
    private static Trace defaultTraces;

    private TraceLog(TraceLog.Builder builder) {
        this.impl = new TraceLogImpl();
        this.impl.setDefaultTag(builder.tag);
        Iterator var2 = builder.traces.iterator();

        while(var2.hasNext()) {
            Trace trace = (Trace)var2.next();
            this.impl.addTrace(trace);
        }

    }

    public static Trace defaultTrace() {
        if (defaultTraces == null) {
            defaultTraces = new DefaultLog();
        }

        return defaultTraces;
    }

    public TraceLog t(String tag) {
        this.impl.t(tag);
        return this;
    }

    public void d(String message, Object... args) {
        this.impl.d(message, args);
    }

    public void d(Object object) {
        this.impl.d(object);
    }

    public void e(Object object) {
        this.impl.e(object);
    }

    public void w(Object object) {
        this.impl.w(object);
    }

    public void i(Object object) {
        this.impl.i(object);
    }

    public void v(Object object) {
        this.impl.v(object);
    }

    public void e(String message, Object... args) {
        this.impl.e(message, args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        this.impl.e(throwable, message, args);
    }

    public void w(String message, Object... args) {
        this.impl.w(message, args);
    }

    public void i(String message, Object... args) {
        this.impl.i(message, args);
    }

    public void v(String message, Object... args) {
        this.impl.v(message, args);
    }

    public void wtf(String message, Object... args) {
        this.impl.wtf(message, args);
    }

    public void json(String json) {
        this.impl.json(json);
    }

    public void xml(String xml) {
        this.impl.xml(xml);
    }

    public void log(int priority, String tag, String message, Throwable throwable) {
        this.impl.log(priority, tag, message, throwable);
    }

    public static class Builder implements Config {
        private String tag;
        private List<Trace> traces = new ArrayList();

        public Builder() {
        }

        public TraceLog.Builder setDefaultTag(String tag) {
            this.tag = tag;
            return this;
        }

        public TraceLog.Builder addTrace(Trace trace) {
            this.traces.add(trace);
            return this;
        }

        public TraceLog build() {
            if (TextUtils.isEmpty(this.tag)) {
                this.tag = "TraceLog";
            }

            if (this.traces.size() == 0) {
                this.traces.add(new DefaultLog());
            }

            TraceLog traceLog = new TraceLog(this);
            return traceLog;
        }
    }
}
