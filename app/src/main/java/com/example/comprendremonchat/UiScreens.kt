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

// ─── Thème couleurs ─────────────────────────────────────────

private val LightColors = lightColorScheme(
    primary = Color(0xFF8E4A2D),
    onPrimary = Color.White,
    background = Color(0xFFF4EFE8),
    surface = Color(0xFFF8F4EE),
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
    val PaperSoft = Color(0xFFF8F4EE)
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
        Brush.verticalGradient(listOf(Color(0xFFF8F4EE), Color(0xFFF4EFE8), Color(0xFFF1E7DE)))
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
        Text(text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = PremiumPalette.Primary)
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
        modifier = modifier.fillMaxWidth().height(58.dp),
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
            Text(text)
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
    EditorialContainer(
        modifier = modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccueilIllustrationCard()
            PrimaryGlowButton(
                text = stringResource(R.string.btn_demarrer),
                onClick = onCommencer,
                leading = { Icon(Icons.Rounded.Pets, contentDescription = null, tint = Color.White) }
            )
            SecondaryPremiumButton(
                text = stringResource(R.string.btn_dictionnaire),
                onClick = onDictionnaire,
                leading = { Icon(Icons.Rounded.MenuBook, contentDescription = null) }
            )
            SecondaryPremiumButton(
                text = stringResource(R.string.btn_alimentation),
                onClick = onAlimentation,
                leading = { Icon(Icons.Rounded.Star, contentDescription = null) }
            )
            if (hasSavedProgress) {
                SecondaryPremiumButton(
                    text = stringResource(R.string.btn_reprendre),
                    onClick = onReprendre,
                    leading = { Icon(Icons.Rounded.AutoStories, contentDescription = null) }
                )
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
            PremiumCard {
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
                else if (isSystemInDarkTheme()) Color(0xFF231B17) else Color(0xFFF8F4EE),
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
    EditorialContainer(
        modifier = modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 10.dp),
        maxWidth = 780
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_votre_bilan), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text("${stringResource(R.string.titre_bilan_pour)} ${nomChatAffiche(nomChat)}", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                AccentChip(analyse.profil.profilType)
            }

            if (!analyse.raceCategorie.isNullOrBlank()) {
                RaceCard(raceCategorie = analyse.raceCategorie, racePrecise = analyse.racePrecise)
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_lecture_principale), centered = true)
                Spacer(modifier = Modifier.height(8.dp))
                Text(analyse.hypothesePrincipale, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(12.dp))
                val couleur = couleurPriorite(analyse.prioriteAction)
                val couleurFond = couleurFondPriorite(analyse.prioriteAction)
                Box(modifier = Modifier.clip(RoundedCornerShape(999.dp)).background(couleurFond).padding(horizontal = 14.dp, vertical = 8.dp)) {
                    Text("${stringResource(R.string.label_priorite)}${textePrioriteAction(analyse.prioriteAction)}", color = couleur, fontWeight = FontWeight.SemiBold)
                }
            }

            PremiumCard(centered = true) {
                EditorialKicker("${stringResource(R.string.kicker_ressent)} ${nomChatAffiche(nomChat)}", centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(analyse.profil.phraseHumaine, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(14.dp))
                Text(resumeEmotionnel(analyse.problemePrincipal), style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, color = PremiumPalette.Primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(intentionChat(analyse.problemePrincipal), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(6.dp))
                Text(besoinPrincipal(analyse.problemePrincipal), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_coup_oeil), centered = true)
                Spacer(modifier = Modifier.height(14.dp))
                QuatreAxesGrid(analyse = analyse)
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_niveau_situation), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                AccentChip(texteNiveauSituation(analyse.niveauSituation))
                Spacer(modifier = Modifier.height(14.dp))
                Text(analyse.messageSituation, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Text(analyse.raisonSituation, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_inquieter), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(texteVigilance(analyse.vigilance, nomChat), textAlign = TextAlign.Center)
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_ce_qui_passe), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(analyse.explicationPrincipale, textAlign = TextAlign.Center)
            }

            HighlightAdviceCard(title = stringResource(R.string.highlight_chip), advice = analyse.conseilPrincipal)

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_3_jours), centered = true)
                Spacer(modifier = Modifier.height(14.dp))
                SubsectionTitle(stringResource(R.string.subsection_a_faire)); Spacer(modifier = Modifier.height(8.dp))
                analyse.planAction.aFaire.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(8.dp)) }
                Spacer(modifier = Modifier.height(8.dp))
                SubsectionTitle(stringResource(R.string.subsection_a_eviter)); Spacer(modifier = Modifier.height(8.dp))
                analyse.planAction.aEviter.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(8.dp)) }
                Spacer(modifier = Modifier.height(8.dp))
                SubsectionTitle(stringResource(R.string.subsection_a_observer)); Spacer(modifier = Modifier.height(8.dp))
                analyse.planAction.aObserver.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(8.dp)) }
            }

            if (analyse.conseilsPratiques.isNotEmpty()) {
                PremiumCard(centered = true) {
                    EditorialKicker(stringResource(R.string.kicker_conseils), centered = true)
                    Spacer(modifier = Modifier.height(12.dp))
                    analyse.conseilsPratiques.forEach { Bullet(it, centered = true); Spacer(modifier = Modifier.height(8.dp)) }
                }
            }

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

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_important), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.texte_important_bilan), textAlign = TextAlign.Center)
            }

            FichesRecommandeesCard(analyse = analyse, nomChat = nomChatAffiche(nomChat), onOpenFiche = onOpenFiche, onOpenAlimentation = onOpenAlimentation)

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_a_retenir), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(phraseFin(nomChat), textAlign = TextAlign.Center)
            }

            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_le_livre), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.texte_livre), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(14.dp))
                PrimaryGlowButton(
                    text = stringResource(R.string.btn_voir_livres),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(if (isEnglish()) "https://understanding-my-cat.carrd.co" else "https://comprendre-mon-chat.carrd.co"))
                        context.startActivity(intent)
                    },
                    leading = { Icon(Icons.Rounded.MenuBook, contentDescription = null, tint = Color.White) }
                )
            }

            ConsultationCard()

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
fun ConsultationCard() {
    if (!showConsultation()) return
    val context = LocalContext.current
    val backgroundBrush = if (isSystemInDarkTheme())
        Brush.verticalGradient(listOf(Color(0xFF2E2018), Color(0xFF231B14)))
    else
        Brush.verticalGradient(listOf(Color(0xFFF5EBE0), Color(0xFFEEE0D2)))
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color(0xFF5A4035) else Color(0xFFD4B8A8))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(backgroundBrush).padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val uriHandler = LocalUriHandler.current
            EditorialKicker(strConsultationTitre(), centered = true)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                strConsultationSousTitre(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = PremiumPalette.Primary
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(strConsultationDescription(), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                    .background(if (isSystemInDarkTheme()) Color(0xFF2A1F1A) else Color(0xFFF4EDE6))
                    .padding(14.dp)
            ) {
                Text(
                    strConsultationDisclaimer(),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                strConsultationFormule30(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            PrimaryGlowButton(
                text = strConsultationBouton(),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(CONSULTATION_BOOKING_URL))
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                strConsultationFormule60(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            PrimaryGlowButton(
                text = strConsultationBouton(),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(CONSULTATION_BOOKING_URL_1H))
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = strConsultationCGV(),
                color = PremiumPalette.Primary,
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { uriHandler.openUri(CGV_URL) }
                    .padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = strConsultationSite(),
                color = PremiumPalette.Primary,
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { uriHandler.openUri(WEBSITE_URL) }
                    .padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (isEnglish()) "Follow us on Facebook" else "Nous suivre sur Facebook",
                color = Color(0xFF1877F2),
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { uriHandler.openUri("https://www.facebook.com/comprendrenosanimaux/") }
                    .padding(vertical = 8.dp)
            )
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
                        Text(QuestionnaireEngine.libelleNiveauAxe(niveau), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = PremiumPalette.Primary, textAlign = TextAlign.Center)
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
    onOpenAlimentation: () -> Unit
) {
    val context = LocalContext.current
    val fichesBehavior = recommanderFichesComportement(analyse, context)
    val fichesAlim = recommanderFichesAlimentation(analyse, context)
    if (fichesBehavior.isEmpty() && fichesAlim.isEmpty()) return

    PremiumCard(centered = false) {
        EditorialKicker("${stringResource(R.string.kicker_aller_plus_loin)} $nomChat")
        Spacer(modifier = Modifier.height(14.dp))

        if (fichesBehavior.isNotEmpty()) {
            Text(stringResource(R.string.label_fiches_comportementales), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = PremiumPalette.PrimarySoft)
            Spacer(modifier = Modifier.height(8.dp))
            fichesBehavior.forEach { (ficheId, titre) ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onOpenFiche(ficheId) }
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSystemInDarkTheme()) Color(0xFF2A1F1A) else Color(0xFFF4EDE6))
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
                    .background(if (isSystemInDarkTheme()) Color(0xFF2A1F1A) else Color(0xFFF4EDE6))
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
fun RaceCard(raceCategorie: String?, racePrecise: String?) {
    val categorie = categoriesRaces.firstOrNull { it.nom == raceCategorie }
    val nomAffiche = when {
        !racePrecise.isNullOrBlank() -> racePrecise
        !raceCategorie.isNullOrBlank() -> raceCategorie
        else -> return
    }
    PremiumCard(centered = true) {
        EditorialKicker(stringResource(R.string.kicker_profil_race), centered = true)
        Spacer(modifier = Modifier.height(10.dp))
        Text(nomAffiche, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = PremiumPalette.Primary, textAlign = TextAlign.Center)
        if (categorie?.predispositions?.isNotEmpty() == true) {
            Spacer(modifier = Modifier.height(14.dp))
            Text(stringResource(R.string.label_predispositions), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            categorie.predispositions.forEach { pred ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top) {
                    Box(modifier = Modifier.padding(top = 7.dp).size(6.dp).background(PremiumPalette.Accent, CircleShape))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(pred, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center, modifier = Modifier.widthIn(max = 500.dp))
                }
            }
        }
        if (!categorie?.nuanceAnalyse.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(14.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                .background(if (isSystemInDarkTheme()) Color(0xFF2A1F1A) else Color(0xFFF4EDE6)).padding(14.dp)) {
                Text(categorie!!.nuanceAnalyse, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
            }
        }
    }
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
            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_dictionnaire), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.titre_dictionnaire), style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = recherche, onValueChange = { recherche = it },
                    placeholder = { Text(stringResource(R.string.placeholder_recherche)) }, singleLine = true,
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PremiumPalette.Primary, unfocusedBorderColor = MaterialTheme.colorScheme.outline,
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
            PremiumCard(centered = true) {
                EditorialKicker(stringResource(R.string.kicker_important), centered = true)
                Spacer(modifier = Modifier.height(10.dp))
                Text(stringResource(R.string.texte_important_dictionnaire), textAlign = TextAlign.Center)
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
                PremiumCard(centered = true) {
                    EditorialKicker(stringResource(R.string.kicker_fiche_comportementale), centered = true)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(fiche.titreKey), style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(fiche.resumeKey), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                PremiumCard {
                    EditorialKicker(stringResource(R.string.kicker_explication))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(fiche.explicationKey), style = MaterialTheme.typography.bodyLarge)
                }
                PremiumCard {
                    EditorialKicker(stringResource(R.string.kicker_que_faire))
                    Spacer(modifier = Modifier.height(12.dp))
                    Bullet(stringResource(fiche.queFaireKey))
                }
                PremiumCard {
                    EditorialKicker(stringResource(R.string.kicker_a_eviter_fiche))
                    Spacer(modifier = Modifier.height(12.dp))
                    Bullet(stringResource(fiche.aEviterKey))
                }
                PremiumCard(centered = true) {
                    EditorialKicker("Rappel", centered = true)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(R.string.texte_rappel_fiche), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                Text(stringResource(entry.titreKey), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(entry.resumeKey), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                        Text(stringResource(entry.titreKey), style = MaterialTheme.typography.headlineSmall); Spacer(modifier = Modifier.height(12.dp))
                        Text(stringResource(entry.contenuKey), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant); Spacer(modifier = Modifier.height(18.dp))
                        SecondaryPremiumButton(stringResource(R.string.btn_retour_categorie), onClick = { selectedEntryState.value = null }, leading = { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null) })
                    }
                    PremiumCard(centered = true) {
                        EditorialKicker(stringResource(R.string.kicker_important), centered = true); Spacer(modifier = Modifier.height(10.dp))
                        Text(stringResource(R.string.texte_rappel_alimentation), textAlign = TextAlign.Center)
                    }
                }
                selectedCategoryState.value != null -> {
                    val categorie = selectedCategoryState.value!!
                    val items = entries.filter { it.categorie == categorie }
                    PremiumCard(centered = true) {
                        EditorialKicker(stringResource(R.string.kicker_alimentation_detail), centered = true); Spacer(modifier = Modifier.height(10.dp))
                        Text(categorie.titre(), style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
                    }
                    items.forEach { entry -> DictionnaireListItem(entry = entry, onClick = { selectedEntryState.value = entry }) }
                    SecondaryPremiumButton(stringResource(R.string.btn_retour_rubriques), onClick = { selectedCategoryState.value = null }, leading = { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null) })
                }
                else -> {
                    PremiumCard(centered = true) {
                        EditorialKicker(stringResource(R.string.kicker_alimentation), centered = true); Spacer(modifier = Modifier.height(10.dp))
                        Text(stringResource(R.string.texte_alimentation_intro), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    PremiumCard {
                        EditorialKicker(stringResource(R.string.kicker_a_retenir_alimentation)); Spacer(modifier = Modifier.height(12.dp))
                        Bullet(stringResource(R.string.bullet_carnivore)); Spacer(modifier = Modifier.height(8.dp))
                        Bullet(stringResource(R.string.bullet_plantes_toxiques)); Spacer(modifier = Modifier.height(8.dp))
                        Bullet(stringResource(R.string.bullet_ingestion_suspecte))
                    }
                    categories.forEach { categorie -> DictionnaireCategoryButton(categorie = categorie, onClick = { selectedCategoryState.value = categorie }) }
                    PremiumCard(centered = true) {
                        EditorialKicker(stringResource(R.string.kicker_important), centered = true); Spacer(modifier = Modifier.height(10.dp))
                        Text(stringResource(R.string.texte_important_alimentation), textAlign = TextAlign.Center)
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
                Text(categorie.titre(), style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(6.dp))
                Text(when (categorie) {
                    DictionnaireCategorie.DANGEREUX -> stringResource(R.string.cat_dangereux_desc)
                    DictionnaireCategorie.AUTORISES -> stringResource(R.string.cat_autorises_desc)
                    DictionnaireCategorie.INGESTION -> stringResource(R.string.cat_ingestion_desc)
                    DictionnaireCategorie.DIGESTION -> stringResource(R.string.cat_digestion_desc)
                }, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                Text(stringResource(entry.titreKey), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(entry.resumeKey), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://laurenaharoy-ctrl.github.io/comprendremonchien/confidentialite.html"))
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