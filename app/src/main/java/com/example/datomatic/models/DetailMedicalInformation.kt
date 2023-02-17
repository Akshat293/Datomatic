package com.example.datomatic.models

data class DetailMedicalInformation(
    val allergies: Allergies,
    val anyOther: AnyOther,
    val asthma: Asthma,
    val bloodDiseases: BloodDiseases,
    val diabetes: Diabetes,
    val heartDiseases: HeartDiseases,
    val kidneyDiseases: KidneyDiseases,
    val liverDiseases: LiverDiseases,
    val nervousSystemDiseases: NervousSystemDiseases,
    val pastDrugs: PastDrugs,
    val respiraroryDiseases: RespiraroryDiseases,
    val thyroidDiseases: ThyroidDiseases
)