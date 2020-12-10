package com.example.falconlaunch.models

data class ApiData(
    val links: Link,
    val static_fire_date_utc: String,
    val success: Boolean,
    val details: String,
    val flight_number: Int,
    val name: String
)
