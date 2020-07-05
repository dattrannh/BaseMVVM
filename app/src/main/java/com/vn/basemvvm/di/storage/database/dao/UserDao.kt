package com.vn.basemvvm.di.storage.database.dao

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*
import com.vn.basemvvm.data.db.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
           "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Completable

    @Query("SELECT * FROM user")
    fun all(): Flowable<List<User>>

    @Query("SELECT * from user where id = :id LIMIT 1")
    fun loadUserById(id: Int): Flowable<User>

    // Emits the number of users added to the database.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertLargeNumberOfUsers(users: List<User>): Completable

    // Makes sure that the operation finishes successfully.
    @Insert
    fun insertLargeNumberOfUsers(@NonNull vararg users: User): Completable

    /* Emits the number of users removed from the database. Always emits at
       least one user. */
    @Delete
    fun deleteAllUsers(@NonNull users: List<User>): Single<Int>
}