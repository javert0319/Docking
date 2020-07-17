package com.lib.frame.viewmodel._enum;

/**
 * @ClassName: LayoutManager
 * @Description: LayoutManager 属性
 * @Author: CHIA
 * @CreateDate: 2019/3/15 14:01
 */
public class LayoutManager {

    private int spanCount;
    private int orientation;
    private boolean isReverseLayout;

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isReverseLayout() {
        return isReverseLayout;
    }

    public void setReverseLayout(boolean reverseLayout) {
        isReverseLayout = reverseLayout;
    }
}
