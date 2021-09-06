package com.claraberriel.data

import android.content.Context
import android.util.Log
import com.claraberriel.data.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val PUBLIC_API_KEY_ARG = "appid"
private const val MAX_TRYOUTS = 3
private const val INIT_TRYOUT = 1

class WeatherRequestGenerator(context: Context) {

    private val httpClient = OkHttpClient.Builder()
         /*
            .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
            )
            .addInterceptor(ChuckerInterceptor(context))

          */
            .addInterceptor { chain ->
                val defaultRequest = chain.request()

                val defaultHttpUrl = defaultRequest.url

                val httpUrl = defaultHttpUrl.newBuilder()
                        .addQueryParameter(PUBLIC_API_KEY_ARG, Constants.KEY)
                        .build()

                val requestBuilder = defaultRequest.newBuilder()
                        .url(httpUrl)

                chain.proceed(requestBuilder.build())
            }
            .addInterceptor { chain ->
                val request = chain.request()
                var response = chain.proceed(request)
                var tryOuts = INIT_TRYOUT

                while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                    Log.d(
                            this@WeatherRequestGenerator.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                    )
                    tryOuts++
                    response = chain.proceed(request)
                }

                response
            }

    private val builder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
