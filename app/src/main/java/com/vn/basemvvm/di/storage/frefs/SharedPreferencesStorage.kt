package com.vn.basemvvm.di.storage.frefs

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vn.basemvvm.data.model.Post
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass
import kotlin.reflect.KType


class SharedPreferencesStorage @Inject constructor(context: Context, @PreferenceInfo fileName: String): LocalStorage {

    private val sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override fun putString(key: String, value: String?) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override var authorization: String?
        get() = getString("authorization")
        set(value) { putString("authorization", value)}


    override fun <T: Any> putData(key: String, t: T) {
        putString(key, Gson().toJson(t))
    }

    override fun <T: Any> getData(key: String): T? {
        val string = getString(key) ?: return null
        return Gson().fromJson(string, object : TypeToken<T>(){}.type)
    }

    override fun <T : Any> getData(key: String, clazz: Class<T>): T? {
        val string = getString(key) ?: return null
        return Gson().fromJson(string, clazz)
    }
}

