package com.engie.eea_tech_interview.di.koin

import com.engie.eea_tech_interview.Constants
import com.engie.eea_tech_interview.data.network.MovieApiService
import com.engie.eea_tech_interview.data.network.createOkHttpClient
import com.engie.eea_tech_interview.data.network.createMoshiConverter
import com.engie.eea_tech_interview.data.network.createRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module(createdAtStart = true) {
    single {
        val baseUrl = Constants.BASE_URL
        createRetrofit(baseUrl, get(), get())
    }

    single { createOkHttpClient(androidContext()) }

    single { createMoshiConverter() }

    single {
        get<Retrofit>().create(MovieApiService::class.java)
    }
}