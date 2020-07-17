package com.chia.tracelog.parser;

import android.os.Bundle;

import java.util.Iterator;

/**
 * @ClassName: BundleParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class BundleParse implements Parser<Bundle> {
    public BundleParse() {
    }

    public Class<Bundle> parseClassType() {
        return Bundle.class;
    }

    public String parseString(Bundle bundle) {
        if (bundle == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder(bundle.getClass().getName() + " [" + LINE_SEPARATOR);
            Iterator var3 = bundle.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                builder.append(String.format("'%s' => %s " + LINE_SEPARATOR, key, ObjectUtil.objectToString(bundle.get(key))));
            }

            builder.append("]");
            return builder.toString();
        }
    }
}
