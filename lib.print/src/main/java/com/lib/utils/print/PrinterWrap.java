package com.lib.utils.print;

import com.chia.logbase.Printer;

/**
 * @ClassName: PrinterWrap
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class PrinterWrap implements Printer {
    private Printer printer;

    public PrinterWrap(Printer printer) {
        this.printer = printer;
    }

    public Printer t(String tag) {
        return this.printer.t(tag);
    }

    public void d(String message, Object... args) {
        this.printer.d(message, args);
    }

    public void e(String message, Object... args) {
        this.printer.e(message, args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        this.printer.e(throwable, message, args);
    }

    public void w(String message, Object... args) {
        this.printer.w(message, args);
    }

    public void i(String message, Object... args) {
        this.printer.i(message, args);
    }

    public void v(String message, Object... args) {
        this.printer.v(message, args);
    }

    public void wtf(String message, Object... args) {
        this.printer.wtf(message, args);
    }

    public void d(Object object) {
        this.printer.d(object);
    }

    public void e(Object object) {
        this.printer.e(object);
    }

    public void w(Object object) {
        this.printer.w(object);
    }

    public void i(Object object) {
        this.printer.i(object);
    }

    public void v(Object object) {
        this.printer.v(object);
    }

    public void json(String json) {
        this.printer.json(json);
    }

    public void xml(String xml) {
        this.printer.xml(xml);
    }

    public void log(int priority, String tag, String message, Throwable throwable) {
        this.printer.log(priority, tag, message, throwable);
    }
}
