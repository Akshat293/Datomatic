package com.example.datomatic.api

import com.example.datomatic.models.*
import retrofit2.Call
import retrofit2.http.*


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


    @GET("/patient/get-prescriptions/{doctorId}")
    fun getPrescription(@Header("authorization") Authorization:String, @Path("doctorId") doctorId:String ):Call<Prescription>

    @GET("/patient/get-reports/")
    fun getReports(@Header("authorization") Authorization:String ):Call<Reports>

    @GET("/data/prescription/{prescriptionId}")
    fun getPrecDetail(@Header("authorization") Authorization:String, @Path("prescriptionId") prescriptionId:String ):Call<PrescriptionDetail>

    @POST("/patient/share-prescription")
    fun sharePrec(@Header("authorization") Authorization:String, @Body share:Share):Call<Share_mess>





    }






