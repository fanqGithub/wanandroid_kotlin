package com.fanqi.wankt.common.bean


import com.google.gson.annotations.SerializedName

data class DataHomeList(
    @SerializedName("data")
    val data: Data,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)