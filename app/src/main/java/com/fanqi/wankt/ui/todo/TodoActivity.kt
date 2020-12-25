package com.fanqi.wankt.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.fanqi.wankt.R
import com.fanqi.wankt.common.bean.UserCoin
import com.fanqi.wankt.utils.Logger
import com.fanqi.wankt.utils.toast
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.coroutines.*

class TodoActivity : AppCompatActivity() {

    private var mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        btnView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return true
            }
        })
        init()
    }


    fun init(){

        println("1:current thread is ${Thread.currentThread().name}")

        GlobalScope.launch {

            println("2:current thread is ${Thread.currentThread().name}")

            delay(2000)

            println("3:current thread is ${Thread.currentThread().name}")
        }

        runBlocking {
            val job1 = launch {

                println("current 1")
                yield()
                println("current 3")
                yield()
                println("current 5")
            }

            val job2 = launch {

                println("current 2")
                yield()
                println("current 4")
                yield()
                println("current 6")
            }


        }

//        var userCoin: UserCoin? =null
//        println(userCoin.username)

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d("TodoActivity dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d("TodoActivity onTouchEvent")
        return super.onTouchEvent(event)
    }

    fun viewClick(view: View) {
        toast("view click", this)
    }

    fun layoutClick(view: View) {
        toast("layoutClick click", this)
    }

    fun groupAclick(view: View) {
        toast("groupAclick click", this)
    }

}
