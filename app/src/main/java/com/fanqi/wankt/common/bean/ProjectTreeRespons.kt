package com.fanqi.wankt.common.bean


import com.google.gson.annotations.SerializedName

data class ProjectTreeRespons(
    @SerializedName("data")
    val categoryList: List<ProjectCategory>,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)