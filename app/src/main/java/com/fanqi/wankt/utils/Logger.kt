package com.fanqi.wankt.utils

import android.util.Log

/**
 * Created by fanqi on 2019-12-20.
 * Description:对象声明
 */
object Logger {

    private val isDebug: Boolean = true
    private val TAG: String = "AppLog"

    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }

}