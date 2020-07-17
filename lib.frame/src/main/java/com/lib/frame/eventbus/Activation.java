package com.lib.frame.eventbus;

/**
 * 用作EventBus数据的传递
 */
public class Activation {
    public static final int ADD_TOOLBAR_VIEW = 0001;
    public static final int ON_FIRST_LOCATION_CHANGE = 0002;
    public static final int ON_CHOICE_ITEM_CHANGE = 0003;
    public static final int REGISTER_THIRDPART_ACCOUNT = 0004;
    public static final int ON_ADD_DEVICES = 0005;
    public static final int ON_CHOICE_ICON = 0006;
    public static final int ON_CHOICE_DEVICE = 0007;
    public static final int ON_DEVICE_COUNT_CHANGE = 0011;
    public static final int ON_DEVICE_REFRESH = 0012;
    public static final int LOAD_DEVICE = 9000;
    public static final int ON_CHOICE_TIME = 9001;
    public static final int ON_CHOICE_PLAY_LIST = 9002;
    public static final int ON_CHOICE_ACTION_FINISH = 9003;
    public static final int ON_NEW_SCENE_FINISH = 9004;
    public static final int ON_EDIT_SCENE_FINISH = 9005;
    public static final int ON_DEL_SCENE_FINISH = 9006;
    public static final int MUSIC_PROGRESS_CHANGE = 9007;
    public static final int ON_LOCAL_MUSIC_CHANGE = 9008;
    public static final int BOARDLINK_CHOOSE_PIC = 9009;
    public static final int BOARDLINK_BIND_ICON_SUCCESS = 9010;
    public static final int ON_FAMILY_CHANGE = 6001;//家庭数量改变
    public static final int ON_ROOM_CHANGE = 6002;
    public static final int ON_ROOM_SELECTED_CHANGE = 6003;
    public static final int ON_UHOME_PUSHED_DATA_CHANGE = 70004;
    public static final int ON_MQTT_DATA = 6004;
    public static final int ON_TIME_DATA_CREATE = 3001;
    public static final int ON_RATE_DATA_CREATE = 3002;
    public static final int ON_ACTION_DATA_CREATE = 3002;
    public static final int ON_DELAY_DATA_CREATE = 3003;
    public static final int ON_RELOAD_DATA_CREATE = 3004;
    public static final int ON_START_CONDITIONS = 3005;
    public static final int ON_RETURN_SECURITY = 3006;

    public static final int ON_START_CONDITIONS_CREATE = 3004;
    public static final int ON_AUTO_DATA_DEVICEID = 3101;
    public static final int ON_AUTO_DATA_DEVICEID_TRIGGER = 3102;

    public static final int ON_AUTO_DATA_DEVICE_ID = 3005;

    public static final int ON_AUTO_DATA_K8_SWITCH = 3006;
    public static final int K8_SWITCH_ACTIVITY_CHANGE = 3007;
    public static final int WIFI_CAMERA_UNBIND = 3008;
    public static final int ON_CHOICE_PLAY_LIST_SUB = 3009;
    public static final int ON_RGB_ADD_MODEL = 3010;
    public static final int ON_RGB_EDIT_COLOR = 3011;
    public static final int ON_RGB_RGB_COLORS = 3012;
    public static final int ON_RGB_RGB_TEMPERATURE = 3013;
    public static final int ON_RGB_STATE = 3014;

    private int what = Integer.MIN_VALUE; //比较的唯一值
    private String arg1;
    private String arg2;
    private Object obj1;
    private Object obj2;

    /**
     * 进行比较 what相同即认为意图相同
     *
     * @param _what
     * @return
     */
    public boolean compare(int _what) {
        if (what == Integer.MIN_VALUE) return false;

        return what == _what;
    }

    public Activation(int what) {
        this.what = what;
    }

    public Activation(int what, String arg1) {
        this.what = what;
        this.arg1 = arg1;
    }

    public Activation(int what, Object obj1) {
        this.what = what;
        this.obj1 = obj1;
    }

    public Activation(int what, String arg1, String arg2) {
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public Activation(int what, Object obj1, Object obj2) {
        this.what = what;
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public Activation(int what, String arg1, String arg2, Object obj1) {
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.obj1 = obj1;
    }

    public Activation(int what, String arg1, String arg2, Object obj1, Object obj2) {
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public Object getObj1() {
        return obj1;
    }

    public void setObj1(Object obj1) {
        this.obj1 = obj1;
    }

    public Object getObj2() {
        return obj2;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }


    @Override
    public String toString() {
        return "Activation{" +
                "what=" + what +
                ", arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", obj1=" + obj1 +
                ", obj2=" + obj2 +
                '}';
    }
}
