package com.raagava.android.interview.apps.marrowquiz.data.remote

import com.raagava.android.interview.apps.marrowquiz.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }
}