package com.lib.utils.print;

import com.chia.logbase.Trace;

/**
 * @ClassName: LTrace
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class LTrace implements Trace {
    public LTrace() {
    }

    public boolean isLoggable(String tag, int priority,boolean isLoggable) {
        return isLoggable;
    }

    public void log(int priority, String tag, String message) {
        switch(priority) {
            case 2:
                LWrap.v(tag, message);
                break;
            case 3:
                LWrap.d(tag, message);
                break;
            case 4:
                LWrap.i(tag, message);
                break;
            case 5:
                LWrap.w(tag, message);
                break;
            case 6:
                LWrap.e(tag, message);
                break;
            case 7:
                LWrap.e(tag, message);
        }

    }
}
