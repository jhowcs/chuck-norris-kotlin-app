package com.jhowcs.chucknorrisapp.data

import com.squareup.moshi.Json

class JokeApi(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "value") val value: String
)