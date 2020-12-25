package com.fanqi.wankt.learn

/**
 * Created by fanqi on 2020/12/24.
 * Description:
 *
 * 注意：kotlin的类默认是final的，如果要继承一个类，你需要给这个类加open修饰符
 */
class B: A(){

    val sum2: Int.(Int) -> Int = {
        this + it
    }

    infix fun Int.加(i:Int):Int = this + i


    fun test1(){
        3.sum2(2)

        5 加 2
    }
}