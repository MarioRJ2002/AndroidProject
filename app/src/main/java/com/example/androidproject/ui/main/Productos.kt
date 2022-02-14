
package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    var productoId: Int? = 0,

    @ColumnInfo(name = "nombre")
    var nombre: String?,

    @ColumnInfo(name = "precio")
    var precio: Double?

)
