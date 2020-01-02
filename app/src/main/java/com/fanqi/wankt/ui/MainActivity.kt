package com.fanqi.wankt.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.fanqi.wankt.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = """
            |First Line
            |Second Line
            |Third Line
            """.trimMargin()


        tvKt.text = text
    }

    fun sum(a: Int, b: Int) = a + b


    fun forcontroll(){
        val arrays:Array<Int> = arrayOf(5,3,75,666,6,10)

        for ((i,v) in arrays.withIndex()){
            println("arrays遍历第:$i 位 值为:$v")
        }

    }

}
