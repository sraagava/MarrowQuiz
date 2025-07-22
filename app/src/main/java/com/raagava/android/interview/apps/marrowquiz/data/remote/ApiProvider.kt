package com.raagava.android.interview.apps.marrowquiz.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raagava.android.interview.apps.marrowquiz.BuildConfig
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider : KoinComponent {

    val context: Context by inject()

    fun getRetrofit(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context = context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }
}