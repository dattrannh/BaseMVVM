package com.vn.basemvvm.di.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vn.basemvvm.data.db.User
import com.vn.basemvvm.di.storage.database.dao.UserDao


/**
 * Created by DatTV on 05,July,2020
 * trandatbkhn@gmail.com
 * Copyright © 2020 Dat Tran. All rights reserved.
 */

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}