package com.laurena.comprendremonchat

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Feedback
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.core.content.FileProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "comprendre_mon_chat_state")

sealed interface AppScreen {
    data object Accueil : AppScreen
    data object Onboarding : AppScreen
    data object Questionnaire : AppScreen
    data object Chargement : AppScreen
    data object Resultat : AppScreen
    data object Dictionnaire : AppScreen
    data class DictionnaireDetail(val ficheId: String) : AppScreen
    data object Alimentation : AppScreen
    data object Feedback : AppScreen
    data object Historique : AppScreen
    data class HistoriqueDetail(val bilanId: String) : AppScreen
    data object Parametres : AppScreen
}

fun AppScreen.toStorageValue(): String = when (this) {
    AppScreen.Accueil -> "accueil"
    AppScreen.Onboarding -> "accueil"
    AppScreen.Questionnaire -> "questionnaire"
    AppScreen.Chargement -> "questionnaire"
    AppScreen.Resultat -> "resultat"
    AppScreen.Dictionnaire -> "dictionnaire"
    is AppScreen.DictionnaireDetail -> "dictionnaire_detail:${this.ficheId}"
    AppScreen.Alimentation -> "alimentation"
    AppScreen.Feedback -> "accueil"
    AppScreen.Historique -> "historique"
    is AppScreen.HistoriqueDetail -> "historique"
    AppScreen.Parametres -> "accueil"
}

fun screenFromStorage(value: String): AppScreen = when {
    value == "questionnaire" -> AppScreen.Questionnaire
    value == "resultat" -> AppScreen.Resultat
    value == "dictionnaire" -> AppScreen.Dictionnaire
    value.startsWith("dictionnaire_detail:") -> {
        val ficheId = value.removePrefix("dictionnaire_detail:")
        if (ficheId.isBlank()) AppScreen.Dictionnaire else AppScreen.DictionnaireDetail(ficheId)
    }
    value == "alimentation" -> AppScreen.Alimentation
    value == "historique" -> AppScreen.Historique
    else -> AppScreen.Accueil
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Forcer la locale
        val locale = Locale.getDefault()
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)

        setContent {
            ComprendreMonchatTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ComprendreMonchatApp()
                }
            }
        }
    }
}

