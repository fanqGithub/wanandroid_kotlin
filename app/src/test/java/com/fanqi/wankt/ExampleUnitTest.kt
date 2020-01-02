package com.fanqi.wankt

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun forcontroll() {

        //循环
        val arrays: Array<Int> = arrayOf(5, 3, 75, 666, 6, 10)

//        for ((i, v) in arrays.withIndex()) {
//            println("arrays遍历第:$i 位 值为:$v")
//        }
//
//        //支持标签跳出某个循环，用@标识 例如 Mine@
//        f1@ for (i in 1..100) {
//            f2@ for (j in 1..50) {
//                if (i + j == 20) {
//                    println("20")
//                    break@f1
//                } else {
//                    println("--------")
//                }
//            }
//        }

        var p: Person = Person("fan")
        var p1: Person = Person("fanq", "nan")


        println("p=${p}")
        println("p1=${p1}")
        println("check resunlt=${KtUtil.check(1, 1)}")

    }


    class Person(personName: String) {
        init {

        }
        var sex: String = ""
        var name = personName

        //如果有主构造函数，那么需要在次构造函数上加上:this主构造函数里面的内容
        constructor(name: String, sex: String) : this(name) {
            this.name = name
            this.sex = sex
        }

        override fun toString(): String {
            return "{name=$name,sex=$sex}"
        }
    }


}
