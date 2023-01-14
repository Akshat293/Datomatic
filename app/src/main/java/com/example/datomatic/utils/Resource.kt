package com.example.datomatic.utils

import com.example.datomatic.models.Doctor_list

sealed class Resource<T>(
    val data: Doctor_list? = null,
    val message: String? = null
) {

    class Success<T>(data: Doctor_list?) : Resource<T>(data = data)
    class Error<T>(message: String) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()

}