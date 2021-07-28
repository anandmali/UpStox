package com.anandmali.upstox.remote

import com.anandmali.upstox.remote.data.StockApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface StocksApi {

    @GET("v3/6d0ad460-f600-47a7-b973-4a779ebbaeaf")
    suspend fun getStocks(): Response<StockApiResponse>

}