package com.fanqi.wankt.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.fanqi.wankt.R
import com.fanqi.wankt.utils.Logger

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d("TodoActivity dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

}
