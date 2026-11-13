package com.laurena.comprendremonchat

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.getValue
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.laurena.comprendremonchat.R
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight

data class OnboardingSlide(
    val kicker: String,
    val titre: String,
    val description: String,
    val illustrationType: IllustrationType,
    val features: List<Pair<ImageVector, String>> = emptyList()
)

enum class IllustrationType {
    CHAT_LOGO,
    QUATRE_AXES,
    BILAN_COMPLET
}

fun onboardingSlides(): List<OnboardingSlide> = trList(
    listOf(
        OnboardingSlide(
            kicker = "Bienvenue",
            titre = "Comprendre mon chat",
            description = "Cette application vous aide à décoder les comportements de votre chat et à obtenir des pistes concrètes adaptées à son profil unique.",
            illustrationType = IllustrationType.CHAT_LOGO
        ),
        OnboardingSlide(
            kicker = "Comment ça marche",
            titre = "",
            description = "En quelques minutes, vous explorez les quatre axes qui façonnent le comportement de votre chat au quotidien.",
            illustrationType = IllustrationType.QUATRE_AXES,
            features = listOf(
                Icons.Rounded.Psychology to "Sécurité émotionnelle",
                Icons.Rounded.Favorite to "Lien humain",
                Icons.Rounded.Spa to "Expression des instincts",
                Icons.Rounded.Analytics to "Cohabitation"
            )
        ),
        OnboardingSlide(
            kicker = "Ce que vous obtenez",
            titre = "",
            description = "À la fin du questionnaire, vous recevez un bilan détaillé avec des conseils concrets, les premières actions à mettre en place et un PDF à partager avec votre vétérinaire.",
            illustrationType = IllustrationType.BILAN_COMPLET,
            features = listOf(
                Icons.Rounded.CheckCircle to "Bilan émotionnel",
                Icons.Rounded.PictureAsPdf to "Export PDF",
                Icons.Rounded.History to "Historique des bilans"
            )
        )
    ),
    listOf(
        OnboardingSlide(
            kicker = "Welcome",
            titre = "Understanding My Cat",
            description = "This app helps you decode your cat's behaviors and get concrete, personalized guidance tailored to its unique profile.",
            illustrationType = IllustrationType.CHAT_LOGO
        ),
        OnboardingSlide(
            kicker = "How it works",
            titre = "",
            description = "In just a few minutes, you explore the four axes that shape your cat's everyday behavior.",
            illustrationType = IllustrationType.QUATRE_AXES,
            features = listOf(
                Icons.Rounded.Psychology to "Emotional security",
                Icons.Rounded.Favorite to "Human bond",
                Icons.Rounded.Spa to "Expression of instincts",
                Icons.Rounded.Analytics to "Cohabitation"
            )
        ),
        OnboardingSlide(
            kicker = "What you get",
            titre = "",
            description = "At the end of the questionnaire, you receive a detailed assessment with practical advice, the first steps to take, and a PDF to share with your vet.",
            illustrationType = IllustrationType.BILAN_COMPLET,
            features = listOf(
                Icons.Rounded.CheckCircle to "Emotional assessment",
                Icons.Rounded.PictureAsPdf to "PDF export",
                Icons.Rounded.History to "Assessment history"
            )
        )
    ),
    listOf(
        OnboardingSlide(
            kicker = "Willkommen",
            titre = "Meine Katze verstehen",
            description = "Diese App hilft Ihnen, das Verhalten Ihrer Katze zu entschlüsseln und konkrete Ansätze zu erhalten, die zu ihrem einzigartigen Profil passen.",
            illustrationType = IllustrationType.CHAT_LOGO
        ),
        OnboardingSlide(
            kicker = "So funktioniert’s",
            titre = "",
            description = "In wenigen Minuten erkunden Sie die vier Achsen, die das Verhalten Ihrer Katze im Alltag prägen.",
            illustrationType = IllustrationType.QUATRE_AXES,
            features = listOf(
                Icons.Rounded.Psychology to "Emotionale Sicherheit",
                Icons.Rounded.Favorite to "Bindung zum Menschen",
                Icons.Rounded.Spa to "Ausdruck der Instinkte",
                Icons.Rounded.Analytics to "Zusammenleben und Revier"
            )
        ),
        OnboardingSlide(
            kicker = "Was Sie erhalten",
            titre = "",
            description = "Am Ende des Fragebogens erhalten Sie eine ausführliche Einschätzung mit konkreten Tipps, den ersten Schritten zur Umsetzung und einem PDF, das Sie mit Ihrem Tierarzt teilen können.",
            illustrationType = IllustrationType.BILAN_COMPLET,
            features = listOf(
                Icons.Rounded.CheckCircle to "Emotionale Einschätzung",
                Icons.Rounded.PictureAsPdf to "PDF-Export",
                Icons.Rounded.History to "Verlauf der Einschätzungen"
            )
        )
    ),
    listOf(
        OnboardingSlide(
            kicker = "Te damos la bienvenida",
            titre = "Comprender a mi gato",
            description = "Esta aplicación te ayuda a descifrar el comportamiento de tu gato y a obtener pistas concretas adaptadas a su perfil único.",
            illustrationType = IllustrationType.CHAT_LOGO
        ),
        OnboardingSlide(
            kicker = "Cómo funciona",
            titre = "",
            description = "En pocos minutos, exploras los cuatro ejes que dan forma al comportamiento de tu gato en el día a día.",
            illustrationType = IllustrationType.QUATRE_AXES,
            features = listOf(
                Icons.Rounded.Psychology to "Seguridad emocional",
                Icons.Rounded.Favorite to "Vínculo con el humano",
                Icons.Rounded.Spa to "Expresión de los instintos",
                Icons.Rounded.Analytics to "Convivencia y territorio"
            )
        ),
        OnboardingSlide(
            kicker = "Lo que obtienes",
            titre = "",
            description = "Al final del cuestionario, recibes una evaluación detallada con consejos concretos, las primeras acciones que poner en marcha y un PDF para compartir con tu veterinario.",
            illustrationType = IllustrationType.BILAN_COMPLET,
            features = listOf(
                Icons.Rounded.CheckCircle to "Evaluación emocional",
                Icons.Rounded.PictureAsPdf to "Exportación en PDF",
                Icons.Rounded.History to "Historial de evaluaciones"
            )
        )
    )
)

