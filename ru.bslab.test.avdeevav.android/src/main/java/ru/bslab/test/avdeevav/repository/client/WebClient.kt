package ru.bslab.test.avdeevav.repository.client

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object WebClient {

    private const val url = "https://imya.bslab.ru"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getProviderService(): ProviderService {

        return retrofit.create(ProviderService::class.java)
    }
}