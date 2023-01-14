package com.example.datomatic.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class Doctor(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val hospitalName: String,
    val name: String,
    val recentVisit: String,
    val recentVisitDate: String,
    val recentVisitTime: String
): Serializable