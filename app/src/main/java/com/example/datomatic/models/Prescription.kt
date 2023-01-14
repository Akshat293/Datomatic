package com.example.datomatic.models

data class Prescription(
    val message: String,
    val prescriptions: List<PrescriptionX>
)