package com.laurena.comprendremonchat

import java.util.Locale

// ═══════════════════════════════════════════════════════════
// DÉTECTION DE LANGUE
// ═══════════════════════════════════════════════════════════

fun isEnglish(): Boolean {
    val locales = android.os.LocaleList.getDefault()
    for (i in 0 until locales.size()) {
        if (locales[i].language == "en") return true
    }
    return false
}

// ═══════════════════════════════════════════════════════════
// TEXTES DU MOTEUR — HELPERS TRADUITS
// ═══════════════════════════════════════════════════════════

fun libelleAxeTraduit(axe: Axe): String = if (isEnglish()) {
    when (axe) {
        Axe.SECURITE -> "Emotional security"
        Axe.LIEN -> "Human bond"
        Axe.INSTINCTS -> "Expression of instincts"
        Axe.COHABITATION -> "Cohabitation"
    }
} else {
    when (axe) {
        Axe.SECURITE -> "Sécurité émotionnelle"
        Axe.LIEN -> "Lien humain"
        Axe.INSTINCTS -> "Expression des instincts"
        Axe.COHABITATION -> "Cohabitation"
    }
}

fun texteNiveauSituationTraduit(niveau: NiveauSituation): String = if (isEnglish()) {
    when (niveau) {
        NiveauSituation.STABLE -> "Stable situation"
        NiveauSituation.A_TRAVAILLER -> "Needs work"
        NiveauSituation.SENSIBLE -> "Sensitive situation"
    }
} else {
    when (niveau) {
        NiveauSituation.STABLE -> "Situation stable"
        NiveauSituation.A_TRAVAILLER -> "À travailler"
        NiveauSituation.SENSIBLE -> "Situation sensible"
    }
}

fun texteVigilanceTraduit(vigilance: NiveauVigilance, nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return if (isEnglish()) {
        when (vigilance) {
            NiveauVigilance.FAIBLE -> "Nothing urgent stands out for $nom at this stage."
            NiveauVigilance.MODEREE -> "A few points deserve attention for $nom."
            NiveauVigilance.ELEVEE -> "Some elements justify prompt attention for $nom."
        }
    } else {
        when (vigilance) {
            NiveauVigilance.FAIBLE -> "Rien d'urgent ne ressort pour $nom à ce stade."
            NiveauVigilance.MODEREE -> "Quelques points méritent attention pour $nom."
            NiveauVigilance.ELEVEE -> "Certains éléments justifient une attention rapide pour $nom."
        }
    }
}

fun textePrioriteActionTraduit(priorite: PrioriteAction): String = if (isEnglish()) {
    when (priorite) {
        PrioriteAction.FAIBLE -> "Low"
        PrioriteAction.MODEREE -> "Moderate"
        PrioriteAction.ELEVEE -> "High"
        PrioriteAction.URGENTE -> "Urgent"
    }
} else {
    when (priorite) {
        PrioriteAction.FAIBLE -> "Faible"
        PrioriteAction.MODEREE -> "Modérée"
        PrioriteAction.ELEVEE -> "Élevée"
        PrioriteAction.URGENTE -> "Urgente"
    }
}

fun resumeEmotionnelTraduit(axe: Axe): String = if (isEnglish()) {
    when (axe) {
        Axe.SECURITE -> "A cat struggling to feel safe in its environment"
        Axe.LIEN -> "A cat whose relationship with its human is at the heart of its difficulties"
        Axe.INSTINCTS -> "A cat whose instinctive needs are not sufficiently expressed"
        Axe.COHABITATION -> "A cat struggling in its relationships with those around it"
    }
} else {
    when (axe) {
        Axe.SECURITE -> "Un chat qui peine à se sentir en sécurité dans son environnement"
        Axe.LIEN -> "Un chat dont la relation avec son humain est au cœur de ses difficultés"
        Axe.INSTINCTS -> "Un chat dont les besoins instinctifs ne sont pas suffisamment exprimés"
        Axe.COHABITATION -> "Un chat en difficulté dans ses relations avec son entourage"
    }
}

fun intentionChatTraduit(axe: Axe): String = if (isEnglish()) {
    when (axe) {
        Axe.SECURITE -> "Its reactions reflect an attempt to protect itself from what it perceives as threatening."
        Axe.LIEN -> "Its behavior reflects a need for connection or difficulty managing closeness."
        Axe.INSTINCTS -> "Its behaviors are often the expression of natural instincts that haven't found an appropriate outlet."
        Axe.COHABITATION -> "Its reactions are often an attempt to manage a social situation that overwhelms it."
    }
} else {
    when (axe) {
        Axe.SECURITE -> "Ses réactions traduisent une tentative de se protéger face à ce qu'il perçoit comme menaçant."
        Axe.LIEN -> "Son comportement reflète un besoin de connexion ou au contraire une difficulté à gérer la proximité."
        Axe.INSTINCTS -> "Ses comportements sont souvent l'expression d'instincts naturels qui n'ont pas trouvé de débouché adapté."
        Axe.COHABITATION -> "Ses réactions sont souvent une tentative de gérer une situation sociale qui le dépasse."
    }
}

