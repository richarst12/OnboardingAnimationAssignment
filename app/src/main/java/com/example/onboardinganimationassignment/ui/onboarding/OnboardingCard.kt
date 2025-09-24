package com.example.onboardinganimationassignment.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem
import androidx.core.graphics.toColorInt
import com.example.onboardinganimationassignment.ui.theme.SubtitleGray
import com.example.onboardinganimationassignment.ui.theme.TranslucentWhite

@Composable
fun OnboardingCard(item: UiOnboardingItem, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(24.dp)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = hexColorOrDefault(item.cardBackgroundHex, Color(0xFF713A65)))
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.TopCenter) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                item.stickerText?.let { s ->
                    Box(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)) {
                        Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = TranslucentWhite)) {
                            Box(Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                Text(text = s, color = Color.White, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = item.title, color = Color.White, style = MaterialTheme.typography.headlineSmall)
                    if (!item.subtitle.isNullOrBlank()) {
                        Spacer(Modifier.height(6.dp))
                        Text(text = item.subtitle, color = SubtitleGray, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun hexColorOrDefault(hex: String?, fallback: Color): Color {
    return try {
        if (hex.isNullOrEmpty()) fallback else Color(hex.toColorInt())
    } catch (e: Exception) {
        fallback
    }
}
