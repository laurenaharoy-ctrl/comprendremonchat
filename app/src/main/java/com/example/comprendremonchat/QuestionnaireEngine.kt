package com.example.comprendremonchat

import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════
// MODÈLES DE DONNÉES
// ═══════════════════════════════════════════════════════════

enum class Axe {
    PEUR, ATTACHEMENT, IMPULSIVITE, REACTIVITE
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
    val syntheseAvancee: String, val raceCategorie: String?, val racePrecise: String?
)

// ═══════════════════════════════════════════════════════════
// HELPERS TEXTE
// ═══════════════════════════════════════════════════════════

fun nomChatAffiche(nom: String): String =
    nom.trim().replaceFirstChar { it.uppercase() }.ifBlank { "votre chat" }

fun libelleAxe(axe: Axe): String = when (axe) {
    Axe.PEUR -> "Sensibilité émotionnelle"
    Axe.ATTACHEMENT -> "Besoin d'attachement"
    Axe.IMPULSIVITE -> "Régulation de l'excitation"
    Axe.REACTIVITE -> "Réactivité à l'environnement"
}

fun texteNiveauSituation(niveau: NiveauSituation): String = when (niveau) {
    NiveauSituation.STABLE -> "Situation stable"
    NiveauSituation.A_TRAVAILLER -> "À travailler"
    NiveauSituation.SENSIBLE -> "Situation sensible"
}

fun texteVigilance(vigilance: NiveauVigilance, nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return when (vigilance) {
        NiveauVigilance.FAIBLE -> "Rien d'urgent ne ressort pour $nom à ce stade."
        NiveauVigilance.MODEREE -> "Quelques points méritent attention pour $nom."
        NiveauVigilance.ELEVEE -> "Certains éléments justifient une attention rapide pour $nom."
    }
}

fun textePrioriteAction(priorite: PrioriteAction): String = when (priorite) {
    PrioriteAction.FAIBLE -> "Faible"
    PrioriteAction.MODEREE -> "Modérée"
    PrioriteAction.ELEVEE -> "Élevée"
    PrioriteAction.URGENTE -> "Urgente"
}

fun resumeEmotionnel(axe: Axe): String = when (axe) {
    Axe.PEUR -> "Un chat qui ressent parfois le monde comme menaçant"
    Axe.ATTACHEMENT -> "Un chat qui a besoin de sentir une présence rassurante"
    Axe.IMPULSIVITE -> "Un chat dont l'énergie déborde facilement"
    Axe.REACTIVITE -> "Un chat très attentif à ce qui l'entoure"
}

fun intentionChat(axe: Axe): String = when (axe) {
    Axe.PEUR -> "Ses réactions traduisent une tentative de faire face à ce qu'il perçoit comme difficile."
    Axe.ATTACHEMENT -> "Son besoin de proximité est une façon de se sentir en sécurité."
    Axe.IMPULSIVITE -> "Sa difficulté à se réguler n'est pas un manque de volonté."
    Axe.REACTIVITE -> "Ses réactions sont souvent une tentative de gérer une situation qui le dépasse."
}

fun besoinPrincipal(axe: Axe): String = when (axe) {
    Axe.PEUR -> "Besoin principal : prévisibilité, douceur et respect de ses seuils."
    Axe.ATTACHEMENT -> "Besoin principal : construire progressivement une autonomie sereine."
    Axe.IMPULSIVITE -> "Besoin principal : cadre clair, pauses fréquentes et retours au calme."
    Axe.REACTIVITE -> "Besoin principal : gestion de la distance et environnement maîtrisé."
}

fun phraseFin(nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return "L'objectif n'est pas d'étiqueter $nom, mais d'avancer de manière plus adaptée et rassurante pour vous deux."
}

data class CategorieRace(
    val id: String, val nom: String,
    val predispositions: List<String>, val nuanceAnalyse: String
)

