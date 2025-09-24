package com.example.onboardinganimationassignment.di

import com.example.onboardinganimationassignment.domain.usecase.GetOnboardingItemsUseCase
import com.example.onboardinganimationassignment.domain.usecase.GetOnboardingItemsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindGetOnboardingItemsUseCase(
        impl: GetOnboardingItemsUseCaseImpl
    ): GetOnboardingItemsUseCase
}
