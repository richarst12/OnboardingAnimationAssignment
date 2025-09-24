package com.example.onboardinganimationassignment.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OnboardingResponse(
    val success: Boolean,
    val data: OnboardingDataWrapper?
)

@JsonClass(generateAdapter = true)
data class OnboardingDataWrapper(
    val manualBuyEducationData: ManualBuyEducationData?
)

@JsonClass(generateAdapter = true)
data class ManualBuyEducationData(
    val toolBarText: String? = null,
    val introTitle: String? = null,
    val introSubtitle: String? = null,
    @field:Json(name = "educationCardList")
    val educationCardList: List<OnboardingCardDto> = emptyList(),
    val saveButtonCta: SaveButtonCta? = null,
    val ctaLottie: String? = null,
    val screenType: String? = null
)
