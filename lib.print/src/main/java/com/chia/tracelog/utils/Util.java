package com.chia.tracelog.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName: Util
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class Util {
    public Util() {
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }

    }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }

    public static String date2Str(Date d) {
        return date2Str(d, (String)null);
    }

    public static String date2Str(Date d, String format) {
        if (d == null) {
            return null;
        } else {
            if (format == null || format.length() == 0) {
                format = "yy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String s = sdf.format(d);
            return s;
        }
    }

    public static void deleteAll(String path) {
        File file = new File(path);
        deleteAll(file);
    }

    public static void deleteAll(File file) {
        if (!file.isFile() && file.list().length != 0) {
            File[] files = file.listFiles();

            for(int i = 0; i < files.length; ++i) {
                deleteAll(files[i]);
                files[i].delete();
            }

            if (file.exists()) {
                file.delete();
            }
        } else {
            file.delete();
        }

    }

    public static boolean saveStrToFile(String str, String fileName, boolean append) {
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileWriter writer = new FileWriter(fileName, append);
            writer.write(str);
            writer.close();
            return true;
        } catch (IOException var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static boolean saveStrToFile(String str, String fileName, String charsetName) {
        if (str != null && !"".equals(str)) {
            FileOutputStream stream = null;

            boolean var5;
            try {
                File file = new File(fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                byte[] b = null;
                if (charsetName != null && !"".equals(charsetName)) {
                    b = str.getBytes(charsetName);
                } else {
                    b = str.getBytes();
                }

                stream = new FileOutputStream(file);
                stream.write(b, 0, b.length);
                stream.flush();
                boolean var6 = true;
                return var6;
            } catch (Exception var16) {
                var5 = false;
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                        stream = null;
                    } catch (Exception var15) {
                    }
                }

            }

            return var5;
        } else {
            return false;
        }
    }

    public static boolean checkDirSize(String filepath, int maxSize) {
        File file = new File(filepath);
        long size = getDirSize(file);
        return size <= (long)maxSize;
    }

    public static long getDirSize(File file) {
        if (!file.exists()) {
            System.out.println("文件或者文件夹不存在:" + file.getAbsolutePath());
            return 0L;
        } else if (!file.isDirectory()) {
            long size = file.length();
            return size;
        } else {
            File[] children = file.listFiles();
            long size = 0L;
            File[] var4 = children;
            int var5 = children.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File f = var4[var6];
                size += getDirSize(f);
            }

            return size;
        }
    }

    static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        } else {
            if (a != null && b != null) {
                int length = a.length();
                if (length == b.length()) {
                    if (a instanceof String && b instanceof String) {
                        return a.equals(b);
                    }

                    for(int i = 0; i < length; ++i) {
                        if (a.charAt(i) != b.charAt(i)) {
                            return false;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        } else {
            for(Throwable t = tr; t != null; t = t.getCause()) {
                if (t instanceof UnknownHostException) {
                    return "";
                }
            }

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }
    }

    public static String toString(Object object) {
        if (object == null) {
            return "null";
        } else if (!object.getClass().isArray()) {
            return object.toString();
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[])((boolean[])object));
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[])((byte[])object));
        } else if (object instanceof char[]) {
            return Arrays.toString((char[])((char[])object));
        } else if (object instanceof short[]) {
            return Arrays.toString((short[])((short[])object));
        } else if (object instanceof int[]) {
            return Arrays.toString((int[])((int[])object));
        } else if (object instanceof long[]) {
            return Arrays.toString((long[])((long[])object));
        } else if (object instanceof float[]) {
            return Arrays.toString((float[])((float[])object));
        } else if (object instanceof double[]) {
            return Arrays.toString((double[])((double[])object));
        } else {
            return object instanceof Object[] ? Arrays.deepToString((Object[])((Object[])object)) : "Couldn't find a correct type for the object";
        }
    }
}
