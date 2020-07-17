package com.chia.tracelog.logs;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chia.logbase.Trace;
import com.chia.tracelog.utils.Util;

import java.io.File;
import java.util.Date;

/**
 * @ClassName: DiskLogTrace
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class DiskLogTrace implements Trace {
    private Context context = null;
    private final String fileSuffix = ".log";
    private String fileName = "TraceLog";
    private String folder = "TraceLog";
    private int maxCacheSize = 10485760;
    private boolean useCacheDir = true;
    private String path = null;
    private WriteHandler writeHandler;

    public DiskLogTrace(Context context, int maxCacheSize, String folder, String fileName, boolean useCacheDir) {
        this.context = context;
        this.fileName = fileName;
        this.folder = folder;
        this.maxCacheSize = maxCacheSize;
        this.useCacheDir = useCacheDir;
        this.init();
    }

    public DiskLogTrace(Context context, int maxCacheSize, String folder, String fileName) {
        this.context = context;
        this.maxCacheSize = maxCacheSize;
        this.folder = folder;
        this.fileName = fileName;
        this.init();
    }

    public DiskLogTrace(Context context, String folder, String fileName) {
        this.context = context;
        this.fileName = fileName;
        this.folder = folder;
        this.init();
    }

    public DiskLogTrace(Context context, int maxCacheSize) {
        this.context = context;
        this.maxCacheSize = maxCacheSize;
        this.init();
    }

    public DiskLogTrace(Context context) {
        this.context = context;
        this.init();
    }

    public DiskLogTrace(Context context, String path) {
        this.context = context;
        this.path = path;
        this.init();
    }

    private void init() {
        if (TextUtils.isEmpty(this.path)) {
            String filePath = this.generateDir(this.context, this.folder);
            this.path = this.generatePath(filePath);
        }

        HandlerThread ht = new HandlerThread("TraceLog." + System.currentTimeMillis());
        ht.start();
        this.writeHandler = new WriteHandler(ht.getLooper(), this.path, this.maxCacheSize);
    }

    private String generatePath(String dir) {
        if (TextUtils.isEmpty(this.path)) {
            this.path = dir + File.separator + this.fileName + ".log";
        }

        return this.path;
    }

    private String generateDir(Context context, String folder) {
        String dir;
        if (Environment.getExternalStorageState().equals("mounted")) {
            if (this.useCacheDir) {
                dir = context.getExternalCacheDir() + File.separator + folder;
            } else {
                dir = Environment.getExternalStorageDirectory() + File.separator + folder;
            }
        } else {
            dir = context.getCacheDir().getPath() + File.separator + folder;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        return dir;
    }

    private void saveLogToFile(String tag, String message) {
        StringBuilder sb = this.generateLog(tag, message);
        Message msg = Message.obtain();
        msg.obj = sb.toString();
        this.writeHandler.sendMessage(msg);
    }

    @NonNull
    private StringBuilder generateLog(String tag, String message) {
        String time = Util.date2Str(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(time);
        sb.append(" ");
        sb.append(tag);
        sb.append(":");
        sb.append(message);
        sb.append("\r\n");
        return sb;
    }

    public boolean isLoggable(String tag, int priority,boolean isLoggable) {
        return true;//日志是否写入文件
    }

    public void log(int priority, String tag, String message) {
        if (!TextUtils.isEmpty(message)) {
            this.saveLogToFile(tag, message);
        }
    }

    private static class WriteHandler extends Handler {
        private long lastClearTime = 0L;
        private final int maxCacheSize;
        private final String path;

        public WriteHandler(Looper looper, String path, int maxCacheSize) {
            super(looper);
            this.path = path;
            this.maxCacheSize = maxCacheSize;
            this.clearCacheIfTimeOut(path);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            this.saveLogToFile((String)msg.obj, this.path);
        }

        private void saveLogToFile(String message, String path) {
            this.preSaveLogToFile(path);
            Util.saveStrToFile(message, path, true);
        }

        private void preSaveLogToFile(String path) {
            this.clearCacheIfTimeOut(path);
        }

        private void clearCacheIfTimeOut(String filePath) {
            if (System.currentTimeMillis() - this.lastClearTime > 3600000L) {
                this.clearCache(filePath);
                this.lastClearTime = System.currentTimeMillis();
            }
        }

        private void clearCache(String filePath) {
            if (!Util.checkDirSize(filePath, this.maxCacheSize)) {
                Util.deleteAll(filePath);
            }
        }
    }
}
