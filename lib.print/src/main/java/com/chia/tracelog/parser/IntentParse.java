package com.chia.tracelog.parser;

import android.content.Intent;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: IntentParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class IntentParse implements Parser<Intent> {
    private static Map<Integer, String> flagMap = new HashMap();

    public IntentParse() {
    }

    public Class<Intent> parseClassType() {
        return Intent.class;
    }

    public String parseString(Intent intent) {
        StringBuilder builder = new StringBuilder(this.parseClassType().getSimpleName() + " [" + LINE_SEPARATOR);
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Scheme", intent.getScheme()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Action", intent.getAction()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "DataString", intent.getDataString()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Type", intent.getType()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Package", intent.getPackage()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "ComponentInfo", intent.getComponent()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Flags", this.getFlags(intent.getFlags())));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Categories", intent.getCategories()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Extras", (new BundleParse()).parseString(intent.getExtras())));
        return builder.toString() + "]";
    }

    private String getFlags(int flags) {
        StringBuilder builder = new StringBuilder();
        Iterator var3 = flagMap.keySet().iterator();

        while(var3.hasNext()) {
            int flagKey = (Integer)var3.next();
            if ((flagKey & flags) == flagKey) {
                builder.append((String)flagMap.get(flagKey));
                builder.append(" | ");
            }
        }

        if (TextUtils.isEmpty(builder.toString())) {
            builder.append(flags);
        } else if (builder.indexOf("|") != -1) {
            builder.delete(builder.length() - 2, builder.length());
        }

        return builder.toString();
    }

    static {
        Class cla = Intent.class;
        Field[] fields = cla.getDeclaredFields();
        Field[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            field.setAccessible(true);
            if (field.getName().startsWith("FLAG_")) {
                int value = 0;

                try {
                    Object object = field.get(cla);
                    if (object instanceof Integer || object.getClass().getSimpleName().equals("int")) {
                        value = (Integer)object;
                    }
                } catch (Exception var8) {
                    var8.printStackTrace();
                }

                if (flagMap.get(value) == null) {
                    flagMap.put(value, field.getName());
                }
            }
        }

    }
}
