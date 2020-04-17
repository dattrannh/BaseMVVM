package com.vn.basemvvm.di.network

import androidx.lifecycle.LiveData
import com.vn.basemvvm.data.model.Post
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

    @GET("/posts")
    fun getData(): Observable<Response<List<Post>>>

    @GET("/posts")
    fun getLiveData(): LiveData<List<Post>>
}