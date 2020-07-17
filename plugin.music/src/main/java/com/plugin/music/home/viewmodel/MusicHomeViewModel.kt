package com.plugin.music.home.viewmodel

import com.chia.global.Global
import com.lib.frame.view.BaseView
import com.lib.frame.viewmodel.BaseViewModel
import com.lib.network.NetFacade
import com.lib.network.ParamsCreator
import com.lib.utils.print.T
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @ClassName:   MusicHomeViewModel
 * @Description: 作用描述
 * @Author:      CHIA
 * @CreateDate:  2020/7/17
 */
class MusicHomeViewModel : BaseViewModel<BaseView>(){

    var disposable:CompositeDisposable ?= null

    fun getSMSAuthCode(){
        val mapMap = HashMap<String,Any>()
        mapMap[""] = "18762670092"
        val options = ParamsCreator.generateRequestBodyParams(mapMap)
        disposable?.add(
            NetFacade.getInstance().provideDefaultService()
            .getSMSAuthCode(options)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response ->
                val data = Global.toJson(response)
            }, {
                T.show(it.message?:"")
            }))
    }

    fun loadMiguHome(){
        val mapMap = HashMap<String,Any>()
        mapMap["deviceId"] = "1053434406741512192"
        mapMap["tokenId"] = "UHRjNXg3aXF1dlg3NjB1Vm12MUNHQUNKMGJOWFE4SXo\\u003d"
        mapMap["authCode"] = "3819AE422E844540B7299B9FE3C0F765"
        mapMap["limit"] = 3
        val options = ParamsCreator.generateRequestBodyParams(mapMap)
        disposable?.add(NetFacade.getInstance().provideDefaultService()
            .loadMiguHome(options)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({response ->

            },{
                T.show(it.message?:"")
            }))
    }
}