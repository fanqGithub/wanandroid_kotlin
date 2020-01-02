package com.fanqi.wankt.ui.home.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.fanqi.wankt.common.bean.DataX

/**
 * Created by fanqi on 2019-12-23.
 * Description:
 */
class HomeDataSourceFactory : DataSource.Factory<Int, DataX>() {

    var homeListLiveDataSource = MutableLiveData<HomeListDataSource>()


    override fun create(): DataSource<Int, DataX> {
        var homeListDataSource = HomeListDataSource()
        homeListLiveDataSource.postValue(homeListDataSource)
        return homeListDataSource
    }


}