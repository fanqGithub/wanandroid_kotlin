package com.fanqi.wankt.ui.hot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanqi.wankt.common.ServiceFactory
import com.fanqi.wankt.common.bean.FriendData
import com.fanqi.wankt.common.bean.FriendRespons
import com.fanqi.wankt.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotSearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "热搜"
    }
    val text: LiveData<String> = _text

    var hotSearchLiveData = MutableLiveData<List<FriendData>>()
    var commonUseLiveData = MutableLiveData<List<FriendData>>()

    fun getHotData() {
        val call = ServiceFactory.INSTANCE.wanService.hotKey()
        call.enqueue(object : Callback<FriendRespons> {
            override fun onFailure(call: Call<FriendRespons>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<FriendRespons>, response: Response<FriendRespons>) {
                if (response.isSuccessful) {
                    val friendListData = response.body()?.dataList
                    if (friendListData != null) {
                        hotSearchLiveData.postValue(friendListData)
                    }
                }
            }
        })
    }


    fun getCommonData() {
        val call = ServiceFactory.INSTANCE.wanService.friendData()
        call.enqueue(object : Callback<FriendRespons> {
            override fun onFailure(call: Call<FriendRespons>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<FriendRespons>, response: Response<FriendRespons>) {
                if (response.isSuccessful) {
                    val commonListData = response.body()?.dataList
                    if (commonListData != null) {
                        commonUseLiveData.postValue(commonListData)
                    }
                }
            }
        })
    }
}