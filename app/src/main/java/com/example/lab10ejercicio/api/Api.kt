package com.example.lab10ejercicio.api
//import com.example.lab10ejercicio.modelos.Task
import retrofit2.Call
import retrofit2.http.*
import com.example.lab10ejercicio.modelos.BookResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    fun searchBooks(@Query("q") query: String): Call<BookResponse>
}
