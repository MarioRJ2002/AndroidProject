package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface DatabaseDao {

    @Insert
    suspend fun insert(night: Producto)

    @Update
    suspend fun update(night: Producto)

    @Query("SELECT * from productos WHERE productoId = :key")
    suspend fun get(key: Int): Producto?

    @Query("DELETE FROM productos")
    suspend fun clear()

    @Query("SELECT * FROM productos ORDER BY productoId ASC")
    fun getAllProducts(): LiveData<List<Producto>>

    @Query("SELECT EXISTS(SELECT * FROM productos WHERE  nombre= :nombre and precio= :precio)")
    fun existeProd(nombre:String?, precio:Double?) : Boolean

}

