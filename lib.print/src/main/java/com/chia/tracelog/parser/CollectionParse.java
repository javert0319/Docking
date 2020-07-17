package com.chia.tracelog.parser;

import java.util.Collection;
import java.util.Iterator;

/**
 * @ClassName: CollectionParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class CollectionParse implements Parser<Collection> {
    public CollectionParse() {
    }

    public Class<Collection> parseClassType() {
        return Collection.class;
    }

    public String parseString(Collection collection) {
        String simpleName = collection.getClass().getName();
        String msg = "%s size = %d [" + LINE_SEPARATOR;
        msg = String.format(msg, simpleName, collection.size());
        if (!collection.isEmpty()) {
            Iterator<Object> iterator = collection.iterator();

            String itemString;
            Object item;
            for(int flag = 0; iterator.hasNext(); msg = msg + String.format(itemString, flag, ObjectUtil.objectToString(item), flag++ < collection.size() - 1 ? "," + LINE_SEPARATOR : LINE_SEPARATOR)) {
                itemString = "[%d]:%s%s";
                item = iterator.next();
            }
        }

        return msg + "]";
    }
}
