package com.plugin.music.home.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chia.global.Global
import com.lib.frame.view.BaseActivity
import com.lib.frame.view.BaseView
import com.lib.frame.viewmodel.BaseViewModel
import com.lib.network.NetFacade
import com.lib.network.ParamsCreator
import com.lib.utils.print.T
import com.plugin.music.R
import com.plugin.music.databinding.MusicActivityMainBinding
import com.plugin.music.databinding.MusicActivityMainBindingImpl
import com.plugin.music.home.viewmodel.MusicHomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@Route(path = "/music/main")
class MainActivity : BaseActivity<BaseView,MusicHomeViewModel>() {

    val disposable = CompositeDisposable()

    private lateinit var binding: MusicActivityMainBinding

    override fun createViewModel(): MusicHomeViewModel {
        return MusicHomeViewModel()
    }

    override fun initVarAndView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.music_activity_main)
        ARouter.getInstance().inject(this)
        initToolbar("音乐信息",true)
        binding.vm = mViewModel
    }

    override fun initDoing() {
        super.initDoing()
        mViewModel.disposable = disposable
        mViewModel.getSMSAuthCode()
        mViewModel.loadMiguHome()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}