fun besoinPrincipalTraduit(axe: Axe): String = if (isEnglish()) {
    when (axe) {
        Axe.SECURITE -> "Main need: predictability, available refuges and respect for its thresholds."
        Axe.LIEN -> "Main need: finding the right balance between reassuring presence and autonomy."
        Axe.INSTINCTS -> "Main need: environmental enrichment and channeled expression of its instincts."
        Axe.COHABITATION -> "Main need: space and resource management to reduce tensions."
    }
} else {
    when (axe) {
        Axe.SECURITE -> "Besoin principal : prévisibilité, refuges disponibles et respect de ses seuils."
        Axe.LIEN -> "Besoin principal : trouver le juste équilibre entre présence rassurante et autonomie."
        Axe.INSTINCTS -> "Besoin principal : enrichissement environnemental et expression canalisée de ses instincts."
        Axe.COHABITATION -> "Besoin principal : gestion de l'espace et des ressources pour réduire les tensions."
    }
}

fun phraseFinTraduit(nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return if (isEnglish()) {
        "The goal is not to label $nom, but to understand them better so you can move forward together more serenely."
    } else {
        "L'objectif n'est pas d'étiqueter $nom, mais de mieux le comprendre pour avancer ensemble de façon plus sereine."
    }
}

fun libelleNiveauAxeTraduit(niveau: NiveauAxe): String = if (isEnglish()) {
    when (niveau) {
        NiveauAxe.PEU_MARQUE -> "Low"
        NiveauAxe.A_SURVEILLER -> "To monitor"
        NiveauAxe.MARQUE -> "Marked"
        NiveauAxe.TRES_MARQUE -> "Very marked"
    }
} else {
    when (niveau) {
        NiveauAxe.PEU_MARQUE -> "Peu marqué"
        NiveauAxe.A_SURVEILLER -> "À surveiller"
        NiveauAxe.MARQUE -> "Marqué"
        NiveauAxe.TRES_MARQUE -> "Très marqué"
    }
}

// ═══════════════════════════════════════════════════════════
// TEXTES DU MOTEUR — PROFIL ET ANALYSE
// ═══════════════════════════════════════════════════════════

fun determinerProfilTypeTraduit(securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
    val top = listOf(Axe.SECURITE to securite, Axe.LIEN to lien, Axe.INSTINCTS to instincts, Axe.COHABITATION to cohabitation)
        .sortedByDescending { it.second }
    val first = top[0].first
    val firstScore = top[0].second
    return if (isEnglish()) {
        if (firstScore <= 25) return "Well-balanced and fulfilled cat"
        when (first) {
            Axe.SECURITE -> if (firstScore >= 75) "Anxious cat" else "Sensitive cat"
            Axe.LIEN -> if (firstScore >= 75) "Over-attached cat" else "Dependent cat"
            Axe.INSTINCTS -> if (firstScore >= 75) "Under-stimulated cat" else "Insufficiently stimulated cat"
            Axe.COHABITATION -> if (firstScore >= 75) "Conflicted cat" else "Territorial cat"
        }
    } else {
        if (firstScore <= 25) return "Chat épanoui et équilibré"
        when (first) {
            Axe.SECURITE -> if (firstScore >= 75) "Chat anxieux" else "Chat sensible"
            Axe.LIEN -> if (firstScore >= 75) "Chat hyperattaché" else "Chat fusionnel"
            Axe.INSTINCTS -> if (firstScore >= 75) "Chat en manque de stimulation" else "Chat sous-stimulé"
            Axe.COHABITATION -> if (firstScore >= 75) "Chat en conflit" else "Chat territorial"
        }
    }
}

fun phraseHumaineTraduit(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
    val maxAxe = maxOf(securite, lien, instincts, cohabitation)
    val nom = nomChatAffiche(nomChat)
    return if (isEnglish()) {
        when {
            maxAxe <= 25 -> "$nom seems to be evolving on an overall stable and serene foundation."
            maxAxe <= 50 -> "$nom shows some vulnerabilities that deserve attention."
            maxAxe <= 75 -> "$nom seems to be going through a difficult period in some areas."
            else -> "$nom is showing significant signals that require particular attention."
        }
    } else {
        when {
            maxAxe <= 25 -> "$nom semble évoluer sur une base globalement stable et sereine."
            maxAxe <= 50 -> "$nom présente quelques fragilités qui méritent attention."
            maxAxe <= 75 -> "$nom semble traverser une période de difficulté sur certains aspects."
            else -> "$nom présente des signaux importants qui nécessitent une attention particulière."
        }
    }
}

