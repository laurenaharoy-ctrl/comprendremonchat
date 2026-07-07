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
    val cibleAgressionAnimal: Boolean = false
)

// ═══════════════════════════════════════════════════════════
// HELPERS TEXTE
// ═══════════════════════════════════════════════════════════

fun nomChatAffiche(nom: String): String =
    nom.trim().replaceFirstChar { it.uppercase() }.ifBlank { if (isEnglish()) "your cat" else "votre chat" }

fun libelleAxe(axe: Axe): String = libelleAxeTraduit(axe)

fun texteNiveauSituation(niveau: NiveauSituation): String = texteNiveauSituationTraduit(niveau)

fun texteVigilance(vigilance: NiveauVigilance, nomChat: String): String =
    texteVigilanceTraduit(vigilance, nomChat)

fun textePrioriteAction(priorite: PrioriteAction): String = textePrioriteActionTraduit(priorite)

fun resumeEmotionnel(axe: Axe): String = resumeEmotionnelTraduit(axe)

fun intentionChat(axe: Axe): String = intentionChatTraduit(axe)

fun besoinPrincipal(axe: Axe): String = besoinPrincipalTraduit(axe)

fun phraseFin(nomChat: String): String = phraseFinTraduit(nomChat)

data class CategorieRace(
    val id: String, val nom: String,
    val predispositions: List<String>, val nuanceAnalyse: String
)

// ═══════════════════════════════════════════════════════════
// DONNÉES DE RACES — BILINGUES
// ═══════════════════════════════════════════════════════════

