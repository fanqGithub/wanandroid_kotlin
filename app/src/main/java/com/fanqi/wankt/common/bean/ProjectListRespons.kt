package com.fanqi.wankt.common.bean


import com.google.gson.annotations.SerializedName

data class ProjectListRespons(
    @SerializedName("data")
    val projectPage: ProjectPageData,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)