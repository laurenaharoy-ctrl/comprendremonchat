package com.example.comprendremonchat

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
    // ── NOUVEAU : Origines possibles ─────────────────────────────────────────
    val originesPossibles: String = ""
)

// ═══════════════════════════════════════════════════════════
// HELPERS TEXTE
// ═══════════════════════════════════════════════════════════

fun nomChatAffiche(nom: String): String =
    nom.trim().replaceFirstChar { it.uppercase() }.ifBlank { "votre chat" }

fun libelleAxe(axe: Axe): String = when (axe) {
    Axe.SECURITE -> "Sécurité émotionnelle"
    Axe.LIEN -> "Lien humain"
    Axe.INSTINCTS -> "Expression des instincts"
    Axe.COHABITATION -> "Cohabitation"
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
    Axe.SECURITE -> "Un chat qui peine à se sentir en sécurité dans son environnement"
    Axe.LIEN -> "Un chat dont la relation avec son humain est au cœur de ses difficultés"
    Axe.INSTINCTS -> "Un chat dont les besoins instinctifs ne sont pas suffisamment exprimés"
    Axe.COHABITATION -> "Un chat en difficulté dans ses relations avec son entourage"
}

fun intentionChat(axe: Axe): String = when (axe) {
    Axe.SECURITE -> "Ses réactions traduisent une tentative de se protéger face à ce qu'il perçoit comme menaçant."
    Axe.LIEN -> "Son comportement reflète un besoin de connexion ou au contraire une difficulté à gérer la proximité."
    Axe.INSTINCTS -> "Ses comportements sont souvent l'expression d'instincts naturels qui n'ont pas trouvé de débouché adapté."
    Axe.COHABITATION -> "Ses réactions sont souvent une tentative de gérer une situation sociale qui le dépasse."
}

fun besoinPrincipal(axe: Axe): String = when (axe) {
    Axe.SECURITE -> "Besoin principal : prévisibilité, refuges disponibles et respect de ses seuils."
    Axe.LIEN -> "Besoin principal : trouver le juste équilibre entre présence rassurante et autonomie."
    Axe.INSTINCTS -> "Besoin principal : enrichissement environnemental et expression canalisée de ses instincts."
    Axe.COHABITATION -> "Besoin principal : gestion de l'espace et des ressources pour réduire les tensions."
}

fun phraseFin(nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return "L'objectif n'est pas d'étiqueter $nom, mais de mieux le comprendre pour avancer ensemble de façon plus sereine."
}

data class CategorieRace(
    val id: String, val nom: String,
    val predispositions: List<String>, val nuanceAnalyse: String
)

