package com.fanqi.wankt.design_pattern;

/**
 * Created by fanqi on 2020/12/25.
 * Description:抽象类实现一个接口，并且置空接口中的方法
 */
abstract class InterAdapter implements Pay {
    public void onPay() {
    }

    public void onDone() {
    }
}
