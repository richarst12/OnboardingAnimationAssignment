package com.example.onboardinganimationassignment.ui.onboarding.base

import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem

sealed class UiState {
    object Loading : UiState()
    data class Success(val items: List<UiOnboardingItem>) : UiState()
    data class Error(val message: String) : UiState()
}