fun genererProfilGlobalTraduit(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): ProfilGlobal {
    val scoreGlobal = ((securite + lien + instincts + cohabitation) / 4.0).toInt()
    val profilType = determinerProfilTypeTraduit(securite, lien, instincts, cohabitation)
    val phraseHumaine = phraseHumaineTraduit(nomChat, securite, lien, instincts, cohabitation)
    val maxAxe = maxOf(securite, lien, instincts, cohabitation)
    return if (isEnglish()) {
        when {
            maxAxe <= 25 -> ProfilGlobal("Overall balanced profile", "The answers suggest a cat that is comfortable in its own skin.", profilType, scoreGlobal, phraseHumaine)
            securite >= 65 && lien >= 65 -> ProfilGlobal("Emotional insecurity and dependency", "The profile suggests a cat constantly seeking reassurance.", profilType, scoreGlobal, phraseHumaine)
            securite >= 65 -> ProfilGlobal("Marked emotional insecurity", "The answers suggest a cat struggling to feel safe.", profilType, scoreGlobal, phraseHumaine)
            lien >= 65 -> ProfilGlobal("Over-attachment or relational difficulties", "The bond with humans seems central to the difficulties.", profilType, scoreGlobal, phraseHumaine)
            instincts >= 65 -> ProfilGlobal("Insufficiently channeled instincts", "The cat's natural needs are not finding an appropriate outlet.", profilType, scoreGlobal, phraseHumaine)
            cohabitation >= 65 -> ProfilGlobal("Cohabitation difficulties", "Relationships with others are a source of tension.", profilType, scoreGlobal, phraseHumaine)
            else -> ProfilGlobal("Profile to nuance", "A few points of vigilance without one aspect clearly dominating.", profilType, scoreGlobal, phraseHumaine)
        }
    } else {
        when {
            maxAxe <= 25 -> ProfilGlobal("Profil globalement équilibré", "Les réponses suggèrent un chat bien dans ses pattes.", profilType, scoreGlobal, phraseHumaine)
            securite >= 65 && lien >= 65 -> ProfilGlobal("Insécurité émotionnelle et dépendance", "Le profil évoque un chat qui cherche constamment à se rassurer.", profilType, scoreGlobal, phraseHumaine)
            securite >= 65 -> ProfilGlobal("Insécurité émotionnelle marquée", "Les réponses suggèrent un chat qui peine à se sentir en sécurité.", profilType, scoreGlobal, phraseHumaine)
            lien >= 65 -> ProfilGlobal("Hyperattachement ou difficultés relationnelles", "Le lien avec l'humain semble au cœur des difficultés.", profilType, scoreGlobal, phraseHumaine)
            instincts >= 65 -> ProfilGlobal("Instincts insuffisamment canalisés", "Les besoins naturels du chat ne trouvent pas de débouché adapté.", profilType, scoreGlobal, phraseHumaine)
            cohabitation >= 65 -> ProfilGlobal("Difficultés de cohabitation", "Les relations avec l'entourage sont source de tension.", profilType, scoreGlobal, phraseHumaine)
            else -> ProfilGlobal("Profil à nuancer", "Quelques points de vigilance sans qu'un aspect ne domine clairement.", profilType, scoreGlobal, phraseHumaine)
        }
    }
}

fun explicationProblemeTraduit(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
    if (maxOf(securite, lien, instincts, cohabitation) <= 25) return if (isEnglish())
        "The information collected does not highlight any marked difficulty at this stage."
    else
        "Les éléments recueillis ne mettent pas en évidence de difficulté marquée à ce stade."
    return if (isEnglish()) {
        when (axe) {
            Axe.SECURITE -> "The answers suggest your cat has difficulty feeling safe. It may perceive its environment as unpredictable or threatening, generating exhausting permanent vigilance."
            Axe.LIEN -> "The bond with you seems to play a central role in your cat's difficulties. Whether too strong (over-attachment) or too fragile, this can generate disruptive behaviors."
            Axe.INSTINCTS -> "Your cat's instinctive needs — hunting, exploration, scratching — are not finding sufficient appropriate outlets. This frustration can be expressed through undesirable behaviors."
            Axe.COHABITATION -> "Your cat seems to struggle in its relationships with those around it, whether other animals or certain household members. Space and resource management is likely at play."
        }
    } else {
        when (axe) {
            Axe.SECURITE -> "Les réponses suggèrent que votre chat éprouve des difficultés à se sentir en sécurité. Il perçoit peut-être son environnement comme imprévisible ou menaçant, ce qui génère une vigilance permanente épuisante."
            Axe.LIEN -> "Le lien avec vous semble occuper une place centrale dans les difficultés de votre chat. Qu'il soit trop fort (hyperattachement) ou trop fragile, cela peut générer des comportements perturbants."
            Axe.INSTINCTS -> "Les besoins instinctifs de votre chat — chasse, exploration, griffage — ne trouvent pas suffisamment de débouché adapté. Cette frustration peut s'exprimer à travers des comportements indésirables."
            Axe.COHABITATION -> "Votre chat semble en difficulté dans ses relations avec son entourage, qu'il s'agisse d'autres animaux ou de certains membres du foyer. La gestion de l'espace et des ressources est probablement en jeu."
        }
    }
}

fun conseilPrincipalTraduit(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
    if (maxOf(securite, lien, instincts, cohabitation) <= 25) return if (isEnglish())
        "Maintain a stable, consistent and predictable framework — this is the foundation of feline well-being."
    else
        "Maintenir un cadre stable, cohérent et prévisible — c'est la base du bien-être félin."
    return if (isEnglish()) {
        when (axe) {
            Axe.SECURITE -> "Enrich the environment with varied refuges and ensure a stable, predictable routine. Security is built through reassuring repetition."
            Axe.LIEN -> "Gradually work on autonomy with short, emotionally neutral departures, while maintaining moments of contact chosen by the cat."
            Axe.INSTINCTS -> "Introduce daily interactive play sessions (feather wand, prey toys) and food puzzles to mentally and physically stimulate your cat."
            Axe.COHABITATION -> "Review resource and space management — each cat must have access to its own resources without having to defend them."
        }
    } else {
        when (axe) {
            Axe.SECURITE -> "Enrichir l'environnement de refuges variés et garantir une routine stable et prévisible. La sécurité se construit dans la répétition rassurante."
            Axe.LIEN -> "Travailler progressivement l'autonomie avec des départs courts et sans rituel émotionnel, tout en maintenant des moments de contact choisis par le chat."
            Axe.INSTINCTS -> "Introduire des sessions de jeu interactif quotidiennes (canne à plume, jouets proies) et des food puzzles pour stimuler mentalement et physiquement votre chat."
            Axe.COHABITATION -> "Revoir la gestion des ressources et de l'espace — chaque chat doit avoir accès à ses propres ressources sans avoir à les défendre."
        }
    }
}

