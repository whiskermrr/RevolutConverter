package com.whisker.revolutconverter.di.module

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.whisker.revolutconverter.App
import com.whisker.revolutconverter.api.exception.NoConnectivityException
import com.whisker.revolutconverter.api.service.RevolutConverterService
import com.whisker.revolutconverter.repository.CurrencyDataRepository
import com.whisker.revolutconverter.repository.CurrencyRepository
import com.whisker.revolutconverter.utils.isNetworkAvailable
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App) : Context = app.applicationContext


    @Provides
    @Singleton
    @Named("ConnectivityInterceptor")
    fun provideConnectivityInterceptor(context: Context) : Interceptor {
        return Interceptor { chain ->
            if(!context.isNetworkAvailable()) {
                throw NoConnectivityException()
            } else {
                chain.proceed(chain.request())
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
            @Named("ConnectivityInterceptor") connectivityInterceptor: Interceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://revolut.duckdns.org/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

    }

    @Provides
    @Singleton
    fun provideRevolutConverterService(retrofit: Retrofit) : RevolutConverterService {
        return retrofit.create(RevolutConverterService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(revolutService: RevolutConverterService) : CurrencyRepository {
        return CurrencyDataRepository(revolutService)
    }
}