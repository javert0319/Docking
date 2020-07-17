package com.plugin.hdl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.frame.view.simple.BaseSimpleActivity

@Route(path = "/hdl/main")
class MainActivity : BaseSimpleActivity() {

    override fun initVarAndView(savedInstanceState: Bundle?) {
        setContentView(R.layout.hdl_activity_main)
        ARouter.getInstance().inject(this)
        initToolbar("HDL",true)
    }

}
