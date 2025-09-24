package com.example.onboardinganimationassignment.data.mappers

import com.example.onboardinganimationassignment.data.model.OnboardingCardDto
import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem

fun OnboardingCardDto.toUiModel(orderIndex: Int): UiOnboardingItem {
    return UiOnboardingItem(
        id = image ?: "card_$orderIndex",
        order = orderIndex,
        title = collapsedStateText ?: "",
        subtitle = expandStateText ?: "",
        imageUrl = image,
        cardBackgroundHex = backGroundColor,
        accentHex = startGradient ?: endGradient,
        stickerText = null,
        stickerPosition = null,
        stickerAnimation = null,
        ctaText = null,
        ctaVisibleOn = null,
        ctaAction = null,
        entryAnimation = null,
        entryDurationMs = null,
        staggerMs = null
    )
}
