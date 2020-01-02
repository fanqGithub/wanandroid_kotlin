package com.fanqi.wankt.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fanqi.wankt.common.ServiceFactory
import com.fanqi.wankt.common.bean.Banner
import com.fanqi.wankt.common.bean.BannerRespons
import com.fanqi.wankt.common.bean.DataHomeList
import com.fanqi.wankt.common.bean.DataX
import com.fanqi.wankt.ui.home.paging.HomeDataSourceFactory
import com.fanqi.wankt.ui.home.paging.HomeListDataSource
import com.fanqi.wankt.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//负责数据的获取绑定
class HomeViewModel : ViewModel() {

    var homeBannerLiveData = MutableLiveData<List<Banner>>()

//    private var currentPage = 0
//
//    /*  MutableLiveData为一种容器，需要按照以下方法创建 */
//    /* MutableLiveData中地数据无法直接赋值，
//        需通过setValue或getaValue赋值或取值
//        也可通过postValue
//    */
//
//    var homeData = MutableLiveData<List<DataX>>()
//
//    var listDataX: List<DataX>? = null
//
//    private fun getHomeList() {
//        val call = ServiceFactory.INSTANCE.wanService.articleList(currentPage)
//        call.enqueue(object : Callback<DataHomeList> {
//            override fun onFailure(call: Call<DataHomeList>, t: Throwable) {
//                Logger.e(t.toString())
//            }
//
//            override fun onResponse(call: Call<DataHomeList>, response: Response<DataHomeList>) {
//                if (response.isSuccessful) {
//                    val homeList = response.body()?.data
//                    listDataX = homeList!!.datas
//                    homeData.value=listDataX
////                    homeData.postValue(listDataX)
//                }
//            }
//
//        })
//    }
//
//    fun initData() {
//        currentPage = 0
//        getHomeList()
//    }
//
//    fun nextPage() {
//        ++currentPage
//        getHomeList()
//    }

    ///-----尝试使用paging lib
    var homePagList: LiveData<PagedList<DataX>>
    private var liveDataSource: LiveData<HomeListDataSource>

    init {
        val itemDataSourceFactory = HomeDataSourceFactory()
        liveDataSource = itemDataSourceFactory.homeListLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(HomeListDataSource.PAGE_SIZE)
            .build()
        homePagList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }


    fun loadBanner() {
        val call = ServiceFactory.INSTANCE.wanService.banner()
        call.enqueue(object : Callback<BannerRespons> {
            override fun onResponse(call: Call<BannerRespons>, response: Response<BannerRespons>) {
                if (response.isSuccessful) {
                    val listBanner = response.body()?.bannerList
                    homeBannerLiveData.postValue(listBanner)
                }
            }

            override fun onFailure(call: Call<BannerRespons>, t: Throwable) {
                Logger.e(t.toString())
            }

        })
    }

    fun collectArt(artId: Int) {
        val call = ServiceFactory.INSTANCE.wanService.collectArticle(artId)
        call.enqueue(object : Callback<com.fanqi.wankt.common.bean.Response<Any>> {
            override fun onFailure(
                call: Call<com.fanqi.wankt.common.bean.Response<Any>>,
                t: Throwable
            ) {

            }

            override fun onResponse(
                call: Call<com.fanqi.wankt.common.bean.Response<Any>>,
                response: Response<com.fanqi.wankt.common.bean.Response<Any>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                    }
                }
            }
        })
    }

    fun removeCollect(artId: Int) {
        val call = ServiceFactory.INSTANCE.wanService.removeCollectArticle(artId)
        call.enqueue(object : Callback<com.fanqi.wankt.common.bean.Response<Any>> {
            override fun onFailure(
                call: Call<com.fanqi.wankt.common.bean.Response<Any>>,
                t: Throwable
            ) {

            }

            override fun onResponse(
                call: Call<com.fanqi.wankt.common.bean.Response<Any>>,
                response: Response<com.fanqi.wankt.common.bean.Response<Any>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                    }
                }
            }

        })
    }


}