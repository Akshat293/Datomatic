package com.example.datomatic.api

import com.example.datomatic.models.Credentials
import com.example.datomatic.models.Doctor
import com.example.datomatic.models.Doctor_list
import com.example.datomatic.models.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers(
        "Content-Type: application/json"
    )
        @POST("/auth/login")
        fun getToken(@Body credentials:Credentials): Call<Token>

    @Headers(
        "Content-Type: application/json"
    )
    @GET("/patient/get-doctors")
    fun getDoctor(@Header("authorization") Authorization:String ):Call<Doctor_list>
    }
