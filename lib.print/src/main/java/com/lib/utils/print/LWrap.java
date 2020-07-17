package com.lib.utils.print;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName: LWrap
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
class LWrap {
    public static boolean isDebug = true;
    public static String defaultTAG = "mLog";
    private static final int JSON_INDENT = 4;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final int V = 1;
    private static final int D = 2;
    private static final int I = 3;
    private static final int W = 4;
    private static final int E = 5;
    private static final int A = 6;
    private static final int JSON = 7;

    LWrap() {}

    public static void init(boolean _isDebug) {
        isDebug = _isDebug;
    }

    private static void warn(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    private static void error(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }

    private static void print(int type, String TAG, String msg) {
        if (isDebug) {
            if (TextUtils.isEmpty(msg)) {
                d(TAG, "Empty or Null json content");
            } else {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                int index = 10;
                String className = stackTrace[index].getFileName();
                String methodName = stackTrace[index].getMethodName();
                int lineNumber = stackTrace[index].getLineNumber();
                methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");
                if (type != 7) {
                    stringBuilder.append(msg);
                }

                String logStr = stringBuilder.toString();
                switch(type) {
                    case 1:
                        Log.v(TAG, logStr);
                        break;
                    case 2:
                        Log.d(TAG, logStr);
                        break;
                    case 3:
                        Log.i(TAG, logStr);
                        break;
                    case 4:
                        Log.w(TAG, logStr);
                        break;
                    case 5:
                        Log.e(TAG, logStr);
                        break;
                    case 6:
                        Log.wtf(TAG, logStr);
                        break;
                    case 7:
                        String message = null;

                        try {
                            if (msg.startsWith("{")) {
                                JSONObject jsonObject = new JSONObject(msg);
                                message = jsonObject.toString(4);
                            } else if (msg.startsWith("[")) {
                                JSONArray jsonArray = new JSONArray(msg);
                                message = jsonArray.toString(4);
                            }
                        } catch (JSONException var17) {
                            e(TAG, var17.getCause().getMessage() + "\n" + msg);
                            return;
                        }

                        printLine(TAG, true);
                        message = logStr + LINE_SEPARATOR + message;
                        String[] lines = message.split(LINE_SEPARATOR);
                        StringBuilder jsonContent = new StringBuilder();
                        String[] var13 = lines;
                        int var14 = lines.length;

                        for(int var15 = 0; var15 < var14; ++var15) {
                            String line = var13[var15];
                            jsonContent.append("║ ").append(line).append(LINE_SEPARATOR);
                        }

                        Log.d(TAG, jsonContent.toString());
                        printLine(TAG, false);
                }

            }
        }
    }

    public static void e(String msg) {
        print(5, defaultTAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        error(defaultTAG, msg, tr);
    }

    public static void w(String msg) {
        print(4, defaultTAG, msg);
    }

    public static void w(String msg, Throwable tr) {
        warn(defaultTAG, msg, tr);
    }

    public static void i(String msg) {
        print(3, defaultTAG, msg);
    }

    public static void d(String msg) {
        print(2, defaultTAG, msg);
    }

    public static void v(String msg) {
        print(1, defaultTAG, msg);
    }

    public static void json(String msg) {
        print(7, defaultTAG, msg);
    }

    private static String getFinalTAG(String defaultTAG, String subTAG) {
        return defaultTAG + "-" + subTAG;
    }

    public static void e(String subTAG, String msg) {
        print(5, getFinalTAG(defaultTAG, subTAG), msg);
    }

    public static void e(String subTAG, String msg, Throwable tr) {
        error(getFinalTAG(subTAG, subTAG), msg, tr);
    }

    public static void w(String subTAG, String msg) {
        print(4, getFinalTAG(defaultTAG, subTAG), msg);
    }

    public static void w(String subTAG, String msg, Throwable tr) {
        warn(getFinalTAG(defaultTAG, subTAG), msg, tr);
    }

    public static void i(String subTAG, String msg) {
        print(3, getFinalTAG(defaultTAG, subTAG), msg);
    }

    public static void d(String subTAG, String msg) {
        print(2, getFinalTAG(defaultTAG, subTAG), msg);
    }

    public static void v(String subTAG, String msg) {
        print(1, getFinalTAG(defaultTAG, subTAG), msg);
    }

    public static void json(String subTAG, String msg) {
        print(7, getFinalTAG(defaultTAG, subTAG), msg);
    }

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }

    }
}
