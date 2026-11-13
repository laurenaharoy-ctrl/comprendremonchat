package com.laurena.comprendremonchat

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.rotate
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Block
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate

// ─── Thème couleurs ─────────────────────────────────────────

private val LightColors = lightColorScheme(
    primary = Color(0xFF8E4A2D),
    onPrimary = Color.White,
    background = Color(0xFFEEDFD5),
    surface = Color(0xFFFCF9F6),
    onSurface = Color(0xFF33231D),
    onBackground = Color(0xFF33231D),
    surfaceVariant = Color(0xFFF0E5DC),
    onSurfaceVariant = Color(0xFF75584C),
    outline = Color(0xFFE0D2C6),
    secondary = Color(0xFFB86A4A),
    tertiary = Color(0xFFD9A58F),
    error = Color(0xFF8E4A2D)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFD39A7F),
    onPrimary = Color(0xFF3C1F14),
    background = Color(0xFF191411),
    surface = Color(0xFF231B17),
    onSurface = Color(0xFFF6EEE8),
    onBackground = Color(0xFFF6EEE8),
    surfaceVariant = Color(0xFF342923),
    onSurfaceVariant = Color(0xFFD2B9AB),
    outline = Color(0xFF56433B),
    secondary = Color(0xFFB86A4A),
    tertiary = Color(0xFFD9A58F),
    error = Color(0xFFD39A7F)
)

object PremiumPalette {
    val Primary = Color(0xFF8E4A2D)
    val PrimarySoft = Color(0xFFB86A4A)
    val Accent = Color(0xFFD9A58F)
    val Paper = Color(0xFFF4EFE8)
    val PaperSoft = Color(0xFFFCF9F6)
    val PaperWarm = Color(0xFFF1E7DE)
    val Ink = Color(0xFF33231D)
    val InkSoft = Color(0xFF75584C)
    val InkMuted = Color(0xFFA2897C)
    val Border = Color(0xFFE0D2C6)
    val Warning = Color(0xFF8E4A2D)
    val PrioriteFaible = Color(0xFF9E8572)
    val PrioriteModere = Color(0xFFB8845A)
    val PrioriteElevee = Color(0xFF8E4A2D)
    val PrioriteUrgente = Color(0xFF6B2D1A)
    val PrioriteFaibleBg = Color(0xFFF4EDE6)
    val PrioriteModereBg = Color(0xFFF5E8DC)
    val PrioriteEleveeBg = Color(0xFFF2E0D6)
    val PrioriteUrgenteBg = Color(0xFFEDD8D0)
    val MorsureBg = Color(0xFF3D1209)
    val MorsureBorder = Color(0xFF8E2A10)
    val MorsuText = Color(0xFFFFF0EC)
}

// ─── Dictionnaire alimentation ───────────────────────────────

enum class DictionnaireCategorie { DANGEREUX, AUTORISES, INGESTION, DIGESTION }

@Composable
fun DictionnaireCategorie.titre(): String = when (this) {
    DictionnaireCategorie.DANGEREUX -> stringResource(R.string.cat_dangereux_titre)
    DictionnaireCategorie.AUTORISES -> stringResource(R.string.cat_autorises_titre)
    DictionnaireCategorie.INGESTION -> stringResource(R.string.cat_ingestion_titre)
    DictionnaireCategorie.DIGESTION -> stringResource(R.string.cat_digestion_titre)
}

data class DictionnaireEntry(
    val categorie: DictionnaireCategorie,
    val titreKey: Int,
    val resumeKey: Int,
    val contenuKey: Int
)

data class ComportementEntry(
    val id: String,
    val titreKey: Int,
    val resumeKey: Int,
    val explicationKey: Int,
    val queFaireKey: Int,
    val aEviterKey: Int
)

fun getComportementEntryById(id: String): ComportementEntry? =
    comportementEntries().firstOrNull { it.id == id }

fun dictionnaireEntries(): List<DictionnaireEntry> {
    return listOf(
        DictionnaireEntry(DictionnaireCategorie.DANGEREUX, R.string.alim_dangereux1_titre, R.string.alim_dangereux1_resume, R.string.alim_dangereux1_contenu),
        DictionnaireEntry(DictionnaireCategorie.DANGEREUX, R.string.alim_dangereux2_titre, R.string.alim_dangereux2_resume, R.string.alim_dangereux2_contenu),
        DictionnaireEntry(DictionnaireCategorie.DANGEREUX, R.string.alim_dangereux3_titre, R.string.alim_dangereux3_resume, R.string.alim_dangereux3_contenu),
        DictionnaireEntry(DictionnaireCategorie.AUTORISES, R.string.alim_autorises1_titre, R.string.alim_autorises1_resume, R.string.alim_autorises1_contenu),
        DictionnaireEntry(DictionnaireCategorie.AUTORISES, R.string.alim_autorises2_titre, R.string.alim_autorises2_resume, R.string.alim_autorises2_contenu),
        DictionnaireEntry(DictionnaireCategorie.AUTORISES, R.string.alim_autorises3_titre, R.string.alim_autorises3_resume, R.string.alim_autorises3_contenu),
        DictionnaireEntry(DictionnaireCategorie.INGESTION, R.string.alim_ingestion1_titre, R.string.alim_ingestion1_resume, R.string.alim_ingestion1_contenu),
        DictionnaireEntry(DictionnaireCategorie.INGESTION, R.string.alim_ingestion2_titre, R.string.alim_ingestion2_resume, R.string.alim_ingestion2_contenu),
        DictionnaireEntry(DictionnaireCategorie.DIGESTION, R.string.alim_digestion1_titre, R.string.alim_digestion1_resume, R.string.alim_digestion1_contenu),
        DictionnaireEntry(DictionnaireCategorie.DIGESTION, R.string.alim_digestion2_titre, R.string.alim_digestion2_resume, R.string.alim_digestion2_contenu),
        DictionnaireEntry(DictionnaireCategorie.DIGESTION, R.string.alim_digestion3_titre, R.string.alim_digestion3_resume, R.string.alim_digestion3_contenu),
        DictionnaireEntry(DictionnaireCategorie.DIGESTION, R.string.alim_digestion4_titre, R.string.alim_digestion4_resume, R.string.alim_digestion4_contenu)
    )
}

