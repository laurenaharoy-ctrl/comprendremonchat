package com.laurena.comprendremonchat

import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════
// MODÈLES DE DONNÉES
// ═══════════════════════════════════════════════════════════

enum class Axe {
    SECURITE, LIEN, INSTINCTS, COHABITATION
}

enum class NiveauAxe {
    PEU_MARQUE, A_SURVEILLER, MARQUE, TRES_MARQUE
}

enum class NiveauVigilance { FAIBLE, MODEREE, ELEVEE }
enum class NiveauSituation { STABLE, A_TRAVAILLER, SENSIBLE }
enum class PrioriteAction { FAIBLE, MODEREE, ELEVEE, URGENTE }

sealed class Question(val id: String, val titre: String)

class QuestionTexte(id: String, titre: String) : Question(id, titre)

class QuestionChoix(
    id: String, titre: String,
    val options: List<String>,
    val axe: Axe? = null,
    val scoreParOption: List<Int>? = null,
    val poids: Int = 1,
    val signalAlerte: Boolean = false,
    val signalCritique: Boolean = false
) : Question(id, titre)

data class ProfilGlobal(
    val titre: String, val resume: String,
    val profilType: String, val scoreGlobal: Int, val phraseHumaine: String
)

data class ContexteAnalyse(
    val temporalite: Int, val evolution: Int, val frequence: Int,
    val intensite: Int, val generalisation: Int, val changement: Int,
    val physique: Int, val scoreContexte: Int
)

data class PlanAction(
    val aFaire: List<String>, val aEviter: List<String>, val aObserver: List<String>
)

data class PrioriteImmediate(
    val niveau: PrioriteAction, val titre: String,
    val message: String, val actionsImmediates: List<String>
)

data class ExplicationResultat(
    val raisonsPrincipales: List<String>,
    val facteursAggravants: List<String>,
    val facteursProtecteurs: List<String>
)

data class ResultatAnalyse(
    val peur: Int, val attachement: Int, val impulsivite: Int, val reactivite: Int,
    val niveauPeur: NiveauAxe, val niveauAttachement: NiveauAxe,
    val niveauImpulsivite: NiveauAxe, val niveauReactivite: NiveauAxe,
    val profil: ProfilGlobal, val vigilance: NiveauVigilance,
    val niveauSituation: NiveauSituation, val contexte: ContexteAnalyse,
    val problemePrincipal: Axe, val problemesImportants: List<Axe>,
    val explicationPrincipale: String, val conseilPrincipal: String,
    val conseilsPratiques: List<String>, val planAction: PlanAction,
    val messageSituation: String, val raisonSituation: String,
    val messageAide: String?, val apparitionBrutale: Boolean, val aDejaMordu: Boolean,
    val hypothesePrincipale: String, val prioriteAction: PrioriteAction,
    val prioriteImmediate: PrioriteImmediate, val explicationResultat: ExplicationResultat,
    val facteursAggravants: List<String>, val facteursProtecteurs: List<String>,
    val syntheseAvancee: String, val raceCategorie: String?, val racePrecise: String?,
    val originesPossibles: String = "",
    val marquageHabitudePostSterilisation: Boolean = false,
    val suspicionDeclinCognitif: Boolean = false,
    val cibleAgressionAnimal: Boolean = false,
    val lieuResidence: Int? = null
)

// ═══════════════════════════════════════════════════════════
// HELPERS TEXTE
// ═══════════════════════════════════════════════════════════

fun nomChatAffiche(nom: String): String =
    nom.trim().replaceFirstChar { it.uppercase() }.ifBlank { tr("votre chat", "your cat", "Ihre Katze", "tu gato") }

fun libelleAxe(axe: Axe): String = libelleAxeTraduit(axe)

fun texteNiveauSituation(niveau: NiveauSituation): String = texteNiveauSituationTraduit(niveau)

fun texteVigilance(vigilance: NiveauVigilance, nomChat: String): String =
    texteVigilanceTraduit(vigilance, nomChat)

fun textePrioriteAction(priorite: PrioriteAction): String = textePrioriteActionTraduit(priorite)

fun resumeEmotionnel(axe: Axe, niveau: NiveauAxe = NiveauAxe.MARQUE): String =
    resumeEmotionnelTraduit(axe, niveau)

fun intentionChat(axe: Axe, niveau: NiveauAxe = NiveauAxe.MARQUE): String = intentionChatTraduit(axe, niveau)

fun besoinPrincipal(axe: Axe, niveau: NiveauAxe = NiveauAxe.MARQUE): String = besoinPrincipalTraduit(axe, niveau)

fun niveauAxePrincipal(analyse: ResultatAnalyse): NiveauAxe = when (analyse.problemePrincipal) {
    Axe.SECURITE -> analyse.niveauPeur
    Axe.LIEN -> analyse.niveauAttachement
    Axe.INSTINCTS -> analyse.niveauImpulsivite
    Axe.COHABITATION -> analyse.niveauReactivite
}

fun phraseFin(nomChat: String): String = phraseFinTraduit(nomChat)

data class CategorieRace(
    val id: String, val nom: String,
    val predispositions: List<String>, val nuanceAnalyse: String
)

// ═══════════════════════════════════════════════════════════
// DONNÉES DE RACES — BILINGUES
// ═══════════════════════════════════════════════════════════

val categoriesRaces get() = trList(categoriesRacesChatFr, categoriesRacesChatEn, categoriesRacesChatDe, categoriesRacesChatEs)

val categoriesRacesChatFr = listOf(
    CategorieRace("europeen", "Européen / Gouttière",
        listOf("Grande adaptabilité", "Tempérament variable selon l'histoire individuelle"),
        "L'histoire de socialisation précoce joue un rôle déterminant dans son équilibre émotionnel."),
    CategorieRace("maine_coon", "Maine Coon",
        listOf("Sociabilité marquée", "Besoin de stimulation important", "Attachement fort à sa famille"),
        "Un manque de stimulation peut générer de l'ennui et des comportements compensatoires."),
    CategorieRace("persan", "Persan",
        listOf("Tempérament calme et posé", "Sensibilité aux changements", "Besoin de calme"),
        "Les perturbations environnementales peuvent le déstabiliser facilement malgré son calme apparent."),
    CategorieRace("siamois", "Siamois",
        listOf("Forte vocalisation", "Attachement intense", "Très expressif émotionnellement"),
        "L'anxiété de séparation et les vocalisations excessives sont plus fréquentes dans cette famille."),
    CategorieRace("ragdoll", "Ragdoll",
        listOf("Très grande tolérance", "Fort besoin de présence humaine", "Peu conflictuel"),
        "Attention à ne pas confondre sa tolérance avec une absence de besoins — il peut souffrir en silence."),
    CategorieRace("bengal", "Bengal",
        listOf("Niveau d'énergie très élevé", "Besoin intense de stimulation", "Forte personnalité"),
        "Un manque de stimulation physique et mentale peut rapidement générer des comportements problématiques."),
    CategorieRace("british", "British Shorthair",
        listOf("Tempérament calme et indépendant", "Bonne tolérance à la solitude", "Peu expressif"),
        "Son calme apparent peut masquer un stress si les signaux subtils ne sont pas détectés."),
    CategorieRace("abyssin", "Abyssin",
        listOf("Très actif et curieux", "Besoin de liberté", "Peu tolérant à l'ennui"),
        "L'enrichissement environnemental est indispensable — il s'ennuie vite et réagit fortement."),
    CategorieRace("sacre_birmanie", "Sacré de Birmanie",
        listOf("Doux et équilibré", "Attachement modéré", "Bonne cohabitation"),
        "Généralement équilibré, mais sensible aux tensions dans le foyer."),
    CategorieRace("autre", "Autre race / inconnu",
        listOf("Profil individuel à observer"),
        "L'histoire individuelle et la socialisation précoce sont les facteurs les plus déterminants.")
)

val categoriesRacesChatEn = listOf(
    CategorieRace("europeen", "European / Mixed breed",
        listOf("Great adaptability", "Temperament varies according to individual history"),
        "Early socialization plays a key role in its emotional balance."),
    CategorieRace("maine_coon", "Maine Coon",
        listOf("Marked sociability", "Strong need for stimulation", "Strong attachment to its family"),
        "A lack of stimulation can lead to boredom and compensatory behaviors."),
    CategorieRace("persan", "Persian",
        listOf("Calm and composed temperament", "Sensitivity to changes", "Need for calm"),
        "Disruptions in its environment can easily unsettle it, despite its apparent calm."),
    CategorieRace("siamois", "Siamese",
        listOf("Very vocal", "Intense attachment", "Very emotionally expressive"),
        "Separation anxiety and excessive meowing are more common in this group."),
    CategorieRace("ragdoll", "Ragdoll",
        listOf("Very high tolerance", "Strong need for human presence", "Rarely gets into conflicts"),
        "Be careful not to confuse its tolerance with an absence of needs — it can suffer in silence."),
    CategorieRace("bengal", "Bengal",
        listOf("Very high energy level", "Intense need for stimulation", "Strong personality"),
        "A lack of physical and mental stimulation can quickly lead to problem behaviors."),
    CategorieRace("british", "British Shorthair",
        listOf("Calm and independent temperament", "Good tolerance for solitude", "Not very expressive"),
        "Its apparent calm can mask stress if subtle signals are not detected."),
    CategorieRace("abyssin", "Abyssinian",
        listOf("Very active and curious", "Need for freedom", "Low tolerance for boredom"),
        "Environmental enrichment is essential — it gets bored quickly and reacts strongly."),
    CategorieRace("sacre_birmanie", "Birman",
        listOf("Gentle and balanced", "Moderate attachment", "Good cohabitation"),
        "Generally balanced, but sensitive to tensions in the household."),
    CategorieRace("autre", "Other breed / unknown",
        listOf("Individual profile — worth observing closely"),
        "Individual history and early socialization are the most important factors.")
)

val categoriesRacesChatDe = listOf(
    CategorieRace("europeen", "Europäisch Kurzhaar / Mischling",
        listOf("Große Anpassungsfähigkeit", "Temperament je nach individueller Geschichte unterschiedlich"),
        "Die frühe Sozialisierung spielt eine entscheidende Rolle für ihr emotionales Gleichgewicht."),
    CategorieRace("maine_coon", "Maine Coon",
        listOf("Ausgeprägte Geselligkeit", "Großes Bedürfnis nach Beschäftigung", "Starke Bindung an ihre Familie"),
        "Mangelnde Beschäftigung kann Langeweile und Ersatzverhalten auslösen."),
    CategorieRace("persan", "Perser",
        listOf("Ruhiges, ausgeglichenes Temperament", "Empfindlich gegenüber Veränderungen", "Bedürfnis nach Ruhe"),
        "Störungen in der Umgebung können sie trotz ihrer scheinbaren Ruhe leicht aus dem Gleichgewicht bringen."),
    CategorieRace("siamois", "Siam",
        listOf("Sehr gesprächig", "Intensive Bindung", "Emotional sehr ausdrucksstark"),
        "Trennungsangst und übermäßiges Miauen kommen in dieser Gruppe häufiger vor."),
    CategorieRace("ragdoll", "Ragdoll",
        listOf("Sehr große Toleranz", "Starkes Bedürfnis nach menschlicher Nähe", "Wenig konfliktfreudig"),
        "Die Toleranz der Ragdoll sollte nicht mit fehlenden Bedürfnissen verwechselt werden – sie kann still leiden."),
    CategorieRace("bengal", "Bengal",
        listOf("Sehr hohes Energieniveau", "Intensives Bedürfnis nach Beschäftigung", "Starke Persönlichkeit"),
        "Mangelnde körperliche und geistige Beschäftigung kann schnell problematisches Verhalten auslösen."),
    CategorieRace("british", "Britisch Kurzhaar",
        listOf("Ruhiges, unabhängiges Temperament", "Verträgt das Alleinsein gut", "Wenig ausdrucksstark"),
        "Die scheinbare Ruhe dieser Rasse kann Stress verbergen, wenn die feinen Signale nicht erkannt werden."),
    CategorieRace("abyssin", "Abessinier",
        listOf("Sehr aktiv und neugierig", "Bedürfnis nach Freiheit", "Verträgt Langeweile schlecht"),
        "Eine abwechslungsreich gestaltete Umgebung ist unerlässlich – sie langweilt sich schnell und reagiert stark."),
    CategorieRace("sacre_birmanie", "Heilige Birma",
        listOf("Sanft und ausgeglichen", "Mäßige Bindung", "Gutes Zusammenleben"),
        "Meist ausgeglichen, aber empfindlich gegenüber Spannungen im Haushalt."),
    CategorieRace("autre", "Andere Rasse / unbekannt",
        listOf("Individuelles Profil – genau beobachten"),
        "Die individuelle Geschichte und die frühe Sozialisierung sind die entscheidendsten Faktoren.")
)