@Composable
fun OnboardingScreen(onTerminer: () -> Unit) {
    val slides = onboardingSlides()
    val pagerState = rememberPagerState(pageCount = { slides.size })
    val scope = rememberCoroutineScope()

    val couleurPatte = if (isSystemInDarkTheme()) PremiumPalette.PrimarySoft.copy(alpha = 0.09f) else PremiumPalette.Primary.copy(alpha = 0.07f)
    AppBackground {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val t = 20.dp.toPx()
            traceDePattes(Offset(size.width * 0.04f, size.height * 0.30f), Offset(size.width * 0.30f, -t), 4, t, couleurPatte)
            traceDePattes(Offset(size.width + t, size.height * 0.80f), Offset(size.width * 0.62f, size.height * 1.02f), 4, t, couleurPatte)
        }
        Column(
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = onTerminer) {
                    Text(
                        tr("Passer", "Skip", "Überspringen", "Omitir"),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                OnboardingSlideContent(slide = slides[page])
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(slides.size) { index ->
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

                val estDernierSlide = pagerState.currentPage == slides.lastIndex
                PrimaryGlowButton(
                    text = if (estDernierSlide) {
                        tr("Commencer", "Get started", "Los geht’s", "Empezar")
                    } else {
                        tr("Suivant", "Next", "Weiter", "Siguiente")
                    },
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
    // Les 3 pages suivent exactement la même structure : titre, (accroche), illustration, texte, complément
    val isDark = isSystemInDarkTheme()
    val bienvenue = slide.illustrationType == IllustrationType.CHAT_LOGO
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(if (bienvenue) slide.titre else slide.kicker, style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold, color = marronTexte(),
            textAlign = TextAlign.Center)
        if (bienvenue) {
            Spacer(modifier = Modifier.height(18.dp))
            Text(tr("Mieux le comprendre pour mieux l'accompagner", "Understand them better to support them better", "Sie besser verstehen, um sie besser zu begleiten", "Comprenderlo mejor para acompañarlo mejor"),
                style = styleDoux().copy(fontSize = 18.sp, lineHeight = 25.sp), color = marronTexte(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(18.dp))
        } else {
            Spacer(modifier = Modifier.height(18.dp))
        }

        Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
            if (bienvenue) {
                Box(modifier = Modifier.matchParentSize().background(Brush.radialGradient(listOf(Color.White.copy(alpha = if (isDark) 0.06f else 0.6f), Color.Transparent))))
                Image(painter = painterResource(id = R.drawable.logo_accueil_rogne),
                    contentDescription = tr("Logo Comprendre mon chat", "Understanding My Cat logo", "Logo Meine Katze verstehen", "Logotipo de Comprender a mi gato"),
                    modifier = Modifier.fillMaxHeight(), contentScale = ContentScale.Fit)
            } else {
                Box(
                    modifier = Modifier.size(150.dp).clip(CircleShape)
                        .background(Brush.radialGradient(listOf(PremiumPalette.PaperWarm, PremiumPalette.Paper))),
                    contentAlignment = Alignment.Center
                ) {
                    if (slide.illustrationType == IllustrationType.QUATRE_AXES) QuatreAxesIllustration() else BilanIllustration()
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text(slide.description, style = styleDoux().copy(fontSize = 17.sp, lineHeight = 24.sp),
            color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(if (bienvenue) 28.dp else 16.dp))

        if (bienvenue) {
            Text(tr("Créée par Lauréna Haroy\ncomportementaliste animalier", "Created by Lauréna Haroy\nanimal behaviourist", "Entwickelt von Lauréna Haroy\nVerhaltensberaterin für Tiere", "Creada por Lauréna Haroy\nespecialista en comportamiento animal"),
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                slide.features.forEach { (icon, label) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                            .background(if (isDark) Color(0xFF231B17) else PremiumPalette.PaperSoft)
                            .border(1.dp, if (isDark) Color(0xFF56433B) else PremiumPalette.Border, RoundedCornerShape(14.dp))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(30.dp).clip(CircleShape).background(PremiumPalette.Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, contentDescription = null, tint = Beige, modifier = Modifier.size(16.dp))
                        }
                        Text(label, style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun QuatreAxesIllustration() {
    val primary = PremiumPalette.Primary
    val soft = PremiumPalette.PrimarySoft
    val accent = PremiumPalette.Accent

    Canvas(modifier = Modifier.size(140.dp)) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val r = size.width * 0.38f

        listOf(0.33f, 0.66f, 1f).forEach { ratio ->
            drawCircle(color = accent.copy(alpha = 0.15f), radius = r * ratio,
                center = Offset(cx, cy), style = Stroke(width = 1f))
        }

        val angles = listOf(270f, 0f, 90f, 180f)
        val valeurs = listOf(0.75f, 0.55f, 0.80f, 0.45f)

        angles.forEach { angle ->
            val rad = Math.toRadians(angle.toDouble())
            drawLine(color = accent.copy(alpha = 0.4f), start = Offset(cx, cy),
                end = Offset(cx + (r * Math.cos(rad)).toFloat(), cy + (r * Math.sin(rad)).toFloat()),
                strokeWidth = 1.5f)
        }

        val points = angles.mapIndexed { i, angle ->
            val rad = Math.toRadians(angle.toDouble())
            Offset(cx + (r * valeurs[i] * Math.cos(rad)).toFloat(),
                cy + (r * valeurs[i] * Math.sin(rad)).toFloat())
        }
        val path = Path().apply {
            moveTo(points[0].x, points[0].y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
            close()
        }
        drawPath(path, color = primary.copy(alpha = 0.20f))
        drawPath(path, color = primary, style = Stroke(width = 2.5f, cap = StrokeCap.Round, join = StrokeJoin.Round))
        points.forEach { pt ->
            drawCircle(color = primary, radius = 5f, center = pt)
            drawCircle(color = Color.White, radius = 2.5f, center = pt)
        }
        drawCircle(color = soft, radius = 5f, center = Offset(cx, cy))
    }
}

@Composable
fun BilanIllustration() {
    val primary = PremiumPalette.Primary
    val soft = PremiumPalette.PrimarySoft
    val accent = PremiumPalette.Accent
    val border = PremiumPalette.Border

    Canvas(modifier = Modifier.size(140.dp)) {
        val w = size.width
        val h = size.height
        val stroke = Stroke(width = 2f, cap = StrokeCap.Round)

        translate(w * 0.5f, h * 0.5f) {
            drawRoundRect(color = border, topLeft = Offset(-28f, -36f), size = Size(60f, 76f), cornerRadius = CornerRadius(8f))
            drawRoundRect(color = Color.White, topLeft = Offset(-32f, -40f), size = Size(60f, 76f), cornerRadius = CornerRadius(8f))
            drawRoundRect(color = border, topLeft = Offset(-32f, -40f), size = Size(60f, 76f), cornerRadius = CornerRadius(8f), style = stroke)
            drawRoundRect(color = primary.copy(alpha = 0.85f), topLeft = Offset(-32f, -40f), size = Size(60f, 18f), cornerRadius = CornerRadius(8f))
            listOf(-14f, -4f, 6f, 16f).forEachIndexed { i, y ->
                val w2 = if (i % 2 == 0) 44f else 32f
                drawRoundRect(color = accent.copy(alpha = 0.4f), topLeft = Offset(-24f, y), size = Size(w2, 4f), cornerRadius = CornerRadius(2f))
            }
            drawRoundRect(color = border, topLeft = Offset(-24f, 26f), size = Size(44f, 5f), cornerRadius = CornerRadius(3f))
            drawRoundRect(color = soft, topLeft = Offset(-24f, 26f), size = Size(30f, 5f), cornerRadius = CornerRadius(3f))
            drawCircle(color = primary, radius = 14f, center = Offset(22f, 28f))
            val check = Path().apply { moveTo(15f, 28f); lineTo(20f, 34f); lineTo(30f, 22f) }
            drawPath(check, color = Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round))
        }
    }
}

@Composable
fun AccueilIllustrationCard() {
    val isDark = isSystemInDarkTheme()
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                tr("Comprendre mon chat", "Understanding My Cat", "Meine Katze verstehen", "Comprender a mi gato"),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold, color = PremiumPalette.Primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_accueil),
                    contentDescription = tr("Logo Comprendre mon chat", "Understanding My Cat logo", "Logo Meine Katze verstehen", "Logotipo de Comprender a mi gato"),
                    modifier = Modifier.size(160.dp).clip(RoundedCornerShape(28.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                tr("Bienvenue", "Welcome", "Willkommen", "Te damos la bienvenida"),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}