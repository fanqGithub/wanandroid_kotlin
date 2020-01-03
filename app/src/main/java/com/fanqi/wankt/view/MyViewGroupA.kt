package com.fanqi.wankt.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.fanqi.wankt.utils.Logger

/**
 * Created by fanqi on 2020-01-03.
 * Description:
 */
class MyViewGroupA(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d("MyViewGroupA dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d("MyViewGroupA onInterceptTouchEvent")
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d("MyViewGroupA onTouchEvent")
        return super.onTouchEvent(event)
    }

}