@Composable
fun ComprendreMonchatApp() {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val questions = remember { questionsApplication() }

    var indexQuestion by remember { mutableStateOf(0) }
    var screen by remember { mutableStateOf<AppScreen>(AppScreen.Accueil) }
    var hasSavedProgress by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(false) }
    var screenAvantFeedback by remember { mutableStateOf<AppScreen>(AppScreen.Accueil) }

    val reponsesTexte = remember { mutableStateMapOf<String, String>() }
    val reponsesChoix = remember { mutableStateMapOf<String, Int>() }

    val bilans by HistoriqueManager.getBilans(context).collectAsState(initial = emptyList())

    fun saveState() {
        scope.launch {
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey("screen")] = screen.toStorageValue()
                prefs[intPreferencesKey("index")] = indexQuestion
                prefs[stringPreferencesKey("reponsesTexte")] =
                    JSONObject(reponsesTexte.toMap()).toString()
                prefs[stringPreferencesKey("reponsesChoix")] =
                    JSONObject(reponsesChoix.toMap()).toString()
            }
            hasSavedProgress = reponsesTexte.isNotEmpty() || reponsesChoix.isNotEmpty()
        }
    }

    fun clearSavedState() {
        scope.launch {
            context.dataStore.edit { prefs -> prefs.clear() }
            hasSavedProgress = false
        }
    }

    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first()
        val onboardingFait = prefs[stringPreferencesKey("onboarding_done")] == "true"
        val savedScreen = prefs[stringPreferencesKey("screen")] ?: "accueil"
        val savedIndex = prefs[intPreferencesKey("index")] ?: 0
        val texteJson = prefs[stringPreferencesKey("reponsesTexte")]
        val choixJson = prefs[stringPreferencesKey("reponsesChoix")]

        if (!texteJson.isNullOrBlank()) {
            val texteObj = JSONObject(texteJson)
            for (key in texteObj.keys()) reponsesTexte[key] = texteObj.getString(key)
        }
        if (!choixJson.isNullOrBlank()) {
            val choixObj = JSONObject(choixJson)
            for (key in choixObj.keys()) reponsesChoix[key] = choixObj.getInt(key)
        }

        hasSavedProgress = reponsesTexte.isNotEmpty() || reponsesChoix.isNotEmpty()

        screen = when {
            !onboardingFait -> AppScreen.Onboarding
            hasSavedProgress -> screenFromStorage(savedScreen)
            else -> AppScreen.Accueil
        }
        if (hasSavedProgress && onboardingFait) indexQuestion = savedIndex

        isLoaded = true
    }

    fun marquerOnboardingFait() {
        scope.launch {
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey("onboarding_done")] = "true"
            }
        }
    }

    fun reinitialiserOnboarding() {
        scope.launch {
            context.dataStore.edit { prefs ->
                prefs.remove(stringPreferencesKey("onboarding_done"))
            }
        }
    }

    val questionsVisibles = remember(reponsesChoix.toMap()) {
        questions.filter { questionDoitEtreAffichee(it, reponsesChoix) }
    }

    LaunchedEffect(questionsVisibles.size, isLoaded) {
        if (!isLoaded) return@LaunchedEffect
        if (questionsVisibles.isEmpty()) indexQuestion = 0
        else if (indexQuestion > questionsVisibles.lastIndex) indexQuestion = questionsVisibles.lastIndex
    }

    fun envoyerFeedbackEmail(categorie: String, ecran: String, message: String, version: String) {
        val sujet = "[Comprendre mon chat] $categorie — $ecran"
        val corps = """
Catégorie : $categorie
Écran concerné : $ecran
Version appli : $version

Message :
$message

---
Envoyé depuis l'application Comprendre mon chat
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("laurenaharoy@yahoo.fr"))
            putExtra(Intent.EXTRA_SUBJECT, sujet)
            putExtra(Intent.EXTRA_TEXT, corps)
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Envoyer le signalement"))
        } catch (e: Exception) {
            scope.launch {
                snackbarHostState.showSnackbar("Aucune application email trouvée sur cet appareil.")
            }
        }
    }

    BackHandler(enabled = screen != AppScreen.Accueil && screen != AppScreen.Onboarding) {
        when (screen) {
            AppScreen.Questionnaire -> {
                if (indexQuestion > 0) { indexQuestion--; saveState() }
                else { screen = AppScreen.Accueil; saveState() }
            }
            AppScreen.Chargement -> { screen = AppScreen.Questionnaire; saveState() }
            AppScreen.Resultat -> { screen = AppScreen.Questionnaire; saveState() }
            AppScreen.Dictionnaire -> { screen = AppScreen.Accueil; saveState() }
            is AppScreen.DictionnaireDetail -> { screen = AppScreen.Dictionnaire; saveState() }
            AppScreen.Alimentation -> { screen = AppScreen.Accueil; saveState() }
            AppScreen.Feedback -> { screen = screenAvantFeedback }
            AppScreen.Historique -> { screen = AppScreen.Accueil; saveState() }
            is AppScreen.HistoriqueDetail -> { screen = AppScreen.Historique }
            AppScreen.Parametres -> { screen = AppScreen.Accueil }
            AppScreen.Accueil, AppScreen.Onboarding -> Unit
        }
    }

    AppBackground {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                val context = androidx.compose.ui.platform.LocalContext.current
                val titreEcran = when (screen) {
                    AppScreen.Accueil -> ""
                    AppScreen.Onboarding -> ""
                    AppScreen.Questionnaire -> if (isEnglish()) "Questionnaire" else "Questionnaire"
                    AppScreen.Chargement -> if (isEnglish()) "Analysis" else "Analyse"
                    AppScreen.Resultat -> if (isEnglish()) "Report" else "Résultat"
                    AppScreen.Dictionnaire -> if (isEnglish()) "Behavioral dictionary" else "Dictionnaire comportemental"
                    is AppScreen.DictionnaireDetail -> {
                        val ficheId = (screen as AppScreen.DictionnaireDetail).ficheId
                        context.getString(R.string.kicker_fiche_comportementale)
                    }
                    AppScreen.Alimentation -> if (isEnglish()) "Nutrition" else "Alimentation"
                    AppScreen.Feedback -> if (isEnglish()) "Report an issue" else "Signalement"
                    AppScreen.Historique -> if (isEnglish()) "Report history" else "Historique des bilans"
                    is AppScreen.HistoriqueDetail -> if (isEnglish()) "Report detail" else "Détail du bilan"
                    AppScreen.Parametres -> if (isEnglish()) "Settings" else "Paramètres"
                }

                val onBack: (() -> Unit)? = if (screen != AppScreen.Accueil && screen != AppScreen.Chargement) {
                    {
                        when (screen) {
                            AppScreen.Questionnaire -> {
                                if (indexQuestion > 0) indexQuestion--
                                else screen = AppScreen.Accueil
                                saveState()
                            }
                            AppScreen.Resultat -> { screen = AppScreen.Questionnaire; saveState() }
                            AppScreen.Dictionnaire -> { screen = AppScreen.Accueil; saveState() }
                            is AppScreen.DictionnaireDetail -> { screen = AppScreen.Dictionnaire; saveState() }
                            AppScreen.Alimentation -> { screen = AppScreen.Accueil; saveState() }
                            AppScreen.Feedback -> screen = screenAvantFeedback
                            AppScreen.Historique -> { screen = AppScreen.Accueil; saveState() }
                            is AppScreen.HistoriqueDetail -> screen = AppScreen.Historique
                            AppScreen.Parametres -> screen = AppScreen.Accueil
                            else -> Unit
                        }
                    }
                } else null

                val actions: @Composable () -> Unit = {
                    when (screen) {
                        AppScreen.Accueil -> {
                            if (bilans.isNotEmpty()) {
                                IconButton(onClick = {
                                    screen = AppScreen.Historique
                                    saveState()
                                }) {
                                    Icon(
                                        Icons.Rounded.History,
                                        contentDescription = "Historique des bilans",
                                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                            IconButton(onClick = { screen = AppScreen.Parametres }) {
                                Icon(
                                    Icons.Rounded.Settings,
                                    contentDescription = "Paramètres",
                                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        AppScreen.Chargement, AppScreen.Feedback, AppScreen.Onboarding, AppScreen.Parametres -> Unit
                        else -> {
                            IconButton(onClick = {
                                screenAvantFeedback = screen
                                screen = AppScreen.Feedback
                            }) {
                                Icon(
                                    Icons.Rounded.Feedback,
                                    contentDescription = "Signaler un problème",
                                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }

                PremiumTopBarWithActions(
                    title = titreEcran,
                    onBack = onBack,
                    actions = actions
                )
            }
        ) { padding ->

            if (!isLoaded) {
                ChargementMinimal()
                return@Scaffold
            }

            when (screen) {
                AppScreen.Onboarding -> {
                    OnboardingScreen(
                        onTerminer = {
                            marquerOnboardingFait()
                            screen = AppScreen.Accueil
                        }
                    )
                }

                AppScreen.Accueil -> {
                    AccueilScreen(
                        modifier = Modifier.padding(padding),
                        hasSavedProgress = hasSavedProgress,
                        onCommencer = {
                            reponsesTexte.clear()
                            reponsesChoix.clear()
                            indexQuestion = 0
                            screen = AppScreen.Questionnaire
                            clearSavedState()
                            saveState()
                        },
                        onReprendre = { screen = AppScreen.Questionnaire; saveState() },
                        onDictionnaire = { screen = AppScreen.Dictionnaire; saveState() },
                        onAlimentation = { screen = AppScreen.Alimentation; saveState() }
                    )
                }

                AppScreen.Questionnaire -> {
                    if (questionsVisibles.isNotEmpty()) {
                        val question = questionsVisibles[indexQuestion]
                        QuestionnaireScreen(
                            modifier = Modifier.padding(padding),
                            question = question,
                            progress = (indexQuestion + 1f) / questionsVisibles.size,
                            numero = indexQuestion + 1,
                            total = questionsVisibles.size,
                            valeurTexte = reponsesTexte[question.id].orEmpty(),
                            choixSelectionne = reponsesChoix[question.id],
                            nomChat = reponsesTexte["nom_chat"].orEmpty(),
                            onValeurChangee = { reponsesTexte[question.id] = it; saveState() },
                            onChoixSelectionne = { reponsesChoix[question.id] = it; saveState() },
                            onSuivant = {
                                if (indexQuestion < questionsVisibles.lastIndex) indexQuestion++
                                else screen = AppScreen.Chargement
                                saveState()
                            }
                        )
                    }
                }

                AppScreen.Chargement -> {
                    ChargementAnalyseScreen(
                        modifier = Modifier.padding(padding),
                        onTermine = { screen = AppScreen.Resultat; saveState() }
                    )
                }

                AppScreen.Resultat -> {
                    val analyse = QuestionnaireEngine.calculerResultat(
                        questions, reponsesTexte, reponsesChoix
                    )

                    LaunchedEffect(Unit) {
                        HistoriqueManager.sauvegarderBilan(
                            context,
                            reponsesTexte["nom_chat"].orEmpty(),
                            analyse
                        )
                    }

                    val textePartage = construireTextePartageBilan(
                        reponsesTexte["nom_chat"].orEmpty(), analyse
                    )

                    ResultatScreen(
                        modifier = Modifier.padding(padding),
                        nomChat = reponsesTexte["nom_chat"].orEmpty(),
                        analyse = analyse,
                        onShare = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, textePartage)
                            }
                            context.startActivity(Intent.createChooser(intent, "Partager"))
                        },
                        onCopy = {
                            clipboard.setText(AnnotatedString(textePartage))
                            scope.launch { snackbarHostState.showSnackbar("Copié") }
                        },
                        onExportPdf = {
                            val file = PdfExporter.exporterBilanPdf(
                                context = context,
                                nomChat = reponsesTexte["nom_chat"].orEmpty(),
                                analyse = analyse
                            )
                            val uri = FileProvider.getUriForFile(
                                context, "${context.packageName}.fileprovider", file
                            )
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "application/pdf"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(Intent.createChooser(intent, "Partager PDF"))
                        },
                        onRecommencer = {
                            reponsesTexte.clear()
                            reponsesChoix.clear()
                            indexQuestion = 0
                            screen = AppScreen.Accueil
                            clearSavedState()
                        },
                        onOpenFiche = { ficheId ->
                            screen = AppScreen.DictionnaireDetail(ficheId)
                            saveState()
                        },
                        onOpenAlimentation = {
                            screen = AppScreen.Alimentation
                            saveState()
                        }
                    )
                }

                AppScreen.Dictionnaire -> {
                    DictionnaireInfoScreen(
                        modifier = Modifier.padding(padding),
                        onOpenFiche = { ficheId ->
                            screen = AppScreen.DictionnaireDetail(ficheId)
                            saveState()
                        }
                    )
                }

                is AppScreen.DictionnaireDetail -> {
                    val ficheId = (screen as AppScreen.DictionnaireDetail).ficheId
                    DictionnaireDetailScreen(modifier = Modifier.padding(padding), ficheId = ficheId)
                }

                AppScreen.Alimentation -> {
                    DictionnaireScreen(modifier = Modifier.padding(padding))
                }

                AppScreen.Feedback -> {
                    FeedbackScreen(
                        modifier = Modifier.padding(padding),
                        ecranActuel = screenAvantFeedback.toStorageValue(),
                        onEnvoyer = { categorie, ecran, message ->
                            val version = try {
                                context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "?"
                            } catch (e: Exception) { "?" }
                            envoyerFeedbackEmail(categorie, ecran, message, version)
                        },
                        onRetour = { screen = screenAvantFeedback }
                    )
                }

                AppScreen.Historique -> {
                    HistoriqueScreen(
                        modifier = Modifier.padding(padding),
                        bilans = bilans,
                        onOuvrirBilan = { bilanId -> screen = AppScreen.HistoriqueDetail(bilanId) },
                        onSupprimerBilan = { bilanId ->
                            scope.launch { HistoriqueManager.supprimerBilan(context, bilanId) }
                        },
                        onSupprimerTout = {
                            scope.launch { HistoriqueManager.supprimerTout(context) }
                        }
                    )
                }

                is AppScreen.HistoriqueDetail -> {
                    val bilanId = (screen as AppScreen.HistoriqueDetail).bilanId
                    val bilan = bilans.firstOrNull { it.id == bilanId }
                    if (bilan != null) {
                        HistoriqueDetailScreen(
                            modifier = Modifier.padding(padding),
                            bilan = bilan,
                            onSupprimer = {
                                scope.launch {
                                    HistoriqueManager.supprimerBilan(context, bilanId)
                                    screen = AppScreen.Historique
                                }
                            }
                        )
                    }
                }

                AppScreen.Parametres -> {
                    ParametresScreen(
                        modifier = Modifier.padding(padding),
                        onRevoirOnboarding = {
                            reinitialiserOnboarding()
                            screen = AppScreen.Onboarding
                        }
                    )
                }
            }
        }
    }
}

fun questionDoitEtreAffichee(question: Question, reponsesChoix: Map<String, Int>): Boolean {
    return when (question.id) {
        "vie_interieur" -> reponsesChoix["acces_exterieur"] != 0
        "proprete_type" -> (reponsesChoix["proprete_stress"] ?: 0) != 0
        "chaleur_marquage" -> reponsesChoix["sterilise"] == 3 && (reponsesChoix["marquage_urinaire"] ?: 0) != 0
        "marquage_habitude_post_sterilisation" -> reponsesChoix["sterilise"] == 1 && (reponsesChoix["marquage_urinaire"] ?: 0) != 0
        "senior_desorientation" -> reponsesChoix["age"] == 3
        "senior_vocalise_nocturne" -> reponsesChoix["age"] == 3
        "a_deja_griffe_mordu" -> reponsesChoix["agressivite_caresses"] != 0
        "cible_agression" -> reponsesChoix["a_deja_griffe_mordu"] == 1
        else -> true
    }
}

fun construireTextePartageBilan(nomChat: String, analyse: ResultatAnalyse): String {
    val nom = nomChatAffiche(nomChat)
    val titrePartage = if (isEnglish()) "Emotional report for $nom" else "Bilan émotionnel pour $nom"
    val hypotheseLabel = if (isEnglish()) "Hypothesis:" else "Hypothèse :"
    val prioriteLabel = if (isEnglish()) "Priority:" else "Priorité :"
    val scoresLabel = if (isEnglish()) "Scores:" else "Scores :"
    val securiteLabel = if (isEnglish()) "Emotional security" else "Sécurité émotionnelle"
    val lienLabel = if (isEnglish()) "Human bond" else "Lien humain"
    val instinctsLabel = if (isEnglish()) "Instincts" else "Instincts"
    val cohabLabel = if (isEnglish()) "Cohabitation" else "Cohabitation"
    val avertissement = if (isEnglish()) "⚠️ Indicative report" else "⚠️ Bilan indicatif"

    return "$titrePartage\n\n$hypotheseLabel\n${analyse.hypothesePrincipale}\n\n$prioriteLabel\n${textePrioriteAction(analyse.prioriteAction)}\n\n${analyse.syntheseAvancee}\n\n$scoresLabel\n$securiteLabel : ${QuestionnaireEngine.libelleNiveauAxe(analyse.niveauPeur)}\n$lienLabel : ${QuestionnaireEngine.libelleNiveauAxe(analyse.niveauAttachement)}\n$instinctsLabel : ${QuestionnaireEngine.libelleNiveauAxe(analyse.niveauImpulsivite)}\n$cohabLabel : ${QuestionnaireEngine.libelleNiveauAxe(analyse.niveauReactivite)}\n\n$avertissement"
}