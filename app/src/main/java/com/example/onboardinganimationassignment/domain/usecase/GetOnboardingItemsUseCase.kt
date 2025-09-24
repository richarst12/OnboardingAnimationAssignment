package com.example.onboardinganimationassignment.domain.usecase

import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem

interface GetOnboardingItemsUseCase {
    suspend fun execute(): Result<List<UiOnboardingItem>>
}