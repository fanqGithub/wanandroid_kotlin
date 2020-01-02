package com.fanqi.wankt.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 * @author:范启
 * Created on 2019-12-29.
 * Description:
 */

fun toast(msg: String, context: Context) {
    Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).show()
}


fun encodeCookie(cookies: List<String>): String {
    val sb = StringBuilder()
    val set = HashSet<String>()
    cookies
        .map { cookie ->
            cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
        .forEach {
            it.filterNot { set.contains(it) }.forEach { set.add(it) }
        }

    val ite = set.iterator()
    while (ite.hasNext()) {
        val cookie = ite.next()
        sb.append(cookie).append(";")
    }

    val last = sb.lastIndexOf(";")
    if (sb.length - 1 == last) {
        sb.deleteCharAt(last)
    }

    return sb.toString()
}
