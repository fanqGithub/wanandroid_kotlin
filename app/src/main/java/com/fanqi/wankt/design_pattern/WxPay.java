package com.fanqi.wankt.design_pattern;

/**
 * Created by fanqi on 2020/12/25.
 * Description:
 */
class WxPay extends InterAdapter{
    @Override
    public void onPay() {
        super.onPay();
        System.out.println("微信支付");
    }
}
