package com.lib.frame.view.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lib.frame.R;

/**
 * @ClassName: LoadingProgressBar
 * @Description: 加载圈
 * @Author: CHIA
 * @CreateDate: 2019/11/8
 */
public class LoadingProgressBar {

    /**
     * 显示加载圈
     * @param activity
     * @param isCover 是否需要遮罩 防止点击
     */
    public static void show(Activity activity,boolean isCover){
        if (isLoading(activity)){
            return;
        }
        FrameLayout root = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (root != null){
            View loadingView = LayoutInflater.from(activity).inflate(R.layout.layout_frame_loading_projressbar, null);
            if (isCover) {
                //遮罩层设置点击事件，拦截底层视图的点击事件
                loadingView.findViewById(R.id.cover).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
            loadingView.findViewById(R.id.cover).setVisibility(isCover ? View.VISIBLE : View.GONE);
            root.removeView(loadingView);
            root.addView(loadingView);
        }
    }

    /**
     * 隐藏加载圈
     * @param activity
     */
    public static void dismiss(Activity activity){
        FrameLayout root = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (root != null){
            View loadingView = root.findViewById(R.id.cover_root);
            if (loadingView != null) {
                root.removeView(loadingView);
            }
        }
    }

    /**
     * 加载圈是否正在显示
     * @param activity
     * @return
     */
    public static boolean isLoading(Activity activity){
        FrameLayout root = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (root != null){
            View loadingView = root.findViewById(R.id.cover_root);
            return loadingView != null && root.indexOfChild(loadingView) != -1;
        }
        return false;
    }
}
