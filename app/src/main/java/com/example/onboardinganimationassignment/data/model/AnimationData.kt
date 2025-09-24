package com.example.onboardinganimationassignment.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimationData(
    val entry: String? = null,
    @field:Json(name = "entry_duration_ms")
    val entryDurationMs: Int? = 400,
    @field:Json(name = "stagger_ms")
    val staggerMs: Int? = 100
)
