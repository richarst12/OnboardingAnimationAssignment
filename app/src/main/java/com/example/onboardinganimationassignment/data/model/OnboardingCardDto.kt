package com.example.onboardinganimationassignment.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OnboardingCardDto(
    @field:Json(name = "image") val image: String? = null,
    @field:Json(name = "collapsedStateText") val collapsedStateText: String? = null,
    @field:Json(name = "expandStateText") val expandStateText: String? = null,
    @field:Json(name = "backGroundColor") val backGroundColor: String? = null,
    @field:Json(name = "strokeStartColor") val strokeStartColor: String? = null,
    @field:Json(name = "strokeEndColor") val strokeEndColor: String? = null,
    @field:Json(name = "startGradient") val startGradient: String? = null,
    @field:Json(name = "endGradient") val endGradient: String? = null
)
