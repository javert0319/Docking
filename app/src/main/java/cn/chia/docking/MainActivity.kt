package cn.chia.docking

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.utils.print.L
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler: Handler = Handler()
    private var position = 0
    private var notificationManager:NotificationManager ?= null
    private var notification:Notification ?= null //下载通知进度提示
    private var builder: NotificationCompat.Builder ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ARouter.getInstance().inject(this)
        initNotification()
        bt_halo.setOnClickListener {
            Log.i("jiawei","MainActivity onCreate Notification")
            ARouter.getInstance().build("/halo/main").navigation()
        }
        bt_hdl.setOnClickListener {
            ARouter.getInstance().build("/hdl/main").navigation()
        }
        /*
        Params
        parameter	description
        downloadUrl	download link
        icoResId	Notification icon，default is app icon
        icoSmallResId	Notification bottom right corner icon，default is app icon
        storeDir	store apk dir，default in sdcard/Android/package/update
        updateProgress	Refresh notification progress bar, default updated each time you download add 1%
        downloadNotificationFlag	downloading Notification Flag
        downloadErrorNotificationFlag	download error Notification Flag
        downloadSuccessNotificationFlag	download success Notification Flag
        isSendBroadcast	Whether to send broadcast
        * */
        /*UpdateService.Builder.create(URL)
            .setStoreDir("update")
            .setIcoResId(R.mipmap.ic_launcher)
            .setIsSendBroadcast(true)
            .setDownloadSuccessNotificationFlag(Notification.DEFAULT_SOUND)
            .setDownloadErrorNotificationFlag(Notification.DEFAULT_SOUND)
            .setUpdateProgress(1)
            .build(this);*/
    }

    private val runn: Runnable = object : Runnable {
        override fun run() {
            position++
            builder?.setProgress(100, (position * 100), false);
            builder?.setContentText("下载进度:" + (position * 100) + "%");
            notification = builder?.build();
            notificationManager?.notify(1, notification);
            if (position < 100) handler.postDelayed(this, 200) // 200毫秒progress加1
        }
    }

    //初始化通知
    private fun initNotification() {
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        builder = NotificationCompat.Builder(this)
        builder!!.setContentTitle("正在更新...") //设置通知标题
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher_round)) //设置通知的大图标
            .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
            .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
            .setAutoCancel(false)//设置通知被点击一次是否自动取消
            .setContentText("下载进度:" + "0%")
            .setProgress(100, 0, false);
        notification = builder!!.build()//构建通知对象
    }

    /**
     * 倒计时显示
     */
    private fun countDown() {
        /**
         * CountDownTimer timer = new CountDownTimer(3000, 1000)中，
         * 第一个参数表示总时间，第二个参数表示间隔时间。
         * 意思就是每隔一秒会回调一次方法onTick，然后1秒之后会回调onFinish方法。
         */
        val timer: CountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                L.i(
                    "jiawei",
                    "NotificationActivity countDown: " + "已发送(" + millisUntilFinished / 1000 + ")"
                )
            }

            override fun onFinish() {
                L.i("jiawei", "NotificationActivity countDown: " + "重新获取验证码")
            }
        }
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        timer.start()
    }
}
