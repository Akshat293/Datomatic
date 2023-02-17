package com.example.datomatic.models

data class PatientInfo(
    val basicMedicalInformation: BasicMedicalInformation,
    val detailMedicalInformation: DetailMedicalInformation,
    val personalInformation: PersonalInformation
)