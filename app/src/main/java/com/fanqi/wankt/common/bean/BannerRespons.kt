package com.fanqi.wankt.common.bean


import com.google.gson.annotations.SerializedName

data class BannerRespons(
    @SerializedName("data")
    val bannerList: List<Banner>,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)