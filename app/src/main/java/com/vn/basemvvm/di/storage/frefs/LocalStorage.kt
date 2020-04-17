package com.vn.basemvvm.di.storage.frefs

import kotlin.reflect.KClass
import kotlin.reflect.KType

interface LocalStorage {

    fun putString(key: String, value: String?)
    fun getString(key: String): String?

    var authorization: String?

    fun <T: Any> putData(key: String, t: T)

    fun <T : Any> getData(key: String): T?

    fun <T : Any> getData(key: String, clazz: Class<T>): T?
}