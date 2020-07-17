package com.chia.tracelog.parser;

/**
 * @ClassName: ParserUtils
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class ParserUtils {
    public ParserUtils() {
    }

    public static String shorten(String string, int count, int length) {
        if (string == null) {
            return null;
        } else {
            String resultString = string;
            if (Math.abs(length) < string.length()) {
                if (length > 0) {
                    resultString = string.substring(0, length);
                }

                if (length < 0) {
                    resultString = string.substring(string.length() + length, string.length());
                }
            }

            return Math.abs(count) > resultString.length() ? String.format("%" + count + "s", resultString) : resultString;
        }
    }

    public static String shortenClassName(String className, int count, int maxLength) throws Exception {
        className = shortenPackagesName(className, count);
        if (className == null) {
            return null;
        } else if (maxLength == 0) {
            return className;
        } else if (maxLength > className.length()) {
            return className;
        } else {
            StringBuilder builder;
            int index;
            int i;
            if (maxLength < 0) {
                maxLength = -maxLength;
                builder = new StringBuilder();

                for(index = className.length() - 1; index > 0; index = i - 1) {
                    i = className.lastIndexOf(46, index);
                    if (i == -1) {
                        if (builder.length() > 0 && builder.length() + index + 1 > maxLength) {
                            builder.insert(0, '*');
                            break;
                        }

                        builder.insert(0, className.substring(0, index + 1));
                    } else {
                        if (builder.length() > 0 && builder.length() + (index + 1 - i) + 1 > maxLength) {
                            builder.insert(0, '*');
                            break;
                        }

                        builder.insert(0, className.substring(i, index + 1));
                    }
                }

                return builder.toString();
            } else {
                builder = new StringBuilder();

                for(index = 0; index < className.length(); index = i + 1) {
                    i = className.indexOf(46, index);
                    if (i == -1) {
                        if (builder.length() > 0) {
                            builder.insert(builder.length(), '*');
                        } else {
                            builder.insert(builder.length(), className.substring(index, className.length()));
                        }
                        break;
                    }

                    if (builder.length() > 0 && i + 1 > maxLength) {
                        builder.insert(builder.length(), '*');
                        break;
                    }

                    builder.insert(builder.length(), className.substring(index, i + 1));
                }

                return builder.toString();
            }
        }
    }

    private static String shortenPackagesName(String className, int count) {
        if (className == null) {
            return null;
        } else if (count == 0) {
            return className;
        } else {
            StringBuilder builder = new StringBuilder();
            int index;
            int i;
            if (count > 0) {
                int points = 1;

                for(index = 0; index < className.length(); ++points) {
                    i = className.indexOf(46, index);
                    if (i == -1) {
                        builder.insert(builder.length(), className.substring(index, className.length()));
                        break;
                    }

                    if (points == count) {
                        builder.insert(builder.length(), className.substring(index, i));
                        break;
                    }

                    builder.insert(builder.length(), className.substring(index, i + 1));
                    index = i + 1;
                }
            } else if (count < 0) {
                String exceptString = shortenPackagesName(className, -count);
                if (!className.equals(exceptString)) {
                    return className.replaceFirst(exceptString + '.', "");
                }

                index = className.lastIndexOf(46) + 1;
                i = className.length();
                builder.insert(builder.length(), className.substring(index, i));
            }

            return builder.toString();
        }
    }
}
