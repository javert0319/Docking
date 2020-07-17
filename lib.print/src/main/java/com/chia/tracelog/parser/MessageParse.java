package com.chia.tracelog.parser;

import android.os.Message;

/**
 * @ClassName: MessageParse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class MessageParse implements Parser<Message> {
    public MessageParse() {
    }

    public Class<Message> parseClassType() {
        return Message.class;
    }

    public String parseString(Message message) {
        if (message == null) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder(message.getClass().getName() + " [" + LINE_SEPARATOR);
            stringBuilder.append(String.format("%s = %s", "what", message.what)).append(LINE_SEPARATOR);
            stringBuilder.append(String.format("%s = %s", "when", message.getWhen())).append(LINE_SEPARATOR);
            stringBuilder.append(String.format("%s = %s", "arg1", message.arg1)).append(LINE_SEPARATOR);
            stringBuilder.append(String.format("%s = %s", "arg2", message.arg2)).append(LINE_SEPARATOR);
            stringBuilder.append(String.format("%s = %s", "data", (new BundleParse()).parseString(message.getData()))).append(LINE_SEPARATOR);
            stringBuilder.append(String.format("%s = %s", "obj", ObjectUtil.objectToString(message.obj))).append(LINE_SEPARATOR);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }
}
