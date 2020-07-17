package com.chia.tracelog.parser;

import java.util.Arrays;

/**
 * @ClassName: ArrayUtil
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public final class ArrayUtil {
    public ArrayUtil() {
    }

    public static int getArrayDimension(Object object) {
        int dim = 0;

        for(int i = 0; i < object.toString().length() && object.toString().charAt(i) == '['; ++i) {
            ++dim;
        }

        return dim;
    }

    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    public static char getType(Object object) {
        if (isArray(object)) {
            String str = object.toString();
            return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
        } else {
            return '\u0000';
        }
    }

    private static void traverseArray(StringBuilder result, Object array) {
        if (isArray(array)) {
            if (getArrayDimension(array) == 1) {
                switch(getType(array)) {
                    case 'B':
                        result.append(Arrays.toString((byte[])((byte[])array)));
                        break;
                    case 'C':
                        result.append(Arrays.toString((char[])((char[])array)));
                        break;
                    case 'D':
                        result.append(Arrays.toString((double[])((double[])array)));
                        break;
                    case 'E':
                    case 'G':
                    case 'H':
                    case 'K':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    default:
                        result.append(Arrays.toString((Object[])((Object[])array)));
                        break;
                    case 'F':
                        result.append(Arrays.toString((float[])((float[])array)));
                        break;
                    case 'I':
                        result.append(Arrays.toString((int[])((int[])array)));
                        break;
                    case 'J':
                        result.append(Arrays.toString((long[])((long[])array)));
                        break;
                    case 'L':
                        Object[] objects = (Object[])((Object[])array);
                        result.append("[");

                        for(int i = 0; i < objects.length; ++i) {
                            result.append(ObjectUtil.objectToString(objects[i]));
                            if (i != objects.length - 1) {
                                result.append(",");
                            }
                        }

                        result.append("]");
                        break;
                    case 'S':
                        result.append(Arrays.toString((short[])((short[])array)));
                        break;
                    case 'Z':
                        result.append(Arrays.toString((boolean[])((boolean[])array)));
                }
            } else {
                result.append("[");

                for(int i = 0; i < ((Object[])((Object[])array)).length; ++i) {
                    traverseArray(result, ((Object[])((Object[])array))[i]);
                    if (i != ((Object[])((Object[])array)).length - 1) {
                        result.append(",");
                    }
                }

                result.append("]");
            }
        } else {
            result.append("not a array!!");
        }

    }

    public static String parseArray(Object array) {
        StringBuilder result = new StringBuilder();
        traverseArray(result, array);
        return result.toString();
    }
}
