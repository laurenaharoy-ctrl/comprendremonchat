package com.example.comprendremonchat

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class OnboardingSlide(
    val kicker: String,
    val titre: String,
    val description: String,
    val emoji: String,
    val features: List<Pair<ImageVector, String>> = emptyList()
)

val onboardingSlides = listOf(
    OnboardingSlide(
        kicker = "Bienvenue",
        titre = "Comprendre mon chat",
        description = "Cette application vous aide à décoder les comportements de votre chat et à obtenir des pistes concrètes adaptées à son profil unique.",
        emoji = "🐱"
    ),
    OnboardingSlide(
        kicker = "Comment ça marche",
        titre = "Un questionnaire, quatre dimensions",
        description = "En quelques minutes, explorez les quatre axes qui façonnent le comportement de votre chat au quotidien.",
        emoji = "📊",
        features = listOf(
            Icons.Rounded.Psychology to "Sensibilité émotionnelle",
            Icons.Rounded.Favorite to "Besoin d'attachement",
            Icons.Rounded.Spa to "Gestion de l'excitation",
            Icons.Rounded.Analytics to "Réactivité à l'environnement"
        )
    ),
    OnboardingSlide(
        kicker = "Ce que vous obtenez",
        titre = "Un bilan personnalisé complet",
        description = "À la fin du questionnaire, recevez un bilan détaillé avec des conseils concrets, un plan d'action et un PDF à partager avec votre vétérinaire.",
        emoji = "💡",
        features = listOf(
            Icons.Rounded.CheckCircle to "Bilan comportemental",
            Icons.Rounded.PictureAsPdf to "Export PDF 4 pages",
            Icons.Rounded.History to "Historique des bilans"
        )
    )
)

@Composable
fun OnboardingScreen(onTerminer: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { onboardingSlides.size })
    val scope = rememberCoroutineScope()

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = onTerminer) {
                    Text("Passer", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                OnboardingSlideContent(slide = onboardingSlides[page])
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(onboardingSlides.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        val width by animateFloatAsState(
                            targetValue = if (isSelected) 24f else 8f,
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            label = "dot_$index"
                        )
                        Box(
                            modifier = Modifier.height(8.dp).width(width.dp).clip(CircleShape)
                                .background(if (isSelected) PremiumPalette.Primary else PremiumPalette.Border)
                        )
                    }
                }

                val estDernierSlide = pagerState.currentPage == onboardingSlides.lastIndex
                PrimaryGlowButton(
                    text = if (estDernierSlide) "Commencer" else "Suivant",
                    onClick = {
                        if (estDernierSlide) onTerminer()
                        else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    },
                    leading = if (estDernierSlide) {
                        { Icon(Icons.Rounded.Pets, contentDescription = null, tint = Color.White) }
                    } else null
                )
            }
        }
    }
}

@Composable
fun OnboardingSlideContent(slide: OnboardingSlide) {
    val hasFeatures = slide.features.isNotEmpty()
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!hasFeatures) {
            Text(slide.titre, style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Box(
            modifier = Modifier.size(if (hasFeatures) 100.dp else 160.dp).clip(CircleShape)
                .background(PremiumPalette.PaperWarm),
            contentAlignment = Alignment.Center
        ) {
            Text(slide.emoji, fontSize = if (hasFeatures) 44.sp else 72.sp)
        }

        Spacer(modifier = Modifier.height(if (hasFeatures) 16.dp else 20.dp))

        Text(slide.kicker.uppercase(), style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold, color = PremiumPalette.PrimarySoft,
            letterSpacing = 2.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(8.dp))

        if (hasFeatures) {
            Text(slide.titre, style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(slide.description, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center, lineHeight = 22.sp)

        if (hasFeatures) {
            Spacer(modifier = Modifier.height(14.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                slide.features.forEach { (icon, label) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                            .background(PremiumPalette.PaperSoft.copy(alpha = 0.7f))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(30.dp).clip(CircleShape)
                                .background(PremiumPalette.Primary.copy(alpha = 0.10f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, contentDescription = null, tint = PremiumPalette.Primary,
                                modifier = Modifier.size(16.dp))
                        }
                        Text(label, style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun AccueilIllustrationCard() {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Color(0xFF231B17) else PremiumPalette.PaperSoft
        ),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF56433B) else PremiumPalette.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Comprendre mon chat", style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold, color = PremiumPalette.Primary,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.size(140.dp).clip(CircleShape).background(PremiumPalette.PaperWarm),
                contentAlignment = Alignment.Center
            ) {
                Text("🐱", fontSize = 72.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Bienvenue", style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Évaluez le bien-être de votre compagnon félin",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
        }
    }
}