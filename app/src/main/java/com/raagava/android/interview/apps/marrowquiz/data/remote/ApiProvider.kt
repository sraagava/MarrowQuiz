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

        val okHttpClientBuilder = OkHttpClient.Builder()
            .callTimeout(5000L, java.util.concurrent.TimeUnit.MILLISECONDS)

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(ChuckerInterceptor(context = context))
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }
}