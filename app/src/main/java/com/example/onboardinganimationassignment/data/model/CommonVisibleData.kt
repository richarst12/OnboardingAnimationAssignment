package com.example.onboardinganimationassignment.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommonVisibleData(
    val text: String,
    @field:Json(name = "visible_on")
    val visibleOn: String? = "last",
    val action: String? = null
)