val categoriesRaces = listOf(
    CategorieRace("europeen", "Européen / Gouttière",
        listOf("Grande adaptabilité", "Tempérament variable selon l'histoire individuelle"),
        "L'histoire de socialisation précoce joue un rôle déterminant."),
    CategorieRace("maine_coon", "Maine Coon",
        listOf("Sociabilité marquée", "Besoin de stimulation important"),
        "Un manque de stimulation peut générer de l'ennui et des comportements compensatoires."),
    CategorieRace("persan", "Persan",
        listOf("Tempérament calme", "Sensibilité aux changements"),
        "Les perturbations environnementales peuvent le déstabiliser facilement."),
    CategorieRace("siamois", "Siamois",
        listOf("Forte vocalisation", "Attachement intense"),
        "L'anxiété de séparation peut être plus marquée dans cette famille."),
    CategorieRace("ragdoll", "Ragdoll",
        listOf("Très grande tolérance", "Fort besoin de présence humaine"),
        "Attention à ne pas confondre tolérance et absence de signaux de stress."),
    CategorieRace("bengal", "Bengal",
        listOf("Niveau d'énergie très élevé", "Besoin important de stimulation"),
        "Un manque de stimulation peut rapidement générer des comportements problématiques."),
    CategorieRace("british", "British Shorthair",
        listOf("Tempérament calme et indépendant", "Bonne tolérance à la solitude"),
        "Son calme apparent peut masquer un stress si les signaux subtils ne sont pas détectés."),
    CategorieRace("autre", "Autre race / inconnu",
        listOf("Profil individuel à observer"),
        "L'histoire individuelle et la socialisation précoce sont les facteurs les plus importants.")
)

fun getNuanceAnalyse(race: String): String? =
    categoriesRaces.firstOrNull { it.nom.equals(race, ignoreCase = true) }?.nuanceAnalyse

