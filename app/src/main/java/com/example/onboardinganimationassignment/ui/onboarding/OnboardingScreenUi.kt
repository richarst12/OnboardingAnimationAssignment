package com.example.onboardinganimationassignment.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem
import com.example.onboardinganimationassignment.ui.MainViewModel
import com.example.onboardinganimationassignment.ui.onboarding.base.UiState
import kotlinx.coroutines.delay
import androidx.core.graphics.toColorInt

/**
 * OnboardingScreenUi contains the main screen ui which display the animation of collapse of dropdown
 */

private enum class OnboardingState {
    Loading, Intro, Onboarding
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreenUi(
    viewModel: MainViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var onboardingState by remember { mutableStateOf(OnboardingState.Loading) }
    var currentCardIndex by remember { mutableStateOf(0) }
    val collapsedItems = remember { mutableStateListOf<UiOnboardingItem>() }

    var isAutoProgressing by remember { mutableStateOf(true) }

    LaunchedEffect(uiState, isAutoProgressing) {
        if (!isAutoProgressing || uiState !is UiState.Success || (uiState as UiState.Success).items.isEmpty()) {
            return@LaunchedEffect
        }
        val items = (uiState as UiState.Success).items

        onboardingState = OnboardingState.Intro
        delay(5000)
        onboardingState = OnboardingState.Onboarding

        while (currentCardIndex < items.size) {
            when (currentCardIndex) {
                0 -> {
                    delay(7000)
                    if (currentCardIndex < items.size) {
                        collapsedItems.add(items[0])
                        currentCardIndex++
                    }
                }
                1 -> {
                    delay(7000)
                    if (currentCardIndex < items.size) {
                        collapsedItems.add(items[1])
                        currentCardIndex++
                    }
                }
                else -> {
                    delay(6000)
                    isAutoProgressing = false
                }
            }
        }
    }

    val onBackClick: () -> Unit = {
        isAutoProgressing = false
        if (currentCardIndex > 0) {
            currentCardIndex--
            if (collapsedItems.isNotEmpty()) {
                collapsedItems.removeAt(collapsedItems.lastIndex)
            }
        }
    }

    val cardColor = if (uiState is UiState.Success && (uiState as UiState.Success).items.isNotEmpty()) {
        hexColorOrDefault((uiState as UiState.Success).items.getOrNull(currentCardIndex)?.cardBackgroundHex, Color(0xFF2b1828))
    } else Color(0xFF2b1828)

    val endColor = Color(cardColor.red * 0.7f, cardColor.green * 0.7f, cardColor.blue * 0.7f, 1f)
    val backgroundBrush = Brush.verticalGradient(listOf(cardColor, endColor))

    Box(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().background(backgroundBrush))

        AnimatedVisibility(
            visible = onboardingState == OnboardingState.Intro,
            enter = fadeIn(tween(420)),
            exit = fadeOut(tween(300))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome to", color = Color(0xFFd9cfa8), style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text("Onboarding", color = Color(0xFFF2C94C),
                    style = MaterialTheme.typography.headlineLarge, fontSize = 32.sp)
            }
        }

        AnimatedVisibility(
            visible = onboardingState == OnboardingState.Onboarding,
            enter = fadeIn(tween(420)),
            exit = fadeOut()
        ) {
            when (uiState) {
                is UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                is UiState.Error -> {
                    val msg = (uiState as UiState.Error).message
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Error: $msg", color = Color.White)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchApi() }) { Text("Retry") }
                    }
                }
                is UiState.Success -> {
                    val items = (uiState as UiState.Success).items
                    OnboardingContent(
                        items = items,
                        onFinish = onFinish,
                        currentCardIndex = currentCardIndex,
                        onBackClick = onBackClick,
                        collapsedItems = collapsedItems
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingContent(
    items: List<UiOnboardingItem>,
    onFinish: () -> Unit,
    currentCardIndex: Int,
    onBackClick: () -> Unit,
    collapsedItems: List<UiOnboardingItem>
) {
    var showCtaButton by remember { mutableStateOf(false) }

    LaunchedEffect(currentCardIndex, items.size) {
        showCtaButton = (currentCardIndex == items.lastIndex)
    }

    Scaffold(
        topBar = {
            ToolbarAnimated(
                visible = true,
                onBackClick = onBackClick,
                canGoBack = currentCardIndex > 0
            )
        },
        containerColor = Color.Transparent,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(12.dp))

                CollapsedRow(
                    items = collapsedItems,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(Modifier.height(12.dp))

                val isLandingPage = (currentCardIndex == items.lastIndex) && !showCtaButton
                if (!isLandingPage) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        AnimatedContent(
                            targetState = currentCardIndex,
                            transitionSpec = {
                                (fadeIn(tween(420)) + scaleIn(initialScale = 0.98f))
                                    .togetherWith(fadeOut(tween(300)) + scaleOut(targetScale = 0.98f))
                            }
                        ) { targetIndex ->
                            OnboardingCard(
                                item = items.getOrNull(targetIndex) ?: items.first(),
                                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "Landing Page", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                    }
                }

                Spacer(Modifier.height(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentCardIndex == items.lastIndex) {
                        AnimatedVisibility(visible = showCtaButton, enter = fadeIn(tween(400)), exit = fadeOut(tween(200))) {
                            Button(onClick = onFinish, shape = RoundedCornerShape(24.dp)) {
                                Text(items.last().ctaText ?: "Save in Gold")
                            }
                        }
                    }
                }
                Spacer(Modifier.height(30.dp))
            }
        }
    )
}


@Composable
fun ToolbarAnimated(visible: Boolean, onBackClick: () -> Unit, canGoBack: Boolean) {
    val enter = slideInVertically(initialOffsetY = { -it / 2 }, animationSpec = tween(420)) + fadeIn()
    val exit = slideOutVertically(targetOffsetY = { -it / 2 }, animationSpec = tween(220)) + fadeOut()

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp)) {
            IconButton(onClick = onBackClick, enabled = canGoBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text("Onboarding", color = Color.White, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CollapsedRow(items: List<UiOnboardingItem>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEachIndexed { idx, item ->
            val enterAnim = slideInVertically(initialOffsetY = { it }, animationSpec = tween(400, delayMillis = idx * 100))
            val exitAnim = fadeOut(tween(200))

            AnimatedVisibility(
                visible = true,
                enter = enterAnim,
                exit = exitAnim
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0x334C3B5C))
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!item.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.title,
                            modifier = Modifier.size(36.dp).clip(CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                    Text(item.title, color = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}



private fun hexColorOrDefault(hex: String?, fallback: Color): Color {
    return try {
        if (hex.isNullOrEmpty()) fallback else Color(hex.toColorInt())
    } catch (e: Exception) {
        fallback
    }
}