val categoriesRacesChatEs = listOf(
    CategorieRace("europeen", "Europeo / común",
        listOf("Gran capacidad de adaptación", "Temperamento variable según su historia personal"),
        "La socialización temprana desempeña un papel decisivo en su equilibrio emocional."),
    CategorieRace("maine_coon", "Maine coon",
        listOf("Sociabilidad marcada", "Gran necesidad de estimulación", "Fuerte apego a su familia"),
        "La falta de estimulación puede provocar aburrimiento y comportamientos compensatorios."),
    CategorieRace("persan", "Persa",
        listOf("Temperamento tranquilo y sereno", "Sensibilidad a los cambios", "Necesidad de calma"),
        "Los cambios en su entorno pueden desestabilizarlo con facilidad, a pesar de su calma aparente."),
    CategorieRace("siamois", "Siamés",
        listOf("Muy hablador", "Apego intenso", "Muy expresivo emocionalmente"),
        "La ansiedad por separación y los maullidos excesivos son más frecuentes en este grupo."),
    CategorieRace("ragdoll", "Ragdoll",
        listOf("Tolerancia muy alta", "Gran necesidad de presencia humana", "Poco conflictivo"),
        "No hay que confundir su tolerancia con una ausencia de necesidades: puede sufrir en silencio."),
    CategorieRace("bengal", "Bengalí",
        listOf("Nivel de energía muy alto", "Necesidad intensa de estimulación", "Fuerte personalidad"),
        "La falta de estimulación física y mental puede provocar rápidamente comportamientos problemáticos."),
    CategorieRace("british", "British shorthair",
        listOf("Temperamento tranquilo e independiente", "Buena tolerancia a la soledad", "Poco expresivo"),
        "Su calma aparente puede ocultar estrés si no se detectan las señales sutiles."),
    CategorieRace("abyssin", "Abisinio",
        listOf("Muy activo y curioso", "Necesidad de libertad", "Poca tolerancia al aburrimiento"),
        "Un entorno enriquecido es imprescindible: se aburre rápido y reacciona con intensidad."),
    CategorieRace("sacre_birmanie", "Sagrado de Birmania",
        listOf("Dulce y equilibrado", "Apego moderado", "Buena convivencia"),
        "En general equilibrado, pero sensible a las tensiones en el hogar."),
    CategorieRace("autre", "Otra raza / desconocida",
        listOf("Perfil individual que conviene observar"),
        "La historia personal y la socialización temprana son los factores más determinantes.")
)

fun getNuanceAnalyse(race: String): String? =
    categoriesRaces.firstOrNull { it.nom.equals(race, ignoreCase = true) }?.nuanceAnalyse

fun getPredispositions(race: String): List<String> =
    categoriesRaces.firstOrNull { it.nom.equals(race, ignoreCase = true) }?.predispositions ?: emptyList()

// ═══════════════════════════════════════════════════════════
// HELPERS SEXE/STÉRILISATION
// ═══════════════════════════════════════════════════════════

fun estSterilise(reponsesChoix: Map<String, Int>): Boolean =
    reponsesChoix["sterilise"] == 0 || reponsesChoix["sterilise"] == 1

fun estMaleEntier(reponsesChoix: Map<String, Int>): Boolean =
    reponsesChoix["sterilise"] == 2

fun estFemelleEntiere(reponsesChoix: Map<String, Int>): Boolean =
    reponsesChoix["sterilise"] == 3

// ═══════════════════════════════════════════════════════════
// MOTEUR DE CALCUL
// ═══════════════════════════════════════════════════════════

object QuestionnaireEngine {

    fun convertirChoixEnPoints(question: QuestionChoix, indexChoisi: Int): Int {
        val scoreBase = question.scoreParOption?.getOrNull(indexChoisi) ?: when (indexChoisi) {
            0 -> 0; 1 -> 1; 2 -> 2; 3 -> 3; else -> 0
        }
        return scoreBase * question.poids
    }

    fun calculerPourcentageAxe(axe: Axe, questions: List<Question>, reponsesChoix: Map<String, Int>): Int {
        val questionsAxe = questions.filterIsInstance<QuestionChoix>().filter { it.axe == axe }
        if (questionsAxe.isEmpty()) return 0
        val scoreMax = questionsAxe.sumOf { q -> (q.scoreParOption?.maxOrNull() ?: 2) * q.poids }
        val score = questionsAxe.sumOf { q -> convertirChoixEnPoints(q, reponsesChoix[q.id] ?: 0) }
        if (scoreMax == 0) return 0
        return ((score.toFloat() / scoreMax.toFloat()) * 100f).roundToInt()
    }

    fun calculerNiveauAxe(score: Int): NiveauAxe = when {
        score <= 29 -> NiveauAxe.PEU_MARQUE
        score <= 54 -> NiveauAxe.A_SURVEILLER
        score <= 74 -> NiveauAxe.MARQUE
        else -> NiveauAxe.TRES_MARQUE
    }

    fun libelleNiveauAxe(niveau: NiveauAxe): String = libelleNiveauAxeTraduit(niveau)

    fun determinerProblemePrincipal(securite: Int, lien: Int, instincts: Int, cohabitation: Int): Axe =
        listOf(Axe.SECURITE to securite, Axe.LIEN to lien, Axe.INSTINCTS to instincts, Axe.COHABITATION to cohabitation)
            .maxByOrNull { it.second }!!.first

    fun determinerProfilType(securite: Int, lien: Int, instincts: Int, cohabitation: Int): String =
        determinerProfilTypeTraduit(securite, lien, instincts, cohabitation)