fun getPredispositions(race: String): List<String> =
    categoriesRaces.firstOrNull { it.nom.equals(race, ignoreCase = true) }?.predispositions ?: emptyList()

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

    fun calculerScoreGlobal(peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): Int =
        ((peur + attachement + impulsivite + reactivite) / 4f).roundToInt()

    fun calculerNiveauAxe(score: Int): NiveauAxe = when {
        score <= 29 -> NiveauAxe.PEU_MARQUE
        score <= 54 -> NiveauAxe.A_SURVEILLER
        score <= 74 -> NiveauAxe.MARQUE
        else -> NiveauAxe.TRES_MARQUE
    }

    fun libelleNiveauAxe(niveau: NiveauAxe): String = when (niveau) {
        NiveauAxe.PEU_MARQUE -> "Peu marqué"
        NiveauAxe.A_SURVEILLER -> "À surveiller"
        NiveauAxe.MARQUE -> "Marqué"
        NiveauAxe.TRES_MARQUE -> "Très marqué"
    }

    fun determinerProblemePrincipal(peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): Axe =
        listOf(Axe.PEUR to peur, Axe.ATTACHEMENT to attachement, Axe.IMPULSIVITE to impulsivite, Axe.REACTIVITE to reactivite)
            .maxByOrNull { it.second }!!.first

    fun determinerProfilType(peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): String {
        val top = listOf(Axe.PEUR to peur, Axe.ATTACHEMENT to attachement, Axe.IMPULSIVITE to impulsivite, Axe.REACTIVITE to reactivite)
            .sortedByDescending { it.second }
        val first = top[0].first; val second = top[1].first; val firstScore = top[0].second
        if (firstScore <= 30) return "Chat bien ancré"
        return when {
            first == Axe.PEUR && second == Axe.REACTIVITE -> "Félin très sensible"
            first == Axe.ATTACHEMENT && second == Axe.PEUR -> "Cœur collé-serré"
            first == Axe.ATTACHEMENT && second == Axe.REACTIVITE -> "Très attaché"
            first == Axe.IMPULSIVITE && second == Axe.REACTIVITE -> "Débordant d'énergie"
            first == Axe.IMPULSIVITE && second == Axe.PEUR -> "Vif et sensible"
            first == Axe.REACTIVITE -> "Chat très réactif"
            first == Axe.PEUR -> "Émotif vigilant"
            first == Axe.ATTACHEMENT -> "Fusionnel"
            first == Axe.IMPULSIVITE -> "Moteur sensible"
            else -> "Profil équilibré"
        }
    }

    fun phraseHumaineProfil(nomChat: String, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): String {
        val maxAxe = maxOf(peur, attachement, impulsivite, reactivite)
        val nom = nomChatAffiche(nomChat)
        return when {
            maxAxe <= 30 -> "$nom semble évoluer sur une base globalement stable et adaptée."
            maxAxe <= 60 -> "$nom présente quelques points de fragilité, sans que cela ne prenne toute la place."
            else -> "$nom semble actuellement en difficulté dans certaines situations."
        }
    }

    fun genererProfilGlobal(nomChat: String, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): ProfilGlobal {
        val scoreGlobal = calculerScoreGlobal(peur, attachement, impulsivite, reactivite)
        val profilType = determinerProfilType(peur, attachement, impulsivite, reactivite)
        val phraseHumaine = phraseHumaineProfil(nomChat, peur, attachement, impulsivite, reactivite)
        return when {
            peur <= 30 && attachement <= 30 && impulsivite <= 30 && reactivite <= 30 ->
                ProfilGlobal("Profil globalement équilibré", "Les réponses suggèrent un fonctionnement plutôt stable.", profilType, scoreGlobal, phraseHumaine)
            peur >= 60 && reactivite >= 60 ->
                ProfilGlobal("Sensibilité émotionnelle et réactivité marquées", "Le profil suggère une sensibilité importante.", profilType, scoreGlobal, phraseHumaine)
            attachement >= 60 ->
                ProfilGlobal("Besoin de proximité plus important", "Les réponses font ressortir un besoin de proximité marqué.", profilType, scoreGlobal, phraseHumaine)
            impulsivite >= 60 ->
                ProfilGlobal("Régulation plus difficile", "Le profil évoque une difficulté dans la gestion de l'excitation.", profilType, scoreGlobal, phraseHumaine)
            peur >= 60 ->
                ProfilGlobal("Sensibilité émotionnelle plus marquée", "Les réponses suggèrent une sensibilité importante.", profilType, scoreGlobal, phraseHumaine)
            reactivite >= 60 ->
                ProfilGlobal("Réactivité plus marquée", "Le profil suggère une tendance à réagir fortement.", profilType, scoreGlobal, phraseHumaine)
            else ->
                ProfilGlobal("Profil à nuancer", "Quelques points de vigilance sans qu'un aspect ne domine.", profilType, scoreGlobal, phraseHumaine)
        }
    }

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

    fun calculerNiveauVigilance(questions: List<Question>, reponsesChoix: Map<String, Int>, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int, contexte: ContexteAnalyse): NiveauVigilance {
        val questionsChoix = questions.filterIsInstance<QuestionChoix>()
        val critiqueDetecte = questionsChoix.any { q -> q.signalCritique && (reponsesChoix[q.id] ?: 0) > 0 }
        val nbAlertes = questionsChoix.count { q -> q.signalAlerte && (reponsesChoix[q.id] ?: 0) >= 2 }
        val scoreMax = maxOf(peur, attachement, impulsivite, reactivite)
        return when {
            critiqueDetecte -> NiveauVigilance.ELEVEE
            contexte.physique >= 4 -> NiveauVigilance.ELEVEE
            reponsesChoix["apparition"] == 1 && scoreMax >= 50 -> NiveauVigilance.ELEVEE
            contexte.scoreContexte >= 10 -> NiveauVigilance.ELEVEE
            nbAlertes >= 2 -> NiveauVigilance.MODEREE
            scoreMax >= 70 -> NiveauVigilance.MODEREE
            contexte.scoreContexte >= 6 -> NiveauVigilance.MODEREE
            else -> NiveauVigilance.FAIBLE
        }
    }

    fun calculerNiveauSituation(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): NiveauSituation {
        val maxAxe = maxOf(peur, attachement, impulsivite, reactivite)
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

    fun genererMessageSituation(niveauSituation: NiveauSituation, nomChat: String): String {
        val nom = nomChatAffiche(nomChat)
        return when (niveauSituation) {
            NiveauSituation.STABLE -> "À ce stade, la situation semble plutôt stable pour $nom."
            NiveauSituation.A_TRAVAILLER -> "La situation mérite d'être travaillée progressivement pour $nom."
            NiveauSituation.SENSIBLE -> "La situation paraît plus sensible pour $nom et justifie une attention particulière."
        }
    }

    fun genererRaisonSituation(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): String {
        val raisons = mutableListOf<String>()
        if (reponsesChoix["duree_probleme"] == 0) raisons += "Le caractère très récent du comportement invite à une vigilance particulière."
        if (reponsesChoix["evolution_probleme"] == 2) raisons += "Le fait que cela semble s'aggraver peut indiquer que le problème prend plus de place."
        if (contexte.physique >= 4) raisons += "Des signes physiques ou une gêne possible invitent à la prudence."
        return raisons.firstOrNull() ?: "L'ensemble des réponses invite à avancer progressivement."
    }

    fun genererConseilsPratiquesPersonnalises(nomChat: String, reponsesChoix: Map<String, Int>, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): List<String> {
        val scoreMax = maxOf(peur, attachement, impulsivite, reactivite)
        if (scoreMax == 0) return listOf("Continuer l'observation du quotidien et maintenir les repères déjà en place.")
        return listOf("Une approche progressive, un axe après l'autre, paraît préférable.").take(4)
    }

    fun determinerProblemesImportants(peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): List<Axe> {
        return mutableListOf<Axe>().apply {
            if (peur >= 70) add(Axe.PEUR)
            if (attachement >= 70) add(Axe.ATTACHEMENT)
            if (impulsivite >= 70) add(Axe.IMPULSIVITE)
            if (reactivite >= 70) add(Axe.REACTIVITE)
        }
    }

    fun explicationProbleme(axe: Axe, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): String {
        if (maxOf(peur, attachement, impulsivite, reactivite) <= 30) return "Les éléments recueillis ne mettent pas en évidence de difficulté marquée à ce stade."
        return when (axe) {
            Axe.PEUR -> "Les réactions observées semblent s'inscrire dans une sensibilité émotionnelle relativement élevée."
            Axe.ATTACHEMENT -> "Les éléments recueillis suggèrent un besoin de proximité relativement important."
            Axe.IMPULSIVITE -> "Les réponses évoquent une difficulté possible dans la régulation de l'excitation."
            Axe.REACTIVITE -> "Les éléments recueillis suggèrent une réactivité marquée face à certains éléments de son environnement."
        }
    }

    fun conseilPrincipal(axe: Axe, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): String {
        if (maxOf(peur, attachement, impulsivite, reactivite) <= 30) return "Maintenir un cadre stable, cohérent et prévisible."
        return when (axe) {
            Axe.ATTACHEMENT -> "Travailler progressivement les moments de séparation, en restant sur des durées très courtes et maîtrisées."
            Axe.PEUR -> "Respecter les seuils de tolérance du chat et travailler à distance des éléments déclencheurs."
            Axe.REACTIVITE -> "Réduire la pression environnementale et maintenir le chat dans une zone de confort."
            Axe.IMPULSIVITE -> "Structurer les interactions avec des temps courts et des pauses régulières."
        }
    }

    fun genererPlanAction(axe: Axe, reponsesChoix: Map<String, Int>, nomChat: String): PlanAction {
        val aFaire = mutableListOf<String>(); val aEviter = mutableListOf<String>(); val aObserver = mutableListOf<String>()
        when (axe) {
            Axe.ATTACHEMENT -> {
                aFaire += "Réduire la charge émotionnelle autour des départs et retours."
                aFaire += "Proposer progressivement de petits moments d'autonomie."
                aEviter += "Les rituels de départ très marqués."
                aObserver += "Le moment précis où la tension apparaît."
                aObserver += "La capacité du chat à se poser seul."
            }
            Axe.PEUR -> {
                aFaire += "Travailler à distance suffisante pour que le chat reste calme."
                aFaire += "Laisser le chat observer sans le contraindre."
                aEviter += "Forcer le chat à affronter ce qui l'inquiète."
                aObserver += "La distance à laquelle la tension apparaît."
                aObserver += "Les signaux précoces de stress."
            }
            Axe.IMPULSIVITE -> {
                aFaire += "Structurer les interactions avec des séquences courtes et des pauses."
                aFaire += "Valoriser les moments de calme."
                aEviter += "Les interactions trop longues ou trop stimulantes."
                aObserver += "La rapidité de montée en excitation."
                aObserver += "Le temps nécessaire pour retrouver le calme."
            }
            Axe.REACTIVITE -> {
                aFaire += "Augmenter la distance avec les déclencheurs."
                aFaire += "Enrichir l'environnement avec des refuges et points hauts."
                aEviter += "Les confrontations directes."
                aObserver += "Les déclencheurs précis et leur intensité."
                aObserver += "La distance à laquelle le chat bascule."
            }
        }
        if (reponsesChoix["signe_physique"] == 2 || reponsesChoix["signe_physique"] == 3) aFaire += "Prévoir un avis vétérinaire pour écarter une cause physique."
        return PlanAction(aFaire.take(3), aEviter.take(3), aObserver.take(3))
    }

    fun genererMessageAide(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, niveauSituation: NiveauSituation, nomChat: String): String? {
        val nom = nomChatAffiche(nomChat)
        return when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> "Un accompagnement vétérinaire comportemental est recommandé pour $nom."
            contexte.physique >= 4 -> "Un avis vétérinaire est recommandé pour $nom."
            niveauSituation == NiveauSituation.SENSIBLE -> "La situation mérite un regard professionnel."
            else -> null
        }
    }

    fun detecterFacteursAggravants(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): List<String> {
        val facteurs = mutableListOf<String>()
        if (reponsesChoix["apparition"] == 1) facteurs += "Apparition brutale"
        if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Comportement en aggravation"
        if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Intensité très forte"
        if (contexte.physique >= 4) facteurs += "Suspicion de gêne ou cause physique"
        if (maxOf(peur, attachement, impulsivite, reactivite) >= 75) facteurs += "Niveau élevé sur au moins un axe"
        return facteurs.distinct()
    }

    fun detecterFacteursProtecteurs(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): List<String> {
        val facteurs = mutableListOf<String>()
        if (reponsesChoix["evolution_probleme"] == 0) facteurs += "Une amélioration semble déjà présente"
        if (reponsesChoix["frequence_probleme"] == 0) facteurs += "Le comportement reste peu fréquent"
        if (contexte.scoreContexte <= 3) facteurs += "Le contexte global ne suggère pas une situation dégradée"
        return facteurs.distinct()
    }

    fun detecterHypothesePrincipale(reponsesChoix: Map<String, Int>, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int, contexte: ContexteAnalyse): String {
        return when {
            contexte.physique >= 4 -> "Les éléments invitent d'abord à écarter une composante physique."
            attachement >= 60 -> "Les réponses peuvent évoquer une difficulté autour de la gestion de la séparation."
            peur >= 60 && reactivite >= 60 -> "Sensibilité émotionnelle associée à des réactions marquées face à l'environnement."
            impulsivite >= 60 -> "Difficulté possible dans la régulation émotionnelle."
            reactivite >= 60 -> "Réactivité accrue face à certains éléments de l'environnement."
            peur >= 60 -> "Sensibilité émotionnelle importante."
            else -> "Aucune hypothèse dominante ne se dégage clairement. Plusieurs facteurs peuvent être impliqués."
        }
    }

    fun determinerPrioriteAction(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): PrioriteAction {
        val maxAxe = maxOf(peur, attachement, impulsivite, reactivite)
        return when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteAction.URGENTE
            contexte.physique >= 4 -> PrioriteAction.URGENTE
            contexte.scoreContexte >= 10 -> PrioriteAction.ELEVEE
            maxAxe >= 75 -> PrioriteAction.ELEVEE
            contexte.scoreContexte >= 5 -> PrioriteAction.MODEREE
            maxAxe >= 55 -> PrioriteAction.MODEREE
            else -> PrioriteAction.FAIBLE
        }
    }

    fun construirePrioriteImmediate(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, priorite: PrioriteAction, niveauSituation: NiveauSituation, nomChat: String): PrioriteImmediate {
        val nom = nomChatAffiche(nomChat)
        return when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteImmediate(PrioriteAction.URGENTE, "Priorité immédiate : consulter", "Comme il y a déjà eu griffure ou morsure, la situation ne doit pas être banalisée pour $nom.", listOf("Éviter les situations à risque.", "Consulter un vétérinaire comportementaliste."))
            contexte.physique >= 4 -> PrioriteImmediate(PrioriteAction.URGENTE, "Priorité immédiate : écarter une cause physique", "Des signes physiques sont signalés chez $nom.", listOf("Prendre un avis vétérinaire rapidement."))
            priorite == PrioriteAction.ELEVEE -> PrioriteImmediate(PrioriteAction.ELEVEE, "Priorité immédiate : agir sans tarder", "La situation justifie une action rapide pour $nom.", listOf("Alléger les contextes difficiles.", "Envisager un accompagnement professionnel."))
            priorite == PrioriteAction.MODEREE -> PrioriteImmediate(PrioriteAction.MODEREE, "Priorité immédiate : avancer progressivement", "La situation mérite attention pour $nom.", listOf("Commencer un travail progressif.", "Observer fréquence et intensité."))
            else -> PrioriteImmediate(PrioriteAction.FAIBLE, "Priorité immédiate : surveiller calmement", "Rien d'urgent pour $nom.", listOf("Continuer l'observation.", "Maintenir un cadre stable."))
        }
    }

    fun construireExplicationResultat(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse, peur: Int, attachement: Int, impulsivite: Int, reactivite: Int): ExplicationResultat {
        val raisons = mutableListOf<String>()
        if (reponsesChoix["evolution_probleme"] == 2) raisons += "Le comportement semble s'aggraver."
        if (reponsesChoix["intensite_probleme"] == 3) raisons += "L'intensité décrite paraît importante."
        if (raisons.isEmpty()) raisons += "Les réponses suggèrent quelques points de vigilance."
        return ExplicationResultat(raisons.take(3), detecterFacteursAggravants(reponsesChoix, contexte, peur, attachement, impulsivite, reactivite), detecterFacteursProtecteurs(reponsesChoix, contexte))
    }

    fun genererSyntheseAvancee(nom: String, hypothese: String, priorite: PrioriteAction, aggravants: List<String>, protecteurs: List<String>): String {
        val intro = when (priorite) {
            PrioriteAction.FAIBLE -> "$nom présente un fonctionnement globalement stable."
            PrioriteAction.MODEREE -> "$nom présente une difficulté qui mérite une approche progressive."
            PrioriteAction.ELEVEE -> "$nom semble en difficulté sur un plan nécessitant une attention active."
            PrioriteAction.URGENTE -> "$nom présente des éléments qui justifient une attention rapide."
        }
        val aggr = if (aggravants.isNotEmpty()) "Éléments majorants : ${aggravants.joinToString(", ")}." else ""
        val prot = if (protecteurs.isNotEmpty()) "Éléments favorables : ${protecteurs.joinToString(", ")}." else ""
        return listOf(intro, "Hypothèse : $hypothese", aggr, prot).filter { it.isNotBlank() }.joinToString("\n\n")
    }

    fun calculerResultat(questions: List<Question>, reponsesTexte: Map<String, String>, reponsesChoix: Map<String, Int>): ResultatAnalyse {
        val peur = calculerPourcentageAxe(Axe.PEUR, questions, reponsesChoix)
        val attachement = calculerPourcentageAxe(Axe.ATTACHEMENT, questions, reponsesChoix)
        val impulsivite = calculerPourcentageAxe(Axe.IMPULSIVITE, questions, reponsesChoix)
        val reactivite = calculerPourcentageAxe(Axe.REACTIVITE, questions, reponsesChoix)
        val profil = genererProfilGlobal(reponsesTexte["nom_chat"].orEmpty(), peur, attachement, impulsivite, reactivite)
        val contexte = calculerContexte(reponsesChoix)
        val vigilance = calculerNiveauVigilance(questions, reponsesChoix, peur, attachement, impulsivite, reactivite, contexte)
        val niveauSituation = calculerNiveauSituation(reponsesChoix, contexte, peur, attachement, impulsivite, reactivite)
        val problemePrincipal = determinerProblemePrincipal(peur, attachement, impulsivite, reactivite)
        val planAction = genererPlanAction(problemePrincipal, reponsesChoix, reponsesTexte["nom_chat"].orEmpty())
        val hypothesePrincipale = detecterHypothesePrincipale(reponsesChoix, peur, attachement, impulsivite, reactivite, contexte)
        val prioriteAction = determinerPrioriteAction(reponsesChoix, contexte, peur, attachement, impulsivite, reactivite)
        val facteursAggravants = detecterFacteursAggravants(reponsesChoix, contexte, peur, attachement, impulsivite, reactivite)
        val facteursProtecteurs = detecterFacteursProtecteurs(reponsesChoix, contexte)
        val prioriteImmediate = construirePrioriteImmediate(reponsesChoix, contexte, prioriteAction, niveauSituation, reponsesTexte["nom_chat"].orEmpty())
        val explicationResultat = construireExplicationResultat(reponsesChoix, contexte, peur, attachement, impulsivite, reactivite)
        val syntheseAvancee = genererSyntheseAvancee(nomChatAffiche(reponsesTexte["nom_chat"].orEmpty()), hypothesePrincipale, prioriteAction, facteursAggravants, facteursProtecteurs)
        val raceCategorieTexte = reponsesChoix["race_categorie"]?.let { categoriesRaces.getOrNull(it)?.nom }
        return ResultatAnalyse(
            peur = peur, attachement = attachement, impulsivite = impulsivite, reactivite = reactivite,
            niveauPeur = calculerNiveauAxe(peur), niveauAttachement = calculerNiveauAxe(attachement),
            niveauImpulsivite = calculerNiveauAxe(impulsivite), niveauReactivite = calculerNiveauAxe(reactivite),
            profil = profil, vigilance = vigilance, niveauSituation = niveauSituation, contexte = contexte,
            problemePrincipal = problemePrincipal, problemesImportants = determinerProblemesImportants(peur, attachement, impulsivite, reactivite),
            explicationPrincipale = explicationProbleme(problemePrincipal, peur, attachement, impulsivite, reactivite),
            conseilPrincipal = conseilPrincipal(problemePrincipal, peur, attachement, impulsivite, reactivite),
            conseilsPratiques = genererConseilsPratiquesPersonnalises(reponsesTexte["nom_chat"].orEmpty(), reponsesChoix, peur, attachement, impulsivite, reactivite),
            planAction = planAction,
            messageSituation = genererMessageSituation(niveauSituation, reponsesTexte["nom_chat"].orEmpty()),
            raisonSituation = genererRaisonSituation(reponsesChoix, contexte),
            messageAide = genererMessageAide(reponsesChoix, contexte, niveauSituation, reponsesTexte["nom_chat"].orEmpty()),
            apparitionBrutale = reponsesChoix["apparition"] == 1,
            aDejaMordu = reponsesChoix["a_deja_griffe_mordu"] == 1,
            hypothesePrincipale = hypothesePrincipale, prioriteAction = prioriteAction,
            prioriteImmediate = prioriteImmediate, explicationResultat = explicationResultat,
            facteursAggravants = facteursAggravants, facteursProtecteurs = facteursProtecteurs,
            syntheseAvancee = syntheseAvancee, raceCategorie = raceCategorieTexte, racePrecise = null
        )
    }

    fun titreSectionPourQuestion(questionId: String): String = when (questionId) {
        "nom_chat", "age", "sexe", "sterilise" -> "Informations générales"
        "race_categorie" -> "Votre chat"
        "peur_stimuli", "adaptation_changements", "comportement_exterieur", "reaction_peur" -> "Sensibilité et peur"
        "support_absences", "pendant_absence", "suit_partout", "autre_personne_apaise", "proprete_maison", "si_non_quand" -> "Attachement et séparation"
        "calmer_apres_excitation", "jeu_comportement", "vole_objets", "poursuite_mouvement" -> "Excitation et impulsivité"
        "reaction_inconnus", "reaction_chats", "a_deja_griffe_mordu", "defense_ressources" -> "Réactivité"
        "a_un_probleme" -> "Pour aller plus loin"
        else -> "Contexte actuel"
    }

    fun aideQuestion(questionId: String): String? = when (questionId) {
        "race_categorie" -> "Choisissez la famille qui ressemble le plus à votre chat."
        "reaction_peur" -> "Choisissez la réaction la plus fréquente quand votre chat est inquiet."
        "support_absences" -> "Pensez au moment où vous partez et au temps où votre chat reste seul."
        "jeu_comportement" -> "Par exemple s'il mord fort, griffe, déborde ou a du mal à s'arrêter."
        "a_deja_griffe_mordu" -> "Même une griffure ou morsure ponctuelle compte."
        "signe_physique" -> "Même un doute peut être utile à signaler."
        else -> null
    }
}

