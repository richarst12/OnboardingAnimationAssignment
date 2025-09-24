package com.example.onboardinganimationassignment.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveButtonCta(
    val text: String? = null,
    val deeplink: String? = null,
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val strokeColor: String? = null
)


