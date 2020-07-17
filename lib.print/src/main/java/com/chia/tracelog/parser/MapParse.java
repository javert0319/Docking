package com.chia.tracelog.parser;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: MapParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class MapParse implements Parser<Map> {
    public MapParse() {
    }

    public Class<Map> parseClassType() {
        return Map.class;
    }

    public String parseString(Map map) {
        String msg = map.getClass().getName() + " [" + LINE_SEPARATOR;
        Set<Object> keys = map.keySet();

        Object key;
        String itemString;
        Object value;
        for(Iterator var4 = keys.iterator(); var4.hasNext(); msg = msg + String.format(itemString, ObjectUtil.objectToString(key), ObjectUtil.objectToString(value))) {
            key = var4.next();
            itemString = "%s -> %s" + LINE_SEPARATOR;
            value = map.get(key);
            if (value != null) {
                if (value instanceof String) {
                    value = "\"" + value + "\"";
                } else if (value instanceof Character) {
                    value = "'" + value + "'";
                }
            }
        }

        return msg + "]";
    }
}
