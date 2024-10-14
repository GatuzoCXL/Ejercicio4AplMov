package com.example.lab10ejercicio

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.lab10ejercicio.api.ApiService
//import com.example.lab10ejercicio.modelos.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.lab10ejercicio.modelos.BookResponse
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.util.Log


class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var textViewResults: TextView
    private lateinit var editTextSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewResults = findViewById(R.id.textViewResults)
        editTextSearch = findViewById(R.id.editTextSearch)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val buttonSearch: Button = findViewById(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString()
            if (query.isNotEmpty()) {
                searchBooks(query)
            }
        }
    }

    private fun searchBooks(query: String) {
        apiService.searchBooks(query).enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items
                    textViewResults.text = books?.joinToString("\n") { it.volumeInfo.title } ?: "No se encontraron libros"
                } else if (response.code() == 429) {
                    textViewResults.text = "Demasiadas solicitudes. Intenta más tarde."
                } else {
                    Log.e("API Error", "Código de error: ${response.code()}")
                    textViewResults.text = "Error en la búsqueda: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
                textViewResults.text = "Error: ${t.message}"
            }
        })
    }
}