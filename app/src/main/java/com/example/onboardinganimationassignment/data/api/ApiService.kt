package com.example.onboardinganimationassignment.data.api

import com.example.onboardinganimationassignment.data.model.OnboardingResponse
import retrofit2.http.GET

interface ApiService {
    @GET("_assets/shared/education-metadata.json")
    suspend fun getOnboardingData(): OnboardingResponse
}
