package com.fanqi.wankt

/**
 * Created by fanqi on 2019-12-18.
 * Description:
 */
fun topLevelFunction(){}

class KtUtil {

    //在 Kotlin 里，静态变量和静态方法这两个概念被去除了。

    //如果想在 Kotlin 中像 Java 一样通过类直接引用该怎么办呢？Kotlin 的答案是 companion object：

    companion object {
        //相当于Java中的静态变量了
        val runMode = "scan"

        fun check(a: Int, b: Int): Boolean {
            if (a == b) {
                return true;
            }
            return false;
        }

    }
}