fun comportementEntries(): List<ComportementEntry> {
    return listOf(
        ComportementEntry("ronronnement", R.string.comp_ronronnement_titre, R.string.comp_ronronnement_resume, R.string.comp_ronronnement_explication, R.string.comp_ronronnement_que_faire, R.string.comp_ronronnement_a_eviter),
        ComportementEntry("clin-oeil-lent", R.string.comp_clin_oeil_titre, R.string.comp_clin_oeil_resume, R.string.comp_clin_oeil_explication, R.string.comp_clin_oeil_que_faire, R.string.comp_clin_oeil_a_eviter),
        ComportementEntry("queue-dressee", R.string.comp_queue_dressee_titre, R.string.comp_queue_dressee_resume, R.string.comp_queue_dressee_explication, R.string.comp_queue_dressee_que_faire, R.string.comp_queue_dressee_a_eviter),
        ComportementEntry("queue-fouettante", R.string.comp_queue_fouettante_titre, R.string.comp_queue_fouettante_resume, R.string.comp_queue_fouettante_explication, R.string.comp_queue_fouettante_que_faire, R.string.comp_queue_fouettante_a_eviter),
        ComportementEntry("oreilles-arriere", R.string.comp_oreilles_arriere_titre, R.string.comp_oreilles_arriere_resume, R.string.comp_oreilles_arriere_explication, R.string.comp_oreilles_arriere_que_faire, R.string.comp_oreilles_arriere_a_eviter),
        ComportementEntry("ventre-expose", R.string.comp_ventre_expose_titre, R.string.comp_ventre_expose_resume, R.string.comp_ventre_expose_explication, R.string.comp_ventre_expose_que_faire, R.string.comp_ventre_expose_a_eviter),
        ComportementEntry("petrir", R.string.comp_petrir_titre, R.string.comp_petrir_resume, R.string.comp_petrir_explication, R.string.comp_petrir_que_faire, R.string.comp_petrir_a_eviter),
        ComportementEntry("frotter-tete", R.string.comp_frotter_tete_titre, R.string.comp_frotter_tete_resume, R.string.comp_frotter_tete_explication, R.string.comp_frotter_tete_que_faire, R.string.comp_frotter_tete_a_eviter),
        ComportementEntry("chatter", R.string.comp_chatter_titre, R.string.comp_chatter_resume, R.string.comp_chatter_explication, R.string.comp_chatter_que_faire, R.string.comp_chatter_a_eviter),
        ComportementEntry("griffage", R.string.comp_griffage_titre, R.string.comp_griffage_resume, R.string.comp_griffage_explication, R.string.comp_griffage_que_faire, R.string.comp_griffage_a_eviter),
        ComportementEntry("marquage-urinaire", R.string.comp_marquage_urinaire_titre, R.string.comp_marquage_urinaire_resume, R.string.comp_marquage_urinaire_explication, R.string.comp_marquage_urinaire_que_faire, R.string.comp_marquage_urinaire_a_eviter),
        ComportementEntry("hyperactivite-nocturne", R.string.comp_hyperactivite_titre, R.string.comp_hyperactivite_resume, R.string.comp_hyperactivite_explication, R.string.comp_hyperactivite_que_faire, R.string.comp_hyperactivite_a_eviter),
        ComportementEntry("destruction-absence", R.string.comp_destruction_titre, R.string.comp_destruction_resume, R.string.comp_destruction_explication, R.string.comp_destruction_que_faire, R.string.comp_destruction_a_eviter),
        ComportementEntry("vocalisation-excessive", R.string.comp_vocalisation_titre, R.string.comp_vocalisation_resume, R.string.comp_vocalisation_explication, R.string.comp_vocalisation_que_faire, R.string.comp_vocalisation_a_eviter),
        ComportementEntry("coup-de-chaleur", R.string.comp_coup_de_chaleur_titre, R.string.comp_coup_de_chaleur_resume, R.string.comp_coup_de_chaleur_explication, R.string.comp_coup_de_chaleur_que_faire, R.string.comp_coup_de_chaleur_a_eviter)
    )
}

// ─── Thème ───────────────────────────────────────────────────

@Composable
fun ComprendreMonchatTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = MaterialTheme.typography.copy(
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        ),
        content = content
    )
}

@Composable
fun AppBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    val brush = if (isSystemInDarkTheme())
        Brush.verticalGradient(listOf(Color(0xFF241B17), Color(0xFF1D1613), Color(0xFF171210)))
    else
        Brush.verticalGradient(listOf(Color(0xFFEEDFD5), Color(0xFFEAD9CD), Color(0xFFE6D3C6)))
    Box(modifier = modifier.fillMaxSize().background(brush)) {
        Box(modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.08f)
            .background(Brush.radialGradient(colors = listOf(Color.White, Color.Transparent))))
        content()
    }
}

@Composable
fun EditorialContainer(
    modifier: Modifier = Modifier,
    maxWidth: Int = 760,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Column(modifier = Modifier.fillMaxWidth().widthIn(max = maxWidth.dp), content = content)
    }
}

@Composable
fun PremiumCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(22.dp),
    centered: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Color(0xFF231B17) else PremiumPalette.PaperSoft
        ),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF56433B) else PremiumPalette.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
        ) { content() }
    }
}

@Composable
fun AccentChip(text: String) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(999.dp))
            .background(if (isSystemInDarkTheme()) Color(0xFF342923) else Color(0xFFF0E5DC))
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun SectionChip(text: String) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(999.dp))
            .background(if (isSystemInDarkTheme()) Color(0xFF3D2920) else Color(0xFFEDD8CC))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = marronTexte())
    }
}

@Composable
fun EditorialKicker(text: String, centered: Boolean = false) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = if (centered) TextAlign.Center else TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PrimaryGlowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leading: (@Composable () -> Unit)? = null
) {
    Button(
        onClick = onClick, enabled = enabled,
        modifier = modifier.fillMaxWidth().heightIn(min = 58.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PremiumPalette.Primary,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFCFB3A5),
            disabledContentColor = Color.White.copy(alpha = 0.8f)
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leading?.invoke()
            if (leading != null) Spacer(modifier = Modifier.width(8.dp))
            Text(text, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SecondaryPremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Color(0xFF342923) else Color(0xFFF0E5DC),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leading?.invoke()
            if (leading != null) Spacer(modifier = Modifier.width(8.dp))
            Text(text)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumTopBar(title: String, onBack: (() -> Unit)?) {
    CenterAlignedTopAppBar(
        title = { Text(title, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.nav_retour), tint = MaterialTheme.colorScheme.onBackground)
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun ChargementMinimal() {
    AppBackground {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PremiumPalette.Primary)
        }
    }
}

@Composable
fun ChargementAnalyseScreen(modifier: Modifier = Modifier, onTermine: () -> Unit) {
    val messages = listOf(
        stringResource(R.string.chargement_analyse),
        stringResource(R.string.chargement_profil),
        stringResource(R.string.chargement_bilan)
    )
    var messageIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        repeat(messages.size) { messageIndex = it; delay(700) }
        delay(400); onTermine()
    }
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    val dot1Alpha by infiniteTransition.animateFloat(0.3f, 1f, infiniteRepeatable(tween(600, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "d1")
    val dot2Alpha by infiniteTransition.animateFloat(0.3f, 1f, infiniteRepeatable(tween(600, delayMillis = 200, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "d2")
    val dot3Alpha by infiniteTransition.animateFloat(0.3f, 1f, infiniteRepeatable(tween(600, delayMillis = 400, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "d3")
    AppBackground {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(28.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.size(14.dp).alpha(dot1Alpha).background(PremiumPalette.Primary, CircleShape))
                    Box(modifier = Modifier.size(14.dp).alpha(dot2Alpha).background(PremiumPalette.PrimarySoft, CircleShape))
                    Box(modifier = Modifier.size(14.dp).alpha(dot3Alpha).background(PremiumPalette.Accent, CircleShape))
                }
                Text(messages[messageIndex], style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun AccueilScreen(
    modifier: Modifier = Modifier,
    hasSavedProgress: Boolean,
    onCommencer: () -> Unit,
    onReprendre: () -> Unit,
    onDictionnaire: () -> Unit,
    onAlimentation: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val couleurPatte = if (isDark) PremiumPalette.PrimarySoft.copy(alpha = 0.09f) else PremiumPalette.Primary.copy(alpha = 0.07f)
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val t = 20.dp.toPx()
            // une trace en haut à gauche, qui sort de l'écran
            traceDePattes(Offset(size.width * 0.04f, size.height * 0.30f), Offset(size.width * 0.30f, -t), 4, t, couleurPatte)
            // une trace en bas à droite, qui entre dans l'écran
            traceDePattes(Offset(size.width + t, size.height * 0.80f), Offset(size.width * 0.62f, size.height * 1.02f), 4, t, couleurPatte)
        }
        EditorialContainer(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars).padding(horizontal = 20.dp, vertical = 8.dp)) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(tr("Comprendre mon chat", "Understanding My Cat", "Meine Katze verstehen", "Comprender a mi gato"), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = marronTexte(), textAlign = TextAlign.Center)
                Text(tr("Mieux le comprendre pour mieux l'accompagner", "Understand them better to support them better", "Sie besser verstehen, um sie besser zu begleiten", "Comprenderlo mejor para acompañarlo mejor"),
                    style = styleDoux().copy(fontSize = 19.sp, lineHeight = 26.sp), color = marronTexte(), textAlign = TextAlign.Center)
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(width = 320.dp, height = 240.dp).background(Brush.radialGradient(listOf(Color.White.copy(alpha = if (isDark) 0.06f else 0.6f), Color.Transparent))))
                    Image(painter = painterResource(id = R.drawable.logo_accueil_rogne), contentDescription = tr("Logo Comprendre mon chat", "Understanding My Cat logo", "Logo Meine Katze verstehen", "Logotipo de Comprender a mi gato"), modifier = Modifier.height(210.dp), contentScale = ContentScale.Fit)
                }
                Text(tr("Créée par Lauréna Haroy\ncomportementaliste animalier", "Created by Lauréna Haroy\nanimal behaviourist", "Entwickelt von Lauréna Haroy\nVerhaltensberaterin für Tiere", "Creada por Lauréna Haroy\nespecialista en comportamiento animal"),
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(6.dp))
                PrimaryGlowButton(text = stringResource(R.string.btn_demarrer), onClick = onCommencer, leading = { Icon(Icons.Rounded.Pets, contentDescription = null, tint = Color.White) })
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TuileAccueil(stringResource(R.string.btn_dictionnaire), Icons.Rounded.MenuBook, onDictionnaire, Modifier.weight(1f))
                    TuileAccueil(stringResource(R.string.btn_alimentation), Icons.Rounded.Star, onAlimentation, Modifier.weight(1f))
                }
                if (hasSavedProgress) SecondaryPremiumButton(text = stringResource(R.string.btn_reprendre), onClick = onReprendre, leading = { Icon(Icons.Rounded.AutoStories, contentDescription = null) })
            }
        }
    }
}

@Composable
fun TuileAccueil(texte: String, icone: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()
    Card(modifier = modifier.clickable(onClick = onClick), shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF231B17) else PremiumPalette.PaperSoft),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF56433B) else PremiumPalette.Border)) {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icone, contentDescription = null, tint = PremiumPalette.PrimarySoft, modifier = Modifier.size(26.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(texte, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = marronTexte(), textAlign = TextAlign.Center)
        }
    }
}

