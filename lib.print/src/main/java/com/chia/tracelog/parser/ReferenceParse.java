package com.chia.tracelog.parser;

import java.lang.ref.Reference;

/**
 * @ClassName: ReferenceParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class ReferenceParse implements Parser<Reference> {
    public ReferenceParse() {
    }

    public Class<Reference> parseClassType() {
        return Reference.class;
    }

    public String parseString(Reference reference) {
        Object actual = reference.get();
        StringBuilder builder = new StringBuilder(reference.getClass().getSimpleName() + "<" + actual.getClass().getSimpleName() + "> {");
        builder.append("→" + ObjectUtil.objectToString(actual));
        return builder.toString() + "}";
    }
}
