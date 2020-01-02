package com.fanqi.wankt.ui.home.paging

import androidx.paging.PageKeyedDataSource
import com.fanqi.wankt.common.ServiceFactory
import com.fanqi.wankt.common.bean.DataHomeList
import com.fanqi.wankt.common.bean.DataX
import com.fanqi.wankt.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by fanqi on 2019-12-23.
 * Description:
 */
class HomeListDataSource : PageKeyedDataSource<Int, DataX>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataX>
    ) {
        val call = ServiceFactory.INSTANCE.wanService.articleList(FIRST_PAGE)
        call.enqueue(object : Callback<DataHomeList> {
            override fun onFailure(call: Call<DataHomeList>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<DataHomeList>, response: Response<DataHomeList>) {
                if (response.isSuccessful) {
                    val homeList = response.body()?.data
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.data.datas
                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)
                    }

                }
            }

        })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
        val call = ServiceFactory.INSTANCE.wanService.articleList(params.key)
        call.enqueue(object : Callback<DataHomeList> {
            override fun onFailure(call: Call<DataHomeList>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<DataHomeList>, response: Response<DataHomeList>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.data.datas
                    val key = params.key + 1
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
        val call = ServiceFactory.INSTANCE.wanService.articleList(params.key)
        call.enqueue(object : Callback<DataHomeList> {
            override fun onFailure(call: Call<DataHomeList>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<DataHomeList>, response: Response<DataHomeList>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.data.datas
                    val key = if (params.key > 1) params.key - 1 else 0
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

        })
    }


    companion object {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE = 0
    }

}