/** Trace de pattes discrète, de [depart] vers [arrivee], pas alternés gauche / droite. */
fun androidx.compose.ui.graphics.drawscope.DrawScope.traceDePattes(depart: Offset, arrivee: Offset, nombre: Int, taille: Float, couleur: Color) {
    val dx = arrivee.x - depart.x; val dy = arrivee.y - depart.y
    val longueur = kotlin.math.sqrt(dx * dx + dy * dy)
    val ux = dx / longueur; val uy = dy / longueur
    val angle = Math.toDegrees(kotlin.math.atan2(uy.toDouble(), ux.toDouble())).toFloat() + 90f
    for (i in 0 until nombre) {
        val f = (i + 0.5f) / nombre
        val cote = if (i % 2 == 0) -0.45f else 0.45f
        val c = Offset(depart.x + dx * f - uy * cote * taille, depart.y + dy * f + ux * cote * taille)
        rotate(angle, pivot = c) {
            drawOval(couleur, topLeft = Offset(c.x - 0.5f * taille, c.y - 0.15f * taille), size = Size(taille, 0.82f * taille))
            for ((ox, oy) in listOf(-0.56f to -0.42f, -0.2f to -0.78f, 0.2f to -0.78f, 0.56f to -0.42f)) {
                drawOval(couleur, topLeft = Offset(c.x + ox * taille - 0.15f * taille, c.y + oy * taille - 0.2f * taille), size = Size(0.3f * taille, 0.4f * taille))
            }
        }
    }
}

