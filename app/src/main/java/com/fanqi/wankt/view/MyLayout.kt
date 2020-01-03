package com.fanqi.wankt.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.fanqi.wankt.utils.Logger

/**
 * Created by fanqi on 2020-01-03.
 * Description:
 */
class MyLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d("MyLayout dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d("MyLayout onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d("MyLayout onTouchEvent")
        return super.onTouchEvent(event)
    }

}