fun genererPlanActionTraduit(axe: Axe, reponsesChoix: Map<String, Int>, nomChat: String): PlanAction {
    val aFaire = mutableListOf<String>()
    val aEviter = mutableListOf<String>()
    val aObserver = mutableListOf<String>()
    if (isEnglish()) {
        when (axe) {
            Axe.SECURITE -> {
                aFaire += "Create or optimize refuges (boxes, elevated hiding spots, tunnels)."
                aFaire += "Maintain a stable daily routine for meals, play and interactions."
                aFaire += "Let the cat initiate contact rather than imposing it."
                aEviter += "Sudden changes to the environment or routine."
                aEviter += "Forcing contact when the cat signals it wants to be alone."
                aObserver += "The moments and situations that trigger fear or withdrawal."
                aObserver += "Changes in use of refuges."
            }
            Axe.LIEN -> {
                aFaire += "Practice short, neutral departures without emotional fanfare."
                aFaire += "Leave worn clothing to provide reassurance during your absence."
                aFaire += "Offer activity toys (dispensers, food puzzles)."
                aEviter += "Highly marked departure rituals that amplify anxiety."
                aEviter += "Systematically giving in to attention demands."
                aObserver += "Behavior in the minutes following your departure."
                aObserver += "Ability to settle and relax alone."
            }
            Axe.INSTINCTS -> {
                aFaire += "Introduce 2 to 3 interactive play sessions per day of 10 to 15 minutes."
                aFaire += "Rotate toys regularly to maintain interest."
                aFaire += "Use food puzzles to feed the hunting instinct."
                aEviter += "Toys left out permanently that lose their appeal."
                aEviter += "Interactions that are too short or too predictable."
                aObserver += "Energy level and interest in play."
                aObserver += "Behaviors that improve after play sessions."
            }
            Axe.COHABITATION -> {
                aFaire += "Double all resources (food bowls, litter boxes, scratching posts, beds)."
                aFaire += "Create reserved areas for each animal with secure access."
                aFaire += "Encourage positive interactions in the presence of food or play."
                aEviter += "Forcing interactions between animals in tension."
                aEviter += "Situations of competition for resources."
                aObserver += "Precursor signals of tension before conflicts."
                aObserver += "Areas of space that each animal claims."
            }
        }
        if (reponsesChoix["signe_physique"] == 2 || reponsesChoix["signe_physique"] == 3) {
            aFaire += "Consult a veterinarian to rule out a physical cause for the observed behavior."
        }
    } else {
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
    }
    return PlanAction(aFaire.take(3), aEviter.take(3), aObserver.take(3))
}

fun genererMessageSituationTraduit(niveauSituation: NiveauSituation, nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return if (isEnglish()) {
        when (niveauSituation) {
            NiveauSituation.STABLE -> "At this stage, the situation seems fairly stable for $nom."
            NiveauSituation.A_TRAVAILLER -> "The situation deserves to be worked on progressively for $nom."
            NiveauSituation.SENSIBLE -> "The situation seems more sensitive for $nom and warrants special attention."
        }
    } else {
        when (niveauSituation) {
            NiveauSituation.STABLE -> "À ce stade, la situation semble plutôt stable pour $nom."
            NiveauSituation.A_TRAVAILLER -> "La situation mérite d'être travaillée progressivement pour $nom."
            NiveauSituation.SENSIBLE -> "La situation paraît plus sensible pour $nom et justifie une attention particulière."
        }
    }
}

fun genererRaisonSituationTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): String {
    return if (isEnglish()) {
        when {
            reponsesChoix["duree_probleme"] == 0 -> "The very recent nature of the behavior calls for particular vigilance."
            reponsesChoix["evolution_probleme"] == 2 -> "The fact that it seems to be worsening may indicate the problem is taking up more space."
            contexte.physique >= 4 -> "Physical signs or possible discomfort call for consulting a veterinarian as a priority."
            else -> "The overall answers suggest moving forward progressively."
        }
    } else {
        when {
            reponsesChoix["duree_probleme"] == 0 -> "Le caractère très récent du comportement invite à une vigilance particulière."
            reponsesChoix["evolution_probleme"] == 2 -> "Le fait que cela semble s'aggraver peut indiquer que le problème prend plus de place."
            contexte.physique >= 4 -> "Des signes physiques ou une gêne possible invitent à consulter un vétérinaire en priorité."
            else -> "L'ensemble des réponses invite à avancer progressivement."
        }
    }
}

