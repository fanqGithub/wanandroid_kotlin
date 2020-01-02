package com.fanqi.wankt.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanqi.wankt.base.Preference
import com.fanqi.wankt.common.ServiceFactory
import com.fanqi.wankt.common.bean.MineData
import com.fanqi.wankt.common.bean.Response
import com.fanqi.wankt.common.bean.UserCoin
import com.fanqi.wankt.constant.Constant
import retrofit2.Call
import retrofit2.Callback

class MineViewModel : ViewModel() {

    var mineData = MutableLiveData<MineData>()
    var userCoinData = MutableLiveData<UserCoin>()

    fun initUserData() {
        var userName by Preference(Constant.USER_NAME, "")
        var userData = MineData()
        userData.userName = userName
        userCoin()
        mineData.postValue(userData)
    }

    private fun userCoin() {
        val call = ServiceFactory.INSTANCE.wanService.coinUserInfo()
        call.enqueue(object : Callback<Response<UserCoin>> {
            override fun onFailure(call: Call<Response<UserCoin>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<Response<UserCoin>>,
                response: retrofit2.Response<Response<UserCoin>>
            ) {

                if (response.isSuccessful) {
                    var responseBody = response.body()
                    var coin = responseBody!!.data
                    userCoinData.postValue(coin)
                }
            }
        })
    }


}
