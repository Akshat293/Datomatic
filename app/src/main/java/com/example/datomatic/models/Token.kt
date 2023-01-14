package com.example.datomatic.models
import com.google.gson.annotations.SerializedName
data class Token(
    @SerializedName("token")val token: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("userType")val userType: String
)