// ═══════════════════════════════════════════════════════════
// QUESTIONS
// ═══════════════════════════════════════════════════════════

fun questionsApplication(): List<Question> {
    return listOf(
        QuestionTexte("nom_chat", "Quel est le prénom de votre chat ?"),

        QuestionChoix("race_categorie", "À quelle famille de races appartient votre chat ?",
            listOf("Européen / Gouttière", "Maine Coon", "Persan", "Siamois", "Ragdoll",
                "Bengal", "British Shorthair", "Autre race / inconnu")),

        QuestionChoix("age", "Quel âge a votre chat ?",
            listOf("Moins d'1 an", "Entre 1 et 3 ans", "Entre 4 et 8 ans", "9 ans et +")),

        QuestionChoix("sexe", "Votre chat est :", listOf("Un mâle", "Une femelle")),

        QuestionChoix("sterilise", "Votre chat est-il stérilisé ?",
            listOf("Oui, il est stérilisé", "Non, c'est un mâle entier", "Non, c'est une femelle entière")),

        QuestionChoix("peur_stimuli", "Votre chat montre-t-il de la peur face à certaines situations ?",
            listOf("Jamais", "Parfois", "Souvent"),
            axe = Axe.PEUR, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("adaptation_changements", "Votre chat a-t-il du mal à s'adapter aux changements ?",
            listOf("Non", "Un peu", "Oui"),
            axe = Axe.PEUR, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("comportement_exterieur", "Votre chat est plutôt :",
            listOf("Calme et détendu", "Excité / difficile à canaliser", "Craintif / en évitement"),
            axe = Axe.PEUR, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("reaction_peur", "Quand votre chat a peur, il réagit comment ?",
            listOf("Il récupère vite", "Il se cache / fuit", "Il panique ou devient agressif"),
            axe = Axe.PEUR, scoreParOption = listOf(0, 1, 4), signalAlerte = true),

        QuestionChoix("support_absences", "Comment votre chat vit-il vos absences ?",
            listOf("Bien", "Moyennement", "Difficilement"),
            axe = Axe.ATTACHEMENT, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("pendant_absence", "Pendant vos absences, votre chat :",
            listOf("Reste calme", "Peut vocaliser ou s'agiter", "Détruit / vocalise beaucoup / panique"),
            axe = Axe.ATTACHEMENT, scoreParOption = listOf(0, 1, 4), signalAlerte = true),

        QuestionChoix("suit_partout", "Votre chat vous suit-il partout dans la maison ?",
            listOf("Non", "Parfois", "Il ne me quitte pratiquement pas"),
            axe = Axe.ATTACHEMENT, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("autre_personne_apaise", "La présence d'une autre personne suffit-elle à l'apaiser ?",
            listOf("Oui", "Il n'est vraiment apaisé qu'avec moi", "Je ne sais pas"),
            axe = Axe.ATTACHEMENT, scoreParOption = listOf(0, 2, 0)),

        QuestionChoix("proprete_maison", "Votre chat est-il propre à la maison ?",
            listOf("Oui", "Non", "Parfois"),
            axe = Axe.ATTACHEMENT, scoreParOption = listOf(0, 2, 1)),

        QuestionChoix("si_non_quand", "Si non, dans quelles situations ?",
            listOf("Lors de vos absences", "En votre présence", "La nuit", "De manière aléatoire")),

        QuestionChoix("calmer_apres_excitation", "Votre chat a-t-il du mal à se calmer après un moment excitant ?",
            listOf("Non", "Parfois", "Oui"),
            axe = Axe.IMPULSIVITE, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("jeu_comportement", "Quand il joue, votre chat :",
            listOf("Reste contrôlé", "Peut beaucoup s'exciter", "Les jeux deviennent difficiles à contrôler"),
            axe = Axe.IMPULSIVITE, scoreParOption = listOf(0, 1, 4), signalAlerte = true),

        QuestionChoix("vole_objets", "Votre chat vole-t-il de la nourriture ou des objets ?",
            listOf("Non", "Parfois", "Souvent"),
            axe = Axe.IMPULSIVITE, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("poursuite_mouvement", "Votre chat poursuit-il facilement ce qui bouge ?",
            listOf("Non", "Parfois", "Souvent"),
            axe = Axe.IMPULSIVITE, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("reaction_inconnus", "Votre chat réagit-il difficilement aux personnes inconnues ?",
            listOf("Non", "Parfois", "Souvent"),
            axe = Axe.REACTIVITE, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("reaction_chats", "Votre chat réagit-il difficilement aux autres chats ?",
            listOf("Non", "Parfois", "Souvent"),
            axe = Axe.REACTIVITE, scoreParOption = listOf(0, 1, 2)),

        QuestionChoix("a_deja_griffe_mordu", "Votre chat a-t-il déjà griffé ou mordu ?",
            listOf("Non", "Oui"),
            axe = Axe.REACTIVITE, scoreParOption = listOf(0, 4), poids = 2, signalCritique = true),

        QuestionChoix("defense_ressources", "Votre chat grogne-t-il quand on s'approche de sa gamelle ou de ses affaires ?",
            listOf("Non, jamais", "Parfois", "Oui, c'est fréquent"),
            axe = Axe.REACTIVITE, scoreParOption = listOf(0, 2, 4), signalAlerte = true),

        QuestionChoix("a_un_probleme", "Y a-t-il un comportement qui vous préoccupe en ce moment ?",
            listOf("Oui, j'aimerais comprendre", "Non, tout va bien")),

        QuestionChoix("apparition", "Le comportement est apparu :",
            listOf("Progressivement", "Du jour au lendemain", "Je ne sais pas vraiment")),

        QuestionChoix("situation_principale", "Il apparaît principalement :",
            listOf("Dans beaucoup de situations", "Surtout en votre absence", "Surtout en votre présence")),

        QuestionChoix("duree_probleme", "Depuis combien de temps ?",
            listOf("Moins d'1 semaine", "1 à 4 semaines", "Plusieurs mois", "Depuis toujours")),

        QuestionChoix("evolution_probleme", "Ce comportement :",
            listOf("S'améliore", "Reste stable", "S'aggrave")),

        QuestionChoix("frequence_probleme", "À quelle fréquence ?",
            listOf("Rarement", "Quelques fois par semaine", "Tous les jours", "Plusieurs fois par jour")),

        QuestionChoix("intensite_probleme", "Quand cela arrive, c'est plutôt :",
            listOf("Gérable facilement", "Gênant", "Difficile à gérer", "Perte de contrôle / dangereux")),

        QuestionChoix("generalisation_probleme", "Ce comportement arrive :",
            listOf("Dans une situation précise", "Dans plusieurs situations", "Dans la plupart des situations")),

        QuestionChoix("changement_recent", "Y a-t-il eu un changement important récemment ?",
            listOf("Aucun changement", "Un changement léger", "Un changement important")),

        QuestionChoix("signe_physique", "Avez-vous remarqué un changement physique chez votre chat ?",
            listOf("Non, rien", "Oui, il semble plus fatigué", "Oui, il semble avoir mal", "Oui, autre chose"))
    )
}