fun genererConseilsPratiquesToTraduit(nomChat: String, reponsesChoix: Map<String, Int>,
                                      securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> {
    val conseils = mutableListOf<String>()
    val nom = nomChatAffiche(nomChat)
    if (isEnglish()) {
        if (securite >= 50) conseils += "Multiply refuges and hiding spots so $nom can feel safe at all times."
        if (lien >= 50) conseils += "Gradually work on autonomy while maintaining stable, predictable rituals."
        if (instincts >= 50) conseils += "Offer daily interactive play sessions to channel natural instincts."
        if (cohabitation >= 50) conseils += "Ensure duplicate resources (bowls, litter boxes, scratching posts) to reduce competition."
        if (reponsesChoix["surtoilettage"] == 1) conseils += "Over-grooming is often a sign of chronic stress — identify and reduce sources of tension."
        if (reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix)) conseils += "Urine marking in a neutered cat generally signals territorial stress."
        if (estMaleEntier(reponsesChoix) && cohabitation >= 40) conseils += "In an intact male, marking and territorial tensions are more frequent — neutering can be discussed with your vet."
        if (estFemelleEntiere(reponsesChoix) && securite >= 40) conseils += "In an intact female, some behaviors may vary with the cycle — observe whether tensions increase at certain times."
        if (conseils.isEmpty()) conseils += "Continue observing daily life and maintain the benchmarks already in place."
    } else {
        if (securite >= 50) conseils += "Multiplier les refuges et cachettes pour que $nom puisse se sentir en sécurité à tout moment."
        if (lien >= 50) conseils += "Travailler progressivement l'autonomie en gardant des rituels stables et prévisibles."
        if (instincts >= 50) conseils += "Proposer des sessions de jeu interactif quotidiennes pour canaliser les instincts naturels."
        if (cohabitation >= 50) conseils += "Assurer des ressources en double (gamelles, litières, griffoirs) pour réduire la compétition."
        if (reponsesChoix["surtoilettage"] == 1) conseils += "Le surtoilettage est souvent un signe de stress chronique — identifier et réduire les sources de tension."
        if (reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix)) conseils += "Le marquage urinaire chez un chat stérilisé signale généralement un stress territorial."
        if (estMaleEntier(reponsesChoix) && cohabitation >= 40) conseils += "Chez un mâle entier, le marquage et les tensions territoriales sont plus fréquents — la stérilisation peut être discutée avec votre vétérinaire."
        if (estFemelleEntiere(reponsesChoix) && securite >= 40) conseils += "Chez une femelle entière, certains comportements peuvent varier selon le cycle — observer si les tensions augmentent à certaines périodes."
        if (conseils.isEmpty()) conseils += "Continuer l'observation du quotidien et maintenir les repères déjà en place."
    }
    return conseils.take(4)
}

fun genererMessageAideTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                              niveauSituation: NiveauSituation, nomChat: String,
                              securite: Int, lien: Int, instincts: Int, cohabitation: Int): String? {
    val nom = nomChatAffiche(nomChat)
    return if (isEnglish()) {
        when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> "A scratch or bite has been reported — support from a veterinary behaviorist or feline behaviorist is recommended for $nom."
            contexte.physique >= 4 -> "Physical signs have been noted — a veterinary consultation is recommended as a priority for $nom before any behavioral approach."
            reponsesChoix["surtoilettage"] == 1 -> "Over-grooming can have a medical origin — veterinary advice is recommended for $nom before acting on the behavioral level."
            reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix) -> "Urine marking in a neutered cat warrants a veterinary check-up for $nom first to rule out a urinary infection."
            niveauSituation == NiveauSituation.SENSIBLE -> "The situation warrants the attention of a feline behaviorist who can support $nom and guide you concretely."
            maxOf(securite, lien, instincts, cohabitation) >= 75 -> "The intensity of the observed difficulties suggests that a feline behaviorist could provide valuable help for $nom."
            else -> null
        }
    } else {
        when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> "Une griffure ou morsure a été signalée — un accompagnement par un vétérinaire comportementaliste ou un comportementaliste félin est recommandé pour $nom."
            contexte.physique >= 4 -> "Des signes physiques ont été relevés — une consultation vétérinaire est recommandée en priorité pour $nom avant toute approche comportementale."
            reponsesChoix["surtoilettage"] == 1 -> "Le surtoilettage peut avoir une origine médicale — un avis vétérinaire est conseillé pour $nom avant d'agir sur le plan comportemental."
            reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix) -> "Le marquage urinaire chez un chat stérilisé mérite d'abord un bilan vétérinaire pour $nom pour écarter une infection urinaire."
            niveauSituation == NiveauSituation.SENSIBLE -> "La situation mérite le regard d'un comportementaliste félin qui pourra accompagner $nom et vous guider concrètement."
            maxOf(securite, lien, instincts, cohabitation) >= 75 -> "L'intensité des difficultés observées suggère qu'un comportementaliste félin pourrait apporter une aide précieuse pour $nom."
            else -> null
        }
    }
}

