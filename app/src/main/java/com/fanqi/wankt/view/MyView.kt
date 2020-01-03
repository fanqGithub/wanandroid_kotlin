package com.fanqi.wankt.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.fanqi.wankt.utils.Logger

/**
 * Created by fanqi on 2020-01-03.
 * Description:
 */
class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Logger.d("MyView dispatchTouchEvent")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d("MyView onTouchEvent")
        return super.onTouchEvent(event)
    }
}