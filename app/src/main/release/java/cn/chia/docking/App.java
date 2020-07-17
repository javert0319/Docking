package cn.chia.docking;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chia.global.Global;
import com.lib.network.NetFacade;
import com.lib.network.ParamsCreator;
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
        Global.init(App.getInstance());
        //第三方库的初始化尽量放到SplashActivity中
        initLeakCanary();
        initNecessaryValue();
        initARouter();
        NetFacade.init(App.getInstance());
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

    //初始化必要的值
    private void initNecessaryValue() {
        ParamsCreator.setKEY("A716A953593940D2BD78E1A02CD3C070");
        ParamsCreator.setCID("750064224428658688");
        ParamsCreator.setSID("750837261197414400");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
