package com.fanqi.wankt.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanqi.wankt.base.Preference
import com.fanqi.wankt.common.ServiceFactory
import com.fanqi.wankt.common.bean.LoginResponse
import com.fanqi.wankt.constant.Constant
import com.fanqi.wankt.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author:范启
 * Created on 2019-12-29.
 * Description:
 */
class LoginViewModel : ViewModel() {

    var loginResult = MutableLiveData<LoginResponse>()

    fun login(username: String, password: String) {
        val call = ServiceFactory.INSTANCE.wanService.login(username, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    var loginResponse = response.body()
                    loginResult.postValue(loginResponse)
                    if (loginResponse != null) {
                        if (loginResponse.errorCode == 0) {
                            saveUserInfo(loginResponse!!.data)
                        }
                    }
                }
            }
        })
    }

    private fun saveUserInfo(data: LoginResponse.Data) {
        if (data == null) {
            return
        }
        var username: String by Preference(Constant.USER_NAME, "")
        var userId: Int by Preference(Constant.USER_ID, 0)

        username = data.username
        userId = data.id
    }

}