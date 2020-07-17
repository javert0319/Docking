package com.chia.tracelog.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Constant
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class Constant {
    public static final String STRING_OBJECT_NULL = "Object[object is null]";
    public static final int MAX_CHILD_LEVEL = 2;
    private static List<Parser> defaultParsers;
    public static final String BR = System.getProperty("line.separator");
    public static boolean ISLOGGABLE = false;

    public Constant() {
    }

    public static final List<Parser> getParsers() {
        checkParsers();
        return defaultParsers;
    }

    private static void checkParsers() {
        if (defaultParsers == null) {
            defaultParsers = new ArrayList();
            defaultParsers.add(new BundleParse());
            defaultParsers.add(new IntentParse());
            defaultParsers.add(new CollectionParse());
            defaultParsers.add(new MapParse());
            defaultParsers.add(new ThrowableParse());
            defaultParsers.add(new ReferenceParse());
            defaultParsers.add(new MessageParse());
        }

    }

    public static void addParser(Parser parser) {
        checkParsers();
        if (!defaultParsers.contains(parser)) {
            defaultParsers.add(parser);
        }

    }
}
