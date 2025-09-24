//package com.example.onboardinganimationassignment.ui.onboarding
//
//import androidx.compose.animation.*
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.example.onboardinganimationassignment.domain.model.UiOnboardingItem
//import kotlinx.coroutines.launch
//import androidx.compose.foundation.ExperimentalFoundationApi
//
//@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
//@Composable
//fun OnboardingPager(
//    visible: Boolean,
//    items: List<UiOnboardingItem>,
//    onFinish: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val pagerState = rememberPagerState(
//        initialPage = 0,
//        pageCount = { items.size }
//    )
//
//    val coroutineScope = rememberCoroutineScope()
//
//    AnimatedVisibility(
//        visible = visible,
//        enter = fadeIn(tween(380)) + slideInVertically(initialOffsetY = { it / 6 }),
//        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 8 }),
//        modifier = modifier
//    ) {
//        Column(modifier = Modifier.fillMaxSize()) {
//
//            // Collapsed row (previous items)
//            if (pagerState.currentPage > 0) {
//                CollapsedRow(
//                    visible = true,
//                    items = items.take(pagerState.currentPage),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 12.dp, start = 12.dp, end = 12.dp)
//                )
//            } else {
//                Spacer(modifier = Modifier.height(64.dp))
//            }
//
//            // Main pager area: use foundation HorizontalPager with pageCount + state
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) { page ->
//                val item = items[page]
//
//                AnimatedVisibility(
//                    visible = pagerState.currentPage == page,
//                    enter = fadeIn(tween(300)) + scaleIn(initialScale = 0.98f, animationSpec = tween(300)),
//                    exit = fadeOut(tween(200)),
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 8.dp, vertical = 8.dp)
//                ) {
//                    OnboardingCard(item = item, modifier = Modifier.fillMaxSize())
//                }
//            }
//
//            // Indicator + CTA / Next button
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                // dots indicator
//                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
//                    repeat(items.size) { i ->
//                        val active = i == pagerState.currentPage
//                        Box(
//                            Modifier
//                                .padding(horizontal = 4.dp)
//                                .size(if (active) 12.dp else 8.dp)
//                                .clip(RoundedCornerShape(6.dp))
//                                .background(if (active) Color.White else Color(0x44FFFFFF))
//                        )
//                    }
//                }
//
//                Spacer(Modifier.width(12.dp))
//
//                val isLast = pagerState.currentPage == items.lastIndex
//                if (isLast) {
//                    Button(onClick = onFinish, shape = RoundedCornerShape(24.dp)) {
//                        Text(text = items.last().ctaText ?: "Save in Gold")
//                    }
//                } else {
//                    Button(onClick = {
//                        coroutineScope.launch {
//                            val next = (pagerState.currentPage + 1).coerceAtMost(items.lastIndex)
//                            pagerState.animateScrollToPage(next)
//                        }
//                    }, shape = RoundedCornerShape(24.dp)) {
//                        Text(text = "Next")
//                    }
//                }
//            }
//        }
//    }
//}