@Composable
fun QuestionnaireScreen(
    modifier: Modifier = Modifier,
    question: Question,
    progress: Float,
    numero: Int,
    total: Int,
    nomChat: String = "",
    valeurTexte: String,
    choixSelectionne: Int?,
    onValeurChangee: (String) -> Unit,
    onChoixSelectionne: (Int) -> Unit,
    onSuivant: () -> Unit
) {
    val titreSection = QuestionnaireEngine.titreSectionPourQuestion(question.id)
    val boutonActif = when (question) {
        is QuestionTexte -> valeurTexte.isNotBlank()
        is QuestionChoix -> choixSelectionne != null
    }
    val scrollStateQuestion = rememberScrollState()
    LaunchedEffect(question.id) { scrollStateQuestion.scrollTo(0) }

    EditorialContainer(
        modifier = modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            PremiumCard(centered = true) {
                SectionChip(titreSection)
                Spacer(modifier = Modifier.height(10.dp))
                Text("$numero ${stringResource(R.string.label_sur)} $total", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(12.dp))
                val animatedProgress by animateFloatAsState(progress.coerceIn(0f, 1f), label = "progress")
                Box(modifier = Modifier.fillMaxWidth().height(10.dp)
                    .background(if (isSystemInDarkTheme()) Color(0xFF342923) else Color(0xFFE9DED5), RoundedCornerShape(999.dp))) {
                    Box(modifier = Modifier.fillMaxWidth(animatedProgress).height(10.dp)
                        .background(Brush.horizontalGradient(listOf(PremiumPalette.Primary, PremiumPalette.PrimarySoft)), RoundedCornerShape(999.dp)))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.weight(1f).verticalScroll(scrollStateQuestion), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                when (question) {
                    is QuestionTexte -> {
                        PremiumCard {
                            Text(question.titre, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(18.dp))
                            OutlinedTextField(
                                value = valeurTexte,
                                onValueChange = onValeurChangee,
                                label = { Text(stringResource(R.string.label_prenom_chat)) },
                                placeholder = { Text(stringResource(R.string.placeholder_prenom_chat)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PremiumPalette.Primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = PremiumPalette.Primary,
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                    is QuestionChoix -> {
                        PremiumCard {
                            Text(question.titre, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
                            val aide = QuestionnaireEngine.aideQuestion(question.id)
                            if (aide != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(aide, style = MaterialTheme.typography.bodySmall, color = PremiumPalette.PrimarySoft, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                            question.options.forEachIndexed { index, option ->
                                ChoiceRow(text = option, selected = choixSelectionne == index, onClick = { onChoixSelectionne(index) })
                                if (index != question.options.lastIndex) Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                PrimaryGlowButton(text = stringResource(R.string.btn_continuer), onClick = onSuivant, enabled = boutonActif)
                if (!boutonActif) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (question) {
                            is QuestionTexte -> stringResource(R.string.hint_saisir_prenom)
                            is QuestionChoix -> stringResource(R.string.hint_choisir_reponse)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun ChoiceRow(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .selectable(selected = selected, onClick = onClick, role = Role.RadioButton)
            .background(
                if (selected) PremiumPalette.Accent.copy(alpha = 0.20f)
                else if (isSystemInDarkTheme()) Color(0xFF231B17) else Color(0xFFFCF9F6),
                RoundedCornerShape(20.dp)
            )
            .border(1.dp, if (selected) PremiumPalette.Primary else MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(22.dp)
            .background(if (selected) PremiumPalette.Primary else Color.Transparent, CircleShape)
            .border(2.dp, if (selected) PremiumPalette.Primary else MaterialTheme.colorScheme.outline, CircleShape))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurface)
    }
}

val LoraItalique = FontFamily(Font(R.font.lora_italic, FontWeight.Normal, FontStyle.Italic))
val Beige = Color(0xFFF5EBE0)

@Composable
fun LettreMiCheminScreen(modifier: Modifier = Modifier, nomChat: String, onContinuer: () -> Unit, onPause: () -> Unit) {
    val nom = nomChatAffiche(nomChat)
    val fond = if (isSystemInDarkTheme()) Color(0xFF5A2E1A) else PremiumPalette.Primary
    val lettre = MaterialTheme.typography.titleMedium.copy(fontFamily = LoraItalique, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal, fontSize = 17.sp, lineHeight = 25.sp)
    Box(modifier = modifier.fillMaxSize().background(fond)) {
        Column(
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars).verticalScroll(rememberScrollState()).padding(horizontal = 28.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Rounded.Pets, contentDescription = null, tint = Beige.copy(alpha = 0.8f), modifier = Modifier.size(26.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(tr("Vous êtes à mi-chemin !", "You're halfway there!", "Sie haben die Hälfte geschafft!", "¡Vas por la mitad!"), style = lettre.copy(fontSize = 23.sp, lineHeight = 30.sp), color = Beige, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                tr(
                    "Je sais, le questionnaire est un peu long. Mais cette appli n'est pas un simple test : elle est conçue pour vous aider réellement à comprendre $nom et à repérer une éventuelle difficulté de comportement. Pour cela, j'ai besoin de vous poser un certain nombre de questions.",
                    "I know, the questionnaire is a bit long. But this app isn't just a simple quiz: it's designed to genuinely help you understand $nom and spot any possible behavioral difficulty. To do that, I need to ask you a certain number of questions.",
                    "Ich weiß, der Fragebogen ist etwas lang. Aber diese App ist kein einfacher Test: Sie soll Ihnen wirklich helfen, $nom zu verstehen und eine mögliche Verhaltensschwierigkeit zu erkennen. Dafür muss ich Ihnen eine Reihe von Fragen stellen.",
                    "Lo sé, el cuestionario es un poco largo. Pero esta aplicación no es un simple test: está pensada para ayudarte de verdad a comprender a $nom y a detectar una posible dificultad de comportamiento. Para ello, necesito hacerte cierto número de preguntas."
                ),
                style = lettre, color = Beige, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(tr("Courage, vous avez déjà fait la moitié du chemin !", "Keep going, you're already halfway through!", "Nur Mut, die Hälfte haben Sie schon geschafft!", "¡Ánimo, ya has hecho la mitad del camino!"), style = lettre, color = Beige, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Text("Lauréna", style = lettre.copy(fontSize = 24.sp), color = Beige, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onContinuer, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Beige, contentColor = fond)
            ) { Text(tr("Continuer le questionnaire", "Continue the questionnaire", "Fragebogen fortsetzen", "Continuar el cuestionario"), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold) }
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(onClick = onPause) {
                Text(tr("Faire une pause", "Take a break", "Eine Pause machen", "Hacer una pausa"), color = Beige, textDecoration = TextDecoration.Underline)
            }
            Text(tr("Vos réponses sont enregistrées, vous pourrez reprendre plus tard.", "Your answers are saved, you can pick up where you left off later.", "Ihre Antworten sind gespeichert, Sie können später weitermachen.", "Tus respuestas están guardadas, podrás continuar más tarde."), style = MaterialTheme.typography.bodySmall, color = Beige.copy(alpha = 0.75f), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun EncartMarron(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF6B3820) else PremiumPalette.Primary)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally, content = content)
    }
}

@Composable
fun KickerBeige(text: String) {
    Text(text.uppercase(), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Beige.copy(alpha = 0.8f), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

@Composable
fun KickerDoux(text: String) {
    Text(text.uppercase(), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = marronTexte(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

/** Marron des textes : plus clair en mode sombre pour rester lisible */
@Composable
fun marronTexte(): Color = if (isSystemInDarkTheme()) Color(0xFFE8B08E) else PremiumPalette.Primary

@Composable
fun EncartDoux(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
            .background(if (isSystemInDarkTheme()) Color(0xFF3A2B23) else PremiumPalette.PaperWarm)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, content = content
    )
}

@Composable
fun TitreEncart(text: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Icon(icon, contentDescription = null, tint = PremiumPalette.PrimarySoft, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = marronTexte())
    }
}

@Composable
fun styleDoux() = MaterialTheme.typography.titleMedium.copy(fontFamily = LoraItalique, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal, lineHeight = 26.sp)

@Composable
fun SectionDeroulante(titre: String, ouvertParDefaut: Boolean = false, content: @Composable ColumnScope.() -> Unit) {
    var ouvert by rememberSaveable(titre) { mutableStateOf(ouvertParDefaut) }
    val rotation by animateFloatAsState(if (ouvert) 180f else 0f, label = "chevron_$titre")
    PremiumCard(contentPadding = PaddingValues(0.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { ouvert = !ouvert }.padding(horizontal = 22.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(titre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = marronTexte(), modifier = Modifier.weight(1f))
            Icon(Icons.Rounded.KeyboardArrowDown, contentDescription = null, tint = PremiumPalette.Primary, modifier = Modifier.size(26.dp).rotate(rotation))
        }
        AnimatedVisibility(visible = ouvert, enter = expandVertically() + fadeIn(), exit = shrinkVertically() + fadeOut()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 22.dp, end = 22.dp, bottom = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.HorizontalDivider(color = PremiumPalette.Border, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(16.dp))
                content()
            }
        }
    }
}

@Composable
fun BulletDoux(text: String) {
    Row(verticalAlignment = Alignment.Top) { Box(modifier = Modifier.padding(top = 10.dp).size(7.dp).background(PremiumPalette.PrimarySoft, CircleShape)); Spacer(modifier = Modifier.width(10.dp)); Text(text, modifier = Modifier.weight(1f), style = styleDoux().copy(fontSize = 17.sp, lineHeight = 24.sp), color = marronTexte()) }
}

@Composable
fun LienDiscret(texte: String, onClick: () -> Unit) {
    Text(
        texte, color = marronTexte(), style = MaterialTheme.typography.bodySmall, textDecoration = TextDecoration.Underline,
        modifier = Modifier.clip(RoundedCornerShape(6.dp)).clickable(onClick = onClick).padding(horizontal = 4.dp, vertical = 6.dp)
    )
}

@Composable
fun ResultatScreen(
    modifier: Modifier = Modifier,
    nomChat: String,
    analyse: ResultatAnalyse,
    onShare: () -> Unit,
    onCopy: () -> Unit,
    onExportPdf: () -> Unit,
    onRecommencer: () -> Unit,
    onOpenFiche: (String) -> Unit = {},
    onOpenAlimentation: () -> Unit = {}
) {
    val context = LocalContext.current
    val nom = nomChatAffiche(nomChat)
    // Profil calme : scores bas et situation stable -> on évite les textes contradictoires ou superflus
    val profilCalme = maxOf(analyse.peur, analyse.attachement, analyse.impulsivite, analyse.reactivite) <= 30 && analyse.niveauSituation == NiveauSituation.STABLE
    val explicationGenerique = analyse.explicationPrincipale.startsWith(tr("Les éléments recueillis ne mettent pas", "The information collected does not", "Die gesammelten Angaben zeigen derzeit keine", "La información recopilada no muestra"))
    EditorialContainer(
        modifier = modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 10.dp),
        maxWidth = 780
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            // --- Toujours visible en haut
            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_votre_bilan), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(tr("Pour $nom", "For $nom", "Für $nom", "Para $nom"), style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                AccentChip(analyse.profil.profilType)
            }
            EncartMarron {
                Text(analyse.hypothesePrincipale, style = styleDoux(), textAlign = TextAlign.Center, color = Beige)
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.clip(RoundedCornerShape(999.dp)).background(couleurFondPriorite(analyse.prioriteAction)).padding(horizontal = 14.dp, vertical = 8.dp)) {
                    Text("${stringResource(R.string.label_priorite)}${textePrioriteAction(analyse.prioriteAction)}", color = couleurPriorite(analyse.prioriteAction), fontWeight = FontWeight.SemiBold)
                }
            }
            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_coup_oeil), centered = true)
                Spacer(modifier = Modifier.height(14.dp))
                QuatreAxesGrid(analyse = analyse)
            }

            // --- Onglets à dérouler
            if (!analyse.raceCategorie.isNullOrBlank() || !analyse.racePrecise.isNullOrBlank()) {
                SectionDeroulante(stringResource(R.string.kicker_profil_race)) { RaceCard(raceCategorie = analyse.raceCategorie, racePrecise = analyse.racePrecise, bare = true) }
            }
            SectionDeroulante("${stringResource(R.string.kicker_ressent)} $nom") {
                Text(phraseHumaineCoherente(analyse, nom), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                if (!profilCalme) {
                    Spacer(modifier = Modifier.height(14.dp))
                    EncartDoux {
                        Text(resumeEmotionnel(analyse.problemePrincipal, niveauAxePrincipal(analyse)), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, color = marronTexte())
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(intentionChat(analyse.problemePrincipal, niveauAxePrincipal(analyse)), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(besoinPrincipal(analyse.problemePrincipal, niveauAxePrincipal(analyse)), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            SectionDeroulante(stringResource(R.string.kicker_ce_qui_passe)) {
                if (profilCalme) {
                    // Situation stable : un seul texte, sans « Faut-il s'inquiéter ? » ni raison générique
                    Text(analyse.explicationPrincipale, textAlign = TextAlign.Center)
                    if (explicationGenerique) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(tr("$nom semble évoluer dans un équilibre global satisfaisant.", "$nom seems to be in a good overall balance.", "$nom scheint sich in einem insgesamt guten Gleichgewicht zu befinden.", "$nom parece encontrarse en un buen equilibrio general."), textAlign = TextAlign.Center)
                    }
                } else {
                    // Chaque partie dans son propre encart, pour aérer
                    EncartDoux {
                        KickerDoux(stringResource(R.string.kicker_niveau_situation)); Spacer(modifier = Modifier.height(10.dp))
                        AccentChip(texteNiveauSituation(analyse.niveauSituation)); Spacer(modifier = Modifier.height(12.dp))
                        Text(analyse.messageSituation, textAlign = TextAlign.Center)
                        if (analyse.niveauSituation != NiveauSituation.STABLE && analyse.raisonSituation.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(analyse.raisonSituation, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    EncartDoux {
                        KickerDoux(stringResource(R.string.kicker_inquieter)); Spacer(modifier = Modifier.height(10.dp))
                        Text(texteVigilance(analyse.vigilance, nomChat), textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    EncartDoux {
                        KickerDoux(stringResource(R.string.kicker_ce_qui_passe)); Spacer(modifier = Modifier.height(10.dp))
                        Text(analyse.explicationPrincipale, textAlign = TextAlign.Center)
                    }
                    if (analyse.originesPossibles.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        EncartDoux {
                            KickerDoux(stringResource(R.string.kicker_origines)); Spacer(modifier = Modifier.height(10.dp))
                            Text(analyse.originesPossibles, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
            SectionDeroulante(stringResource(R.string.kicker_3_jours)) {
                if (profilCalme) {
                    Text(tr("Rien de particulier ne ressort de ce bilan : il n'y a pas de travail spécifique à mettre en place.", "Nothing in particular stands out in this assessment: there is no specific work to put in place.", "In dieser Einschätzung fällt nichts Besonderes auf: Es ist keine gezielte Arbeit nötig.", "Nada en particular destaca en esta evaluación: no hay ningún trabajo específico que poner en marcha."), style = styleDoux(), textAlign = TextAlign.Center, color = marronTexte())
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(tr("Continuez simplement à prendre soin de son quotidien :", "Simply keep taking care of everyday life:", "Achten Sie einfach weiterhin auf einen guten Alltag:", "Simplemente sigue cuidando su día a día:"), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))
                    listOf(
                        tr("Garder des repères stables : horaires des repas, coins de repos, bac propre.", "Keep a stable routine: regular meal times, resting spots, a clean litter box.", "Feste Gewohnheiten beibehalten: Fütterungszeiten, Ruheplätze, ein sauberes Katzenklo.", "Mantener rutinas estables: horarios de comida, lugares de descanso, arenero limpio."),
                        tr("Lui offrir chaque jour des moments de jeu et d'exploration : jeux de chasse, hauteurs, cachettes.", "Offer play and exploration every day: hunting games, high spots, hiding places.", "Ihr täglich Spiel und Erkundung bieten: Jagdspiele, erhöhte Plätze, Verstecke.", "Ofrecerle cada día momentos de juego y exploración: juegos de caza, alturas, escondites."),
                        tr("Préserver des moments de calme et de complicité.", "Protect moments of calm and closeness.", "Momente der Ruhe und Verbundenheit pflegen.", "Preservar momentos de calma y complicidad.")
                    ).forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(8.dp)) }
                    Spacer(modifier = Modifier.height(8.dp))
                    EncartDoux {
                        TitreEncart(stringResource(R.string.subsection_a_observer), Icons.Rounded.Visibility); Spacer(modifier = Modifier.height(8.dp))
                        Text(tr("Tout changement inhabituel de comportement, d'appétit, de sommeil ou de propreté. Dans ce cas, n'hésitez pas à refaire le bilan.", "Any unusual change in behavior, appetite, sleep or litter habits. If that happens, feel free to take the assessment again.", "Jede ungewöhnliche Veränderung im Verhalten, beim Appetit, beim Schlaf oder bei der Sauberkeit. In diesem Fall die Einschätzung gern erneut durchführen.", "Cualquier cambio inusual de comportamiento, apetito, sueño o uso del arenero. En ese caso, no dudes en repetir la evaluación."), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
                    }
                } else {
                    AccentChip(stringResource(R.string.highlight_chip)); Spacer(modifier = Modifier.height(12.dp))
                    Text(analyse.conseilPrincipal, style = styleDoux(), textAlign = TextAlign.Center, color = marronTexte()); Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(R.string.highlight_footer), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(20.dp))
                    EncartDoux {
                        TitreEncart(stringResource(R.string.subsection_a_faire), Icons.Rounded.CheckCircle); Spacer(modifier = Modifier.height(8.dp))
                        analyse.planAction.aFaire.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(6.dp)) }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    EncartDoux {
                        TitreEncart(stringResource(R.string.subsection_a_eviter), Icons.Rounded.Block); Spacer(modifier = Modifier.height(8.dp))
                        analyse.planAction.aEviter.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(6.dp)) }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    EncartDoux {
                        TitreEncart(stringResource(R.string.subsection_a_observer), Icons.Rounded.Visibility); Spacer(modifier = Modifier.height(8.dp))
                        analyse.planAction.aObserver.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(6.dp)) }
                    }
                }
            }
            if (analyse.conseilsPratiques.isNotEmpty()) {
                SectionDeroulante(stringResource(R.string.kicker_conseils)) { analyse.conseilsPratiques.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(8.dp)) } }
            }
            SectionDeroulante("${stringResource(R.string.kicker_aller_plus_loin)} $nom") {
                FichesRecommandeesCard(analyse = analyse, nomChat = nom, onOpenFiche = onOpenFiche, onOpenAlimentation = onOpenAlimentation, bare = true)
                Spacer(modifier = Modifier.height(18.dp))
                EditorialKicker(stringResource(R.string.kicker_le_livre), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.texte_livre), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(14.dp))
                PrimaryGlowButton(
                    text = stringResource(R.string.btn_voir_livres),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tr("https://comprendre-mon-chat.carrd.co", "https://understanding-my-cat.carrd.co", "https://meine-katze-verstehen.carrd.co", "https://comprender-a-mi-gato.carrd.co")))
                        context.startActivity(intent)
                    },
                    leading = { Icon(Icons.Rounded.MenuBook, contentDescription = null, tint = Color.White) }
                )
            }

            // --- Toujours visible en bas
            if (analyse.messageAide != null || analyse.aDejaMordu) {
                PremiumCard(centered = true) {
                    EditorialKicker(stringResource(R.string.kicker_demander_aide), centered = true)
                    Spacer(modifier = Modifier.height(10.dp))
                    if (analyse.aDejaMordu) {
                        Text(stringResource(R.string.texte_morsure_signale), textAlign = TextAlign.Center, color = PremiumPalette.PrioriteModere, fontWeight = FontWeight.SemiBold)
                    } else {
                        analyse.messageAide?.let { Text(it, textAlign = TextAlign.Center, color = PremiumPalette.PrioriteUrgente, fontWeight = FontWeight.SemiBold) }
                    }
                }
            }
            EncartMarron {
                Text(
                    tr("Chaque chat est unique. Ce bilan est indicatif : il donne des repères pour $nom, mais l'observation du quotidien reste essentielle. Il ne remplace ni un vétérinaire, ni un professionnel du comportement.",
                        "Every cat is unique. This assessment is indicative: it offers pointers for $nom, but daily observation remains essential. It does not replace a vet or a behavior professional.",
                        "Jede Katze ist einzigartig. Diese Einschätzung ist nur ein Anhaltspunkt: Sie gibt Orientierung für $nom, doch die Beobachtung im Alltag bleibt unerlässlich. Sie ersetzt weder einen Tierarzt noch eine Fachperson für Verhalten.",
                        "Cada gato es único. Esta evaluación es orientativa: ofrece pautas para $nom, pero la observación del día a día sigue siendo esencial. No sustituye a un veterinario ni a un especialista en comportamiento animal."),
                    style = styleDoux(), textAlign = TextAlign.Center, color = Beige
                )
            }
            ConsultationCard(analyse.lieuResidence)
            ActionButtonsGrid(onShare = onShare, onCopy = onCopy, onExportPdf = onExportPdf, onRecommencer = onRecommencer)

            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.HorizontalDivider(color = PremiumPalette.Border, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 40.dp))
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onRecommencer, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(6.dp))
                Text(stringResource(R.string.btn_recommencer), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ═══════════════════════════════════════════════════════════
// CARTE CONSULTATION PERSONNALISÉE (FR uniquement)
// ═══════════════════════════════════════════════════════════

@Composable
fun ConsultationCard(lieuResidence: Int? = null) {
    if (!showConsultation()) return
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val isDark = isSystemInDarkTheme()
    val backgroundBrush = if (isDark)
        Brush.verticalGradient(listOf(Color(0xFF2E2018), Color(0xFF231B14)))
    else
        Brush.verticalGradient(listOf(Color(0xFFF5EBE0), Color(0xFFEEE0D2)))
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF5A4035) else Color(0xFFD4B8A8))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(backgroundBrush).padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Une consultation avec moi ?", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center, color = marronTexte())
            Spacer(modifier = Modifier.height(10.dp))
            val lieu = if (lieuResidence == 0) "en visio ou à domicile en Essonne" else "en visioconférence"
            Text("Une heure ensemble, $lieu, pour comprendre votre chat et construire un plan adapté.", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                if (lieuResidence == 0) "Visio\u00A0: 50\u00A0€  •  À\u00A0domicile\u00A0: 70\u00A0€" else "Visio\u00A0: 50\u00A0€ / 1\u00A0h",
                style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryGlowButton(
                text = strConsultationBouton(lieuResidence),
                onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(TELEPHONE_URI))) },
                leading = { Icon(Icons.Rounded.Phone, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                "Ne remplace pas une consultation vétérinaire : en cas de changement brutal, de douleur ou de symptôme physique, consultez d'abord votre vétérinaire.",
                style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                LienDiscret("CGV") { uriHandler.openUri(CGV_URL) }
                Text("  •  ", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                LienDiscret("Mon site") { uriHandler.openUri(WEBSITE_URL) }
                Text("  •  ", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                LienDiscret("Facebook") { uriHandler.openUri("https://www.facebook.com/comprendremonanimal") }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════
// CARTE ORIGINES POSSIBLES
// ═══════════════════════════════════════════════════════════

@Composable
fun OriginesPossiblesCard(origines: String) {
    val backgroundBrush = if (isSystemInDarkTheme())
        Brush.verticalGradient(listOf(Color(0xFF2E2018), Color(0xFF231B14)))
    else
        Brush.verticalGradient(listOf(Color(0xFFF5EBE0), Color(0xFFEEE0D2)))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF5A4035) else Color(0xFFD4B8A8))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(backgroundBrush).padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccentChip(stringResource(R.string.chip_comprendre_agir))
            Spacer(modifier = Modifier.height(14.dp))
            EditorialKicker(stringResource(R.string.kicker_origines), centered = true)
            Spacer(modifier = Modifier.height(12.dp))
            Text(origines, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun QuatreAxesGrid(analyse: ResultatAnalyse) {
    val axes = listOf(
        Triple(libelleAxe(Axe.SECURITE), analyse.niveauPeur, analyse.peur),
        Triple(libelleAxe(Axe.LIEN), analyse.niveauAttachement, analyse.attachement),
        Triple(libelleAxe(Axe.INSTINCTS), analyse.niveauImpulsivite, analyse.impulsivite),
        Triple(libelleAxe(Axe.COHABITATION), analyse.niveauReactivite, analyse.reactivite)
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        axes.chunked(2).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { (label, niveau, score) ->
                    val animated by animateFloatAsState((score / 100f).coerceIn(0f, 1f), label = "axe_$label")
                    Column(
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp))
                            .background(if (isSystemInDarkTheme()) Color(0xFF231B17) else PremiumPalette.PaperSoft)
                            .border(1.dp, PremiumPalette.Border, RoundedCornerShape(16.dp))
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                        Text(QuestionnaireEngine.libelleNiveauAxe(niveau), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = marronTexte(), textAlign = TextAlign.Center)
                        Box(modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(999.dp)).background(PremiumPalette.PrimarySoft.copy(alpha = 0.2f))) {
                            Box(modifier = Modifier.fillMaxWidth(animated).height(5.dp).clip(RoundedCornerShape(999.dp)).background(PremiumPalette.PrimarySoft))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FichesRecommandeesCard(
    analyse: ResultatAnalyse,
    nomChat: String,
    onOpenFiche: (String) -> Unit,
    onOpenAlimentation: () -> Unit,
    bare: Boolean = false
) {
    val context = LocalContext.current
    val fichesBehavior = recommanderFichesComportement(analyse, context)
    val fichesAlim = recommanderFichesAlimentation(analyse, context)
    if (fichesBehavior.isEmpty() && fichesAlim.isEmpty()) return

    val corps: @Composable ColumnScope.() -> Unit = {

        if (fichesBehavior.isNotEmpty()) {
            Text(stringResource(R.string.label_fiches_comportementales), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = PremiumPalette.PrimarySoft)
            Spacer(modifier = Modifier.height(8.dp))
            fichesBehavior.forEach { (ficheId, titre) ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onOpenFiche(ficheId) }
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSystemInDarkTheme()) Color(0xFF3A2B23) else PremiumPalette.PaperWarm)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(titre, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                    Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = PremiumPalette.PrimarySoft, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }

        if (fichesAlim.isNotEmpty()) {
            if (fichesBehavior.isNotEmpty()) Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.label_reperes_alimentation), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = PremiumPalette.PrimarySoft)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onOpenAlimentation() }
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSystemInDarkTheme()) Color(0xFF3A2B23) else PremiumPalette.PaperWarm)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    fichesAlim.forEach { titre ->
                        Text(titre, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        if (titre != fichesAlim.last()) Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = PremiumPalette.PrimarySoft, modifier = Modifier.size(18.dp))
            }
        }
    }
    if (bare) Column(modifier = Modifier.fillMaxWidth()) { corps() }
    else PremiumCard(centered = false) {
        EditorialKicker("${stringResource(R.string.kicker_aller_plus_loin)} $nomChat")
        Spacer(modifier = Modifier.height(14.dp))
        corps()
    }
}

fun recommanderFichesComportement(analyse: ResultatAnalyse, context: android.content.Context): List<Pair<String, String>> {
    val maxAxe = maxOf(analyse.peur, analyse.attachement, analyse.impulsivite, analyse.reactivite)
    if (maxAxe < 30) return emptyList()
    return when (analyse.problemePrincipal) {
        Axe.SECURITE -> listOf(
            "oreilles-arriere" to context.getString(R.string.comp_oreilles_arriere_titre),
            "queue-fouettante" to context.getString(R.string.comp_queue_fouettante_titre),
            "ventre-expose" to context.getString(R.string.comp_ventre_expose_titre)
        )
        Axe.LIEN -> listOf(
            "destruction-absence" to context.getString(R.string.comp_destruction_titre),
            "vocalisation-excessive" to context.getString(R.string.comp_vocalisation_titre),
            "petrir" to context.getString(R.string.comp_petrir_titre)
        )
        Axe.INSTINCTS -> listOf(
            "hyperactivite-nocturne" to context.getString(R.string.comp_hyperactivite_titre),
            "chatter" to context.getString(R.string.comp_chatter_titre),
            "griffage" to context.getString(R.string.comp_griffage_titre)
        )
        Axe.COHABITATION -> listOf(
            "marquage-urinaire" to context.getString(R.string.comp_marquage_urinaire_titre),
            "oreilles-arriere" to context.getString(R.string.comp_oreilles_arriere_titre),
            "queue-fouettante" to context.getString(R.string.comp_queue_fouettante_titre)
        )
    }
}

fun recommanderFichesAlimentation(analyse: ResultatAnalyse, context: android.content.Context): List<String> {
    return if (analyse.contexte.physique >= 2) {
        listOf(
            context.getString(R.string.alim_ingestion1_titre),
            context.getString(R.string.alim_ingestion2_titre)
        )
    } else {
        listOf(
            context.getString(R.string.alim_dangereux2_titre),
            context.getString(R.string.alim_digestion3_titre)
        )
    }
}

@Composable
fun ActionButtonsGrid(onShare: () -> Unit, onCopy: () -> Unit, onExportPdf: () -> Unit, onRecommencer: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ActionButton(text = stringResource(R.string.btn_partager), icon = Icons.Rounded.Share, primary = true, modifier = Modifier.weight(1f), onClick = onShare)
            ActionButton(text = stringResource(R.string.btn_export_pdf), icon = Icons.Rounded.PictureAsPdf, primary = true, modifier = Modifier.weight(1f), onClick = onExportPdf)
        }
        ActionButton(text = stringResource(R.string.btn_copier_resume), icon = Icons.Rounded.ContentCopy, primary = false, modifier = Modifier.fillMaxWidth(), onClick = onCopy)
    }
}

@Composable
fun ActionButton(text: String, icon: ImageVector, primary: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = modifier.height(52.dp), shape = RoundedCornerShape(18.dp),
        colors = if (primary) ButtonDefaults.buttonColors(containerColor = PremiumPalette.Primary, contentColor = Color.White)
        else ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF342923) else Color(0xFFF0E5DC), contentColor = MaterialTheme.colorScheme.onSurface)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

fun couleurPriorite(p: PrioriteAction) = when (p) {
    PrioriteAction.FAIBLE -> PremiumPalette.PrioriteFaible
    PrioriteAction.MODEREE -> PremiumPalette.PrioriteModere
    PrioriteAction.ELEVEE -> PremiumPalette.PrioriteElevee
    PrioriteAction.URGENTE -> PremiumPalette.PrioriteUrgente
}

fun couleurFondPriorite(p: PrioriteAction) = when (p) {
    PrioriteAction.FAIBLE -> PremiumPalette.PrioriteFaibleBg
    PrioriteAction.MODEREE -> PremiumPalette.PrioriteModereBg
    PrioriteAction.ELEVEE -> PremiumPalette.PrioriteEleveeBg
    PrioriteAction.URGENTE -> PremiumPalette.PrioriteUrgenteBg
}

@Composable
fun RaceCard(raceCategorie: String?, racePrecise: String?, bare: Boolean = false) {
    val categorie = categoriesRaces.firstOrNull { it.nom == raceCategorie }
    val nomAffiche = when {
        !racePrecise.isNullOrBlank() -> racePrecise
        !raceCategorie.isNullOrBlank() -> raceCategorie
        else -> return
    }
    val corps: @Composable ColumnScope.() -> Unit = {
        Text(nomAffiche, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = marronTexte(), textAlign = TextAlign.Center)
        val predispositions = categorie?.predispositions ?: emptyList()
        if (predispositions.isNotEmpty()) {
            val garderMajuscules = appLang() == AppLang.DE
            val items = predispositions.map { (if (garderMajuscules) it else it.replaceFirstChar { c -> c.lowercase() }).trimEnd('.') }
            val liste = if (items.size == 1) items[0] else items.dropLast(1).joinToString(", ") + tr(" et ", " and ", " und ", " y ") + items.last()
            Spacer(modifier = Modifier.height(14.dp))
            Text(stringResource(R.string.label_predispositions) + tr(" : ", ": ", ": ", ": ") + liste + ".", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
        if (!categorie?.nuanceAnalyse.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(14.dp))
            EncartDoux { Text(categorie!!.nuanceAnalyse, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center) }
        }
    }
    if (bare) Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) { corps() }
    else PremiumCard(centered = true) { EditorialKicker(stringResource(R.string.kicker_profil_race), centered = true); Spacer(modifier = Modifier.height(10.dp)); corps() }
}

@Composable
fun HighlightAdviceCard(title: String, advice: String) {
    val backgroundBrush = if (isSystemInDarkTheme())
        Brush.verticalGradient(listOf(Color(0xFF3A2A23), Color(0xFF2A1F1A)))
    else
        Brush.verticalGradient(listOf(Color(0xFFF3E4DA), Color(0xFFECD8CB)))
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF6A4D41) else Color(0xFFD8B9A7))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(backgroundBrush).padding(horizontal = 24.dp, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccentChip(title)
            Spacer(modifier = Modifier.height(14.dp))
            Text(advice, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(14.dp))
            Text(stringResource(R.string.highlight_footer), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SubsectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
}

@Composable
fun Bullet(text: String, centered: Boolean = false) {
    if (centered) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.padding(top = 8.dp).size(7.dp).background(PremiumPalette.PrimarySoft, CircleShape))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text, modifier = Modifier.widthIn(max = 560.dp), color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
        }
    } else {
        Row(verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.padding(top = 8.dp).size(7.dp).background(PremiumPalette.PrimarySoft, CircleShape))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun AlerteMorsureCard(nomChat: String) {
    val isDark = isSystemInDarkTheme()
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF3D1209) else Color(0xFFFFF0EC)),
        border = BorderStroke(2.dp, PremiumPalette.MorsureBorder)
    ) {
        Column(modifier = Modifier.padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Rounded.Warning, contentDescription = null, tint = PremiumPalette.PrioriteUrgente, modifier = Modifier.size(22.dp))
                Text(stringResource(R.string.alerte_morsure_titre), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold, color = PremiumPalette.PrioriteUrgente, textAlign = TextAlign.Center)
                Icon(Icons.Rounded.Warning, contentDescription = null, tint = PremiumPalette.PrioriteUrgente, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(stringResource(R.string.alerte_morsure_corps, nomChat), style = MaterialTheme.typography.titleMedium, color = PremiumPalette.PrioriteUrgente, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(stringResource(R.string.alerte_morsure_conseil), color = if (isDark) Color(0xFFFFCFC5) else Color(0xFF5C1A0A), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun DictionnaireInfoScreen(modifier: Modifier = Modifier, onOpenFiche: (String) -> Unit) {
    val fiches = remember { comportementEntries() }
    var recherche by remember { mutableStateOf("") }
    val context = LocalContext.current
    val fichesFiltrees = remember(recherche) {
        if (recherche.isBlank()) fiches
        else fiches.filter {
            context.getString(it.titreKey).contains(recherche, ignoreCase = true) ||
                    context.getString(it.resumeKey).contains(recherche, ignoreCase = true)
        }
    }

    EditorialContainer(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp),
        maxWidth = 780
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            EncartMarron {
                KickerBeige(stringResource(R.string.kicker_dictionnaire))
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.titre_dictionnaire), style = styleDoux().copy(fontSize = 21.sp, lineHeight = 29.sp), color = Beige, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = recherche, onValueChange = { recherche = it },
                    placeholder = { Text(stringResource(R.string.placeholder_recherche)) }, singleLine = true,
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Beige, unfocusedBorderColor = Beige.copy(alpha = 0.5f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = PremiumPalette.Primary, focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
            if (fichesFiltrees.isEmpty()) {
                PremiumCard(centered = true) {
                    Text(stringResource(R.string.aucune_fiche, recherche), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                fichesFiltrees.forEach { fiche -> ComportementListItem(entry = fiche, onClick = { onOpenFiche(fiche.id) }) }
            }
            EncartDoux {
                KickerDoux(stringResource(R.string.kicker_important)); Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.texte_important_dictionnaire), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun DictionnaireDetailScreen(modifier: Modifier = Modifier, ficheId: String) {
    val fiche = remember(ficheId) { getComportementEntryById(ficheId) }
    EditorialContainer(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp),
        maxWidth = 780
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if (fiche == null) {
                PremiumCard(centered = true) {
                    Text(stringResource(R.string.fiche_introuvable), style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                }
            } else {
                EncartMarron {
                    KickerBeige(stringResource(R.string.kicker_fiche_comportementale)); Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(fiche.titreKey), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, color = Beige, textAlign = TextAlign.Center); Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(fiche.resumeKey), style = styleDoux(), color = Beige, textAlign = TextAlign.Center)
                }
                PremiumCard {
                    EditorialKicker(stringResource(R.string.kicker_explication))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(fiche.explicationKey), style = MaterialTheme.typography.bodyLarge)
                }
                PremiumCard {
                    TitreEncart(stringResource(R.string.kicker_que_faire), Icons.Rounded.CheckCircle); Spacer(modifier = Modifier.height(10.dp)); Bullet(stringResource(fiche.queFaireKey))
                    Spacer(modifier = Modifier.height(20.dp))
                    TitreEncart(stringResource(R.string.kicker_a_eviter_fiche), Icons.Rounded.Block); Spacer(modifier = Modifier.height(10.dp)); Bullet(stringResource(fiche.aEviterKey))
                }
                EncartDoux {
                    KickerDoux(tr("Rappel", "Reminder", "Zur Erinnerung", "Recuerda")); Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(R.string.texte_rappel_fiche), style = styleDoux().copy(fontSize = 16.sp, lineHeight = 23.sp), textAlign = TextAlign.Center, color = marronTexte())
                }
            }
        }
    }
}

@Composable
fun ComportementListItem(entry: ComportementEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF231B17) else PremiumPalette.PaperSoft),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF56433B) else PremiumPalette.Border)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(entry.titreKey), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = marronTexte())
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(entry.resumeKey), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = PremiumPalette.PrimarySoft)
        }
    }
}

@Composable
fun DictionnaireScreen(modifier: Modifier = Modifier) {
    val entries = remember { dictionnaireEntries() }
    val selectedCategoryState = remember { mutableStateOf<DictionnaireCategorie?>(null) }
    val selectedEntryState = remember { mutableStateOf<DictionnaireEntry?>(null) }
    val categories = remember { DictionnaireCategorie.entries.toList() }

    EditorialContainer(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp),
        maxWidth = 780
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            when {
                selectedEntryState.value != null -> {
                    val entry = selectedEntryState.value!!
                    PremiumCard {
                        AccentChip(entry.categorie.titre()); Spacer(modifier = Modifier.height(14.dp))
                        Text(stringResource(entry.titreKey), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, color = marronTexte()); Spacer(modifier = Modifier.height(12.dp))
                        Text(stringResource(entry.contenuKey), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant); Spacer(modifier = Modifier.height(18.dp))
                        SecondaryPremiumButton(stringResource(R.string.btn_retour_categorie), onClick = { selectedEntryState.value = null }, leading = { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null) })
                    }
                    EncartDoux {
                        KickerDoux(tr("Rappel", "Reminder", "Zur Erinnerung", "Recuerda")); Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.texte_rappel_alimentation), style = styleDoux().copy(fontSize = 16.sp, lineHeight = 23.sp), textAlign = TextAlign.Center, color = marronTexte())
                    }
                }
                selectedCategoryState.value != null -> {
                    val categorie = selectedCategoryState.value!!
                    val items = entries.filter { it.categorie == categorie }
                    EncartMarron {
                        KickerBeige(stringResource(R.string.kicker_alimentation_detail)); Spacer(modifier = Modifier.height(10.dp))
                        Text(categorie.titre(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, color = Beige, textAlign = TextAlign.Center)
                    }
                    items.forEach { entry -> DictionnaireListItem(entry = entry, onClick = { selectedEntryState.value = entry }) }
                    SecondaryPremiumButton(stringResource(R.string.btn_retour_rubriques), onClick = { selectedCategoryState.value = null }, leading = { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null) })
                }
                else -> {
                    EncartMarron {
                        KickerBeige(stringResource(R.string.kicker_alimentation)); Spacer(modifier = Modifier.height(10.dp))
                        Text(stringResource(R.string.texte_alimentation_intro), style = styleDoux(), color = Beige, textAlign = TextAlign.Center)
                    }
                    PremiumCard {
                        TitreEncart(stringResource(R.string.kicker_a_retenir_alimentation), Icons.Rounded.Star); Spacer(modifier = Modifier.height(12.dp))
                        BulletDoux(stringResource(R.string.bullet_carnivore)); Spacer(modifier = Modifier.height(10.dp))
                        BulletDoux(stringResource(R.string.bullet_plantes_toxiques)); Spacer(modifier = Modifier.height(10.dp))
                        BulletDoux(stringResource(R.string.bullet_ingestion_suspecte))
                    }
                    categories.forEach { categorie -> DictionnaireCategoryButton(categorie = categorie, onClick = { selectedCategoryState.value = categorie }) }
                    EncartDoux {
                        KickerDoux(stringResource(R.string.kicker_important)); Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.texte_important_alimentation), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun DictionnaireCategoryButton(categorie: DictionnaireCategorie, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF231B17) else PremiumPalette.PaperSoft),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF56433B) else PremiumPalette.Border)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(categorie.titre(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = marronTexte())
                Spacer(modifier = Modifier.height(6.dp))
                Text(when (categorie) {
                    DictionnaireCategorie.DANGEREUX -> stringResource(R.string.cat_dangereux_desc)
                    DictionnaireCategorie.AUTORISES -> stringResource(R.string.cat_autorises_desc)
                    DictionnaireCategorie.INGESTION -> stringResource(R.string.cat_ingestion_desc)
                    DictionnaireCategorie.DIGESTION -> stringResource(R.string.cat_digestion_desc)
                }, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = PremiumPalette.PrimarySoft)
        }
    }
}

