package com.vn.basemvvm.di.storage

import android.content.Context
import androidx.room.Room
import com.vn.basemvvm.di.storage.database.AppDatabase
import com.vn.basemvvm.di.storage.database.DatabaseInfo
import com.vn.basemvvm.di.storage.frefs.LocalStorage
import com.vn.basemvvm.di.storage.frefs.SharedPreferencesStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Storage {

    @Singleton
    @Provides
    fun localStorage(pre: SharedPreferencesStorage) : LocalStorage {
        return pre
    }

    @Singleton
    @Provides
    fun appDatabase(context: Context, @DatabaseInfo dbName: String) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName).fallbackToDestructiveMigration().build()
    }

}