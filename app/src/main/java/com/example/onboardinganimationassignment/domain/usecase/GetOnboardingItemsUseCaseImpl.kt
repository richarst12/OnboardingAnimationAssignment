package com.example.onboardinganimationassignment.domain.usecase

import com.example.onboardinganimationassignment.data.repo.OnboardingRepository
import com.example.onboardinganimationassignment.data.mappers.toUiModel
import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem
import javax.inject.Inject

class GetOnboardingItemsUseCaseImpl @Inject constructor(
    private val repo: OnboardingRepository
) : GetOnboardingItemsUseCase {

    override suspend fun execute(): Result<List<UiOnboardingItem>> {
        return repo.fetchOnboardingData().fold(
            onSuccess = { dto ->
                try {
                    if (dto.success == false) {
                        return Result.success(emptyList())
                    }
                    val mapped = dto.data
                        ?.manualBuyEducationData
                        ?.educationCardList
                        ?.mapIndexed { index, cardDto -> cardDto.toUiModel(index + 1) }
                        ?.toMutableList()
                        ?: mutableListOf()

                    val saveText = dto.data?.manualBuyEducationData?.saveButtonCta?.text
                    if (!saveText.isNullOrBlank() && mapped.isNotEmpty()) {
                        val lastIndex = mapped.lastIndex
                        val last = mapped[lastIndex]
                        mapped[lastIndex] = last.copy(ctaText = saveText)
                    }

                    Result.success(mapped)
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            },
            onFailure = { throwable ->
                Result.failure(throwable)
            }
        )
    }
}