fun detecterHypothesePrincipaleTraduit(reponsesChoix: Map<String, Int>,
                                       securite: Int, lien: Int, instincts: Int, cohabitation: Int, contexte: ContexteAnalyse): String {
    return if (isEnglish()) {
        when {
            contexte.physique >= 4 -> "The reported elements suggest first ruling out a physical or medical component with a veterinarian."
            reponsesChoix["surtoilettage"] == 1 && securite >= 50 -> "Over-grooming combined with emotional insecurity suggests chronic stress expressing itself physically."
            reponsesChoix["marquage_urinaire"] == 1 -> "Urine marking suggests territorial stress — to explore after veterinary check-up."
            securite >= 65 && lien >= 65 -> "Anxious attachment combined with emotional insecurity — your cat is constantly seeking reassurance."
            securite >= 65 -> "Significant emotional insecurity translating into permanent vigilance and fear reactions."
            lien >= 65 -> "Over-attachment or difficulty managing separation generating distress in your absence."
            instincts >= 65 -> "Natural instincts (hunting, exploration, scratching) insufficiently channeled, seeking expression."
            cohabitation >= 65 -> "Cohabitation tensions generating daily stress and conflict."
            else -> "Several factors seem involved without one axis clearly dominating — a holistic approach is recommended."
        }
    } else {
        when {
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
}

fun construirePrioriteImmediateTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                       priorite: PrioriteAction, niveauSituation: NiveauSituation, nomChat: String): PrioriteImmediate {
    val nom = nomChatAffiche(nomChat)
    return if (isEnglish()) {
        when {
            reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteImmediate(PrioriteAction.URGENTE, "Immediate priority: consult a professional",
                "Since there has already been a scratch or bite, the situation should not be minimized for $nom.",
                listOf("Avoid identified risk situations.", "Consult a veterinary behaviorist or feline behaviorist."))
            contexte.physique >= 4 -> PrioriteImmediate(PrioriteAction.URGENTE, "Immediate priority: consult a veterinarian",
                "Physical signs have been reported for $nom — the priority is medical.",
                listOf("Make a veterinary appointment promptly.", "Do not wait for symptoms to worsen."))
            priorite == PrioriteAction.ELEVEE -> PrioriteImmediate(PrioriteAction.ELEVEE, "Immediate priority: act without delay",
                "The situation justifies prompt action for $nom.",
                listOf("Reduce difficult contexts.", "Consider support from a feline behaviorist."))
            priorite == PrioriteAction.MODEREE -> PrioriteImmediate(PrioriteAction.MODEREE, "Immediate priority: move forward progressively",
                "The situation deserves attention for $nom.",
                listOf("Begin gradual work on the environment.", "Observe frequency and intensity of behaviors."))
            else -> PrioriteImmediate(PrioriteAction.FAIBLE, "Immediate priority: monitor calmly",
                "Nothing urgent for $nom — continue observing.",
                listOf("Maintain a stable, predictable framework.", "Gradually enrich the environment."))
        }
    } else {
        when {
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
}

fun detecterFacteursAggravantsTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                      securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> {
    val facteurs = mutableListOf<String>()
    if (isEnglish()) {
        if (reponsesChoix["apparition"] == 1) facteurs += "Sudden onset of the behavior"
        if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Worsening behavior"
        if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Very high intensity"
        if (contexte.physique >= 4) facteurs += "Suspected physical or medical cause"
        if (reponsesChoix["acces_exterieur"] == 0 && instincts >= 50) facteurs += "Indoor cat with poorly channeled instincts"
        if (maxOf(securite, lien, instincts, cohabitation) >= 75) facteurs += "High level on at least one axis"
        if (reponsesChoix["plusieurs_chats"] == 1 && cohabitation >= 50) facteurs += "Conflictual multi-cat cohabitation"
        if (estMaleEntier(reponsesChoix) && cohabitation >= 40) facteurs += "Intact male — marking and territorial tensions more frequent"
    } else {
        if (reponsesChoix["apparition"] == 1) facteurs += "Apparition brutale du comportement"
        if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Comportement en aggravation"
        if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Intensité très forte"
        if (contexte.physique >= 4) facteurs += "Suspicion de cause physique ou médicale"
        if (reponsesChoix["acces_exterieur"] == 0 && instincts >= 50) facteurs += "Chat d'intérieur avec instincts peu canalisés"
        if (maxOf(securite, lien, instincts, cohabitation) >= 75) facteurs += "Niveau élevé sur au moins un axe"
        if (reponsesChoix["plusieurs_chats"] == 1 && cohabitation >= 50) facteurs += "Cohabitation multi-chats conflictuelle"
        if (estMaleEntier(reponsesChoix) && cohabitation >= 40) facteurs += "Mâle entier — marquage et tensions territoriales plus fréquents"
    }
    return facteurs.distinct()
}

fun detecterFacteursProtecteursTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): List<String> {
    val facteurs = mutableListOf<String>()
    if (isEnglish()) {
        if (reponsesChoix["evolution_probleme"] == 0) facteurs += "An improvement already seems present"
        if (reponsesChoix["frequence_probleme"] == 0) facteurs += "The behavior remains infrequent"
        if (reponsesChoix["acces_exterieur"] == 1) facteurs += "Access to the outdoors available"
        if (estSterilise(reponsesChoix)) facteurs += "Neutered cat — stabilizing factor"
        if (contexte.scoreContexte <= 3) facteurs += "The overall context does not suggest a degraded situation"
    } else {
        if (reponsesChoix["evolution_probleme"] == 0) facteurs += "Une amélioration semble déjà présente"
        if (reponsesChoix["frequence_probleme"] == 0) facteurs += "Le comportement reste peu fréquent"
        if (reponsesChoix["acces_exterieur"] == 1) facteurs += "Accès à l'extérieur disponible"
        if (estSterilise(reponsesChoix)) facteurs += "Chat stérilisé — facteur de stabilité"
        if (contexte.scoreContexte <= 3) facteurs += "Le contexte global ne suggère pas une situation dégradée"
    }
    return facteurs.distinct()
}

// ═══════════════════════════════════════════════════════════
// SECTIONS DU QUESTIONNAIRE
// ═══════════════════════════════════════════════════════════

fun titreSectionTraduit(questionId: String): String {
    return if (isEnglish()) {
        when (questionId) {
            "nom_chat", "age", "sterilise", "acces_exterieur", "vie_interieur" -> "Your cat"
            "race_categorie" -> "Breed profile"
            "reaction_bruit", "reaction_inconnu", "cache_souvent", "adaptation_changement",
            "reaction_veterinaire", "surtoilettage" -> "Emotional security"
            "suit_partout", "reaction_absence", "vocalise_absence", "proprete_stress",
            "demande_attention", "dort_avec_vous" -> "Human bond"
            "joue_activement", "chasse_interieur", "griffage_surfaces", "hyperactivite_nocturne",
            "comportement_alimentaire", "destruction_ennui", "marquage_urinaire" -> "Expression of instincts"
            "relation_autres_chats", "relation_chien", "relation_enfants", "agressivite_caresses",
            "a_deja_griffe_mordu", "defense_ressources" -> "Cohabitation"
            "a_un_probleme" -> "Going further"
            else -> "Current context"
        }
    } else {
        when (questionId) {
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
    }
}

fun aideQuestionTraduit(questionId: String): String? {
    return if (isEnglish()) {
        when (questionId) {
            "race_categorie" -> "Choose the family that most resembles your cat."
            "sterilise" -> "Neutering influences certain behaviors such as marking or territorial tensions."
            "surtoilettage" -> "Over-grooming manifests as sparse fur areas or patches without hair."
            "reaction_absence" -> "Think about what you observe on your return or what your neighbors report."
            "agressivite_caresses" -> "For example, it bites or scratches suddenly while you are petting it."
            "a_deja_griffe_mordu" -> "Even a one-time or minor scratch or bite counts."
            "signe_physique" -> "Even a doubt or suspicion is worth reporting."
            "marquage_urinaire" -> "Urine marking is done standing up, tail raised, on vertical surfaces."
            else -> null
        }
    } else {
        when (questionId) {
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
}

// ═══════════════════════════════════════════════════════════
// QUESTIONS TRADUITES
// ═══════════════════════════════════════════════════════════

fun questionsApplicationTraduites(): List<Question> {
    return if (isEnglish()) listOf(
        QuestionTexte("nom_chat", "What is your cat's name?"),
        QuestionChoix("race_categorie", "Which breed family does your cat belong to?",
            listOf("European / Mixed breed", "Maine Coon", "Persian", "Siamese", "Ragdoll",
                "Bengal", "British Shorthair", "Abyssinian", "Sacred Birman", "Other breed / unknown")),
        QuestionChoix("age", "How old is your cat?",
            listOf("Under 1 year (kitten)", "Between 1 and 3 years", "Between 4 and 8 years", "9 years and over (senior)")),
        QuestionChoix("sterilise", "Your cat is:",
            listOf("A neutered male", "A spayed female", "An intact male", "An intact female")),
        QuestionChoix("acces_exterieur", "Does your cat have access to the outdoors?",
            listOf("Yes, freely", "Yes, in a controlled way (secure balcony, supervised garden)", "No, indoors only")),
        QuestionChoix("vie_interieur", "If your cat is indoors, how would you describe its environment?",
            listOf("Enriched (scratching posts, heights, varied toys, accessible windows)",
                "Decent but could be better", "Not very stimulating",
                "I'm not really sure", "My cat has access to the outdoors")),
        QuestionChoix("reaction_bruit", "How does your cat react to sudden or loud noises (vacuum cleaner, thunder, construction)?",
            listOf("It stays calm or slightly surprised, recovers quickly",
                "It startles and moves away but recovers within a few minutes",
                "It hides and takes a long time to come back",
                "It panics completely and stays unsettled for a long time"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
        QuestionChoix("reaction_inconnu", "How does your cat react to a stranger?",
            listOf("It approaches with curiosity or stays indifferent",
                "It observes cautiously from afar then sometimes approaches",
                "It hides for the entire duration of the visit",
                "It shows signs of agitation or aggression"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),
        QuestionChoix("cache_souvent", "How often does your cat hide or isolate itself?",
            listOf("Rarely — it is generally visible and accessible",
                "Sometimes, especially when there are people or noise",
                "Often, several times a day",
                "Most of the time — it is hard to find"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4)),
        QuestionChoix("adaptation_changement", "How does your cat adapt to changes (move, new furniture, visitors)?",
            listOf("Very well — it adapts quickly",
                "Fine — a few days of caution then it passes",
                "With difficulty — it takes several weeks to recover",
                "Very poorly — every change causes a lasting crisis"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),
        QuestionChoix("reaction_veterinaire", "How does a veterinary visit go?",
            listOf("Relatively well, it tolerates transport and the consultation",
                "Stressful but manageable",
                "Very difficult — it panics in the carrier or at the vet",
                "Extremely difficult — it is traumatic every time"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),
        QuestionChoix("surtoilettage", "Have you noticed over-grooming (sparse fur areas, repeated excessive licking)?",
            listOf("No, its coat is normal",
                "Sometimes, without leaving visible marks",
                "Yes, with slightly sparse areas"),
            axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3), signalAlerte = true),
        QuestionChoix("suit_partout", "Does your cat follow you everywhere in the house?",
            listOf("No, it is fairly independent",
                "Sometimes, depending on its mood",
                "Often — it likes to be in the same room as you",
                "Always — it barely leaves your side"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 3)),
        QuestionChoix("reaction_absence", "How does your cat behave when you are away?",
            listOf("It seems to manage calmly",
                "It may vocalize a little at your departure but settles down",
                "It vocalizes or becomes notably agitated",
                "It shows signs of distress (destruction, accidents, neighbors alerted)"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
        QuestionChoix("vocalise_absence", "Does your cat vocalize excessively (repeated, insistent meowing)?",
            listOf("No, it is quiet or vocalizes normally",
                "Sometimes, particularly at mealtimes",
                "Often, to demand your attention",
                "Very often, in an overwhelming way"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),
        QuestionChoix("proprete_stress", "Has your cat ever relieved itself outside its litter box?",
            listOf("No, never",
                "Very rarely, in exceptional circumstances",
                "Occasionally, often linked to a stressful event",
                "Regularly"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 4), signalAlerte = true),
        QuestionChoix("demande_attention", "How does your cat react when you don't give it attention?",
            listOf("It accepts easily and goes about its business",
                "It insists a little then calms down",
                "It insists strongly, meows or causes trouble to get attention",
                "It can become agitated or aggressive if ignored"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),
        QuestionChoix("dort_avec_vous", "Does your cat sleep with you or try to stay close to you at night?",
            listOf("No, it has its own spots",
                "Sometimes, depending on its mood",
                "Often — it prefers to be on your bed",
                "It gets agitated or vocalizes if you close the bedroom door"),
            axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 2)),
        QuestionChoix("joue_activement", "Does your cat actively play with toys?",
            listOf("Yes, enthusiastically — it initiates play sessions itself",
                "Yes, when encouraged",
                "A little — it gets bored quickly or shows little interest",
                "No, no interest in play at all"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
        QuestionChoix("chasse_interieur", "Does your cat display hunting behaviors indoors (stalking, pouncing, catching)?",
            listOf("Yes, regularly with toys or small objects",
                "Sometimes", "Rarely",
                "Never — behavior completely absent"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
        QuestionChoix("griffage_surfaces", "Does your cat scratch unauthorized surfaces (furniture, sofa, carpet)?",
            listOf("No or very rarely — it uses its scratching posts",
                "Sometimes furniture in addition to scratching posts",
                "Often on furniture despite available scratching posts",
                "It only scratches furniture, scratching posts don't interest it"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
        QuestionChoix("hyperactivite_nocturne", "Does your cat show nocturnal hyperactivity (running, jumping, vocalizing at night)?",
            listOf("No, it is calm at night",
                "Sometimes, occasionally",
                "Often — it regularly disrupts your sleep",
                "Every night — it is a significant problem"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),
        QuestionChoix("comportement_alimentaire", "How would you describe your cat's eating behavior?",
            listOf("Normal — it eats well, at its own pace",
                "It eats very fast or often begs",
                "It steals food or rummages through bins",
                "It has significant appetite variations (refuses to eat or eats compulsively)"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
        QuestionChoix("destruction_ennui", "Does your cat cause destruction or damage, especially in your absence?",
            listOf("No, never", "Rarely, a few minor incidents",
                "Sometimes — objects knocked over, plants damaged",
                "Often — the damage is significant and regular"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
        QuestionChoix("marquage_urinaire", "Does your cat practice urine marking (standing up, on vertical surfaces)?",
            listOf("No, never",
                "Rarely, in identified stressful situations",
                "Yes, from time to time",
                "Yes, frequently"),
            axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
        QuestionChoix("relation_autres_chats", "If you have several cats, how are their relations?",
            listOf("Good understanding in general, even mutual affection",
                "Neutral coexistence — they ignore each other",
                "Frequent tensions but no physical aggression",
                "Regular conflicts with aggression",
                "I only have one cat"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),
        QuestionChoix("relation_enfants", "If children are present, how does your cat react?",
            listOf("Very well — it interacts or tolerates them calmly",
                "Fine — it keeps its distance but without tension",
                "It flees or isolates itself when children are around",
                "It can react aggressively (scratches, bites)",
                "No children in the household"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),
        QuestionChoix("agressivite_caresses", "Does your cat bite or scratch during petting or play?",
            listOf("No, never",
                "Rarely — only when warning signals have been ignored",
                "Sometimes, unpredictably",
                "Often — physical interactions are difficult to manage"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
        QuestionChoix("a_deja_griffe_mordu", "Has your cat ever scratched or bitten someone (you, a family member, a child)?",
            listOf("No, never", "Yes, it has happened"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 4), poids = 2, signalCritique = true),
        QuestionChoix("defense_ressources", "Does your cat defend its resources (bowl, litter box, resting spot) aggressively?",
            listOf("No, never",
                "Sometimes — it growls or hisses if approached",
                "Yes, frequently — it doesn't like anyone approaching its things"),
            axe = Axe.COHABITATION, scoreParOption = listOf(0, 2, 4), signalAlerte = true),
        QuestionChoix("a_un_probleme", "Is there a particular behavior that concerns you right now?",
            listOf("Yes, I would like to know more", "No, everything is fine overall")),
        QuestionChoix("apparition", "This behavior appeared:",
            listOf("Gradually", "Suddenly, from one day to the next", "I'm not really sure")),
        QuestionChoix("duree_probleme", "How long have you been observing this behavior?",
            listOf("Less than a week", "Between 1 week and 1 month", "For several months", "Since always or for a very long time")),
        QuestionChoix("evolution_probleme", "Is this behavior evolving?",
            listOf("It is improving", "It remains stable", "It is getting worse")),
        QuestionChoix("frequence_probleme", "How often does this behavior occur?",
            listOf("Rarely — a few times a month", "A few times a week", "Every day", "Several times a day")),
        QuestionChoix("intensite_probleme", "When it happens, it is rather:",
            listOf("Easily manageable", "Inconvenient but bearable", "Difficult to manage", "Very intense, uncontrollable")),
        QuestionChoix("generalisation_probleme", "This behavior occurs:",
            listOf("In one very specific situation", "In several different situations", "In most everyday situations")),
        QuestionChoix("changement_recent", "Has there been a significant change in your cat's life recently?",
            listOf("No notable change",
                "A minor change (new furniture, new routine)",
                "A major change (move, new animal, birth, separation)")),
        QuestionChoix("signe_physique", "Have you noticed any physical changes in your cat (appetite, weight, coat, elimination)?",
            listOf("No, nothing particular", "Perhaps — I'm not certain",
                "Yes, a notable change", "Yes, something that really concerns me"))
    ) else questionsApplication()
}