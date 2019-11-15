package ru.bslab.test.avdeevav.repository.client

import retrofit2.Call
import retrofit2.http.GET

import ru.bslab.test.avdeevav.repository.data.Providers

interface ProviderService {

    @GET("/")
    fun getList(): Call<Providers>
}