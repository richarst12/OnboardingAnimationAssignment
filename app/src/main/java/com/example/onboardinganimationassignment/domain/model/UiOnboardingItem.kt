package com.example.onboardinganimationassignment.domain.model


data class UiOnboardingItem(
    val id: String,
    val order: Int,
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val cardBackgroundHex: String? = null,
    val accentHex: String? = null,
    val stickerText: String? = null,
    val stickerPosition: String? = null,
    val stickerAnimation: String? = null,
    val ctaText: String? = null,
    val ctaVisibleOn: String? = null,
    val ctaAction: String? = null,
    val entryAnimation: String? = null,
    val entryDurationMs: Int? = null,
    val staggerMs: Int? = null
)

