package com.chia.tracelog.logs;

import android.util.Log;

import com.chia.logbase.Trace;

/**
 * @ClassName: DefaultLog
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class DefaultLog implements Trace {
    private static final int MAX_LOG_LENGTH = 4000;

    public DefaultLog() {
    }

    public boolean isLoggable(String tag, int priority,boolean isLoggable) {
        return isLoggable;
    }

    public void log(int priority, String tag, String message) {
        if (message.length() < 4000) {
            if (priority == 7) {
                Log.wtf(tag, message);
            } else {
                Log.println(priority, tag, message);
            }

        } else {
            int i = 0;

            int end;
            for(int length = message.length(); i < length; i = end + 1) {
                int newline = message.indexOf(10, i);
                newline = newline != -1 ? newline : length;

                do {
                    end = Math.min(newline, i + 4000);
                    String part = message.substring(i, end);
                    if (priority == 7) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }

                    i = end;
                } while(end < newline);
            }

        }
    }
}