    fun phraseHumaineProfil(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String =
        phraseHumaineTraduit(nomChat, securite, lien, instincts, cohabitation)

    fun genererProfilGlobal(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): ProfilGlobal =
        genererProfilGlobalTraduit(nomChat, securite, lien, instincts, cohabitation)

    fun calculerContexte(reponsesChoix: Map<String, Int>): ContexteAnalyse {
        val temporalite = when (reponsesChoix["duree_probleme"]) { 0 -> 2; 1 -> 1; else -> 0 }
        val evolution = when (reponsesChoix["evolution_probleme"]) { 2 -> 3; 1 -> 1; else -> 0 }
        val frequence = when (reponsesChoix["frequence_probleme"]) { 3 -> 3; 2 -> 2; 1 -> 1; else -> 0 }
        val intensite = when (reponsesChoix["intensite_probleme"]) { 3 -> 4; 2 -> 3; 1 -> 1; else -> 0 }
        val generalisation = when (reponsesChoix["generalisation_probleme"]) { 2 -> 2; 1 -> 1; else -> 0 }
        val changement = when (reponsesChoix["changement_recent"]) { 2 -> 3; 1 -> 1; else -> 0 }
        val physique = when (reponsesChoix["signe_physique"]) { 3 -> 4; 2 -> 4; 1 -> 2; else -> 0 }
        val scoreContexte = temporalite + evolution + frequence + intensite + generalisation + changement + physique
        return ContexteAnalyse(temporalite, evolution, frequence, intensite, generalisation, changement, physique, scoreContexte)
    }

    fun calculerNiveauVigilance(questions: List<Question>, reponsesChoix: Map<String, Int>,
                                securite: Int, lien: Int, instincts: Int, cohabitation: Int, contexte: ContexteAnalyse): NiveauVigilance {
        val questionsChoix = questions.filterIsInstance<QuestionChoix>()
        val critiqueDetecte = questionsChoix.any { q -> q.signalCritique && (reponsesChoix[q.id] ?: 0) > 0 }
        val nbAlertes = questionsChoix.count { q -> q.signalAlerte && (reponsesChoix[q.id] ?: 0) >= 2 }
        val scoreMax = maxOf(securite, lien, instincts, cohabitation)
        val maleEntier = estMaleEntier(reponsesChoix)
        val femelleEntiere = estFemelleEntiere(reponsesChoix)
        return when {
            critiqueDetecte -> NiveauVigilance.ELEVEE
            contexte.physique >= 4 -> NiveauVigilance.ELEVEE
            reponsesChoix["apparition"] == 1 && scoreMax >= 50 -> NiveauVigilance.ELEVEE
            contexte.scoreContexte >= 10 -> NiveauVigilance.ELEVEE
            nbAlertes >= 2 -> NiveauVigilance.MODEREE
            scoreMax >= 70 -> NiveauVigilance.MODEREE
            contexte.scoreContexte >= 6 -> NiveauVigilance.MODEREE
            maleEntier && cohabitation >= 50 -> NiveauVigilance.MODEREE
            femelleEntiere && securite >= 50 -> NiveauVigilance.MODEREE
            else -> NiveauVigilance.FAIBLE
        }
    }

    fun calculerNiveauSituation(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                securite: Int, lien: Int, instincts: Int, cohabitation: Int): NiveauSituation {
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        val signalCritique = contexte.physique >= 4 ||
                reponsesChoix["a_deja_griffe_mordu"] == 1

        return when {
            signalCritique -> NiveauSituation.SENSIBLE
            maxAxe <= 29 && contexte.scoreContexte < 5 -> NiveauSituation.STABLE
            reponsesChoix["evolution_probleme"] == 2 && reponsesChoix["intensite_probleme"] == 3 -> NiveauSituation.SENSIBLE
            contexte.scoreContexte >= 10 -> NiveauSituation.SENSIBLE
            contexte.scoreContexte >= 5 -> NiveauSituation.A_TRAVAILLER
            maxAxe >= 55 -> NiveauSituation.A_TRAVAILLER
            else -> NiveauSituation.STABLE
        }
    }

    fun genererMessageSituation(niveauSituation: NiveauSituation, nomChat: String): String =
        genererMessageSituationTraduit(niveauSituation, nomChat)

    fun genererRaisonSituation(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, niveauSituation: NiveauSituation): String =
        genererRaisonSituationTraduit(reponsesChoix, contexte, niveauSituation)

    fun genererConseilsPratiquesPersonnalises(nomChat: String, reponsesChoix: Map<String, Int>,
                                              securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> =
        genererConseilsPratiquesToTraduit(nomChat, reponsesChoix, securite, lien, instincts, cohabitation)

    fun determinerProblemesImportants(securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<Axe> {
        return mutableListOf<Axe>().apply {
            if (securite >= 65) add(Axe.SECURITE)
            if (lien >= 65) add(Axe.LIEN)
            if (instincts >= 65) add(Axe.INSTINCTS)
            if (cohabitation >= 65) add(Axe.COHABITATION)
        }
    }

    fun explicationProbleme(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String =
        explicationProblemeTraduit(axe, securite, lien, instincts, cohabitation)

    fun conseilPrincipal(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String =
        conseilPrincipalTraduit(axe, securite, lien, instincts, cohabitation)

    fun genererPlanAction(axe: Axe, reponsesChoix: Map<String, Int>, nomChat: String): PlanAction =
        genererPlanActionTraduit(axe, reponsesChoix, nomChat)

    fun genererMessageAide(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                           niveauSituation: NiveauSituation, nomChat: String,
                           securite: Int, lien: Int, instincts: Int, cohabitation: Int): String? =
        genererMessageAideTraduit(reponsesChoix, contexte, niveauSituation, nomChat, securite, lien, instincts, cohabitation)

    fun detecterFacteursAggravants(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                   securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> =
        detecterFacteursAggravantsTraduit(reponsesChoix, contexte, securite, lien, instincts, cohabitation)

    fun detecterFacteursProtecteurs(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): List<String> =
        detecterFacteursProtecteursTraduit(reponsesChoix, contexte)

    fun detecterHypothesePrincipale(reponsesChoix: Map<String, Int>,
                                    securite: Int, lien: Int, instincts: Int, cohabitation: Int, contexte: ContexteAnalyse): String =
        detecterHypothesePrincipaleTraduit(reponsesChoix, securite, lien, instincts, cohabitation, contexte)

    fun determinerPrioriteAction(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                 securite: Int, lien: Int, instincts: Int, cohabitation: Int): PrioriteAction {
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        val signalCritique = contexte.physique >= 4 ||
                reponsesChoix["a_deja_griffe_mordu"] == 1

        return when {
            signalCritique && reponsesChoix["cible_agression"] == 1 -> PrioriteAction.ELEVEE
            signalCritique -> PrioriteAction.URGENTE
            maxAxe <= 29 && contexte.scoreContexte < 5 -> PrioriteAction.FAIBLE
            contexte.scoreContexte >= 10 -> PrioriteAction.ELEVEE
            maxAxe >= 75 -> PrioriteAction.ELEVEE
            contexte.scoreContexte >= 5 -> PrioriteAction.MODEREE
            maxAxe >= 55 -> PrioriteAction.MODEREE
            else -> PrioriteAction.FAIBLE
        }
    }

    fun construirePrioriteImmediate(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                    priorite: PrioriteAction, niveauSituation: NiveauSituation, nomChat: String): PrioriteImmediate =
        construirePrioriteImmediateTraduit(reponsesChoix, contexte, priorite, niveauSituation, nomChat)

    fun construireExplicationResultat(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                      securite: Int, lien: Int, instincts: Int, cohabitation: Int): ExplicationResultat {
        val raisons = mutableListOf<String>()
        when (appLang()) {
            AppLang.EN -> {
                if (reponsesChoix["evolution_probleme"] == 2) raisons += "The behavior seems to be gradually worsening."
                if (reponsesChoix["intensite_probleme"] == 3) raisons += "The described intensity seems significant and impacts daily life."
                if (reponsesChoix["generalisation_probleme"] == 2) raisons += "The behavior affects many different situations."
                if (raisons.isEmpty()) raisons += "The answers suggest a few points to keep an eye on."
            }
            AppLang.DE -> {
                if (reponsesChoix["evolution_probleme"] == 2) raisons += "Das Verhalten scheint sich nach und nach zu verschlimmern."
                if (reponsesChoix["intensite_probleme"] == 3) raisons += "Die beschriebene Intensität erscheint hoch und beeinträchtigt den Alltag."
                if (reponsesChoix["generalisation_probleme"] == 2) raisons += "Das Verhalten betrifft viele verschiedene Situationen."
                if (raisons.isEmpty()) raisons += "Die Antworten deuten auf einige Punkte hin, die man im Auge behalten sollte."
            }
            AppLang.ES -> {
                if (reponsesChoix["evolution_probleme"] == 2) raisons += "El comportamiento parece estar empeorando poco a poco."
                if (reponsesChoix["intensite_probleme"] == 3) raisons += "La intensidad descrita parece importante y afecta al día a día."
                if (reponsesChoix["generalisation_probleme"] == 2) raisons += "El comportamiento aparece en muchas situaciones diferentes."
                if (raisons.isEmpty()) raisons += "Las respuestas sugieren algunos puntos a vigilar."
            }
            else -> {
                if (reponsesChoix["evolution_probleme"] == 2) raisons += "Le comportement semble s'aggraver progressivement."
                if (reponsesChoix["intensite_probleme"] == 3) raisons += "L'intensité décrite paraît importante et impacte le quotidien."
                if (reponsesChoix["generalisation_probleme"] == 2) raisons += "Le comportement touche de nombreuses situations différentes."
                if (raisons.isEmpty()) raisons += "Les réponses suggèrent quelques points de vigilance à surveiller."
            }
        }
        return ExplicationResultat(raisons.take(3),
            detecterFacteursAggravantsTraduit(reponsesChoix, contexte, securite, lien, instincts, cohabitation),
            detecterFacteursProtecteursTraduit(reponsesChoix, contexte))
    }

    fun genererSyntheseAvancee(nom: String, hypothese: String, priorite: PrioriteAction,
                               aggravants: List<String>, protecteurs: List<String>): String {
        val intro = when (appLang()) {
            AppLang.EN -> {
                when (priorite) {
                    PrioriteAction.FAIBLE -> "$nom seems stable overall."
                    PrioriteAction.MODEREE -> "$nom shows a difficulty that calls for a gradual approach."
                    PrioriteAction.ELEVEE -> "$nom seems to be struggling in an area requiring active attention."
                    PrioriteAction.URGENTE -> "$nom shows signs that warrant prompt professional attention."
                }
            }
            AppLang.DE -> {
                when (priorite) {
                    PrioriteAction.FAIBLE -> "$nom zeigt ein insgesamt stabiles Verhalten."
                    PrioriteAction.MODEREE -> "$nom zeigt eine Schwierigkeit, die ein schrittweises Vorgehen verdient."
                    PrioriteAction.ELEVEE -> "$nom scheint in einem Bereich Schwierigkeiten zu haben, der aktive Aufmerksamkeit erfordert."
                    PrioriteAction.URGENTE -> "$nom zeigt Anzeichen, die eine rasche und professionelle Aufmerksamkeit rechtfertigen."
                }
            }
            AppLang.ES -> {
                when (priorite) {
                    PrioriteAction.FAIBLE -> "$nom presenta un funcionamiento globalmente estable."
                    PrioriteAction.MODEREE -> "$nom presenta una dificultad que merece un enfoque progresivo."
                    PrioriteAction.ELEVEE -> "$nom parece tener dificultades en un aspecto que requiere una atención activa."
                    PrioriteAction.URGENTE -> "$nom presenta elementos que justifican una atención rápida y profesional."
                }
            }
            else -> {
                when (priorite) {
                    PrioriteAction.FAIBLE -> "$nom présente un fonctionnement globalement stable."
                    PrioriteAction.MODEREE -> "$nom présente une difficulté qui mérite une approche progressive."
                    PrioriteAction.ELEVEE -> "$nom semble en difficulté sur un plan nécessitant une attention active."
                    PrioriteAction.URGENTE -> "$nom présente des éléments qui justifient une attention rapide et professionnelle."
                }
            }
        }
        val aggrLabel = tr("Éléments majorants", "Aggravating factors", "Verschärfende Faktoren", "Factores agravantes")
        val protLabel = tr("Éléments favorables", "Protective factors", "Günstige Faktoren", "Factores favorables")
        val hypoLabel = tr("Hypothèse", "Hypothesis", "Hypothese", "Hipótesis")
        val sep = tr(" : ", ": ", ": ", ": ")
        val aggr = if (aggravants.isNotEmpty()) "$aggrLabel$sep${aggravants.joinToString(", ")}." else ""
        val prot = if (protecteurs.isNotEmpty()) "$protLabel$sep${protecteurs.joinToString(", ")}." else ""
        return listOf(intro, "$hypoLabel$sep$hypothese", aggr, prot).filter { it.isNotBlank() }.joinToString("\n\n")
    }

    fun genererOriginesPossibles(
        nomChat: String, axe: Axe,
        securite: Int, lien: Int, instincts: Int, cohabitation: Int,
        reponsesChoix: Map<String, Int>
    ): String {
        val nom = nomChatAffiche(nomChat)
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        if (maxAxe <= 25) return tr("$nom semble évoluer dans un équilibre global satisfaisant. Aucune origine comportementale particulière ne ressort à ce stade.", "$nom seems to be in a good overall balance. No particular behavioral origin stands out at this stage.", "$nom scheint sich in einem insgesamt zufriedenstellenden Gleichgewicht zu befinden. Zum jetzigen Zeitpunkt zeigt sich keine besondere verhaltensbezogene Ursache.", "$nom parece mantener un equilibrio general satisfactorio. Por ahora no destaca ningún origen de comportamiento en particular.")

        return when (appLang()) {
            AppLang.EN -> {
                when (axe) {
                    Axe.SECURITE -> buildString {
                        append("$nom's emotional insecurity can have several origins. ")
                        append("Insufficient early socialization — little exposure to humans, sounds or varied environments before the age of 7 weeks — is often a factor. ")
                        append("Past negative experiences, even isolated ones, can leave a lasting imprint on how a cat perceives its world. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("A solely indoor cat may sometimes lack varied stimulation, which weakens its ability to cope with novelty. ")
                        if (reponsesChoix["age"] == 0) append("Under one year old, a sense of security is still developing — some sensitivity is normal at this age. ")
                        if (reponsesChoix["changement_recent"] == 2) append("A recent major change may have unsettled its usual bearings and heightened this sense of insecurity. ")
                        append("In some cases, a genetic predisposition also plays a role, independently of lived experience.")
                    }
                    Axe.LIEN -> buildString {
                        append("$nom's intense need for closeness can be explained in several ways. ")
                        append("Weaning too early — before 8 weeks — can have a lasting impact on the development of emotional independence. ")
                        append("An environment where the cat has never learned to be alone can also reinforce this need for constant presence. ")
                        if (reponsesChoix["recherche_proximite"] == 3) append("Constantly following its human can be both a symptom and a factor that maintains this dependency on the relationship. ")
                        if (estMaleEntier(reponsesChoix) || estFemelleEntiere(reponsesChoix)) append("In an unneutered cat, some manifestations may also be influenced by hormonal cycles. ")
                        append("This is not a whim: it reflects a genuine difficulty finding inner security when the reassuring person isn't there.")
                    }
                    Axe.INSTINCTS -> buildString {
                        if (reponsesChoix["marquage_habitude_post_sterilisation"] == 0) {
                            append("$nom's urine marking seems to have started during her heat periods, before she was spayed. ")
                            append("The original hormonal cause is gone, but the behavior has turned into an acquired habit — it remains ingrained even though the initial reason no longer exists. ")
                            append("This kind of habitual marking often takes longer to correct than marking linked purely to stress, because a repeated behavior has to be unlearned, rather than simply easing a source of tension. ")
                            append("Targeted behavioral support on this specific point is recommended.")
                            return@buildString
                        }
                        append("$nom's instinctive frustration can have several origins. ")
                        append("The cat is a solitary predator whose needs for hunting, exploration and scratching are deeply ingrained — an environment that does not allow them to be expressed inevitably generates frustration. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("Without access to the outdoors, the cat misses out on much of the natural stimulation that channels these instincts. ")
                        if (reponsesChoix["vie_interieur"] == 2 || reponsesChoix["vie_interieur"] == 3) append("A poorly enriched indoor environment worsens this lack of outlet for its natural instincts. ")
                        val raceCat = reponsesChoix["race_categorie"]
                        if (raceCat == 5 || raceCat == 7) append("Some breeds like the Bengal or Abyssinian have been selected for a very high energy level, which accentuates this need for stimulation. ")
                        append("This is not a character problem but a fundamental need seeking expression, sometimes in undesirable ways for lack of an appropriate alternative.")
                    }
                    Axe.COHABITATION -> buildString {
                        append("$nom's cohabitation difficulties can be explained by several factors. ")
                        append("Cats are territorially sensitive — competition for resources (food, litter box, resting space) is a major source of tension in multi-cat households. ")
                        if (estMaleEntier(reponsesChoix)) append("In an intact male, urine marking and intimidation behaviors are frequent and can fuel conflicts with housemates. ")
                        if (reponsesChoix["changement_recent"] == 2) append("The arrival of a new animal or household member may have disrupted a fragile territorial balance. ")
                        append("Introducing animals to each other too quickly, without a gradual familiarization phase, is one of the most common causes of lasting tension. ")
                        append("In some cases, simply incompatible personalities may also be a factor, independently of human management.")
                    }
                }
            }
            AppLang.DE -> {
                when (axe) {
                    Axe.SECURITE -> buildString {
                        append("Die emotionale Unsicherheit von $nom kann mehrere Ursachen haben. ")
                        append("Oft spielt eine unzureichende frühe Sozialisierung eine Rolle – wenig Kontakt mit Menschen, Geräuschen oder verschiedenen Umgebungen vor der 7. Lebenswoche. ")
                        append("Frühere negative Erlebnisse, selbst einzelne, können dauerhaft prägen, wie eine Katze ihre Welt wahrnimmt. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("Einer reinen Wohnungskatze fehlt es manchmal an abwechslungsreichen Reizen, was ihre Fähigkeit schwächt, mit Neuem umzugehen. ")
                        if (reponsesChoix["age"] == 0) append("Unter einem Jahr baut sich das Sicherheitsgefühl noch auf – eine gewisse Sensibilität ist in diesem Alter normal. ")
                        if (reponsesChoix["changement_recent"] == 2) append("Eine kürzliche große Veränderung kann ihre Orientierungspunkte erschüttert und dieses Gefühl der Unsicherheit verstärkt haben. ")
                        append("In manchen Fällen spielt auch eine genetische Veranlagung eine Rolle, unabhängig vom Erlebten.")
                    }
                    Axe.LIEN -> buildString {
                        append("Das starke Bedürfnis von $nom nach Nähe lässt sich auf verschiedene Weise erklären. ")
                        append("Eine zu frühe Trennung von der Mutter – vor der 8. Woche – kann den Aufbau emotionaler Selbstständigkeit dauerhaft schwächen. ")
                        append("Ein Umfeld, in dem die Katze nie gelernt hat, allein zu bleiben, kann dieses Bedürfnis nach ständiger Anwesenheit ebenfalls verstärken. ")
                        if (reponsesChoix["recherche_proximite"] == 3) append("Ihrem Menschen ständig zu folgen, kann zugleich ein Anzeichen und ein Faktor sein, der diese Abhängigkeit in der Beziehung aufrechterhält. ")
                        if (estMaleEntier(reponsesChoix) || estFemelleEntiere(reponsesChoix)) append("Bei einer unkastrierten Katze können manche Verhaltensweisen auch von den Hormonzyklen beeinflusst werden. ")
                        append("Das ist keine Laune: Es spiegelt eine echte Schwierigkeit wider, inneren Halt zu finden, wenn die beruhigende Bezugsperson fehlt.")
                    }
                    Axe.INSTINCTS -> buildString {
                        if (reponsesChoix["marquage_habitude_post_sterilisation"] == 0) {
                            append("Das Harnmarkieren von $nom scheint während ihrer Rolligkeit vor der Kastration begonnen zu haben. ")
                            append("Die ursprüngliche hormonelle Ursache ist verschwunden, doch das Verhalten hat sich zu einer erlernten Gewohnheit entwickelt – es bleibt bestehen, auch wenn der ursprüngliche Grund nicht mehr existiert. ")
                            append("Eine solche zur Gewohnheit gewordene Markierung lässt sich oft langsamer korrigieren als eine rein stressbedingte, weil ein wiederholtes Verhalten verlernt werden muss, statt nur eine Spannungsquelle zu verringern. ")
                            append("Eine gezielte Verhaltensbegleitung für genau diesen Punkt wird empfohlen.")
                            return@buildString
                        }
                        append("Der instinktive Frust von $nom kann mehrere Ursachen haben. ")
                        append("Die Katze ist ein einzelgängerischer Jäger, dessen Bedürfnisse nach Jagen, Erkunden und Kratzen tief verankert sind – eine Umgebung, die das nicht zulässt, erzeugt unweigerlich Frust. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("Ohne Freigang fehlen der Katze viele natürliche Reize, die diese Instinkte in geordnete Bahnen lenken. ")
                        if (reponsesChoix["vie_interieur"] == 2 || reponsesChoix["vie_interieur"] == 3) append("Eine wenig abwechslungsreich gestaltete Wohnung verstärkt diesen Mangel an Ventilen für ihre natürlichen Instinkte. ")
                        val raceCat = reponsesChoix["race_categorie"]
                        if (raceCat == 5 || raceCat == 7) append("Manche Rassen wie Bengal oder Abessinier wurden auf ein sehr hohes Energieniveau gezüchtet, was dieses Bedürfnis nach Beschäftigung noch verstärkt. ")
                        append("Das ist kein Charakterproblem, sondern ein Grundbedürfnis, das sich ausdrücken will – mangels passender Alternative manchmal auf unerwünschte Weise.")
                    }
                    Axe.COHABITATION -> buildString {
                        append("Die Schwierigkeiten von $nom im Zusammenleben lassen sich durch mehrere Faktoren erklären. ")
                        append("Katzen sind sehr revierbezogen – die Konkurrenz um Ressourcen (Futter, Katzenklo, Ruheplätze) ist in Mehrkatzenhaushalten eine wichtige Spannungsquelle. ")
                        if (estMaleEntier(reponsesChoix)) append("Bei einem unkastrierten Kater sind Harnmarkieren und Einschüchterungsverhalten häufig und können die Konflikte mit den Mitbewohnern verstärken. ")
                        if (reponsesChoix["changement_recent"] == 2) append("Die Ankunft eines neuen Tieres oder eines neuen Familienmitglieds kann ein empfindliches Revier-Gleichgewicht gestört haben. ")
                        append("Eine zu schnelle Zusammenführung der Tiere ohne schrittweise Gewöhnungsphase gehört zu den häufigsten Ursachen anhaltender Spannungen. ")
                        append("In manchen Fällen können auch einfach unvereinbare Persönlichkeiten die Ursache sein, unabhängig vom Umgang des Menschen.")
                    }
                }
            }
            AppLang.ES -> {
                when (axe) {
                    Axe.SECURITE -> buildString {
                        append("La inseguridad emocional de $nom puede tener varios orígenes. ")
                        append("Con frecuencia influye una socialización temprana insuficiente: poco contacto con personas, ruidos o entornos variados antes de las 7 semanas de vida. ")
                        append("Las experiencias negativas pasadas, incluso aisladas, pueden dejar una huella duradera en la forma en que un gato percibe su mundo. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("Un gato que vive solo dentro de casa a veces carece de estímulos variados, lo que debilita su capacidad para afrontar lo nuevo. ")
                        if (reponsesChoix["age"] == 0) append("Antes del año de edad, el sentimiento de seguridad todavía se está construyendo: cierta sensibilidad es normal a esta edad. ")
                        if (reponsesChoix["changement_recent"] == 2) append("Un cambio importante reciente puede haber alterado sus referencias y aumentado este sentimiento de inseguridad. ")
                        append("En algunos casos, una predisposición genética también influye, independientemente de lo vivido.")
                    }
                    Axe.LIEN -> buildString {
                        append("La gran necesidad de cercanía de $nom puede explicarse de varias maneras. ")
                        append("Un destete demasiado temprano, antes de las 8 semanas, puede debilitar de forma duradera la construcción de la autonomía emocional. ")
                        append("Un entorno en el que el gato nunca ha aprendido a quedarse solo también puede reforzar esta necesidad de presencia constante. ")
                        if (reponsesChoix["recherche_proximite"] == 3) append("Seguir constantemente a su humano puede ser a la vez un síntoma y un factor que mantiene esta dependencia afectiva. ")
                        if (estMaleEntier(reponsesChoix) || estFemelleEntiere(reponsesChoix)) append("En un gato sin esterilizar, algunas manifestaciones también pueden estar influidas por los ciclos hormonales. ")
                        append("No es un capricho: refleja una verdadera dificultad para encontrar seguridad interior cuando falta la persona que lo tranquiliza.")
                    }
                    Axe.INSTINCTS -> buildString {
                        if (reponsesChoix["marquage_habitude_post_sterilisation"] == 0) {
                            append("El marcaje con orina de $nom parece haber empezado durante el celo, antes de su esterilización. ")
                            append("La causa hormonal original ha desaparecido, pero el comportamiento se ha convertido en un hábito adquirido: el gesto sigue arraigado aunque la razón inicial ya no exista. ")
                            append("Este tipo de marcaje convertido en hábito suele tardar más en corregirse que un marcaje relacionado solo con el estrés, porque hay que desaprender un gesto repetido en lugar de simplemente reducir una fuente de tensión. ")
                            append("Se recomienda el apoyo de un profesional del comportamiento centrado en este punto concreto.")
                            return@buildString
                        }
                        append("La frustración instintiva de $nom puede tener varios orígenes. ")
                        append("El gato es un depredador solitario cuyas necesidades de cazar, explorar y arañar están profundamente arraigadas: un entorno que no le permite expresarlas genera inevitablemente frustración. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("Sin acceso al exterior, el gato se pierde muchos de los estímulos naturales que canalizan estos instintos. ")
                        if (reponsesChoix["vie_interieur"] == 2 || reponsesChoix["vie_interieur"] == 3) append("Un entorno interior poco enriquecido agrava esta falta de salida para sus instintos naturales. ")
                        val raceCat = reponsesChoix["race_categorie"]
                        if (raceCat == 5 || raceCat == 7) append("Algunas razas, como el bengalí o el abisinio, fueron seleccionadas por un nivel de energía muy alto, lo que acentúa esta necesidad de estimulación. ")
                        append("No es un problema de carácter, sino una necesidad fundamental que busca expresarse, a veces de forma no deseada por falta de una alternativa adecuada.")
                    }
                    Axe.COHABITATION -> buildString {
                        append("Las dificultades de convivencia de $nom pueden explicarse por varios factores. ")
                        append("El gato es muy sensible a su territorio: la competencia por los recursos (comida, arenero, lugar de descanso) es una fuente importante de tensión en los hogares con varios gatos. ")
                        if (estMaleEntier(reponsesChoix)) append("En un macho sin esterilizar, el marcaje con orina y los comportamientos de intimidación son frecuentes y pueden alimentar los conflictos con los otros animales de la casa. ")
                        if (reponsesChoix["changement_recent"] == 2) append("La llegada de un nuevo animal o de un nuevo miembro de la familia puede haber roto un equilibrio territorial frágil. ")
                        append("Una presentación demasiado rápida entre animales, sin una fase de familiarización progresiva, es una de las causas más frecuentes de tensiones duraderas. ")
                        append("En algunos casos, también pueden influir personalidades simplemente incompatibles, independientemente de cómo lo gestionen las personas.")
                    }
                }
            }
            else -> {
                when (axe) {
                    Axe.SECURITE -> buildString {
                        append("L'insécurité émotionnelle de $nom peut avoir plusieurs origines. ")
                        append("Une socialisation précoce insuffisante — peu d'expositions à des humains, des bruits ou des environnements variés avant l'âge de 7 semaines — est souvent en cause. ")
                        append("Des expériences négatives passées, même ponctuelles, peuvent laisser une empreinte durable sur la façon dont un chat perçoit son monde. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("Un chat exclusivement d'intérieur peut parfois manquer de stimulations variées, ce qui fragilise sa capacité à faire face à la nouveauté. ")
                        if (reponsesChoix["age"] == 0) append("À moins d'un an, la construction du sentiment de sécurité est encore en cours — une certaine sensibilité est normale à cet âge. ")
                        if (reponsesChoix["changement_recent"] == 2) append("Un changement important récent peut avoir déstabilisé ses repères et amplifier ce sentiment d'insécurité. ")
                        append("Dans certains cas, une prédisposition génétique joue également un rôle, indépendamment du vécu.")
                    }
                    Axe.LIEN -> buildString {
                        append("Le besoin de proximité intense de $nom peut s'expliquer de plusieurs façons. ")
                        append("Un sevrage trop précoce — avant 8 semaines — peut fragiliser la construction de l'autonomie émotionnelle de façon durable. ")
                        append("Un environnement où le chat n'a jamais appris à rester seul peut aussi renforcer ce besoin de présence constante. ")
                        if (reponsesChoix["recherche_proximite"] == 3) append("Le fait de suivre en permanence son humain peut être à la fois un symptôme et un facteur qui entretient cette dépendance relationnelle. ")
                        if (estMaleEntier(reponsesChoix) || estFemelleEntiere(reponsesChoix)) append("Chez un chat non stérilisé, certaines manifestations peuvent aussi être influencées par les cycles hormonaux. ")
                        append("Ce fonctionnement n'est pas un caprice : il reflète une vraie difficulté à trouver un appui interne en l'absence de la figure rassurante.")
                    }
                    Axe.INSTINCTS -> buildString {
                        if (reponsesChoix["marquage_habitude_post_sterilisation"] == 0) {
                            append("Le marquage urinaire de $nom semble avoir débuté pendant ses chaleurs, avant sa stérilisation. ")
                            append("La cause hormonale d'origine a disparu, mais le comportement s'est transformé en habitude acquise — le geste reste ancré même si la raison initiale n'existe plus. ")
                            append("Ce type de marquage devenu habituel est souvent plus long à corriger qu'un marquage purement lié au stress, car il faut désapprendre un geste répété plutôt que simplement réduire une source de tension. ")
                            append("Un accompagnement comportemental ciblé sur ce point précis est recommandé.")
                            return@buildString
                        }
                        append("La frustration instinctive de $nom peut avoir plusieurs origines. ")
                        append("Le chat est un prédateur solitaire dont les besoins de chasse, d'exploration et de griffage sont profondément ancrés — un environnement qui ne permet pas de les exprimer génère inévitablement de la frustration. ")
                        if (reponsesChoix["acces_exterieur"] == 2) append("L'absence d'accès à l'extérieur prive le chat de nombreuses stimulations naturelles qui canalisent ces instincts. ")
                        if (reponsesChoix["vie_interieur"] == 2 || reponsesChoix["vie_interieur"] == 3) append("Un environnement intérieur peu enrichi aggrave ce manque de débouché pour ses instincts naturels. ")
                        val raceCat = reponsesChoix["race_categorie"]
                        if (raceCat == 5 || raceCat == 7) append("Certaines races comme le Bengal ou l'Abyssin ont été sélectionnées pour un niveau d'énergie très élevé, ce qui accentue ce besoin de stimulation. ")
                        append("Ce n'est pas un problème de caractère mais un besoin fondamental qui cherche à s'exprimer, parfois de façon indésirable faute d'alternative adaptée.")
                    }
                    Axe.COHABITATION -> buildString {
                        append("Les difficultés de cohabitation de $nom peuvent s'expliquer par plusieurs facteurs. ")
                        append("Le chat est territorialement sensible — la compétition pour les ressources (nourriture, litière, espace de repos) est une source de tension majeure en milieu multi-chats. ")
                        if (estMaleEntier(reponsesChoix)) append("Chez un mâle entier, le marquage urinaire et les comportements d'intimidation sont fréquents et peuvent nourrir les conflits avec les cohabitants. ")
                        if (reponsesChoix["changement_recent"] == 2) append("L'arrivée d'un nouvel animal ou d'un nouveau membre du foyer peut avoir rompu un équilibre territorial fragile. ")
                        append("Une introduction trop rapide entre animaux, sans phase de familiarisation progressive, est l'une des causes les plus fréquentes de tensions durables. ")
                        append("Dans certains cas, des personnalités simplement incompatibles peuvent aussi être en cause, indépendamment de la gestion humaine.")
                    }
                }
            }
        }
    }

    fun calculerResultat(questions: List<Question>, reponsesTexte: Map<String, String>, reponsesChoix: Map<String, Int>): ResultatAnalyse {
        val marquageHormonal = reponsesChoix["chaleur_marquage"] == 0
        val securite = calculerPourcentageAxe(Axe.SECURITE, questions, reponsesChoix)
        val lien = calculerPourcentageAxe(Axe.LIEN, questions, reponsesChoix)
        val instincts = if (marquageHormonal) {
            val reponsesAjustees = reponsesChoix.toMutableMap().apply { put("marquage_urinaire", 0) }
            calculerPourcentageAxe(Axe.INSTINCTS, questions, reponsesAjustees)
        } else {
            calculerPourcentageAxe(Axe.INSTINCTS, questions, reponsesChoix)
        }
        val cohabitation = calculerPourcentageAxe(Axe.COHABITATION, questions, reponsesChoix)
        val profil = genererProfilGlobalTraduit(reponsesTexte["nom_chat"].orEmpty(), securite, lien, instincts, cohabitation)
        val contexte = calculerContexte(reponsesChoix)
        val vigilance = calculerNiveauVigilance(questions, reponsesChoix, securite, lien, instincts, cohabitation, contexte)
        val niveauSituation = calculerNiveauSituation(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val problemePrincipal = determinerProblemePrincipal(securite, lien, instincts, cohabitation)
        val planAction = genererPlanActionTraduit(problemePrincipal, reponsesChoix, reponsesTexte["nom_chat"].orEmpty())
        val hypothesePrincipale = detecterHypothesePrincipaleTraduit(reponsesChoix, securite, lien, instincts, cohabitation, contexte)
        val prioriteAction = determinerPrioriteAction(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val facteursAggravants = detecterFacteursAggravantsTraduit(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val facteursProtecteurs = detecterFacteursProtecteursTraduit(reponsesChoix, contexte)
        val prioriteImmediate = construirePrioriteImmediateTraduit(reponsesChoix, contexte, prioriteAction, niveauSituation, reponsesTexte["nom_chat"].orEmpty())
        val explicationResultat = construireExplicationResultat(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val syntheseAvancee = genererSyntheseAvancee(nomChatAffiche(reponsesTexte["nom_chat"].orEmpty()), hypothesePrincipale, prioriteAction, facteursAggravants, facteursProtecteurs)
        val originesPossibles = genererOriginesPossibles(
            reponsesTexte["nom_chat"].orEmpty(),
            problemePrincipal,
            securite, lien, instincts, cohabitation,
            reponsesChoix
        )
        val raceCategorieTexte = reponsesChoix["race_categorie"]?.let { categoriesRaces.getOrNull(it)?.nom }
        return ResultatAnalyse(
            peur = securite, attachement = lien, impulsivite = instincts, reactivite = cohabitation,
            niveauPeur = calculerNiveauAxe(securite), niveauAttachement = calculerNiveauAxe(lien),
            niveauImpulsivite = calculerNiveauAxe(instincts), niveauReactivite = calculerNiveauAxe(cohabitation),
            profil = profil, vigilance = vigilance, niveauSituation = niveauSituation, contexte = contexte,
            problemePrincipal = problemePrincipal,
            problemesImportants = determinerProblemesImportants(securite, lien, instincts, cohabitation),
            explicationPrincipale = explicationProblemeTraduit(problemePrincipal, securite, lien, instincts, cohabitation, reponsesChoix),
            conseilPrincipal = conseilPrincipalTraduit(problemePrincipal, securite, lien, instincts, cohabitation),
            conseilsPratiques = genererConseilsPratiquesToTraduit(reponsesTexte["nom_chat"].orEmpty(), reponsesChoix, securite, lien, instincts, cohabitation),
            planAction = planAction,
            messageSituation = genererMessageSituationTraduit(niveauSituation, reponsesTexte["nom_chat"].orEmpty()),
            raisonSituation = genererRaisonSituationTraduit(reponsesChoix, contexte, niveauSituation),
            messageAide = genererMessageAideTraduit(reponsesChoix, contexte, niveauSituation, reponsesTexte["nom_chat"].orEmpty(), securite, lien, instincts, cohabitation),
            apparitionBrutale = reponsesChoix["apparition"] == 1,
            aDejaMordu = reponsesChoix["a_deja_griffe_mordu"] == 1,
            hypothesePrincipale = hypothesePrincipale, prioriteAction = prioriteAction,
            prioriteImmediate = prioriteImmediate, explicationResultat = explicationResultat,
            facteursAggravants = facteursAggravants, facteursProtecteurs = facteursProtecteurs,
            syntheseAvancee = syntheseAvancee, raceCategorie = raceCategorieTexte, racePrecise = null,
            originesPossibles = originesPossibles,
            marquageHabitudePostSterilisation = reponsesChoix["marquage_habitude_post_sterilisation"] == 0,
            suspicionDeclinCognitif = reponsesChoix["age"] == 3 &&
                    (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2),
            cibleAgressionAnimal = reponsesChoix["cible_agression"] == 1,
            lieuResidence = reponsesChoix["lieu_residence"]
        )
    }

    fun titreSectionPourQuestion(questionId: String): String = titreSectionTraduit(questionId)

    fun aideQuestion(questionId: String): String? = aideQuestionTraduit(questionId)
}


// ═══════════════════════════════════════════════════════════
// QUESTIONS
// ═══════════════════════════════════════════════════════════

fun questionsApplication(): List<Question> {
    return listOf(

        QuestionTexte("nom_chat", tr("Quel est le prénom de votre chat ?", "What is your cat's name?", "Wie heißt Ihre Katze?", "¿Cómo se llama tu gato?")),

        QuestionChoix("race_categorie",
            tr("À quelle famille de races appartient votre chat ?", "Which breed family does your cat belong to?", "Zu welcher Rassegruppe gehört Ihre Katze?", "¿A qué grupo de razas pertenece tu gato?"),
            trList(
                listOf("Européen / Gouttière", "Maine Coon", "Persan", "Siamois", "Ragdoll",
                    "Bengal", "British Shorthair", "Abyssin", "Sacré de Birmanie", "Autre race / inconnu"),
                listOf("European / Mixed breed", "Maine Coon", "Persian", "Siamese", "Ragdoll",
                    "Bengal", "British Shorthair", "Abyssinian", "Birman", "Other breed / unknown"),
                listOf("Europäisch Kurzhaar / Mischling", "Maine Coon", "Perser", "Siam", "Ragdoll",
                    "Bengal", "Britisch Kurzhaar", "Abessinier", "Heilige Birma", "Andere Rasse / unbekannt")
            ,
                listOf("Europeo / común", "Maine coon", "Persa", "Siamés", "Ragdoll", "Bengalí", "British shorthair", "Abisinio", "Sagrado de Birmania", "Otra raza / desconocida"))),

        QuestionChoix("age",
            tr("Quel âge a votre chat ?", "How old is your cat?", "Wie alt ist Ihre Katze?", "¿Qué edad tiene tu gato?"),
            trList(listOf("Moins d'1 an (chaton)", "Entre 1 et 3 ans", "Entre 4 et 8 ans", "9 ans et plus (senior)"), listOf("Under 1 year (kitten)", "Between 1 and 3 years", "Between 4 and 8 years", "9 years and over (senior)"), listOf("Unter 1 Jahr (Kätzchen)", "Zwischen 1 und 3 Jahren", "Zwischen 4 und 8 Jahren", "9 Jahre und älter (Senior)"),
                listOf("Menos de 1 año (gatito)", "Entre 1 y 3 años", "Entre 4 y 8 años", "9 años o más (sénior)"))),

        QuestionChoix("senior_desorientation",
            tr("Votre chat semble-t-il parfois désorienté ou perdu dans des endroits qu'il connaît bien ?", "Does your cat sometimes seem disoriented or lost in places it knows well?", "Wirkt Ihre Katze manchmal orientierungslos oder verloren an Orten, die sie gut kennt?", "¿Tu gato parece a veces desorientado o perdido en lugares que conoce bien?"),
            trList(listOf("Non, jamais", "Parfois, occasionnellement", "Oui, régulièrement"), listOf("No, never", "Sometimes, occasionally", "Yes, regularly"), listOf("Nein, nie", "Manchmal, gelegentlich", "Ja, regelmäßig"),
                listOf("No, nunca", "A veces, ocasionalmente", "Sí, con regularidad"))),

        QuestionChoix("senior_vocalise_nocturne",
            tr("Depuis quelque temps, votre chat vocalise-t-il ou erre-t-il la nuit sans raison apparente (pas de faim, pas de demande d'attention identifiable) ?", "Has your cat recently been vocalizing or wandering at night for no apparent reason (not hungry, not obviously seeking attention)?", "Miaut oder wandert Ihre Katze seit einiger Zeit nachts ohne erkennbaren Grund umher (kein Hunger, kein erkennbarer Wunsch nach Aufmerksamkeit)?", "Desde hace un tiempo, ¿tu gato maúlla o deambula por la noche sin motivo aparente (no tiene hambre ni busca claramente atención)?"),
            trList(listOf("Non, jamais", "Parfois, occasionnellement", "Oui, régulièrement"), listOf("No, never", "Sometimes, occasionally", "Yes, regularly"), listOf("Nein, nie", "Manchmal, gelegentlich", "Ja, regelmäßig"),
                listOf("No, nunca", "A veces, ocasionalmente", "Sí, con regularidad"))),

        QuestionChoix("sterilise",
            tr("Votre chat est :", "Your cat is:", "Ihre Katze ist:", "Tu gato es:"),
            trList(listOf("Un mâle stérilisé", "Une femelle stérilisée", "Un mâle entier", "Une femelle entière"), listOf("A neutered male", "A spayed female", "An intact male", "An intact female"), listOf("Ein kastrierter Kater", "Eine kastrierte Katze", "Ein unkastrierter Kater", "Eine unkastrierte Katze"),
                listOf("Un macho esterilizado", "Una hembra esterilizada", "Un macho sin esterilizar", "Una hembra sin esterilizar"))),

        QuestionChoix("acces_exterieur",
            tr("Votre chat a-t-il accès à l'extérieur ?", "Does your cat have access to the outdoors?", "Hat Ihre Katze Freigang?", "¿Tu gato tiene acceso al exterior?"),
            trList(listOf("Oui, librement", "Oui, de façon contrôlée (balcon sécurisé, jardin surveillé)", "Non, uniquement en intérieur"), listOf("Yes, freely", "Yes, in a controlled way (secure balcony, supervised garden)", "No, indoors only"), listOf("Ja, frei", "Ja, kontrolliert (gesicherter Balkon, beaufsichtigter Garten)", "Nein, nur in der Wohnung"),
                listOf("Sí, libremente", "Sí, de forma controlada (balcón protegido, jardín vigilado)", "No, solo dentro de casa"))),

        QuestionChoix("vie_interieur",
            tr("Si votre chat est d'intérieur, comment décririez-vous son environnement ?", "If your cat is indoors, how would you describe its environment?", "Wenn Ihre Katze in der Wohnung lebt, wie würden Sie ihre Umgebung beschreiben?", "Si tu gato vive dentro de casa, ¿cómo describirías su entorno?"),
            trList(
                listOf("Enrichi (griffoirs, hauteurs, jeux variés, fenêtres accessibles)",
                    "Correct mais peut mieux faire", "Peu stimulant",
                    "Je ne sais pas vraiment", "Mon chat a accès à l'extérieur"),
                listOf("Enriched (scratching posts, high perches, varied toys, accessible windows)",
                    "Decent but could be better", "Not very stimulating",
                    "I'm not really sure", "My cat has access to the outdoors"),
                listOf("Abwechslungsreich gestaltet (Kratzbäume, erhöhte Plätze, abwechslungsreiches Spielzeug, zugängliche Fenster)",
                    "Ordentlich, aber ausbaufähig", "Wenig anregend",
                    "Ich weiß es nicht genau", "Meine Katze hat Freigang")
            ,
                listOf("Enriquecido (rascadores, zonas altas, juguetes variados, ventanas accesibles)", "Correcto, pero mejorable", "Poco estimulante", "No lo sé muy bien", "Mi gato tiene acceso al exterior"))),

        QuestionChoix("reaction_stress_ponctuel",
            tr("Face à une situation stressante ponctuelle (bruit fort, transport, vétérinaire...), votre chat :", "Faced with a one-off stressful situation (loud noise, transport, vet visit...), your cat:", "In einer einzelnen Stresssituation (lautes Geräusch, Transport, Tierarzt …) reagiert Ihre Katze so:", "Ante una situación estresante puntual (ruido fuerte, transporte, veterinario...), tu gato:"),
            trList(
                listOf("Il reste calme ou récupère vite",
                    "Il sursaute ou s'agite mais récupère en quelques minutes",
                    "Il se cache et met longtemps à revenir",
                    "Il panique totalement et reste perturbé longtemps"),
                listOf("Stays calm or recovers quickly",
                    "Startles or gets agitated but recovers within a few minutes",
                    "Hides and takes a long time to come back",
                    "Panics completely and stays unsettled for a long time"),
                listOf("Sie bleibt ruhig oder erholt sich schnell",
                    "Sie erschrickt oder wird unruhig, erholt sich aber innerhalb weniger Minuten",
                    "Sie versteckt sich und braucht lange, um wiederzukommen",
                    "Sie gerät völlig in Panik und bleibt lange verunsichert")
            ,
                listOf("Se queda tranquilo o se recupera rápido", "Se sobresalta o se agita, pero se recupera en pocos minutos", "Se esconde y tarda mucho en volver", "Entra en pánico total y se queda alterado mucho tiempo")),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("reaction_inconnu",
            tr("Comment votre chat réagit-il face à une personne inconnue ?", "How does your cat react to a stranger?", "Wie reagiert Ihre Katze auf einen fremden Menschen?", "¿Cómo reacciona tu gato ante una persona desconocida?"),
            trList(
                listOf("Il s'approche avec curiosité ou reste indifférent",
                    "Il observe de loin prudemment puis s'approche parfois",
                    "Il se cache pour toute la durée de la visite",
                    "Il montre des signes d'agitation ou d'agressivité"),
                listOf("It approaches with curiosity or stays indifferent",
                    "It observes cautiously from afar then sometimes approaches",
                    "It hides for the entire duration of the visit",
                    "It shows signs of agitation or aggression"),
                listOf("Sie nähert sich neugierig oder bleibt gleichgültig",
                    "Sie beobachtet vorsichtig aus der Ferne und nähert sich manchmal",
                    "Sie versteckt sich während des ganzen Besuchs",
                    "Sie zeigt Anzeichen von Unruhe oder Aggression")
            ,
                listOf("Se acerca con curiosidad o se muestra indiferente", "Observa con prudencia desde lejos y a veces se acerca", "Se esconde durante toda la visita", "Muestra señales de agitación o de agresividad")),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),

        QuestionChoix("cache_souvent",
            tr("À quelle fréquence votre chat se cache-t-il ou s'isole-t-il ?", "How often does your cat hide or isolate itself?", "Wie oft versteckt oder isoliert sich Ihre Katze?", "¿Con qué frecuencia se esconde o se aísla tu gato?"),
            trList(
                listOf("Rarement — il est généralement visible et accessible",
                    "Parfois, notamment quand il y a du monde ou du bruit",
                    "Souvent, plusieurs fois par jour",
                    "La plupart du temps — il est difficile à trouver"),
                listOf("Rarely — it is generally visible and accessible",
                    "Sometimes, especially when there are people or noise",
                    "Often, several times a day",
                    "Most of the time — it is hard to find"),
                listOf("Selten – sie ist meist sichtbar und zugänglich",
                    "Manchmal, vor allem wenn Menschen da sind oder es laut ist",
                    "Oft, mehrmals am Tag",
                    "Die meiste Zeit – sie ist schwer zu finden")
            ,
                listOf("Rara vez: suele estar visible y accesible", "A veces, sobre todo cuando hay gente o ruido", "A menudo, varias veces al día", "Casi siempre: cuesta encontrarlo")),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4)),

        QuestionChoix("adaptation_changement",
            tr("Comment votre chat s'adapte-t-il aux changements (déménagement, nouveau meuble, visiteurs) ?", "How does your cat adapt to changes (move, new furniture, visitors)?", "Wie passt sich Ihre Katze an Veränderungen an (Umzug, neue Möbel, Besuch)?", "¿Cómo se adapta tu gato a los cambios (mudanza, mueble nuevo, visitas)?"),
            trList(
                listOf("Très bien — il s'adapte rapidement",
                    "Correctement — quelques jours de prudence puis ça passe",
                    "Difficilement — il met plusieurs semaines à récupérer",
                    "Très mal — chaque changement provoque une crise durable"),
                listOf("Very well — it adapts quickly",
                    "Fine — a few days of caution then it passes",
                    "With difficulty — it takes several weeks to recover",
                    "Very poorly — every change causes a lasting crisis"),
                listOf("Sehr gut – sie passt sich schnell an",
                    "Gut – ein paar Tage Vorsicht, dann legt es sich",
                    "Schwer – sie braucht mehrere Wochen, um sich zu erholen",
                    "Sehr schlecht – jede Veränderung löst eine anhaltende Krise aus")
            ,
                listOf("Muy bien: se adapta rápido", "Bien: unos días de prudencia y se le pasa", "Con dificultad: tarda varias semanas en recuperarse", "Muy mal: cada cambio provoca una crisis duradera")),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),

        QuestionChoix("surtoilettage",
            tr("Avez-vous observé un surtoilettage (zones de poils clairsemés, léchages répétitifs excessifs) ?", "Have you noticed over-grooming (sparse fur areas, repeated excessive licking)?", "Haben Sie übermäßige Fellpflege bemerkt (Stellen mit schütterem Fell, wiederholtes übermäßiges Lecken)?", "¿Has notado un exceso de acicalamiento (zonas con poco pelo, lamidos repetidos y excesivos)?"),
            trList(
                listOf("Non, son pelage est normal",
                    "Parfois, sans que ça laisse de traces visibles",
                    "Oui, avec des zones légèrement clairsemées"),
                listOf("No, its coat is normal",
                    "Sometimes, without leaving visible marks",
                    "Yes, with slightly sparse areas"),
                listOf("Nein, ihr Fell ist normal",
                    "Manchmal, ohne sichtbare Spuren",
                    "Ja, mit leicht schütteren Stellen")
            ,
                listOf("No, su pelaje es normal", "A veces, sin dejar marcas visibles", "Sí, con zonas con algo menos de pelo")),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3), signalAlerte = true),

        QuestionChoix("recherche_proximite",
            tr("Votre chat recherche-t-il la proximité physique avec vous (vous suit, dort collé, cherche à être sur vous), le jour comme la nuit ?", "Does your cat seek physical closeness with you (following you around, sleeping close, being on you), day and night?", "Sucht Ihre Katze körperliche Nähe zu Ihnen (folgt Ihnen, schläft eng bei Ihnen, will auf Ihnen liegen), tagsüber wie nachts?", "¿Tu gato busca la cercanía física contigo (te sigue, duerme pegado a ti, quiere estar encima de ti), tanto de día como de noche?"),
            trList(
                listOf("Non, il est plutôt indépendant, le jour comme la nuit",
                    "Parfois, selon son humeur",
                    "Souvent — il aime rester près de vous",
                    "Toujours — il ne vous quitte pratiquement pas et s'agite si séparé"),
                listOf("No, it is fairly independent, day and night",
                    "Sometimes, depending on its mood",
                    "Often — it likes to stay close to you",
                    "Always — it barely leaves your side and gets agitated if separated"),
                listOf("Nein, sie ist eher selbstständig, tagsüber wie nachts",
                    "Manchmal, je nach Laune",
                    "Oft – sie bleibt gern in Ihrer Nähe",
                    "Immer – sie weicht Ihnen kaum von der Seite und wird unruhig, wenn sie getrennt ist")
            ,
                listOf("No, es más bien independiente, de día y de noche", "A veces, según su estado de ánimo", "A menudo: le gusta estar cerca de ti", "Siempre: casi no se separa de ti y se agita si lo separan")),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 3)),

        QuestionChoix("reaction_absence",
            tr("Comment votre chat se comporte-t-il quand vous êtes absent(e) ?", "How does your cat behave when you are away?", "Wie verhält sich Ihre Katze, wenn Sie nicht da sind?", "¿Cómo se comporta tu gato cuando no estás?"),
            trList(
                listOf("Il semble gérer sereinement",
                    "Il peut vocaliser un peu à votre départ mais se calme",
                    "Il vocalise ou s'agite de façon notable",
                    "Il montre des signes de détresse marqués (agitation intense, vocalises fortes et prolongées)",
                    "Je ne sais pas"),
                listOf("It seems to manage calmly",
                    "It may vocalize a little when you leave but settles down",
                    "It vocalizes or becomes notably agitated",
                    "It shows clear signs of distress (intense agitation, prolonged loud vocalizing)",
                    "I don't know"),
                listOf("Sie scheint ruhig damit zurechtzukommen",
                    "Sie miaut vielleicht ein wenig, wenn Sie gehen, beruhigt sich aber",
                    "Sie miaut oder wird deutlich unruhig",
                    "Sie zeigt deutliche Anzeichen von Not (starke Unruhe, lautes und anhaltendes Miauen)",
                    "Ich weiß es nicht")
            ,
                listOf("Parece llevarlo con tranquilidad", "Puede maullar un poco cuando te vas, pero se calma", "Maúlla o se agita de forma notable", "Muestra señales claras de angustia (agitación intensa, maullidos fuertes y prolongados)", "No lo sé")),
            axe = Axe.LIEN, scoreParOption = listOf(0, 1, 2, 4, 0), signalAlerte = true),

        QuestionChoix("proprete_stress",
            tr("Votre chat a-t-il déjà fait ses besoins en dehors de sa litière ?", "Has your cat ever relieved itself outside its litter box?", "Hat sich Ihre Katze schon einmal außerhalb ihres Katzenklos gelöst?", "¿Tu gato ha hecho alguna vez sus necesidades fuera del arenero?"),
            trList(
                listOf("Non, jamais",
                    "Très rarement, dans des circonstances exceptionnelles",
                    "Occasionnellement, souvent lié à un événement stressant",
                    "Régulièrement"),
                listOf("No, never",
                    "Very rarely, in exceptional circumstances",
                    "Occasionally, often linked to a stressful event",
                    "Regularly"),
                listOf("Nein, nie",
                    "Sehr selten, unter außergewöhnlichen Umständen",
                    "Gelegentlich, oft im Zusammenhang mit einem stressigen Ereignis",
                    "Regelmäßig")
            ,
                listOf("No, nunca", "Muy rara vez, en circunstancias excepcionales", "De vez en cuando, a menudo tras un hecho estresante", "Con regularidad")),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 4), signalAlerte = true),

        QuestionChoix("proprete_type",
            tr("Il s'agit plutôt de :", "Is it mainly:", "Es handelt sich eher um:", "Se trata más bien de:"),
            trList(listOf("Urine", "Selles", "Les deux"), listOf("Urine", "Stools", "Both"), listOf("Urin", "Kot", "Beides"),
                listOf("Orina", "Heces", "Ambas"))),

        QuestionChoix("demande_attention_vocale",
            tr("Votre chat vocalise-t-il de façon insistante pour obtenir votre attention (que vous soyez présent(e) ou après une absence) ?", "Does your cat vocalize insistently to get your attention (whether you are present or after an absence)?", "Miaut Ihre Katze beharrlich, um Ihre Aufmerksamkeit zu bekommen (während Sie da sind oder nach einer Abwesenheit)?", "¿Tu gato maúlla con insistencia para conseguir tu atención (cuando estás presente o después de una ausencia)?"),
            trList(
                listOf("Non, rarement — il accepte facilement d'attendre",
                    "Il vocalise parfois pour demander de l'attention, puis se calme",
                    "Il insiste fortement (miaule, fait des bêtises) pour attirer l'attention",
                    "Il peut devenir agité ou agressif si ignoré"),
                listOf("No, rarely — it easily accepts waiting",
                    "It sometimes vocalizes to ask for attention, then calms down",
                    "It insists strongly (meows, causes trouble) to get attention",
                    "It can become agitated or aggressive if ignored"),
                listOf("Nein, selten – sie kann gut warten",
                    "Sie miaut manchmal, um Aufmerksamkeit zu fordern, und beruhigt sich dann",
                    "Sie bleibt sehr hartnäckig (miaut, stellt etwas an), um Aufmerksamkeit zu bekommen",
                    "Sie kann unruhig oder aggressiv werden, wenn sie ignoriert wird")
            ,
                listOf("No, rara vez: acepta esperar sin problema", "A veces maúlla para pedir atención y luego se calma", "Insiste mucho (maúlla, hace travesuras) para llamar la atención", "Puede agitarse o volverse agresivo si lo ignoras")),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),

        QuestionChoix("jeu_chasse",
            tr("Votre chat joue/chasse-t-il activement (épier, bondir, attraper des jouets ou petits objets) ?", "Does your cat actively play/hunt (stalking, pouncing, catching toys or small objects)?", "Spielt oder jagt Ihre Katze aktiv (Anschleichen, Anspringen, Fangen von Spielzeug oder kleinen Gegenständen)?", "¿Tu gato juega o caza activamente (acecha, salta, atrapa juguetes u objetos pequeños)?"),
            trList(
                listOf("Oui, avec enthousiasme — il initie lui-même des sessions",
                    "Oui, s'il est sollicité",
                    "Peu — il s'ennuie rapidement ou montre peu d'intérêt",
                    "Non, aucun intérêt ni comportement de ce type"),
                listOf("Yes, enthusiastically — it initiates sessions itself",
                    "Yes, when encouraged",
                    "A little — it gets bored quickly or shows little interest",
                    "No, no interest or behavior of this kind"),
                listOf("Ja, begeistert – sie beginnt selbst Spielrunden",
                    "Ja, wenn man sie dazu anregt",
                    "Wenig – sie langweilt sich schnell oder zeigt wenig Interesse",
                    "Nein, kein Interesse und kein solches Verhalten")
            ,
                listOf("Sí, con entusiasmo: él mismo inicia los juegos", "Sí, si se le anima", "Poco: se aburre rápido o muestra poco interés", "No, ningún interés ni comportamiento de este tipo")),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("griffage_surfaces",
            tr("Votre chat griffe-t-il des surfaces non autorisées (meubles, canapé, moquette) ?", "Does your cat scratch surfaces it shouldn't (furniture, sofa, carpet)?", "Kratzt Ihre Katze an nicht erlaubten Flächen (Möbel, Sofa, Teppich)?", "¿Tu gato araña superficies no permitidas (muebles, sofá, alfombra)?"),
            trList(
                listOf("Non ou très rarement — il utilise ses griffoirs",
                    "Parfois les meubles en plus des griffoirs",
                    "Souvent les meubles malgré les griffoirs disponibles",
                    "Il ne griffe que les meubles, les griffoirs ne l'intéressent pas"),
                listOf("No or very rarely — it uses its scratching posts",
                    "Sometimes furniture in addition to scratching posts",
                    "Often on furniture despite available scratching posts",
                    "It only scratches furniture, scratching posts don't interest it"),
                listOf("Nein oder sehr selten – sie nutzt ihre Kratzbäume",
                    "Manchmal an Möbeln, zusätzlich zu den Kratzbäumen",
                    "Oft an Möbeln, obwohl Kratzbäume vorhanden sind",
                    "Sie kratzt nur an Möbeln, Kratzbäume interessieren sie nicht")
            ,
                listOf("No o muy rara vez: usa sus rascadores", "A veces los muebles, además de los rascadores", "A menudo los muebles, aunque tiene rascadores", "Solo araña los muebles, los rascadores no le interesan")),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("hyperactivite_nocturne",
            tr("Votre chat présente-t-il une hyperactivité nocturne (courses, sauts, vocalisations la nuit) ?", "Does your cat show nocturnal hyperactivity (running, jumping, vocalizing at night)?", "Ist Ihre Katze nachts überaktiv (Rennen, Springen, Miauen in der Nacht)?", "¿Tu gato tiene hiperactividad nocturna (carreras, saltos, maullidos por la noche)?"),
            trList(
                listOf("Non, il est calme la nuit", "Parfois, occasionnellement",
                    "Souvent — cela perturbe régulièrement votre sommeil", "Toutes les nuits — c'est un problème important"),
                listOf("No, it is calm at night", "Sometimes, occasionally",
                    "Often — it regularly disrupts your sleep", "Every night — it is a significant problem"),
                listOf("Nein, sie ist nachts ruhig", "Manchmal, gelegentlich",
                    "Oft – sie stört regelmäßig Ihren Schlaf", "Jede Nacht – das ist ein großes Problem")
            ,
                listOf("No, está tranquilo por la noche", "A veces, ocasionalmente", "A menudo: altera tu sueño con regularidad", "Todas las noches: es un problema importante")),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),

        QuestionChoix("comportement_alimentaire",
            tr("Comment décririez-vous le comportement alimentaire de votre chat ?", "How would you describe your cat's eating behavior?", "Wie würden Sie das Fressverhalten Ihrer Katze beschreiben?", "¿Cómo describirías la conducta alimentaria de tu gato?"),
            trList(
                listOf("Normal — il mange bien, à son rythme",
                    "Il mange très vite ou réclame souvent",
                    "Il vole de la nourriture ou fouille les poubelles",
                    "Il a des variations importantes d'appétit (refuse de manger ou mange de façon compulsive)"),
                listOf("Normal — it eats well, at its own pace",
                    "It eats very fast or often begs",
                    "It steals food or rummages through bins",
                    "It has significant appetite variations (refuses to eat or eats compulsively)"),
                listOf("Normal – sie frisst gut, in ihrem eigenen Tempo",
                    "Sie frisst sehr schnell oder bettelt oft",
                    "Sie stiehlt Futter oder durchwühlt den Mülleimer",
                    "Sie hat starke Appetitschwankungen (verweigert das Futter oder frisst zwanghaft)")
            ,
                listOf("Normal: come bien, a su ritmo", "Come muy rápido o pide comida a menudo", "Roba comida o revuelve la basura", "Tiene cambios importantes de apetito (rechaza la comida o come de forma compulsiva)")),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("destruction_ennui",
            tr("Votre chat provoque-t-il des destructions ou dégâts, notamment en votre absence ?", "Does your cat cause destruction or damage, especially in your absence?", "Richtet Ihre Katze Zerstörung oder Schäden an, vor allem in Ihrer Abwesenheit?", "¿Tu gato causa destrozos o daños, sobre todo cuando no estás?"),
            trList(
                listOf("Non, jamais", "Rarement, quelques petits incidents",
                    "Parfois — objets renversés, plantes abîmées",
                    "Souvent — les dégâts sont importants et réguliers"),
                listOf("No, never", "Rarely, a few minor incidents",
                    "Sometimes — objects knocked over, plants damaged",
                    "Often — the damage is significant and regular"),
                listOf("Nein, nie", "Selten, ein paar kleinere Vorfälle",
                    "Manchmal – umgeworfene Gegenstände, beschädigte Pflanzen",
                    "Oft – die Schäden sind erheblich und regelmäßig")
            ,
                listOf("No, nunca", "Rara vez, algunos pequeños incidentes", "A veces: objetos tirados, plantas estropeadas", "A menudo: los daños son importantes y frecuentes")),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("marquage_urinaire",
            tr("Votre chat pratique-t-il le marquage urinaire (debout, sur des surfaces verticales) ?", "Does your cat urine-mark (standing up, on vertical surfaces)?", "Markiert Ihre Katze mit Urin (im Stehen, an senkrechten Flächen)?", "¿Tu gato hace marcaje con orina (de pie, sobre superficies verticales)?"),
            trList(
                listOf("Non, jamais", "Rarement, dans des situations de stress identifiées",
                    "Oui, de temps en temps", "Oui, fréquemment"),
                listOf("No, never", "Rarely, in identified stressful situations",
                    "Yes, from time to time", "Yes, frequently"),
                listOf("Nein, nie", "Selten, in erkennbar stressigen Situationen",
                    "Ja, ab und zu", "Ja, häufig")
            ,
                listOf("No, nunca", "Rara vez, en situaciones de estrés identificadas", "Sí, de vez en cuando", "Sí, con frecuencia")),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("chaleur_marquage",
            tr("Ce marquage a-t-il lieu principalement pendant ses chaleurs (périodes où elle réclame, miaule fort, se frotte beaucoup) ?", "Does this marking happen mainly during her heat periods (times when she calls, meows loudly, rubs a lot)?", "Geschieht dieses Markieren vor allem während ihrer Rolligkeit (Phasen, in denen sie ruft, laut miaut, sich viel reibt)?", "¿Este marcaje ocurre sobre todo durante el celo (periodos en los que llama, maúlla fuerte y se frota mucho)?"),
            trList(listOf("Oui, principalement pendant les chaleurs", "Non, à d'autres moments aussi", "Je ne sais pas"), listOf("Yes, mainly during heat periods", "No, at other times too", "I don't know"), listOf("Ja, vor allem während der Rolligkeit", "Nein, auch zu anderen Zeiten", "Ich weiß es nicht"),
                listOf("Sí, sobre todo durante el celo", "No, también en otros momentos", "No lo sé"))),

        QuestionChoix("marquage_habitude_post_sterilisation",
            tr("Ce marquage a-t-il commencé avant sa stérilisation ?", "Did this marking start before she was spayed?", "Hat dieses Markieren vor ihrer Kastration begonnen?", "¿Este marcaje empezó antes de su esterilización?"),
            trList(listOf("Oui, et ça a continué depuis", "Non, c'est apparu après la stérilisation", "Je ne sais pas / je l'ai adoptée déjà stérilisée"), listOf("Yes, and it has continued since", "No, it appeared after spaying", "I don't know / I adopted her already spayed"), listOf("Ja, und es hat seitdem angehalten", "Nein, es ist nach der Kastration aufgetreten", "Ich weiß es nicht / ich habe sie bereits kastriert übernommen"),
                listOf("Sí, y ha continuado desde entonces", "No, apareció después de la esterilización", "No lo sé / la adopté ya esterilizada"))),

        QuestionChoix("relation_autres_chats",
            tr("Si vous avez plusieurs chats, comment se passent leurs relations ?", "If you have several cats, how do they get along?", "Wenn Sie mehrere Katzen haben, wie ist ihr Verhältnis zueinander?", "Si tienes varios gatos, ¿cómo se llevan entre ellos?"),
            trList(
                listOf("Bonne entente générale, voire affection mutuelle",
                    "Coexistence neutre — ils s'ignorent",
                    "Tensions fréquentes mais sans agression physique",
                    "Conflits réguliers avec agressions",
                    "Je n'ai qu'un seul chat"),
                listOf("They generally get along well, even showing mutual affection",
                    "Neutral coexistence — they ignore each other",
                    "Frequent tensions but no physical aggression",
                    "Regular conflicts with aggression",
                    "I only have one cat"),
                listOf("Meist gutes Einvernehmen, sogar gegenseitige Zuneigung",
                    "Neutrales Nebeneinander – sie ignorieren sich",
                    "Häufige Spannungen, aber keine körperlichen Angriffe",
                    "Regelmäßige Konflikte mit Angriffen",
                    "Ich habe nur eine Katze")
            ,
                listOf("Se llevan bien en general, incluso con cariño mutuo", "Conviven de forma neutra: se ignoran", "Tensiones frecuentes, pero sin agresiones físicas", "Conflictos frecuentes con agresiones", "Solo tengo un gato")),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),

        QuestionChoix("relation_enfants",
            tr("Si des enfants sont présents, comment votre chat réagit-il ?", "If children are present, how does your cat react?", "Wenn Kinder im Haushalt leben, wie reagiert Ihre Katze?", "Si hay niños en casa, ¿cómo reacciona tu gato?"),
            trList(
                listOf("Très bien — il interagit ou les tolère sereinement",
                    "Correctement — il garde ses distances mais sans tension",
                    "Il fuit ou s'isole quand les enfants sont là",
                    "Il peut réagir de façon agressive (griffe, mord)",
                    "Pas d'enfants dans le foyer"),
                listOf("Very well — it interacts or tolerates them calmly",
                    "Fine — it keeps its distance but without tension",
                    "It flees or isolates itself when children are around",
                    "It can react aggressively (scratches, bites)",
                    "No children in the household"),
                listOf("Sehr gut – sie spielt mit ihnen oder duldet sie gelassen",
                    "Gut – sie hält Abstand, aber ohne Anspannung",
                    "Sie flieht oder zieht sich zurück, wenn Kinder da sind",
                    "Sie kann aggressiv reagieren (Kratzen, Beißen)",
                    "Keine Kinder im Haushalt")
            ,
                listOf("Muy bien: interactúa con ellos o los tolera con calma", "Bien: guarda las distancias, pero sin tensión", "Huye o se aísla cuando están los niños", "Puede reaccionar de forma agresiva (araña, muerde)", "No hay niños en casa")),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),

        QuestionChoix("agressivite_caresses",
            tr("Votre chat mord-il ou griffe-t-il pendant les caresses ou les jeux ?", "Does your cat bite or scratch during petting or play?", "Beißt oder kratzt Ihre Katze beim Streicheln oder Spielen?", "¿Tu gato muerde o araña durante las caricias o el juego?"),
            trList(
                listOf("Non, jamais",
                    "Rarement — seulement quand les signaux d'alerte ont été ignorés",
                    "Parfois, de façon imprévisible",
                    "Souvent — les interactions physiques sont difficiles à gérer"),
                listOf("No, never",
                    "Rarely — only when warning signals have been ignored",
                    "Sometimes, unpredictably",
                    "Often — physical interactions are difficult to manage"),
                listOf("Nein, nie",
                    "Selten – nur wenn Warnsignale übersehen wurden",
                    "Manchmal, unvorhersehbar",
                    "Oft – körperliche Kontakte sind schwer zu bewältigen")
            ,
                listOf("No, nunca", "Rara vez: solo cuando se han ignorado las señales de aviso", "A veces, de forma imprevisible", "A menudo: las interacciones físicas son difíciles de manejar")),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("a_deja_griffe_mordu",
            tr("Votre chat a-t-il déjà griffé ou mordu quelqu'un (vous, un proche, un enfant) ?", "Has your cat ever scratched or bitten someone (you, a family member, a child)?", "Hat Ihre Katze schon einmal jemanden gekratzt oder gebissen (Sie, ein Familienmitglied, ein Kind)?", "¿Tu gato ha arañado o mordido alguna vez a alguien (a ti, a un familiar, a un niño)?"),
            trList(listOf("Non, jamais", "Oui, cela s'est déjà produit"), listOf("No, never", "Yes, it has happened"), listOf("Nein, nie", "Ja, das ist schon vorgekommen"),
                listOf("No, nunca", "Sí, ya ha ocurrido")),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 4), poids = 2, signalCritique = true),

        QuestionChoix("cible_agression",
            tr("Envers qui cela s'est-il produit ?", "Who was it directed at?", "Gegen wen hat sich das gerichtet?", "¿Hacia quién fue?"),
            trList(listOf("Une personne", "Un autre animal (chat, chien...)", "Les deux"), listOf("A person", "Another animal (cat, dog...)", "Both"), listOf("Einen Menschen", "Ein anderes Tier (Katze, Hund …)", "Beides"),
                listOf("Una persona", "Otro animal (gato, perro...)", "Ambos")),
            axe = Axe.COHABITATION),

        QuestionChoix("defense_ressources",
            tr("Votre chat défend-il ses ressources (gamelle, litière, coin de repos) de façon agressive ?", "Does your cat defend its resources (bowl, litter box, resting spot) aggressively?", "Verteidigt Ihre Katze ihre Ressourcen (Napf, Katzenklo, Liegeplatz) aggressiv?", "¿Tu gato defiende sus recursos (comedero, arenero, lugar de descanso) de forma agresiva?"),
            trList(
                listOf("Non, jamais",
                    "Parfois — il grogne ou siffle si on s'approche",
                    "Oui, fréquemment — il n'aime pas qu'on s'approche de ses affaires"),
                listOf("No, never",
                    "Sometimes — it growls or hisses if approached",
                    "Yes, frequently — it doesn't like anyone approaching its things"),
                listOf("Nein, nie",
                    "Manchmal – sie knurrt oder faucht, wenn man sich nähert",
                    "Ja, häufig – sie mag es nicht, wenn sich jemand ihren Sachen nähert")
            ,
                listOf("No, nunca", "A veces: gruñe o bufa si alguien se acerca", "Sí, con frecuencia: no le gusta que se acerquen a sus cosas")),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 2, 4), signalAlerte = true),

        QuestionChoix("a_un_probleme",
            tr("Y a-t-il un comportement particulier qui vous préoccupe en ce moment ?", "Is there a particular behavior that concerns you right now?", "Gibt es derzeit ein bestimmtes Verhalten, das Sie beschäftigt?", "¿Hay algún comportamiento concreto que te preocupe en este momento?"),
            trList(listOf("Oui, j'aimerais en savoir plus", "Non, tout va bien dans l'ensemble"), listOf("Yes, I would like to know more", "No, everything is fine overall"), listOf("Ja, ich möchte mehr darüber erfahren", "Nein, insgesamt ist alles in Ordnung"),
                listOf("Sí, me gustaría saber más", "No, en general todo va bien"))),

        QuestionChoix("apparition",
            tr("Ce comportement est apparu :", "This behavior appeared:", "Dieses Verhalten ist aufgetreten:", "Este comportamiento apareció:"),
            trList(listOf("Progressivement", "Du jour au lendemain, de façon brutale", "Je ne sais pas vraiment"), listOf("Gradually", "Suddenly, from one day to the next", "I'm not really sure"), listOf("Nach und nach", "Plötzlich, von einem Tag auf den anderen", "Ich weiß es nicht genau"),
                listOf("Poco a poco", "De un día para otro, de golpe", "No lo sé muy bien"))),

        QuestionChoix("duree_probleme",
            tr("Depuis combien de temps observez-vous ce comportement ?", "How long have you been observing this behavior?", "Seit wann beobachten Sie dieses Verhalten?", "¿Desde cuándo observas este comportamiento?"),
            trList(listOf("Moins d'une semaine", "Entre 1 semaine et 1 mois", "Depuis plusieurs mois", "Depuis toujours ou très longtemps"), listOf("Less than a week", "Between 1 week and 1 month", "For several months", "Always, or for a very long time"), listOf("Weniger als eine Woche", "Zwischen 1 Woche und 1 Monat", "Seit mehreren Monaten", "Schon immer oder seit sehr langer Zeit"),
                listOf("Menos de una semana", "Entre 1 semana y 1 mes", "Desde hace varios meses", "Desde siempre o desde hace mucho tiempo"))),

        QuestionChoix("evolution_probleme",
            tr("Ce comportement évolue-t-il ?", "How is this behavior changing?", "Wie entwickelt sich dieses Verhalten?", "¿Cómo está evolucionando este comportamiento?"),
            trList(listOf("Il s'améliore", "Il reste stable", "Il s'aggrave"), listOf("It is improving", "It remains stable", "It is getting worse"), listOf("Es wird besser", "Es bleibt stabil", "Es verschlimmert sich"),
                listOf("Está mejorando", "Se mantiene estable", "Está empeorando"))),

        QuestionChoix("frequence_probleme",
            tr("À quelle fréquence ce comportement se manifeste-t-il ?", "How often does this behavior occur?", "Wie oft tritt dieses Verhalten auf?", "¿Con qué frecuencia aparece este comportamiento?"),
            trList(listOf("Rarement — quelques fois par mois", "Quelques fois par semaine", "Tous les jours", "Plusieurs fois par jour"), listOf("Rarely — a few times a month", "A few times a week", "Every day", "Several times a day"), listOf("Selten – ein paar Mal im Monat", "Einige Male pro Woche", "Jeden Tag", "Mehrmals täglich"),
                listOf("Rara vez: algunas veces al mes", "Algunas veces por semana", "Todos los días", "Varias veces al día"))),

        QuestionChoix("intensite_probleme",
            tr("Quand cela arrive, c'est plutôt :", "When it happens, it is usually:", "Wenn es passiert, ist es eher:", "Cuando ocurre, suele ser:"),
            trList(listOf("Gérable facilement", "Gênant mais supportable", "Difficile à gérer", "Très intense, incontrôlable"), listOf("Easily manageable", "Inconvenient but bearable", "Difficult to manage", "Very intense, uncontrollable"), listOf("Gut zu bewältigen", "Störend, aber erträglich", "Schwer zu bewältigen", "Sehr intensiv, unkontrollierbar"),
                listOf("Fácil de manejar", "Molesto, pero soportable", "Difícil de manejar", "Muy intenso, incontrolable"))),

        QuestionChoix("generalisation_probleme",
            tr("Ce comportement se produit :", "This behavior occurs:", "Dieses Verhalten tritt auf:", "Este comportamiento ocurre:"),
            trList(listOf("Dans une situation très précise", "Dans plusieurs situations différentes", "Dans la plupart des situations quotidiennes"), listOf("In one very specific situation", "In several different situations", "In most everyday situations"), listOf("In einer ganz bestimmten Situation", "In mehreren verschiedenen Situationen", "In den meisten Alltagssituationen"),
                listOf("En una situación muy concreta", "En varias situaciones diferentes", "En la mayoría de las situaciones cotidianas"))),

        QuestionChoix("changement_recent",
            tr("Y a-t-il eu un changement important récemment dans la vie de votre chat ?", "Has there been a significant change in your cat's life recently?", "Gab es kürzlich eine wichtige Veränderung im Leben Ihrer Katze?", "¿Ha habido recientemente algún cambio importante en la vida de tu gato?"),
            trList(
                listOf("Aucun changement notable",
                    "Un changement léger (nouveau meuble, nouvelle routine)",
                    "Un changement important (déménagement, nouvel animal, naissance, séparation)"),
                listOf("No notable change",
                    "A minor change (new furniture, new routine)",
                    "A major change (move, new animal, birth, separation)"),
                listOf("Keine nennenswerte Veränderung",
                    "Eine kleine Veränderung (neue Möbel, neuer Tagesablauf)",
                    "Eine große Veränderung (Umzug, neues Tier, Geburt, Trennung)")
            ,
                listOf("Ningún cambio notable", "Un cambio leve (mueble nuevo, nueva rutina)", "Un cambio importante (mudanza, nuevo animal, nacimiento, separación)"))),

        QuestionChoix("signe_physique",
            tr("Avez-vous observé des changements physiques chez votre chat (appétit, poids, pelage, éliminations) ?", "Have you noticed any physical changes in your cat (appetite, weight, coat, elimination)?", "Haben Sie körperliche Veränderungen bei Ihrer Katze bemerkt (Appetit, Gewicht, Fell, Ausscheidungen)?", "¿Has notado cambios físicos en tu gato (apetito, peso, pelaje, eliminación)?"),
            trList(
                listOf("Non, rien de particulier", "Peut-être — je ne suis pas certain(e)",
                    "Oui, un changement notable", "Oui, quelque chose qui m'inquiète vraiment"),
                listOf("No, nothing in particular", "Perhaps — I'm not certain",
                    "Yes, a notable change", "Yes, something that really concerns me"),
                listOf("Nein, nichts Besonderes", "Vielleicht – ich bin mir nicht sicher",
                    "Ja, eine deutliche Veränderung", "Ja, etwas, das mich wirklich beunruhigt")
            ,
                listOf("No, nada en particular", "Quizás: no estoy seguro/a", "Sí, un cambio notable", "Sí, algo que me preocupa de verdad"))),

        QuestionChoix("lieu_residence",
            tr("Où habitez-vous ?", "Where do you live?", "Wo wohnen Sie?", "¿Dónde vives?"),
            trList(listOf("Dans l'Essonne (91)", "Ailleurs en France", "Dans un autre pays francophone (Belgique, Suisse, Luxembourg…)"),
                listOf("In Essonne (91)", "Elsewhere in France", "In another French-speaking country (Belgium, Switzerland, Luxembourg…)"),
                listOf("Im Département Essonne (91)", "Anderswo in Frankreich", "In einem anderen französischsprachigen Land (Belgien, Schweiz, Luxemburg …)"),
                listOf("En Essonne (91)", "En otra parte de Francia", "En otro país francófono (Bélgica, Suiza, Luxemburgo…)")))
    )
}