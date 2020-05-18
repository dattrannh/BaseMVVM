package com.vn.basemvvm.di.network

import android.util.Log
import com.vn.basemvvm.BuildConfig
import com.vn.basemvvm.data.model.ErrorResponse
import com.vn.basemvvm.di.storage.frefs.LocalStorage
import com.vn.basemvvm.utils.Constants.BASE_URL
import com.vn.basemvvm.utils.rx.RxBus
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

    const val TIME_OUT: Long = 10

    const val userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.23 (KHTML, like Gecko) Version/10.0 Mobile/14E5239e Safari/602.1"


    @Singleton
    @Provides
    fun providePostApi(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitInterface(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, listenerResponse: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(listenerResponse)
            .followRedirects(true)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }

    @Provides
    fun listenerResponse(storage: LocalStorage) = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response? {
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
            val authorization = storage.authorization
            if (authorization != null) {
                request.addHeader("Authorization", authorization)
            }
            try {
                val response = chain.proceed(request.build())
                if (!response.isSuccessful) {
                    val code = response.code()
                    RxBus.push(ErrorResponse(code, null))
                }
                return response
            }catch (e: Exception) {
                RxBus.push(ErrorResponse(-1, e.cause))
                throw e
            }
        }
    }

//    fun get(url: String, response: ((String) -> Unit), failure: ((Int) -> Unit)? = null) {
//        val request = Request.Builder().url(url).addHeader("user-agent", userAgent).build()
//        val client = provideOkHttpClient(provideLoggingInterceptor())
//        client.newCall(request).enqueue(object : Callback {
//
//            override fun onFailure(call: Call, e: IOException) {
//                failure?.invoke(-1)
//            }
//
//            override fun onResponse(call: Call, res: Response) {
//                val code = res.code()
//                val body = res.body()
//                if (res.isSuccessful && body != null) {
//                    response.invoke(body.string())
//                } else {
//                    failure?.invoke(code)
//                }
//            }
//        })
//    }
}