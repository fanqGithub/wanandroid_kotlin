package com.fanqi.wankt.common.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by fanqi on 2019-12-31.
 * Description:
 */
data class Response<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)