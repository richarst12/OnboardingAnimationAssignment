package com.example.onboardinganimationassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboardinganimationassignment.domain.usecase.GetOnboardingItemsUseCase
import com.example.onboardinganimationassignment.ui.onboarding.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemsUseCase: GetOnboardingItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchApi()
    }

    fun fetchApi() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val item = getItemsUseCase.execute()
            item.fold(
                onSuccess = { list -> _uiState.value = UiState.Success(list) },
                onFailure = { ex -> _uiState.value = UiState.Error(ex.message ?: "Unknown Exception") }
            )
        }
    }
}