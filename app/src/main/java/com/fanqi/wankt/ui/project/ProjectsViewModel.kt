package com.fanqi.wankt.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanqi.wankt.common.ServiceFactory
import com.fanqi.wankt.common.bean.ProjectCategory
import com.fanqi.wankt.common.bean.ProjectItem
import com.fanqi.wankt.common.bean.ProjectListRespons
import com.fanqi.wankt.common.bean.ProjectTreeRespons
import com.fanqi.wankt.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    var categoryDatas = MutableLiveData<List<ProjectCategory>>()
    var projectDatas = MutableLiveData<List<ProjectItem>>()

    fun initCategoryDatas() {
        val call = ServiceFactory.INSTANCE.wanService.projectTree()
        call.enqueue(object : Callback<ProjectTreeRespons> {
            override fun onFailure(call: Call<ProjectTreeRespons>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(
                call: Call<ProjectTreeRespons>,
                response: Response<ProjectTreeRespons>
            ) {
                if (response.isSuccessful) {
                    val categoryList = response.body()?.categoryList
                    if (categoryList != null) {

                        categoryDatas.postValue(categoryList)
                    }
                }
            }

        })
    }

    fun getProjectList(cid: Int) {
        val call = ServiceFactory.INSTANCE.wanService.projectList(currentPage, cid)
        call.enqueue(object : Callback<ProjectListRespons> {
            override fun onFailure(call: Call<ProjectListRespons>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(
                call: Call<ProjectListRespons>,
                response: Response<ProjectListRespons>
            ) {
                if (response.isSuccessful) {
                    var projectListRespons = response.body()
                    if (projectListRespons != null) {
                        projectDatas.postValue(projectListRespons.projectPage.datas)
                    }
                }
            }
        })
    }

    var currentPage = 1

    fun initPage(cid: Int) {
        currentPage = 1
        getProjectList(cid)
    }

    fun nextPage(cid: Int) {
        ++currentPage
        getProjectList(cid)
    }

}