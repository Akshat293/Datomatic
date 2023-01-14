package com.example.datomatic.models

import androidx.room.Entity

@Entity(tableName = "doctor")
data class Doctor_list(
    val doctors: List<Doctor>,
    val message: String
)