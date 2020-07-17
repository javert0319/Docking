package com.chia.tracelog.parser;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * @ClassName: ObjectUtil
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class ObjectUtil {
    public ObjectUtil() {
    }

    public static String objectToString(Object object) {
        return objectToString(object, 0);
    }

    public static boolean isStaticInnerClass(Class cla) {
        if (cla != null && cla.isMemberClass()) {
            int modifiers = cla.getModifiers();
            if ((modifiers & 8) == 8) {
                return true;
            }
        }

        return false;
    }

    public static String objectToString(Object object, int childLevel) {
        if (object == null) {
            return "Object[object is null]";
        } else if (childLevel > 2) {
            return object.toString();
        } else {
            if (Constant.getParsers() != null && Constant.getParsers().size() > 0) {
                Iterator var2 = Constant.getParsers().iterator();

                while(var2.hasNext()) {
                    Parser parser = (Parser)var2.next();
                    if (parser.parseClassType().isAssignableFrom(object.getClass())) {
                        return parser.parseString(object);
                    }
                }
            }

            if (ArrayUtil.isArray(object)) {
                return ArrayUtil.parseArray(object);
            } else if (!object.toString().startsWith(object.getClass().getName() + "@")) {
                return object.toString();
            } else {
                StringBuilder builder = new StringBuilder();
                getClassFields(object.getClass(), builder, object, false, childLevel);
                Class superClass = object.getClass().getSuperclass();
                if (superClass != null) {
                    while(!superClass.equals(Object.class)) {
                        getClassFields(superClass, builder, object, true, childLevel);
                        superClass = superClass.getSuperclass();
                    }
                } else {
                    builder.append(object.toString());
                }

                return builder.toString();
            }
        }
    }

    private static void getClassFields(Class cla, StringBuilder builder, Object o, boolean isSubClass, int childOffset) {
        if (!cla.equals(Object.class)) {
            if (isSubClass) {
                builder.append(Constant.BR + Constant.BR + "=> ");
            }

            String breakLine = "";
            builder.append(cla.getSimpleName() + " {");
            Field[] fields = cla.getDeclaredFields();

            for(int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                field.setAccessible(true);
                if (!cla.isMemberClass() || isStaticInnerClass(cla) || i != 0) {
                    Object subObject = null;
                    boolean var15 = false;

                    String formatString;
                    label273: {
                        try {
                            var15 = true;
                            subObject = field.get(o);
                            var15 = false;
                            break label273;
                        } catch (IllegalAccessException var16) {
                            subObject = var16;
                            var15 = false;
                        } finally {
                            if (var15) {
                                if (subObject != null) {
                                    if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0"))) {
                                        continue;
                                    }

                                    if (subObject instanceof String) {
                                        subObject = "\"" + subObject + "\"";
                                    } else if (subObject instanceof Character) {
                                        subObject = "'" + subObject + "'";
                                    }

                                    if (childOffset < 2) {
                                        subObject = objectToString(subObject, childOffset + 1);
                                    }
                                }

                                String formatStr = breakLine + "%s = %s, ";
                                builder.append(String.format(formatStr, field.getName(), subObject == null ? "null" : subObject.toString()));
                            }
                        }

                        if (subObject != null) {
                            if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0"))) {
                                continue;
                            }

                            if (subObject instanceof String) {
                                subObject = "\"" + subObject + "\"";
                            } else if (subObject instanceof Character) {
                                subObject = "'" + subObject + "'";
                            }

                            if (childOffset < 2) {
                                subObject = objectToString(subObject, childOffset + 1);
                            }
                        }

                        formatString = breakLine + "%s = %s, ";
                        builder.append(String.format(formatString, field.getName(), subObject == null ? "null" : subObject.toString()));
                        continue;
                    }

                    if (subObject != null) {
                        if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0"))) {
                            continue;
                        }

                        if (subObject instanceof String) {
                            subObject = "\"" + subObject + "\"";
                        } else if (subObject instanceof Character) {
                            subObject = "'" + subObject + "'";
                        }

                        if (childOffset < 2) {
                            subObject = objectToString(subObject, childOffset + 1);
                        }
                    }

                    formatString = breakLine + "%s = %s, ";
                    builder.append(String.format(formatString, field.getName(), subObject == null ? "null" : subObject.toString()));
                }
            }

            if (builder.toString().endsWith("{")) {
                builder.append("}");
            } else {
                builder.replace(builder.length() - 2, builder.length() - 1, breakLine + "}");
            }

        }
    }
}
