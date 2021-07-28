package com.anandmali.upstox.di

import com.anandmali.upstox.remote.StocksApi
import com.anandmali.upstox.remote.StocksDomainToUiMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = "https://run.mocky.io/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val htttpLoggingInterceptor = HttpLoggingInterceptor()
        htttpLoggingInterceptor.apply { HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addInterceptor(htttpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideStocksApi(retrofit: Retrofit): StocksApi = retrofit.create(StocksApi::class.java)

    @Singleton
    @Provides
    fun provideStockDomainMapper(): StocksDomainToUiMapper = StocksDomainToUiMapper()
}