val categoriesRaces get() = if (isEnglish()) categoriesRacesChatEn else categoriesRacesChatFr

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
        "Early socialisation history plays a determining role in its emotional balance."),
    CategorieRace("maine_coon", "Maine Coon",
        listOf("Marked sociability", "Strong need for stimulation", "Strong attachment to its family"),
        "A lack of stimulation can generate boredom and compensatory behaviours."),
    CategorieRace("persan", "Persian",
        listOf("Calm and composed temperament", "Sensitivity to changes", "Need for calm"),
        "Environmental disturbances can easily destabilise it despite its apparent calm."),
    CategorieRace("siamois", "Siamese",
        listOf("Strong vocalisation", "Intense attachment", "Very emotionally expressive"),
        "Separation anxiety and excessive vocalisation are more frequent in this family."),
    CategorieRace("ragdoll", "Ragdoll",
        listOf("Very high tolerance", "Strong need for human presence", "Non-conflictual"),
        "Be careful not to confuse its tolerance with an absence of needs — it can suffer in silence."),
    CategorieRace("bengal", "Bengal",
        listOf("Very high energy level", "Intense need for stimulation", "Strong personality"),
        "A lack of physical and mental stimulation can quickly generate problematic behaviours."),
    CategorieRace("british", "British Shorthair",
        listOf("Calm and independent temperament", "Good tolerance for solitude", "Not very expressive"),
        "Its apparent calm can mask stress if subtle signals are not detected."),
    CategorieRace("abyssin", "Abyssinian",
        listOf("Very active and curious", "Need for freedom", "Low tolerance for boredom"),
        "Environmental enrichment is essential — it gets bored quickly and reacts strongly."),
    CategorieRace("sacre_birmanie", "Sacred Birman",
        listOf("Gentle and balanced", "Moderate attachment", "Good cohabitation"),
        "Generally balanced, but sensitive to tensions in the household."),
    CategorieRace("autre", "Other breed / unknown",
        listOf("Individual profile to be observed"),
        "Individual history and early socialisation are the most determining factors.")
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
        return when {
            contexte.physique >= 4 -> NiveauSituation.SENSIBLE
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> NiveauSituation.SENSIBLE
            reponsesChoix["evolution_probleme"] == 2 && reponsesChoix["intensite_probleme"] == 3 -> NiveauSituation.SENSIBLE
            contexte.scoreContexte >= 10 -> NiveauSituation.SENSIBLE
            contexte.scoreContexte >= 5 -> NiveauSituation.A_TRAVAILLER
            maxAxe >= 55 -> NiveauSituation.A_TRAVAILLER
            else -> NiveauSituation.STABLE
        }
    }

    fun genererMessageSituation(niveauSituation: NiveauSituation, nomChat: String): String =
        genererMessageSituationTraduit(niveauSituation, nomChat)

    fun genererRaisonSituation(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): String =
        genererRaisonSituationTraduit(reponsesChoix, contexte)

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
        return when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> PrioriteAction.ELEVEE
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteAction.URGENTE
            contexte.physique >= 4 -> PrioriteAction.URGENTE
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
        if (isEnglish()) {
            if (reponsesChoix["evolution_probleme"] == 2) raisons += "The behavior seems to be gradually worsening."
            if (reponsesChoix["intensite_probleme"] == 3) raisons += "The described intensity seems significant and impacts daily life."
            if (reponsesChoix["generalisation_probleme"] == 2) raisons += "The behavior affects many different situations."
            if (raisons.isEmpty()) raisons += "The answers suggest a few points of vigilance to monitor."
        } else {
            if (reponsesChoix["evolution_probleme"] == 2) raisons += "Le comportement semble s'aggraver progressivement."
            if (reponsesChoix["intensite_probleme"] == 3) raisons += "L'intensité décrite paraît importante et impacte le quotidien."
            if (reponsesChoix["generalisation_probleme"] == 2) raisons += "Le comportement touche de nombreuses situations différentes."
            if (raisons.isEmpty()) raisons += "Les réponses suggèrent quelques points de vigilance à surveiller."
        }
        return ExplicationResultat(raisons.take(3),
            detecterFacteursAggravantsTraduit(reponsesChoix, contexte, securite, lien, instincts, cohabitation),
            detecterFacteursProtecteursTraduit(reponsesChoix, contexte))
    }

    fun genererSyntheseAvancee(nom: String, hypothese: String, priorite: PrioriteAction,
                               aggravants: List<String>, protecteurs: List<String>): String {
        val intro = if (isEnglish()) {
            when (priorite) {
                PrioriteAction.FAIBLE -> "$nom shows an overall stable functioning."
                PrioriteAction.MODEREE -> "$nom presents a difficulty that deserves a gradual approach."
                PrioriteAction.ELEVEE -> "$nom seems to be struggling in an area requiring active attention."
                PrioriteAction.URGENTE -> "$nom presents elements that justify prompt professional attention."
            }
        } else {
            when (priorite) {
                PrioriteAction.FAIBLE -> "$nom présente un fonctionnement globalement stable."
                PrioriteAction.MODEREE -> "$nom présente une difficulté qui mérite une approche progressive."
                PrioriteAction.ELEVEE -> "$nom semble en difficulté sur un plan nécessitant une attention active."
                PrioriteAction.URGENTE -> "$nom présente des éléments qui justifient une attention rapide et professionnelle."
            }
        }
        val aggrLabel = if (isEnglish()) "Aggravating factors" else "Éléments majorants"
        val protLabel = if (isEnglish()) "Protective factors" else "Éléments favorables"
        val hypoLabel = if (isEnglish()) "Hypothesis" else "Hypothèse"
        val aggr = if (aggravants.isNotEmpty()) "$aggrLabel : ${aggravants.joinToString(", ")}." else ""
        val prot = if (protecteurs.isNotEmpty()) "$protLabel : ${protecteurs.joinToString(", ")}." else ""
        return listOf(intro, "$hypoLabel : $hypothese", aggr, prot).filter { it.isNotBlank() }.joinToString("\n\n")
    }

    fun genererOriginesPossibles(
        nomChat: String, axe: Axe,
        securite: Int, lien: Int, instincts: Int, cohabitation: Int,
        reponsesChoix: Map<String, Int>
    ): String {
        val nom = nomChatAffiche(nomChat)
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        if (maxAxe <= 25) return if (isEnglish())
            "$nom seems to be evolving in an overall satisfying balance. No particular behavioral origin stands out at this stage."
        else
            "$nom semble évoluer dans un équilibre global satisfaisant. Aucune origine comportementale particulière ne ressort à ce stade."

        return if (isEnglish()) {
            when (axe) {
                Axe.SECURITE -> buildString {
                    append("$nom's emotional insecurity can have several origins. ")
                    append("Insufficient early socialisation — few exposures to humans, sounds or varied environments before the age of 7 weeks — is often a factor. ")
                    append("Past negative experiences, even isolated ones, can leave a lasting imprint on how a cat perceives its world. ")
                    if (reponsesChoix["acces_exterieur"] == 2) append("A solely indoor cat may sometimes lack varied stimulation, which weakens its ability to cope with novelty. ")
                    if (reponsesChoix["age"] == 0) append("Under one year old, the construction of a sense of security is still underway — some sensitivity is normal at this age. ")
                    if (reponsesChoix["changement_recent"] == 2) append("A recent significant change may have destabilized its reference points and amplified this sense of insecurity. ")
                    append("In some cases, a genetic predisposition also plays a role, independently of lived experience.")
                }
                Axe.LIEN -> buildString {
                    append("$nom's intense need for closeness can be explained in several ways. ")
                    append("Weaning too early — before 8 weeks — can durably weaken the construction of emotional autonomy. ")
                    append("An environment where the cat has never learned to be alone can also reinforce this need for constant presence. ")
                    if (reponsesChoix["suit_partout"] == 3) append("Permanently following its human can be both a symptom and a factor that maintains this relational dependency. ")
                    if (estMaleEntier(reponsesChoix) || estFemelleEntiere(reponsesChoix)) append("In an unneutered cat, some manifestations may also be influenced by hormonal cycles. ")
                    append("This functioning is not a whim: it reflects a genuine difficulty in finding internal support in the absence of the reassuring figure.")
                }
                Axe.INSTINCTS -> buildString {
                    if (reponsesChoix["marquage_habitude_post_sterilisation"] == 0) {
                        append("$nom's urine marking seems to have started during her heat periods, before she was spayed. ")
                        append("The original hormonal cause is gone, but the behavior has turned into an acquired habit — the gesture remains ingrained even though the initial reason no longer exists. ")
                        append("This type of habitual marking is often longer to correct than marking purely linked to stress, since a repeated gesture needs to be unlearned rather than simply reducing a source of tension. ")
                        append("Targeted behavioral support on this specific point is recommended.")
                        return@buildString
                    }
                    append("$nom's instinctive frustration can have several origins. ")
                    append("The cat is a solitary predator whose needs for hunting, exploration and scratching are deeply ingrained — an environment that does not allow them to be expressed inevitably generates frustration. ")
                    if (reponsesChoix["acces_exterieur"] == 2) append("The absence of access to the outdoors deprives the cat of many natural stimulations that channel these instincts. ")
                    if (reponsesChoix["vie_interieur"] == 2 || reponsesChoix["vie_interieur"] == 3) append("A poorly enriched indoor environment worsens this lack of outlet for its natural instincts. ")
                    val raceCat = reponsesChoix["race_categorie"]
                    if (raceCat != null && raceCat <= 1) append("Some breeds like the Bengal or Abyssinian have been selected for a very high energy level, which accentuates this need for stimulation. ")
                    append("This is not a character problem but a fundamental need seeking expression, sometimes in undesirable ways for lack of an appropriate alternative.")
                }
                Axe.COHABITATION -> buildString {
                    append("$nom's cohabitation difficulties can be explained by several factors. ")
                    append("Cats are territorially sensitive — competition for resources (food, litter box, resting space) is a major source of tension in multi-cat households. ")
                    if (estMaleEntier(reponsesChoix)) append("In an intact male, urine marking and intimidation behaviors are frequent and can fuel conflicts with housemates. ")
                    if (reponsesChoix["changement_recent"] == 2) append("The arrival of a new animal or household member may have disrupted a fragile territorial balance. ")
                    append("An introduction too rapid between animals, without a gradual familiarization phase, is one of the most frequent causes of lasting tensions. ")
                    append("In some cases, simply incompatible personalities may also be a factor, independently of human management.")
                }
            }
        } else {
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
                    if (reponsesChoix["suit_partout"] == 3) append("Le fait de suivre en permanence son humain peut être à la fois un symptôme et un facteur qui entretient cette dépendance relationnelle. ")
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
                    if (raceCat != null && raceCat <= 1) append("Certaines races comme le Bengal ou l'Abyssin ont été sélectionnées pour un niveau d'énergie très élevé, ce qui accentue ce besoin de stimulation. ")
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
            raisonSituation = genererRaisonSituationTraduit(reponsesChoix, contexte),
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
            cibleAgressionAnimal = reponsesChoix["cible_agression"] == 1
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

        QuestionTexte("nom_chat", if (isEnglish()) "What is your cat's name?" else "Quel est le prénom de votre chat ?"),

        QuestionChoix("race_categorie",
            if (isEnglish()) "Which breed family does your cat belong to?" else "À quelle famille de races appartient votre chat ?",
            if (isEnglish()) listOf("European / Mixed breed", "Maine Coon", "Persian", "Siamese", "Ragdoll",
                "Bengal", "British Shorthair", "Abyssinian", "Sacred Birman", "Other breed / unknown")
            else listOf("Européen / Gouttière", "Maine Coon", "Persan", "Siamois", "Ragdoll",
                "Bengal", "British Shorthair", "Abyssin", "Sacré de Birmanie", "Autre race / inconnu")),

        QuestionChoix("age",
            if (isEnglish()) "How old is your cat?" else "Quel âge a votre chat ?",
            if (isEnglish()) listOf("Under 1 year (kitten)", "Between 1 and 3 years", "Between 4 and 8 years", "9 years and over (senior)")
            else listOf("Moins d'1 an (chaton)", "Entre 1 et 3 ans", "Entre 4 et 8 ans", "9 ans et plus (senior)")),

        QuestionChoix("senior_desorientation",
            if (isEnglish()) "Does your cat sometimes seem disoriented or lost in places it knows well?"
            else "Votre chat semble-t-il parfois désorienté ou perdu dans des endroits qu'il connaît bien ?",
            if (isEnglish()) listOf("No, never", "Sometimes, occasionally", "Yes, regularly")
            else listOf("Non, jamais", "Parfois, occasionnellement", "Oui, régulièrement")),

        QuestionChoix("senior_vocalise_nocturne",
            if (isEnglish()) "Has your cat recently been vocalizing or wandering at night without an apparent reason (not hungry, no identifiable demand for attention)?"
            else "Depuis quelque temps, votre chat vocalise-t-il ou erre-t-il la nuit sans raison apparente (pas de faim, pas de demande d'attention identifiable) ?",
            if (isEnglish()) listOf("No, never", "Sometimes, occasionally", "Yes, regularly")
            else listOf("Non, jamais", "Parfois, occasionnellement", "Oui, régulièrement")),

        QuestionChoix("sterilise",
            if (isEnglish()) "Your cat is:" else "Votre chat est :",
            if (isEnglish()) listOf("A neutered male", "A spayed female", "An intact male", "An intact female")
            else listOf("Un mâle stérilisé", "Une femelle stérilisée", "Un mâle entier", "Une femelle entière")),

        QuestionChoix("acces_exterieur",
            if (isEnglish()) "Does your cat have access to the outdoors?" else "Votre chat a-t-il accès à l'extérieur ?",
            if (isEnglish()) listOf("Yes, freely", "Yes, in a controlled way (secure balcony, supervised garden)", "No, indoors only")
            else listOf("Oui, librement", "Oui, de façon contrôlée (balcon sécurisé, jardin surveillé)", "Non, uniquement en intérieur")),

        QuestionChoix("vie_interieur",
            if (isEnglish()) "If your cat is indoors, how would you describe its environment?" else "Si votre chat est d'intérieur, comment décririez-vous son environnement ?",
            if (isEnglish()) listOf("Enriched (scratching posts, heights, varied toys, accessible windows)",
                "Decent but could be better", "Not very stimulating",
                "I'm not really sure", "My cat has access to the outdoors")
            else listOf("Enrichi (griffoirs, hauteurs, jeux variés, fenêtres accessibles)",
                "Correct mais peut mieux faire", "Peu stimulant",
                "Je ne sais pas vraiment", "Mon chat a accès à l'extérieur")),

        QuestionChoix("reaction_bruit",
            if (isEnglish()) "How does your cat react to sudden or loud noises (vacuum cleaner, thunder, construction)?"
            else "Comment votre chat réagit-il aux bruits soudains ou forts (aspirateur, tonnerre, travaux) ?",
            if (isEnglish()) listOf("It stays calm or slightly surprised, recovers quickly",
                "It startles and moves away but recovers within a few minutes",
                "It hides and takes a long time to come back",
                "It panics completely and stays unsettled for a long time")
            else listOf("Il reste calme ou légèrement surpris, récupère vite",
                "Il sursaute et s'éloigne mais récupère en quelques minutes",
                "Il se cache et met longtemps à revenir",
                "Il panique totalement et reste perturbé longtemps"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("reaction_inconnu",
            if (isEnglish()) "How does your cat react to a stranger?" else "Comment votre chat réagit-il face à une personne inconnue ?",
            if (isEnglish()) listOf("It approaches with curiosity or stays indifferent",
                "It observes cautiously from afar then sometimes approaches",
                "It hides for the entire duration of the visit",
                "It shows signs of agitation or aggression")
            else listOf("Il s'approche avec curiosité ou reste indifférent",
                "Il observe de loin prudemment puis s'approche parfois",
                "Il se cache pour toute la durée de la visite",
                "Il montre des signes d'agitation ou d'agressivité"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),

        QuestionChoix("cache_souvent",
            if (isEnglish()) "How often does your cat hide or isolate itself?" else "À quelle fréquence votre chat se cache-t-il ou s'isole-t-il ?",
            if (isEnglish()) listOf("Rarely — it is generally visible and accessible",
                "Sometimes, especially when there are people or noise",
                "Often, several times a day",
                "Most of the time — it is hard to find")
            else listOf("Rarement — il est généralement visible et accessible",
                "Parfois, notamment quand il y a du monde ou du bruit",
                "Souvent, plusieurs fois par jour",
                "La plupart du temps — il est difficile à trouver"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4)),

        QuestionChoix("adaptation_changement",
            if (isEnglish()) "How does your cat adapt to changes (move, new furniture, visitors)?"
            else "Comment votre chat s'adapte-t-il aux changements (déménagement, nouveau meuble, visiteurs) ?",
            if (isEnglish()) listOf("Very well — it adapts quickly",
                "Fine — a few days of caution then it passes",
                "With difficulty — it takes several weeks to recover",
                "Very poorly — every change causes a lasting crisis")
            else listOf("Très bien — il s'adapte rapidement",
                "Correctement — quelques jours de prudence puis ça passe",
                "Difficilement — il met plusieurs semaines à récupérer",
                "Très mal — chaque changement provoque une crise durable"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),

        QuestionChoix("reaction_veterinaire",
            if (isEnglish()) "How does a veterinary visit go?" else "Comment se passe une visite chez le vétérinaire ?",
            if (isEnglish()) listOf("Relatively well, it tolerates transport and the consultation",
                "Stressful but manageable",
                "Very difficult — it panics in the carrier or at the vet",
                "Extremely difficult — it is traumatic every time",
                "My cat never goes to the vet")
            else listOf("Relativement bien, il supporte le transport et la consultation",
                "Stressant mais gérable",
                "Très difficile — il panique dans la caisse ou chez le vétérinaire",
                "Extrêmement difficile — c'est un traumatisme à chaque fois",
                "Mon chat ne va jamais chez le vétérinaire"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4, 0)),

        QuestionChoix("surtoilettage",
            if (isEnglish()) "Have you noticed over-grooming (sparse fur areas, repeated excessive licking)?"
            else "Avez-vous observé un surtoilettage (zones de poils clairsemés, léchages répétitifs excessifs) ?",
            if (isEnglish()) listOf("No, its coat is normal",
                "Sometimes, without leaving visible marks",
                "Yes, with slightly sparse areas")
            else listOf("Non, son pelage est normal",
                "Parfois, sans que ça laisse de traces visibles",
                "Oui, avec des zones légèrement clairsemées"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3), signalAlerte = true),

        QuestionChoix("suit_partout",
            if (isEnglish()) "Does your cat follow you everywhere in the house?" else "Votre chat vous suit-il partout dans la maison ?",
            if (isEnglish()) listOf("No, it is fairly independent",
                "Sometimes, depending on its mood",
                "Often — it likes to be in the same room as you",
                "Always — it barely leaves your side")
            else listOf("Non, il est plutôt indépendant",
                "Parfois, selon son humeur",
                "Souvent — il aime être dans la même pièce que vous",
                "Toujours — il ne vous quitte pratiquement pas"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 3)),

        QuestionChoix("reaction_absence",
            if (isEnglish()) "How does your cat behave when you are away?" else "Comment votre chat se comporte-t-il quand vous êtes absent(e) ?",
            if (isEnglish()) listOf("It seems to manage calmly",
                "It may vocalize a little at your departure but settles down",
                "It vocalizes or becomes notably agitated",
                "It shows signs of distress (destruction, accidents, neighbors alerted)",
                "I don't know")
            else listOf("Il semble gérer sereinement",
                "Il peut vocaliser un peu à votre départ mais se calme",
                "Il vocalise ou s'agite de façon notable",
                "Il présente des signes de détresse (destructions, malpropreté, voisins alertés)",
                "Je ne sais pas"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 1, 2, 4, 0), signalAlerte = true),

        QuestionChoix("vocalise_absence",
            if (isEnglish()) "Does your cat vocalize excessively (repeated, insistent meowing)?"
            else "Votre chat vocalise-t-il de façon excessive (miaulements répétés, insistants) ?",
            if (isEnglish()) listOf("No, it is quiet or vocalizes normally",
                "Sometimes, particularly at mealtimes",
                "Often, to demand your attention",
                "Very often, in an overwhelming way")
            else listOf("Non, il est peu vocal ou vocal de façon normale",
                "Parfois, notamment au moment des repas",
                "Souvent, pour demander votre attention",
                "Très souvent, de façon envahissante"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),

        QuestionChoix("proprete_stress",
            if (isEnglish()) "Has your cat ever relieved itself outside its litter box?"
            else "Votre chat a-t-il déjà fait ses besoins en dehors de sa litière ?",
            if (isEnglish()) listOf("No, never",
                "Very rarely, in exceptional circumstances",
                "Occasionally, often linked to a stressful event",
                "Regularly")
            else listOf("Non, jamais",
                "Très rarement, dans des circonstances exceptionnelles",
                "Occasionnellement, souvent lié à un événement stressant",
                "Régulièrement"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 4), signalAlerte = true),

        QuestionChoix("proprete_type",
            if (isEnglish()) "It is rather:" else "Il s'agit plutôt de :",
            if (isEnglish()) listOf("Urine", "Stools", "Both")
            else listOf("Urine", "Selles", "Les deux")),

        QuestionChoix("precision_malproprete",
            if (isEnglish()) "When it happens, is it rather:" else "Quand cela arrive, est-ce plutôt :",
            if (isEnglish()) listOf("Always in the same spot", "In different spots")
            else listOf("Toujours au même endroit", "À des endroits différents")),

        QuestionChoix("demande_attention",
            if (isEnglish()) "How does your cat react when you don't give it attention?"
            else "Comment votre chat réagit-il quand vous ne lui accordez pas d'attention ?",
            if (isEnglish()) listOf("It accepts easily and goes about its business",
                "It insists a little then calms down",
                "It insists strongly, meows or causes trouble to get attention",
                "It can become agitated or aggressive if ignored")
            else listOf("Il accepte facilement et va vaquer à ses occupations",
                "Il insiste un peu puis se calme",
                "Il insiste fortement, miaule ou fait des bêtises pour attirer l'attention",
                "Il peut devenir agité ou agressif si ignoré"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),

        QuestionChoix("dort_avec_vous",
            if (isEnglish()) "Does your cat sleep with you or try to stay close to you at night?"
            else "Votre chat dort-il avec vous ou cherche-t-il à être collé à vous la nuit ?",
            if (isEnglish()) listOf("No, it has its own spots",
                "Sometimes, depending on its mood",
                "Often — it prefers to be on your bed",
                "It gets agitated or vocalizes if you close the bedroom door")
            else listOf("Non, il a ses endroits à lui",
                "Parfois, selon son envie",
                "Souvent — il préfère être sur votre lit",
                "Il s'agite ou vocalise si vous fermez la porte de la chambre"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 2)),

        QuestionChoix("joue_activement",
            if (isEnglish()) "Does your cat actively play with toys?" else "Votre chat joue-t-il activement avec des jouets ?",
            if (isEnglish()) listOf("Yes, enthusiastically — it initiates play sessions itself",
                "Yes, when encouraged",
                "A little — it gets bored quickly or shows little interest",
                "No, no interest in play at all")
            else listOf("Oui, avec enthousiasme — il initie lui-même des sessions",
                "Oui, s'il est sollicité",
                "Peu — il s'ennuie rapidement ou montre peu d'intérêt",
                "Non, aucun intérêt pour le jeu"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("chasse_interieur",
            if (isEnglish()) "Does your cat display hunting behaviors indoors (stalking, pouncing, catching)?"
            else "Votre chat pratique-t-il des comportements de chasse à l'intérieur (épier, bondir, attraper) ?",
            if (isEnglish()) listOf("Yes, regularly with toys or small objects", "Sometimes", "Rarely", "Never — behavior completely absent")
            else listOf("Oui, régulièrement avec des jouets ou de petits objets", "Parfois", "Rarement", "Jamais — comportement totalement absent"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("griffage_surfaces",
            if (isEnglish()) "Does your cat scratch unauthorized surfaces (furniture, sofa, carpet)?"
            else "Votre chat griffe-t-il des surfaces non autorisées (meubles, canapé, moquette) ?",
            if (isEnglish()) listOf("No or very rarely — it uses its scratching posts",
                "Sometimes furniture in addition to scratching posts",
                "Often on furniture despite available scratching posts",
                "It only scratches furniture, scratching posts don't interest it")
            else listOf("Non ou très rarement — il utilise ses griffoirs",
                "Parfois les meubles en plus des griffoirs",
                "Souvent les meubles malgré les griffoirs disponibles",
                "Il ne griffe que les meubles, les griffoirs ne l'intéressent pas"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("hyperactivite_nocturne",
            if (isEnglish()) "Does your cat show nocturnal hyperactivity (running, jumping, vocalizing at night)?"
            else "Votre chat présente-t-il une hyperactivité nocturne (courses, sauts, vocalisations la nuit) ?",
            if (isEnglish()) listOf("No, it is calm at night", "Sometimes, occasionally",
                "Often — it regularly disrupts your sleep", "Every night — it is a significant problem")
            else listOf("Non, il est calme la nuit", "Parfois, occasionnellement",
                "Souvent — cela perturbe régulièrement votre sommeil", "Toutes les nuits — c'est un problème important"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),

        QuestionChoix("comportement_alimentaire",
            if (isEnglish()) "How would you describe your cat's eating behavior?"
            else "Comment décririez-vous le comportement alimentaire de votre chat ?",
            if (isEnglish()) listOf("Normal — it eats well, at its own pace",
                "It eats very fast or often begs",
                "It steals food or rummages through bins",
                "It has significant appetite variations (refuses to eat or eats compulsively)")
            else listOf("Normal — il mange bien, à son rythme",
                "Il mange très vite ou réclame souvent",
                "Il vole de la nourriture ou fouille les poubelles",
                "Il a des variations importantes d'appétit (refuse de manger ou mange de façon compulsive)"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("destruction_ennui",
            if (isEnglish()) "Does your cat cause destruction or damage, especially in your absence?"
            else "Votre chat provoque-t-il des destructions ou dégâts, notamment en votre absence ?",
            if (isEnglish()) listOf("No, never", "Rarely, a few minor incidents",
                "Sometimes — objects knocked over, plants damaged",
                "Often — the damage is significant and regular")
            else listOf("Non, jamais", "Rarement, quelques petits incidents",
                "Parfois — objets renversés, plantes abîmées",
                "Souvent — les dégâts sont importants et réguliers"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("marquage_urinaire",
            if (isEnglish()) "Does your cat practice urine marking (standing up, on vertical surfaces)?"
            else "Votre chat pratique-t-il le marquage urinaire (debout, sur des surfaces verticales) ?",
            if (isEnglish()) listOf("No, never", "Rarely, in identified stressful situations",
                "Yes, from time to time", "Yes, frequently")
            else listOf("Non, jamais", "Rarement, dans des situations de stress identifiées",
                "Oui, de temps en temps", "Oui, fréquemment"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("chaleur_marquage",
            if (isEnglish()) "Does this marking happen mainly during her heat periods (times when she calls, meows loudly, rubs a lot)?"
            else "Ce marquage a-t-il lieu principalement pendant ses chaleurs (périodes où elle réclame, miaule fort, se frotte beaucoup) ?",
            if (isEnglish()) listOf("Yes, mainly during heat periods", "No, at other times too", "I don't know")
            else listOf("Oui, principalement pendant les chaleurs", "Non, à d'autres moments aussi", "Je ne sais pas")),

        QuestionChoix("marquage_habitude_post_sterilisation",
            if (isEnglish()) "Did this marking start before she was spayed?"
            else "Ce marquage a-t-il commencé avant sa stérilisation ?",
            if (isEnglish()) listOf("Yes, and it has continued since", "No, it appeared after spaying", "I don't know / I adopted her already spayed")
            else listOf("Oui, et ça a continué depuis", "Non, c'est apparu après la stérilisation", "Je ne sais pas / je l'ai adoptée déjà stérilisée")),

        QuestionChoix("relation_autres_chats",
            if (isEnglish()) "If you have several cats, how are their relations?" else "Si vous avez plusieurs chats, comment se passent leurs relations ?",
            if (isEnglish()) listOf("Good understanding in general, even mutual affection",
                "Neutral coexistence — they ignore each other",
                "Frequent tensions but no physical aggression",
                "Regular conflicts with aggression",
                "I only have one cat")
            else listOf("Bonne entente générale, voire affection mutuelle",
                "Coexistence neutre — ils s'ignorent",
                "Tensions fréquentes mais sans agression physique",
                "Conflits réguliers avec agressions",
                "Je n'ai qu'un seul chat"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),

        QuestionChoix("relation_enfants",
            if (isEnglish()) "If children are present, how does your cat react?" else "Si des enfants sont présents, comment votre chat réagit-il ?",
            if (isEnglish()) listOf("Very well — it interacts or tolerates them calmly",
                "Fine — it keeps its distance but without tension",
                "It flees or isolates itself when children are around",
                "It can react aggressively (scratches, bites)",
                "No children in the household")
            else listOf("Très bien — il interagit ou les tolère sereinement",
                "Correctement — il garde ses distances mais sans tension",
                "Il fuit ou s'isole quand les enfants sont là",
                "Il peut réagir de façon agressive (griffe, mord)",
                "Pas d'enfants dans le foyer"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),

        QuestionChoix("agressivite_caresses",
            if (isEnglish()) "Does your cat bite or scratch during petting or play?"
            else "Votre chat mord-il ou griffe-t-il pendant les caresses ou les jeux ?",
            if (isEnglish()) listOf("No, never",
                "Rarely — only when warning signals have been ignored",
                "Sometimes, unpredictably",
                "Often — physical interactions are difficult to manage")
            else listOf("Non, jamais",
                "Rarement — seulement quand les signaux d'alerte ont été ignorés",
                "Parfois, de façon imprévisible",
                "Souvent — les interactions physiques sont difficiles à gérer"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("a_deja_griffe_mordu",
            if (isEnglish()) "Has your cat ever scratched or bitten someone (you, a family member, a child)?"
            else "Votre chat a-t-il déjà griffé ou mordu quelqu'un (vous, un proche, un enfant) ?",
            if (isEnglish()) listOf("No, never", "Yes, it has happened")
            else listOf("Non, jamais", "Oui, cela s'est déjà produit"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 4), poids = 2, signalCritique = true),

        QuestionChoix("cible_agression",
            if (isEnglish()) "Who was it directed at?" else "Envers qui cela s'est-il produit ?",
            if (isEnglish()) listOf("A person", "Another animal (cat, dog...)", "Both")
            else listOf("Une personne", "Un autre animal (chat, chien...)", "Les deux"),
            axe = Axe.COHABITATION),

        QuestionChoix("defense_ressources",
            if (isEnglish()) "Does your cat defend its resources (bowl, litter box, resting spot) aggressively?"
            else "Votre chat défend-il ses ressources (gamelle, litière, coin de repos) de façon agressive ?",
            if (isEnglish()) listOf("No, never",
                "Sometimes — it growls or hisses if approached",
                "Yes, frequently — it doesn't like anyone approaching its things")
            else listOf("Non, jamais",
                "Parfois — il grogne ou siffle si on s'approche",
                "Oui, fréquemment — il n'aime pas qu'on s'approche de ses affaires"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 2, 4), signalAlerte = true),

        QuestionChoix("a_un_probleme",
            if (isEnglish()) "Is there a particular behavior that concerns you right now?"
            else "Y a-t-il un comportement particulier qui vous préoccupe en ce moment ?",
            if (isEnglish()) listOf("Yes, I would like to know more", "No, everything is fine overall")
            else listOf("Oui, j'aimerais en savoir plus", "Non, tout va bien dans l'ensemble")),

        QuestionChoix("apparition",
            if (isEnglish()) "This behavior appeared:" else "Ce comportement est apparu :",
            if (isEnglish()) listOf("Gradually", "Suddenly, from one day to the next", "I'm not really sure")
            else listOf("Progressivement", "Du jour au lendemain, de façon brutale", "Je ne sais pas vraiment")),

        QuestionChoix("duree_probleme",
            if (isEnglish()) "How long have you been observing this behavior?" else "Depuis combien de temps observez-vous ce comportement ?",
            if (isEnglish()) listOf("Less than a week", "Between 1 week and 1 month", "For several months", "Since always or for a very long time")
            else listOf("Moins d'une semaine", "Entre 1 semaine et 1 mois", "Depuis plusieurs mois", "Depuis toujours ou très longtemps")),

        QuestionChoix("evolution_probleme",
            if (isEnglish()) "Is this behavior evolving?" else "Ce comportement évolue-t-il ?",
            if (isEnglish()) listOf("It is improving", "It remains stable", "It is getting worse")
            else listOf("Il s'améliore", "Il reste stable", "Il s'aggrave")),

        QuestionChoix("frequence_probleme",
            if (isEnglish()) "How often does this behavior occur?" else "À quelle fréquence ce comportement se manifeste-t-il ?",
            if (isEnglish()) listOf("Rarely — a few times a month", "A few times a week", "Every day", "Several times a day")
            else listOf("Rarement — quelques fois par mois", "Quelques fois par semaine", "Tous les jours", "Plusieurs fois par jour")),

        QuestionChoix("intensite_probleme",
            if (isEnglish()) "When it happens, it is rather:" else "Quand cela arrive, c'est plutôt :",
            if (isEnglish()) listOf("Easily manageable", "Inconvenient but bearable", "Difficult to manage", "Very intense, uncontrollable")
            else listOf("Gérable facilement", "Gênant mais supportable", "Difficile à gérer", "Très intense, incontrôlable")),

        QuestionChoix("generalisation_probleme",
            if (isEnglish()) "This behavior occurs:" else "Ce comportement se produit :",
            if (isEnglish()) listOf("In one very specific situation", "In several different situations", "In most everyday situations")
            else listOf("Dans une situation très précise", "Dans plusieurs situations différentes", "Dans la plupart des situations quotidiennes")),

        QuestionChoix("changement_recent",
            if (isEnglish()) "Has there been a significant change in your cat's life recently?"
            else "Y a-t-il eu un changement important récemment dans la vie de votre chat ?",
            if (isEnglish()) listOf("No notable change",
                "A minor change (new furniture, new routine)",
                "A major change (move, new animal, birth, separation)")
            else listOf("Aucun changement notable",
                "Un changement léger (nouveau meuble, nouvelle routine)",
                "Un changement important (déménagement, nouvel animal, naissance, séparation)")),

        QuestionChoix("signe_physique",
            if (isEnglish()) "Have you noticed any physical changes in your cat (appetite, weight, coat, elimination)?"
            else "Avez-vous observé des changements physiques chez votre chat (appétit, poids, pelage, éliminations) ?",
            if (isEnglish()) listOf("No, nothing particular", "Perhaps — I'm not certain",
                "Yes, a notable change", "Yes, something that really concerns me")
            else listOf("Non, rien de particulier", "Peut-être — je ne suis pas certain(e)",
                "Oui, un changement notable", "Oui, quelque chose qui m'inquiète vraiment"))
    )
}