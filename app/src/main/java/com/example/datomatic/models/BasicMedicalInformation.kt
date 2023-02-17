package com.example.datomatic.models

data class BasicMedicalInformation(
    val alcohol: Alcohol,
    val bloodPressure: BloodPressure,
    val isPregnant: IsPregnant,
    val smoking: Smoking
)