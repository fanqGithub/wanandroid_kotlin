package com.fanqi.wankt

import android.app.Application
import com.fanqi.wankt.base.Preference
import com.fanqi.wankt.constant.AppPreference

/**
 * Created by fanqi on 2019-12-18.
 * Description:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Preference.setContext(applicationContext)
    }


}