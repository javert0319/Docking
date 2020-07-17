package com.chia.tracelog.printer;

import android.text.TextUtils;

import com.chia.logbase.Config;
import com.chia.logbase.Printer;
import com.chia.logbase.Trace;
import com.chia.tracelog.parser.Constant;
import com.chia.tracelog.parser.ObjectUtil;
import com.chia.tracelog.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @ClassName: TraceLogImpl
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class TraceLogImpl implements Printer, Config {
    private static final int JSON_INDENT = 2;
    private final ThreadLocal<String> localTag = new ThreadLocal();
    private final List<Trace> traces = new ArrayList();
    private volatile String defaultTag = "TraceLog";

    public TraceLogImpl() {
    }

    public Printer t(String tag) {
        if (tag != null) {
            this.localTag.set(tag);
        }

        return this;
    }

    public void d(String message, Object... args) {
        this.log(3, (Throwable)null, message, (Object[])args);
    }

    public void d(Object object) {
        this.log(3, (Throwable)null, ObjectUtil.objectToString(object), object);
    }

    public void e(Object object) {
        this.log(6, (Throwable)null, ObjectUtil.objectToString(object), object);
    }

    public void w(Object object) {
        this.log(5, (Throwable)null, ObjectUtil.objectToString(object), object);
    }

    public void i(Object object) {
        this.log(4, (Throwable)null, ObjectUtil.objectToString(object), object);
    }

    public void v(Object object) {
        this.log(2, (Throwable)null, ObjectUtil.objectToString(object), object);
    }

    public void e(String message, Object... args) {
        this.log(6, (Throwable)null, message, (Object[])args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        this.log(6, (Throwable)throwable, message, (Object[])args);
    }

    public void w(String message, Object... args) {
        this.log(5, (Throwable)null, message, (Object[])args);
    }

    public void i(String message, Object... args) {
        this.log(4, (Throwable)null, message, (Object[])args);
    }

    public void v(String message, Object... args) {
        this.log(2, (Throwable)null, message, (Object[])args);
    }

    public void wtf(String message, Object... args) {
        this.log(7, (Throwable)null, message, (Object[])args);
    }

    public void json(String json) {
        if (TextUtils.isEmpty(json)) {
            this.d("Empty/Null json content");
        } else {
            try {
                json = json.trim();
                String message;
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    message = jsonObject.toString(2);
                    this.log(3, (Throwable)null, ObjectUtil.objectToString(message), message);
                    return;
                }

                if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    message = jsonArray.toString(2);
                    this.log(3, (Throwable)null, ObjectUtil.objectToString(message), message);
                    return;
                }

                this.e("Invalid Json");
            } catch (JSONException var4) {
                this.e("Invalid Json");
            }

        }
    }

    public void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            this.d("Empty/Null xml content");
        } else {
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("indent", "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                this.log(3, (Throwable)null, ObjectUtil.objectToString(xmlOutput.getWriter().toString().replaceFirst(">", ">\n")), xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
            } catch (TransformerException var5) {
                this.e("Invalid xml");
            }

        }
    }

    public synchronized void log(int priority, String tag, String message, Throwable throwable) {
        if (throwable != null && message != null) {
            StringBuilder sb = new StringBuilder(message);
            sb.append(" : ");
            sb.append(Util.getStackTraceString(throwable));
            message = sb.toString();
        }

        if (throwable != null && message == null) {
            message = Util.getStackTraceString(throwable);
        }

        if (Util.isEmpty(message)) {
            message = "Empty/NULL log message";
        }

        if (Util.isEmpty(tag)) {
            tag = this.defaultTag;
        }

        Iterator var7 = this.traces.iterator();

        while(var7.hasNext()) {
            Trace adapter = (Trace)var7.next();
            if (adapter.isLoggable(tag, priority, Constant.ISLOGGABLE)) {
                adapter.log(priority, tag, message);
            }
        }
    }

    public TraceLogImpl clearTraces() {
        this.traces.clear();
        return this;
    }

    public TraceLogImpl setDefaultTag(String tag) {
        this.defaultTag = tag;
        return this;
    }

    public TraceLogImpl addTrace(Trace trace) {
        this.traces.add(trace);
        return this;
    }

    private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
        String tag = this.getTag();
        String message = this.createMessage(msg, args);
        this.log(priority, tag, message, throwable);
    }

    private String getTag() {
        String tag = (String)this.localTag.get();
        if (tag != null) {
            this.localTag.remove();
            return tag;
        } else {
            return null;
        }
    }

    private String createMessage(String message, Object... args) {
        return args != null && args.length != 0 ? String.format(message, args) : message;
    }
}

