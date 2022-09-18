package com.nightstalker.artic.features.di

import com.nightstalker.artic.network.ApiConstants.BASE_URL
import com.nightstalker.artic.network.net.ArtworksApi
import com.nightstalker.artic.network.net.ExhibitionsApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Сетевой модуль Koin
 *
 * @author Tamerlan Mamukhov on 2022-09-13
 */
val networkModule = module {

    val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)

    builder.client(httpClient.build())
    val retrofit: Retrofit = builder.build()

    factory { retrofit.create(ArtworksApi::class.java) }
    factory { retrofit.create(ExhibitionsApi::class.java) }

}