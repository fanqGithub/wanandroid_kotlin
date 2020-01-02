package com.fanqi.wankt.common

import com.fanqi.wankt.constant.Constant
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import okhttp3.Cookie
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.fanqi.wankt.base.Preference
import com.fanqi.wankt.constant.AppPreference
import com.fanqi.wankt.constant.Constant.COOKIE_NAME
import com.fanqi.wankt.utils.Logger
import com.fanqi.wankt.utils.encodeCookie
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by fanqi on 2019-12-19.
 * Description:
 */

class ServiceFactory {


    private constructor() {}

    companion object {
        const val TAG = "ServiceFactory"
        val INSTANCE = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = ServiceFactory()
    }

    private fun <T> getService(url: String, service: Class<T>): T = getRetrofit(url).create(service)

    private fun getRetrofit(url: String): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(SaveCookieInterceptor())
            .addInterceptor(ReadCookieInterceptor())
            .addInterceptor(httpLoggingInterceptor)

        val okHttpClient = builder.build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val wanService: WanService = getService(Constant.BASE_URL, WanService::class.java)

    class SaveCookieInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val requestUrl = request.url.toString()
            val domain = request.url.host
            // set-cookie maybe has multi, login to save cookie
            if ((requestUrl.contains(Constant.USER_LOGIN_KEY) || requestUrl.contains(Constant.USER_REGISTER_KEY))
                && !response.headers(Constant.SET_COOKIE_KEY).isEmpty()
            ) {
                val cookies = response.headers(Constant.SET_COOKIE_KEY)
                val cookie = encodeCookie(cookies)
                Logger.d("cookies=" + cookie)
                var spCookie: String by Preference(domain, cookie)
                spCookie = cookie
            }
            return response
        }
    }

    class ReadCookieInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val builder = request.newBuilder()
            val domain = request.url.host
            // get domain cookie
            if (domain.isNotEmpty()) {
                val spDomain: String by Preference(domain, "")
                Logger.d("spdomain=" + spDomain)
                val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
                if (cookie.isNotEmpty()) {
                    builder.addHeader(COOKIE_NAME, cookie)
                }
            }
            return chain.proceed(builder.build())
        }
    }


}

