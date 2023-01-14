package com.example.datomatic.models

data class Medication(
    val RxNORMcode: String,
    val _id: String,
    val dosage: String,
    val frequency: String,
    val medicationName: String,
    val route: String
)