package com.example.datomatic.models

data class PersonalInformation(
    val address: Address,
    val age: Age,
    val bloodGroup: BloodGroup,
    val city: City,
    val country: Country,
    val height: Height,
    val name: Name,
    val phone: Phone,
    val pincode: Pincode,
    val profession: Profession,
    val weight: Weight
)