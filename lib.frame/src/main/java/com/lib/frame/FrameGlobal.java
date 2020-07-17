package com.lib.frame;

/**
 * @author EthanCo
 * @since 2018/5/7
 */

public class FrameGlobal {
    private static boolean useFrameDefTheme = false;

    public static boolean isUseFrameDefTheme() {
        return useFrameDefTheme;
    }

    public static void setUseFrameDefTheme(boolean useFrameDefTheme) {
        FrameGlobal.useFrameDefTheme = useFrameDefTheme;
    }
}
