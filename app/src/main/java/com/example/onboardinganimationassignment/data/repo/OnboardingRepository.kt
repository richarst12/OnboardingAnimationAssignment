package com.example.onboardinganimationassignment.data.repo

import android.util.Log
import com.example.onboardinganimationassignment.data.api.ApiService
import com.example.onboardinganimationassignment.data.model.OnboardingResponse
import javax.inject.Inject

class OnboardingRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun fetchOnboardingData(): Result<OnboardingResponse> {
        return try {
            val resp = apiService.getOnboardingData()
            Log.d("Onboarding Assignment", "Fetched response, success=${resp.success}, cards=${resp.data?.manualBuyEducationData?.educationCardList?.size ?: 0}")
            Result.success(resp)
        } catch (t: Throwable) {
            Log.e("Onboarding Assignment", "fetch error", t)
            Result.failure(t)
        }
    }
}