@Composable
fun DictionnaireListItem(entry: DictionnaireEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF231B17) else PremiumPalette.PaperSoft),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF56433B) else PremiumPalette.Border)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(entry.titreKey), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = marronTexte())
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(entry.resumeKey), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = PremiumPalette.PrimarySoft)
        }
    }
}

@Composable
fun ParametresScreen(modifier: Modifier = Modifier, onRevoirOnboarding: () -> Unit) {
    val context = LocalContext.current
    val version = remember {
        try { context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0" }
        catch (e: Exception) { "1.0" }
    }

    EditorialContainer(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_parametres), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.titre_parametres), style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(6.dp))
                AccentChip(stringResource(R.string.label_version, version))
            }

            PremiumCard {
                EditorialKicker(stringResource(R.string.kicker_tutoriel))
                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(R.string.texte_tutoriel), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(14.dp))
                SecondaryPremiumButton(stringResource(R.string.btn_revoir_intro), onClick = onRevoirOnboarding, leading = { Icon(Icons.Rounded.AutoStories, contentDescription = null) })
            }

            PremiumCard {
                EditorialKicker(stringResource(R.string.kicker_confidentialite))
                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(R.string.texte_confidentialite), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(14.dp))
                SecondaryPremiumButton(stringResource(R.string.btn_politique_confidentialite), onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://laurenaharoy-ctrl.github.io/comprendremonchat/confidentialite.html"))
                    context.startActivity(intent)
                }, leading = { Icon(Icons.Rounded.MenuBook, contentDescription = null) })
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_a_propos), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.texte_a_propos), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            }
        }
    }
}