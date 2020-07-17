package com.lib.network.observer;

/**
 * @ClassName: Type
 * @Description: 异常类型
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public enum Type {
    NO_OPERATE(1),  //无权操作
    TOKEN_INVALID(2); //Token失效

    private int value;

    Type(int i) {
        this.value = i;
    }

    public static Type valueOf(int value) {
        //注意，添加新的TYPE值后，这里需要增加 ---------------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        if (value == 1) {
            return NO_OPERATE;
        } else if(value ==2){
            return TOKEN_INVALID;
        }
        return NO_OPERATE;
    }

    public int getValue() {
        return value;
    }
}
