package com.example.androidproject.ui.main

import com.squareup.moshi.Json

data class ObjetoRetrofit
    (@field:Json(name = "id") val id: String = "",
     @field:Json(name = "name") val name: String = "")