val categoriesRaces = listOf(
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

fun getNuanceAnalyse(race: String): String? =
    categoriesRaces.firstOrNull { it.nom.equals(race, ignoreCase = true) }?.nuanceAnalyse

fun getPredispositions(race: String): List<String> =
    categoriesRaces.firstOrNull { it.nom.equals(race, ignoreCase = true) }?.predispositions ?: emptyList()

// ═══════════════════════════════════════════════════════════
// HELPERS SEXE/STÉRILISATION
// 0 = mâle stérilisé, 1 = femelle stérilisée, 2 = mâle entier, 3 = femelle entière
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

    fun libelleNiveauAxe(niveau: NiveauAxe): String = when (niveau) {
        NiveauAxe.PEU_MARQUE -> "Peu marqué"
        NiveauAxe.A_SURVEILLER -> "À surveiller"
        NiveauAxe.MARQUE -> "Marqué"
        NiveauAxe.TRES_MARQUE -> "Très marqué"
    }

    fun determinerProblemePrincipal(securite: Int, lien: Int, instincts: Int, cohabitation: Int): Axe =
        listOf(Axe.SECURITE to securite, Axe.LIEN to lien, Axe.INSTINCTS to instincts, Axe.COHABITATION to cohabitation)
            .maxByOrNull { it.second }!!.first

    fun determinerProfilType(securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
        val top = listOf(Axe.SECURITE to securite, Axe.LIEN to lien, Axe.INSTINCTS to instincts, Axe.COHABITATION to cohabitation)
            .sortedByDescending { it.second }
        val first = top[0].first
        val firstScore = top[0].second
        if (firstScore <= 25) return "Chat épanoui et équilibré"
        return when (first) {
            Axe.SECURITE -> if (firstScore >= 75) "Chat anxieux" else "Chat sensible"
            Axe.LIEN -> if (firstScore >= 75) "Chat hyperattaché" else "Chat fusionnel"
            Axe.INSTINCTS -> if (firstScore >= 75) "Chat en manque de stimulation" else "Chat sous-stimulé"
            Axe.COHABITATION -> if (firstScore >= 75) "Chat en conflit" else "Chat territorial"
        }
    }

    fun phraseHumaineProfil(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        val nom = nomChatAffiche(nomChat)
        return when {
            maxAxe <= 25 -> "$nom semble évoluer sur une base globalement stable et sereine."
            maxAxe <= 50 -> "$nom présente quelques fragilités qui méritent attention."
            maxAxe <= 75 -> "$nom semble traverser une période de difficulté sur certains aspects."
            else -> "$nom présente des signaux importants qui nécessitent une attention particulière."
        }
    }

    fun genererProfilGlobal(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): ProfilGlobal {
        val scoreGlobal = ((securite + lien + instincts + cohabitation) / 4f).roundToInt()
        val profilType = determinerProfilType(securite, lien, instincts, cohabitation)
        val phraseHumaine = phraseHumaineProfil(nomChat, securite, lien, instincts, cohabitation)
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        return when {
            maxAxe <= 25 -> ProfilGlobal("Profil globalement équilibré", "Les réponses suggèrent un chat bien dans ses pattes.", profilType, scoreGlobal, phraseHumaine)
            securite >= 65 && lien >= 65 -> ProfilGlobal("Insécurité émotionnelle et dépendance", "Le profil évoque un chat qui cherche constamment à se rassurer.", profilType, scoreGlobal, phraseHumaine)
            securite >= 65 -> ProfilGlobal("Insécurité émotionnelle marquée", "Les réponses suggèrent un chat qui peine à se sentir en sécurité.", profilType, scoreGlobal, phraseHumaine)
            lien >= 65 -> ProfilGlobal("Hyperattachement ou difficultés relationnelles", "Le lien avec l'humain semble au cœur des difficultés.", profilType, scoreGlobal, phraseHumaine)
            instincts >= 65 -> ProfilGlobal("Instincts insuffisamment canalisés", "Les besoins naturels du chat ne trouvent pas de débouché adapté.", profilType, scoreGlobal, phraseHumaine)
            cohabitation >= 65 -> ProfilGlobal("Difficultés de cohabitation", "Les relations avec l'entourage sont source de tension.", profilType, scoreGlobal, phraseHumaine)
            else -> ProfilGlobal("Profil à nuancer", "Quelques points de vigilance sans qu'un aspect ne domine clairement.", profilType, scoreGlobal, phraseHumaine)
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
        if (contexte.physique >= 4) raisons += "Des signes physiques ou une gêne possible invitent à consulter un vétérinaire en priorité."
        return raisons.firstOrNull() ?: "L'ensemble des réponses invite à avancer progressivement."
    }

    fun genererConseilsPratiquesPersonnalises(nomChat: String, reponsesChoix: Map<String, Int>,
                                              securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> {
        val conseils = mutableListOf<String>()
        if (securite >= 50) conseils += "Multiplier les refuges et cachettes pour que ${nomChatAffiche(nomChat)} puisse se sentir en sécurité à tout moment."
        if (lien >= 50) conseils += "Travailler progressivement l'autonomie en gardant des rituels stables et prévisibles."
        if (instincts >= 50) conseils += "Proposer des sessions de jeu interactif quotidiennes pour canaliser les instincts naturels."
        if (cohabitation >= 50) conseils += "Assurer des ressources en double (gamelles, litières, griffoirs) pour réduire la compétition."
        if (reponsesChoix["surtoilettage"] == 1) conseils += "Le surtoilettage est souvent un signe de stress chronique — identifier et réduire les sources de tension."
        if (reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix)) conseils += "Le marquage urinaire chez un chat stérilisé signale généralement un stress territorial."
        if (estMaleEntier(reponsesChoix) && cohabitation >= 40) conseils += "Chez un mâle entier, le marquage et les tensions territoriales sont plus fréquents — la stérilisation peut être discutée avec votre vétérinaire."
        if (estFemelleEntiere(reponsesChoix) && securite >= 40) conseils += "Chez une femelle entière, certains comportements peuvent varier selon le cycle — observer si les tensions augmentent à certaines périodes."
        if (conseils.isEmpty()) conseils += "Continuer l'observation du quotidien et maintenir les repères déjà en place."
        return conseils.take(4)
    }

    fun determinerProblemesImportants(securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<Axe> {
        return mutableListOf<Axe>().apply {
            if (securite >= 65) add(Axe.SECURITE)
            if (lien >= 65) add(Axe.LIEN)
            if (instincts >= 65) add(Axe.INSTINCTS)
            if (cohabitation >= 65) add(Axe.COHABITATION)
        }
    }

    fun explicationProbleme(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
        if (maxOf(securite, lien, instincts, cohabitation) <= 25) return "Les éléments recueillis ne mettent pas en évidence de difficulté marquée à ce stade."
        return when (axe) {
            Axe.SECURITE -> "Les réponses suggèrent que votre chat éprouve des difficultés à se sentir en sécurité. Il perçoit peut-être son environnement comme imprévisible ou menaçant, ce qui génère une vigilance permanente épuisante."
            Axe.LIEN -> "Le lien avec vous semble occuper une place centrale dans les difficultés de votre chat. Qu'il soit trop fort (hyperattachement) ou trop fragile, cela peut générer des comportements perturbants."
            Axe.INSTINCTS -> "Les besoins instinctifs de votre chat — chasse, exploration, griffage — ne trouvent pas suffisamment de débouché adapté. Cette frustration peut s'exprimer à travers des comportements indésirables."
            Axe.COHABITATION -> "Votre chat semble en difficulté dans ses relations avec son entourage, qu'il s'agisse d'autres animaux ou de certains membres du foyer. La gestion de l'espace et des ressources est probablement en jeu."
        }
    }

    fun conseilPrincipal(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
        if (maxOf(securite, lien, instincts, cohabitation) <= 25) return "Maintenir un cadre stable, cohérent et prévisible — c'est la base du bien-être félin."
        return when (axe) {
            Axe.SECURITE -> "Enrichir l'environnement de refuges variés et garantir une routine stable et prévisible. La sécurité se construit dans la répétition rassurante."
            Axe.LIEN -> "Travailler progressivement l'autonomie avec des départs courts et sans rituel émotionnel, tout en maintenant des moments de contact choisis par le chat."
            Axe.INSTINCTS -> "Introduire des sessions de jeu interactif quotidiennes (canne à plume, jouets proies) et des food puzzles pour stimuler mentalement et physiquement votre chat."
            Axe.COHABITATION -> "Revoir la gestion des ressources et de l'espace — chaque chat doit avoir accès à ses propres ressources sans avoir à les défendre."
        }
    }

    fun genererPlanAction(axe: Axe, reponsesChoix: Map<String, Int>, nomChat: String): PlanAction {
        val aFaire = mutableListOf<String>()
        val aEviter = mutableListOf<String>()
        val aObserver = mutableListOf<String>()
        when (axe) {
            Axe.SECURITE -> {
                aFaire += "Créer ou optimiser les refuges (boîtes, cachettes en hauteur, tunnels)."
                aFaire += "Maintenir une routine quotidienne stable pour les repas, le jeu et les interactions."
                aFaire += "Laisser le chat initier les contacts plutôt que de l'imposer."
                aEviter += "Les changements brusques de l'environnement ou de la routine."
                aEviter += "Forcer le contact quand le chat signale qu'il veut être seul."
                aObserver += "Les moments et situations qui déclenchent la peur ou le retrait."
                aObserver += "L'évolution de l'utilisation des refuges."
            }
            Axe.LIEN -> {
                aFaire += "Pratiquer des départs courts et neutres, sans effusions émotionnelles."
                aFaire += "Laisser des vêtements portés pour rassurer en votre absence."
                aFaire += "Proposer des jouets d'occupation (distributeurs, puzzles alimentaires)."
                aEviter += "Les rituels de départ très marqués qui amplifient l'anxiété."
                aEviter += "Céder systématiquement aux demandes d'attention."
                aObserver += "Le comportement dans les minutes qui suivent votre départ."
                aObserver += "La capacité à se poser et se détendre seul."
            }
            Axe.INSTINCTS -> {
                aFaire += "Introduire 2 à 3 sessions de jeu interactif par jour de 10 à 15 minutes."
                aFaire += "Varier les jouets régulièrement pour maintenir l'intérêt."
                aFaire += "Utiliser des food puzzles pour nourrir l'instinct de chasse."
                aEviter += "Les jouets laissés en permanence qui perdent leur attrait."
                aEviter += "Les interactions trop courtes ou trop prévisibles."
                aObserver += "Le niveau d'énergie et d'intérêt pour le jeu."
                aObserver += "Les comportements qui s'améliorent après les sessions de jeu."
            }
            Axe.COHABITATION -> {
                aFaire += "Doubler toutes les ressources (gamelles, litières, griffoirs, couchages)."
                aFaire += "Créer des zones réservées à chaque animal avec accès sécurisé."
                aFaire += "Favoriser les interactions positives en présence de nourriture ou de jeu."
                aEviter += "Forcer les interactions entre animaux en tension."
                aEviter += "Les situations de compétition pour les ressources."
                aObserver += "Les signaux précurseurs de tension avant les conflits."
                aObserver += "Les zones de l'espace que chaque animal s'approprie."
            }
        }
        if (reponsesChoix["signe_physique"] == 2 || reponsesChoix["signe_physique"] == 3) {
            aFaire += "Consulter un vétérinaire pour écarter une cause physique au comportement observé."
        }
        return PlanAction(aFaire.take(3), aEviter.take(3), aObserver.take(3))
    }

    fun genererMessageAide(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                           niveauSituation: NiveauSituation, nomChat: String,
                           securite: Int, lien: Int, instincts: Int, cohabitation: Int): String? {
        val nom = nomChatAffiche(nomChat)
        return when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> "Une griffure ou morsure a été signalée — un accompagnement par un vétérinaire comportementaliste ou un comportementaliste félin est recommandé pour $nom."
            contexte.physique >= 4 -> "Des signes physiques ont été relevés — une consultation vétérinaire est recommandée en priorité pour $nom avant toute approche comportementale."
            reponsesChoix["surtoilettage"] == 1 -> "Le surtoilettage peut avoir une origine médicale — un avis vétérinaire est conseillé pour $nom avant d'agir sur le plan comportemental."
            reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix) -> "Le marquage urinaire chez un chat stérilisé mérite d'abord un bilan vétérinaire pour $nom pour écarter une infection urinaire."
            niveauSituation == NiveauSituation.SENSIBLE -> "La situation mérite le regard d'un comportementaliste félin qui pourra accompagner $nom et vous guider concrètement."
            maxOf(securite, lien, instincts, cohabitation) >= 75 -> "L'intensité des difficultés observées suggère qu'un comportementaliste félin pourrait apporter une aide précieuse pour $nom."
            else -> null
        }
    }

    fun detecterFacteursAggravants(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                   securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> {
        val facteurs = mutableListOf<String>()
        if (reponsesChoix["apparition"] == 1) facteurs += "Apparition brutale du comportement"
        if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Comportement en aggravation"
        if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Intensité très forte"
        if (contexte.physique >= 4) facteurs += "Suspicion de cause physique ou médicale"
        if (reponsesChoix["acces_exterieur"] == 0 && instincts >= 50) facteurs += "Chat d'intérieur avec instincts peu canalisés"
        if (maxOf(securite, lien, instincts, cohabitation) >= 75) facteurs += "Niveau élevé sur au moins un axe"
        if (reponsesChoix["plusieurs_chats"] == 1 && cohabitation >= 50) facteurs += "Cohabitation multi-chats conflictuelle"
        if (estMaleEntier(reponsesChoix) && cohabitation >= 40) facteurs += "Mâle entier — marquage et tensions territoriales plus fréquents"
        return facteurs.distinct()
    }

    fun detecterFacteursProtecteurs(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): List<String> {
        val facteurs = mutableListOf<String>()
        if (reponsesChoix["evolution_probleme"] == 0) facteurs += "Une amélioration semble déjà présente"
        if (reponsesChoix["frequence_probleme"] == 0) facteurs += "Le comportement reste peu fréquent"
        if (reponsesChoix["acces_exterieur"] == 1) facteurs += "Accès à l'extérieur disponible"
        if (estSterilise(reponsesChoix)) facteurs += "Chat stérilisé — facteur de stabilité"
        if (contexte.scoreContexte <= 3) facteurs += "Le contexte global ne suggère pas une situation dégradée"
        return facteurs.distinct()
    }

    fun detecterHypothesePrincipale(reponsesChoix: Map<String, Int>,
                                    securite: Int, lien: Int, instincts: Int, cohabitation: Int, contexte: ContexteAnalyse): String {
        return when {
            contexte.physique >= 4 -> "Les éléments signalés invitent d'abord à écarter une composante physique ou médicale avec un vétérinaire."
            reponsesChoix["surtoilettage"] == 1 && securite >= 50 -> "Le surtoilettage associé à une insécurité émotionnelle évoque un stress chronique qui s'exprime corporellement."
            reponsesChoix["marquage_urinaire"] == 1 -> "Le marquage urinaire évoque un stress territorial — à explorer après bilan vétérinaire."
            securite >= 65 && lien >= 65 -> "Un attachement anxieux doublé d'une insécurité émotionnelle — votre chat cherche constamment à se rassurer."
            securite >= 65 -> "Une insécurité émotionnelle importante qui se traduit par une vigilance permanente et des réactions de peur."
            lien >= 65 -> "Un hyperattachement ou une difficulté à gérer la séparation qui génère de la détresse en votre absence."
            instincts >= 65 -> "Des instincts naturels (chasse, exploration, griffage) insuffisamment canalisés qui cherchent à s'exprimer."
            cohabitation >= 65 -> "Des tensions de cohabitation qui génèrent stress et conflits au quotidien."
            else -> "Plusieurs facteurs semblent impliqués sans qu'un axe ne domine clairement — une approche globale est recommandée."
        }
    }

    fun determinerPrioriteAction(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                 securite: Int, lien: Int, instincts: Int, cohabitation: Int): PrioriteAction {
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
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

    fun construirePrioriteImmediate(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                    priorite: PrioriteAction, niveauSituation: NiveauSituation, nomChat: String): PrioriteImmediate {
        val nom = nomChatAffiche(nomChat)
        return when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteImmediate(PrioriteAction.URGENTE, "Priorité immédiate : consulter un professionnel",
                "Comme il y a déjà eu griffure ou morsure, la situation ne doit pas être banalisée pour $nom.",
                listOf("Éviter les situations à risque identifiées.", "Consulter un vétérinaire comportementaliste ou un comportementaliste félin."))
            contexte.physique >= 4 -> PrioriteImmediate(PrioriteAction.URGENTE, "Priorité immédiate : consulter un vétérinaire",
                "Des signes physiques sont signalés chez $nom — la priorité est médicale.",
                listOf("Prendre un rendez-vous vétérinaire rapidement.", "Ne pas attendre que les symptômes s'aggravent."))
            priorite == PrioriteAction.ELEVEE -> PrioriteImmediate(PrioriteAction.ELEVEE, "Priorité immédiate : agir sans tarder",
                "La situation justifie une action rapide pour $nom.",
                listOf("Alléger les contextes difficiles.", "Envisager un accompagnement par un comportementaliste félin."))
            priorite == PrioriteAction.MODEREE -> PrioriteImmediate(PrioriteAction.MODEREE, "Priorité immédiate : avancer progressivement",
                "La situation mérite attention pour $nom.",
                listOf("Commencer un travail progressif sur l'environnement.", "Observer fréquence et intensité des comportements."))
            else -> PrioriteImmediate(PrioriteAction.FAIBLE, "Priorité immédiate : surveiller calmement",
                "Rien d'urgent pour $nom — continuez à observer.",
                listOf("Maintenir un cadre stable et prévisible.", "Enrichir progressivement l'environnement."))
        }
    }

    fun construireExplicationResultat(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                      securite: Int, lien: Int, instincts: Int, cohabitation: Int): ExplicationResultat {
        val raisons = mutableListOf<String>()
        if (reponsesChoix["evolution_probleme"] == 2) raisons += "Le comportement semble s'aggraver progressivement."
        if (reponsesChoix["intensite_probleme"] == 3) raisons += "L'intensité décrite paraît importante et impacte le quotidien."
        if (reponsesChoix["generalisation_probleme"] == 2) raisons += "Le comportement touche de nombreuses situations différentes."
        if (raisons.isEmpty()) raisons += "Les réponses suggèrent quelques points de vigilance à surveiller."
        return ExplicationResultat(raisons.take(3),
            detecterFacteursAggravants(reponsesChoix, contexte, securite, lien, instincts, cohabitation),
            detecterFacteursProtecteurs(reponsesChoix, contexte))
    }

    fun genererSyntheseAvancee(nom: String, hypothese: String, priorite: PrioriteAction,
                               aggravants: List<String>, protecteurs: List<String>): String {
        val intro = when (priorite) {
            PrioriteAction.FAIBLE -> "$nom présente un fonctionnement globalement stable."
            PrioriteAction.MODEREE -> "$nom présente une difficulté qui mérite une approche progressive."
            PrioriteAction.ELEVEE -> "$nom semble en difficulté sur un plan nécessitant une attention active."
            PrioriteAction.URGENTE -> "$nom présente des éléments qui justifient une attention rapide et professionnelle."
        }
        val aggr = if (aggravants.isNotEmpty()) "Éléments majorants : ${aggravants.joinToString(", ")}." else ""
        val prot = if (protecteurs.isNotEmpty()) "Éléments favorables : ${protecteurs.joinToString(", ")}." else ""
        return listOf(intro, "Hypothèse : $hypothese", aggr, prot).filter { it.isNotBlank() }.joinToString("\n\n")
    }

    // ═══════════════════════════════════════════════════════════
    // ORIGINES POSSIBLES
    // ═══════════════════════════════════════════════════════════

    fun genererOriginesPossibles(
        nomChat: String,
        axe: Axe,
        securite: Int,
        lien: Int,
        instincts: Int,
        cohabitation: Int,
        reponsesChoix: Map<String, Int>
    ): String {
        val nom = nomChatAffiche(nomChat)
        val maxAxe = maxOf(securite, lien, instincts, cohabitation)
        if (maxAxe <= 25) return "$nom semble évoluer dans un équilibre global satisfaisant. Aucune origine comportementale particulière ne ressort à ce stade."

        return when (axe) {
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

    fun calculerResultat(questions: List<Question>, reponsesTexte: Map<String, String>, reponsesChoix: Map<String, Int>): ResultatAnalyse {
        val securite = calculerPourcentageAxe(Axe.SECURITE, questions, reponsesChoix)
        val lien = calculerPourcentageAxe(Axe.LIEN, questions, reponsesChoix)
        val instincts = calculerPourcentageAxe(Axe.INSTINCTS, questions, reponsesChoix)
        val cohabitation = calculerPourcentageAxe(Axe.COHABITATION, questions, reponsesChoix)
        val profil = genererProfilGlobal(reponsesTexte["nom_chat"].orEmpty(), securite, lien, instincts, cohabitation)
        val contexte = calculerContexte(reponsesChoix)
        val vigilance = calculerNiveauVigilance(questions, reponsesChoix, securite, lien, instincts, cohabitation, contexte)
        val niveauSituation = calculerNiveauSituation(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val problemePrincipal = determinerProblemePrincipal(securite, lien, instincts, cohabitation)
        val planAction = genererPlanAction(problemePrincipal, reponsesChoix, reponsesTexte["nom_chat"].orEmpty())
        val hypothesePrincipale = detecterHypothesePrincipale(reponsesChoix, securite, lien, instincts, cohabitation, contexte)
        val prioriteAction = determinerPrioriteAction(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val facteursAggravants = detecterFacteursAggravants(reponsesChoix, contexte, securite, lien, instincts, cohabitation)
        val facteursProtecteurs = detecterFacteursProtecteurs(reponsesChoix, contexte)
        val prioriteImmediate = construirePrioriteImmediate(reponsesChoix, contexte, prioriteAction, niveauSituation, reponsesTexte["nom_chat"].orEmpty())
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
            explicationPrincipale = explicationProbleme(problemePrincipal, securite, lien, instincts, cohabitation),
            conseilPrincipal = conseilPrincipal(problemePrincipal, securite, lien, instincts, cohabitation),
            conseilsPratiques = genererConseilsPratiquesPersonnalises(reponsesTexte["nom_chat"].orEmpty(), reponsesChoix, securite, lien, instincts, cohabitation),
            planAction = planAction,
            messageSituation = genererMessageSituation(niveauSituation, reponsesTexte["nom_chat"].orEmpty()),
            raisonSituation = genererRaisonSituation(reponsesChoix, contexte),
            messageAide = genererMessageAide(reponsesChoix, contexte, niveauSituation, reponsesTexte["nom_chat"].orEmpty(), securite, lien, instincts, cohabitation),
            apparitionBrutale = reponsesChoix["apparition"] == 1,
            aDejaMordu = reponsesChoix["a_deja_griffe_mordu"] == 1,
            hypothesePrincipale = hypothesePrincipale, prioriteAction = prioriteAction,
            prioriteImmediate = prioriteImmediate, explicationResultat = explicationResultat,
            facteursAggravants = facteursAggravants, facteursProtecteurs = facteursProtecteurs,
            syntheseAvancee = syntheseAvancee, raceCategorie = raceCategorieTexte, racePrecise = null,
            originesPossibles = originesPossibles
        )
    }

    fun titreSectionPourQuestion(questionId: String): String = when (questionId) {
        "nom_chat", "age", "sterilise", "acces_exterieur", "vie_interieur" -> "Votre chat"
        "race_categorie" -> "Profil de race"
        "reaction_bruit", "reaction_inconnu", "cache_souvent", "adaptation_changement",
        "reaction_veterinaire", "surtoilettage" -> "Sécurité émotionnelle"
        "suit_partout", "reaction_absence", "vocalise_absence", "proprete_stress",
        "demande_attention", "dort_avec_vous" -> "Lien humain"
        "joue_activement", "chasse_interieur", "griffage_surfaces", "hyperactivite_nocturne",
        "comportement_alimentaire", "destruction_ennui", "marquage_urinaire" -> "Expression des instincts"
        "relation_autres_chats", "relation_chien", "relation_enfants", "agressivite_caresses",
        "a_deja_griffe_mordu", "defense_ressources" -> "Cohabitation"
        "a_un_probleme" -> "Pour aller plus loin"
        else -> "Contexte actuel"
    }

    fun aideQuestion(questionId: String): String? = when (questionId) {
        "race_categorie" -> "Choisissez la famille qui ressemble le plus à votre chat."
        "sterilise" -> "La stérilisation influence certains comportements comme le marquage ou les tensions territoriales."
        "surtoilettage" -> "Le surtoilettage se manifeste par des zones de poils clairsemés ou des plaques sans poils."
        "reaction_absence" -> "Pensez à ce que vous observez à votre retour ou ce que vos voisins vous rapportent."
        "agressivite_caresses" -> "Par exemple, il mord ou griffe soudainement pendant que vous le caressez."
        "a_deja_griffe_mordu" -> "Même une griffure ou morsure ponctuelle, même légère, compte."
        "signe_physique" -> "Même un doute ou une suspicion mérite d'être signalé."
        "marquage_urinaire" -> "Le marquage urinaire se fait debout, queue dressée, sur des surfaces verticales."
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
                "Bengal", "British Shorthair", "Abyssin", "Sacré de Birmanie", "Autre race / inconnu")),

        QuestionChoix("age", "Quel âge a votre chat ?",
            listOf("Moins d'1 an (chaton)", "Entre 1 et 3 ans", "Entre 4 et 8 ans", "9 ans et plus (senior)")),

        QuestionChoix("sterilise", "Votre chat est :",
            listOf("Un mâle stérilisé", "Une femelle stérilisée", "Un mâle entier", "Une femelle entière")),

        QuestionChoix("acces_exterieur", "Votre chat a-t-il accès à l'extérieur ?",
            listOf("Oui, librement", "Oui, de façon contrôlée (balcon sécurisé, jardin surveillé)", "Non, uniquement en intérieur")),

        QuestionChoix("vie_interieur", "Si votre chat est d'intérieur, comment décririez-vous son environnement ?",
            listOf("Enrichi (griffoirs, hauteurs, jeux variés, fenêtres accessibles)",
                "Correct mais peut mieux faire", "Peu stimulant",
                "Je ne sais pas vraiment", "Mon chat a accès à l'extérieur")),

        QuestionChoix("reaction_bruit",
            "Comment votre chat réagit-il aux bruits soudains ou forts (aspirateur, tonnerre, travaux) ?",
            listOf("Il reste calme ou légèrement surpris, récupère vite",
                "Il sursaute et s'éloigne mais récupère en quelques minutes",
                "Il se cache et met longtemps à revenir",
                "Il panique totalement et reste perturbé longtemps"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("reaction_inconnu",
            "Comment votre chat réagit-il face à une personne inconnue ?",
            listOf("Il s'approche avec curiosité ou reste indifférent",
                "Il observe de loin prudemment puis s'approche parfois",
                "Il se cache pour toute la durée de la visite",
                "Il montre des signes d'agitation ou d'agressivité"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),

        QuestionChoix("cache_souvent",
            "À quelle fréquence votre chat se cache-t-il ou s'isole-t-il ?",
            listOf("Rarement — il est généralement visible et accessible",
                "Parfois, notamment quand il y a du monde ou du bruit",
                "Souvent, plusieurs fois par jour",
                "La plupart du temps — il est difficile à trouver"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4)),

        QuestionChoix("adaptation_changement",
            "Comment votre chat s'adapte-t-il aux changements (déménagement, nouveau meuble, visiteurs) ?",
            listOf("Très bien — il s'adapte rapidement",
                "Correctement — quelques jours de prudence puis ça passe",
                "Difficilement — il met plusieurs semaines à récupérer",
                "Très mal — chaque changement provoque une crise durable"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),

        QuestionChoix("reaction_veterinaire",
            "Comment se passe une visite chez le vétérinaire ?",
            listOf("Relativement bien, il supporte le transport et la consultation",
                "Stressant mais gérable",
                "Très difficile — il panique dans la caisse ou chez le vétérinaire",
                "Extrêmement difficile — c'est un traumatisme à chaque fois"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),

        QuestionChoix("surtoilettage",
            "Avez-vous observé un surtoilettage (zones de poils clairsemés, léchages répétitifs excessifs) ?",
            listOf("Non, son pelage est normal",
                "Parfois, sans que ça laisse de traces visibles",
                "Oui, avec des zones légèrement clairsemées"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3), signalAlerte = true),

        QuestionChoix("suit_partout",
            "Votre chat vous suit-il partout dans la maison ?",
            listOf("Non, il est plutôt indépendant",
                "Parfois, selon son humeur",
                "Souvent — il aime être dans la même pièce que vous",
                "Toujours — il ne vous quitte pratiquement pas"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 3)),

        QuestionChoix("reaction_absence",
            "Comment votre chat se comporte-t-il quand vous êtes absent(e) ?",
            listOf("Il semble gérer sereinement",
                "Il peut vocaliser un peu à votre départ mais se calme",
                "Il vocalise ou s'agite de façon notable",
                "Il présente des signes de détresse (destructions, malpropreté, voisins alertés)"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("vocalise_absence",
            "Votre chat vocalise-t-il de façon excessive (miaulements répétés, insistants) ?",
            listOf("Non, il est peu vocal ou vocal de façon normale",
                "Parfois, notamment au moment des repas",
                "Souvent, pour demander votre attention",
                "Très souvent, de façon envahissante"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),

        QuestionChoix("proprete_stress",
            "Votre chat a-t-il déjà fait ses besoins en dehors de sa litière ?",
            listOf("Non, jamais",
                "Très rarement, dans des circonstances exceptionnelles",
                "Occasionnellement, souvent lié à un événement stressant",
                "Régulièrement"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 4), signalAlerte = true),

        QuestionChoix("demande_attention",
            "Comment votre chat réagit-il quand vous ne lui accordez pas d'attention ?",
            listOf("Il accepte facilement et va vaquer à ses occupations",
                "Il insiste un peu puis se calme",
                "Il insiste fortement, miaule ou fait des bêtises pour attirer l'attention",
                "Il peut devenir agité ou agressif si ignoré"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),

        QuestionChoix("dort_avec_vous",
            "Votre chat dort-il avec vous ou cherche-t-il à être collé à vous la nuit ?",
            listOf("Non, il a ses endroits à lui",
                "Parfois, selon son envie",
                "Souvent — il préfère être sur votre lit",
                "Il s'agite ou vocalise si vous fermez la porte de la chambre"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 2)),

        QuestionChoix("joue_activement",
            "Votre chat joue-t-il activement avec des jouets ?",
            listOf("Oui, avec enthousiasme — il initie lui-même des sessions",
                "Oui, s'il est sollicité",
                "Peu — il s'ennuie rapidement ou montre peu d'intérêt",
                "Non, aucun intérêt pour le jeu"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("chasse_interieur",
            "Votre chat pratique-t-il des comportements de chasse à l'intérieur (épier, bondir, attraper) ?",
            listOf("Oui, régulièrement avec des jouets ou de petits objets",
                "Parfois", "Rarement",
                "Jamais — comportement totalement absent"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("griffage_surfaces",
            "Votre chat griffe-t-il des surfaces non autorisées (meubles, canapé, moquette) ?",
            listOf("Non ou très rarement — il utilise ses griffoirs",
                "Parfois les meubles en plus des griffoirs",
                "Souvent les meubles malgré les griffoirs disponibles",
                "Il ne griffe que les meubles, les griffoirs ne l'intéressent pas"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("hyperactivite_nocturne",
            "Votre chat présente-t-il une hyperactivité nocturne (courses, sauts, vocalisations la nuit) ?",
            listOf("Non, il est calme la nuit",
                "Parfois, occasionnellement",
                "Souvent — cela perturbe régulièrement votre sommeil",
                "Toutes les nuits — c'est un problème important"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),

        QuestionChoix("comportement_alimentaire",
            "Comment décririez-vous le comportement alimentaire de votre chat ?",
            listOf("Normal — il mange bien, à son rythme",
                "Il mange très vite ou réclame souvent",
                "Il vole de la nourriture ou fouille les poubelles",
                "Il a des variations importantes d'appétit (refuse de manger ou mange de façon compulsive)"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("destruction_ennui",
            "Votre chat provoque-t-il des destructions ou dégâts, notamment en votre absence ?",
            listOf("Non, jamais", "Rarement, quelques petits incidents",
                "Parfois — objets renversés, plantes abîmées",
                "Souvent — les dégâts sont importants et réguliers"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),

        QuestionChoix("marquage_urinaire",
            "Votre chat pratique-t-il le marquage urinaire (debout, sur des surfaces verticales) ?",
            listOf("Non, jamais",
                "Rarement, dans des situations de stress identifiées",
                "Oui, de temps en temps",
                "Oui, fréquemment"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("relation_autres_chats",
            "Si vous avez plusieurs chats, comment se passent leurs relations ?",
            listOf("Bonne entente générale, voire affection mutuelle",
                "Coexistence neutre — ils s'ignorent",
                "Tensions fréquentes mais sans agression physique",
                "Conflits réguliers avec agressions",
                "Je n'ai qu'un seul chat"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),

        QuestionChoix("relation_enfants",
            "Si des enfants sont présents, comment votre chat réagit-il ?",
            listOf("Très bien — il interagit ou les tolère sereinement",
                "Correctement — il garde ses distances mais sans tension",
                "Il fuit ou s'isole quand les enfants sont là",
                "Il peut réagir de façon agressive (griffe, mord)",
                "Pas d'enfants dans le foyer"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),

        QuestionChoix("agressivite_caresses",
            "Votre chat mord-il ou griffe-t-il pendant les caresses ou les jeux ?",
            listOf("Non, jamais",
                "Rarement — seulement quand les signaux d'alerte ont été ignorés",
                "Parfois, de façon imprévisible",
                "Souvent — les interactions physiques sont difficiles à gérer"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),

        QuestionChoix("a_deja_griffe_mordu",
            "Votre chat a-t-il déjà griffé ou mordu quelqu'un (vous, un proche, un enfant) ?",
            listOf("Non, jamais", "Oui, cela s'est déjà produit"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 4), poids = 2, signalCritique = true),

        QuestionChoix("defense_ressources",
            "Votre chat défend-il ses ressources (gamelle, litière, coin de repos) de façon agressive ?",
            listOf("Non, jamais",
                "Parfois — il grogne ou siffle si on s'approche",
                "Oui, fréquemment — il n'aime pas qu'on s'approche de ses affaires"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 2, 4), signalAlerte = true),

        QuestionChoix("a_un_probleme",
            "Y a-t-il un comportement particulier qui vous préoccupe en ce moment ?",
            listOf("Oui, j'aimerais en savoir plus", "Non, tout va bien dans l'ensemble")),

        QuestionChoix("apparition", "Ce comportement est apparu :",
            listOf("Progressivement", "Du jour au lendemain, de façon brutale", "Je ne sais pas vraiment")),

        QuestionChoix("duree_probleme", "Depuis combien de temps observez-vous ce comportement ?",
            listOf("Moins d'une semaine", "Entre 1 semaine et 1 mois", "Depuis plusieurs mois", "Depuis toujours ou très longtemps")),

        QuestionChoix("evolution_probleme", "Ce comportement évolue-t-il ?",
            listOf("Il s'améliore", "Il reste stable", "Il s'aggrave")),

        QuestionChoix("frequence_probleme", "À quelle fréquence ce comportement se manifeste-t-il ?",
            listOf("Rarement — quelques fois par mois", "Quelques fois par semaine", "Tous les jours", "Plusieurs fois par jour")),

        QuestionChoix("intensite_probleme", "Quand cela arrive, c'est plutôt :",
            listOf("Gérable facilement", "Gênant mais supportable", "Difficile à gérer", "Très intense, incontrôlable")),

        QuestionChoix("generalisation_probleme", "Ce comportement se produit :",
            listOf("Dans une situation très précise", "Dans plusieurs situations différentes", "Dans la plupart des situations quotidiennes")),

        QuestionChoix("changement_recent", "Y a-t-il eu un changement important récemment dans la vie de votre chat ?",
            listOf("Aucun changement notable",
                "Un changement léger (nouveau meuble, nouvelle routine)",
                "Un changement important (déménagement, nouvel animal, naissance, séparation)")),

        QuestionChoix("signe_physique", "Avez-vous observé des changements physiques chez votre chat (appétit, poids, pelage, éliminations) ?",
            listOf("Non, rien de particulier", "Peut-être — je ne suis pas certain(e)",
                "Oui, un changement notable", "Oui, quelque chose qui m'inquiète vraiment"))
    )
}