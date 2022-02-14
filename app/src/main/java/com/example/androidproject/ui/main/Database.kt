
package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class ProdDatabase : RoomDatabase() {

    abstract val DatabaseDao: DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ProdDatabase? = null

        fun getInstance(context: Context): ProdDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProdDatabase::class.java,
                        "Products_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
