package com.fanqi.wankt.common.bean


import com.google.gson.annotations.SerializedName

data class FriendRespons(
    @SerializedName("data")
    val dataList: List<FriendData>,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)