package cn.chia.docking;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.utils.print.L;
import com.lib.utils.print.T;
import com.squareup.leakcanary.LeakCanary;

/**
 * @ClassName: App
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        L.init(App.getInstance(),getString(R.string.app_name),true,true);
        T.init(App.getInstance());
        //第三方库的初始化尽量放到SplashActivity中
        initLeakCanary();
        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(App.getInstance());
    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this))
            LeakCanary.install(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
