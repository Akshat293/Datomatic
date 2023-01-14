package com.example.datomatic.models

data class PrescriptionXX(
    val __v: Int,
    val _id: String,
    val age: String,
    val createdAt: String,
    val doctorId: String,
    val gender: String,
    val medications: List<Medication>,
    val name: String,
    val phoneNumber: String,
    val remarks: String,
    val updatedAt: String
)