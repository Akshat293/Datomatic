package com.example.datomatic.models

import java.io.Serializable

data class PrescriptionX(
    val _id: String,
    val createdAt: String,
    val hospitalName: String,
    val name: String,
    val remarks: String
): Serializable