package com.fanqi.wankt.common.bean


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<DataX>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
)