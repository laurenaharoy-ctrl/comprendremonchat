package com.laurena.comprendremonchat

import java.util.Locale

// ═══════════════════════════════════════════════════════════
// DÉTECTION DE LANGUE
// ═══════════════════════════════════════════════════════════

enum class AppLang { FR, EN, DE }

fun appLang(): AppLang = when (android.os.LocaleList.getDefault()[0].language) {
    "en" -> AppLang.EN
    "de" -> AppLang.DE
    else -> AppLang.FR
}

fun isEnglish(): Boolean = appLang() == AppLang.EN

fun isGerman(): Boolean = appLang() == AppLang.DE

/** Renvoie le texte dans la langue du téléphone : français (par défaut), anglais ou allemand. */
fun tr(fr: String, en: String, de: String): String = when (appLang()) {
    AppLang.EN -> en
    AppLang.DE -> de
    AppLang.FR -> fr
}

fun <T> trList(fr: T, en: T, de: T): T = when (appLang()) {
    AppLang.EN -> en
    AppLang.DE -> de
    AppLang.FR -> fr
}

// ═══════════════════════════════════════════════════════════
// TEXTES DU MOTEUR — HELPERS TRADUITS
// ═══════════════════════════════════════════════════════════

fun libelleAxeTraduit(axe: Axe): String = when (appLang()) {
    AppLang.EN -> {
        when (axe) {
            Axe.SECURITE -> "Emotional security"
            Axe.LIEN -> "Human bond"
            Axe.INSTINCTS -> "Expression of instincts"
            Axe.COHABITATION -> "Cohabitation"
        }
    }
    AppLang.DE -> {
        when (axe) {
            Axe.SECURITE -> "Emotionale Sicherheit"
            Axe.LIEN -> "Bindung zum Menschen"
            Axe.INSTINCTS -> "Ausdruck der Instinkte"
            Axe.COHABITATION -> "Zusammenleben und Revier"
        }
    }
    else -> {
        when (axe) {
            Axe.SECURITE -> "Sécurité émotionnelle"
            Axe.LIEN -> "Lien humain"
            Axe.INSTINCTS -> "Expression des instincts"
            Axe.COHABITATION -> "Cohabitation"
        }
    }
}

fun texteNiveauSituationTraduit(niveau: NiveauSituation): String = when (appLang()) {
    AppLang.EN -> {
        when (niveau) {
            NiveauSituation.STABLE -> "Stable situation"
            NiveauSituation.A_TRAVAILLER -> "Needs work"
            NiveauSituation.SENSIBLE -> "Sensitive situation"
        }
    }
    AppLang.DE -> {
        when (niveau) {
            NiveauSituation.STABLE -> "Stabile Situation"
            NiveauSituation.A_TRAVAILLER -> "Daran arbeiten"
            NiveauSituation.SENSIBLE -> "Heikle Situation"
        }
    }
    else -> {
        when (niveau) {
            NiveauSituation.STABLE -> "Situation stable"
            NiveauSituation.A_TRAVAILLER -> "À travailler"
            NiveauSituation.SENSIBLE -> "Situation sensible"
        }
    }
}

fun texteVigilanceTraduit(vigilance: NiveauVigilance, nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return when (appLang()) {
        AppLang.EN -> {
            when (vigilance) {
                NiveauVigilance.FAIBLE -> "Nothing urgent stands out for $nom at this stage."
                NiveauVigilance.MODEREE -> "A few points deserve attention for $nom."
                NiveauVigilance.ELEVEE -> "Some elements justify prompt attention for $nom."
            }
        }
        AppLang.DE -> {
            when (vigilance) {
                NiveauVigilance.FAIBLE -> "Im Moment zeigt sich bei $nom nichts Dringendes."
                NiveauVigilance.MODEREE -> "Einige Punkte verdienen bei $nom Aufmerksamkeit."
                NiveauVigilance.ELEVEE -> "Einige Anzeichen rechtfertigen bei $nom rasche Aufmerksamkeit."
            }
        }
        else -> {
            when (vigilance) {
                NiveauVigilance.FAIBLE -> "Rien d'urgent ne ressort pour $nom à ce stade."
                NiveauVigilance.MODEREE -> "Quelques points méritent attention pour $nom."
                NiveauVigilance.ELEVEE -> "Certains éléments justifient une attention rapide pour $nom."
            }
        }
    }
}

fun textePrioriteActionTraduit(priorite: PrioriteAction): String = when (appLang()) {
    AppLang.EN -> {
        when (priorite) {
            PrioriteAction.FAIBLE -> "Low"
            PrioriteAction.MODEREE -> "Moderate"
            PrioriteAction.ELEVEE -> "High"
            PrioriteAction.URGENTE -> "Urgent"
        }
    }
    AppLang.DE -> {
        when (priorite) {
            PrioriteAction.FAIBLE -> "Gering"
            PrioriteAction.MODEREE -> "Mäßig"
            PrioriteAction.ELEVEE -> "Hoch"
            PrioriteAction.URGENTE -> "Dringend"
        }
    }
    else -> {
        when (priorite) {
            PrioriteAction.FAIBLE -> "Faible"
            PrioriteAction.MODEREE -> "Modérée"
            PrioriteAction.ELEVEE -> "Élevée"
            PrioriteAction.URGENTE -> "Urgente"
        }
    }
}

fun resumeEmotionnelTraduit(axe: Axe, niveau: NiveauAxe = NiveauAxe.MARQUE): String {
    if (niveau == NiveauAxe.PEU_MARQUE) {
        return tr(
            "Un chat globalement à l'aise, avec quelques points à observer.",
            "A generally comfortable cat, with a few points to watch.",
            "Eine insgesamt ausgeglichene Katze, mit einigen Punkten zur Beobachtung."
        )
    }
    return when (appLang()) {
        AppLang.EN -> {
            when (axe) {
                Axe.SECURITE -> "A cat struggling to feel safe in its environment"
                Axe.LIEN -> "A cat whose relationship with its human is at the heart of its difficulties"
                Axe.INSTINCTS -> "A cat whose instinctive needs are not sufficiently expressed"
                Axe.COHABITATION -> "A cat struggling in its relationships with those around it"
            }
        }
        AppLang.DE -> {
            when (axe) {
                Axe.SECURITE -> "Eine Katze, die sich in ihrer Umgebung schwer sicher fühlt"
                Axe.LIEN -> "Eine Katze, deren Beziehung zu ihrem Menschen im Mittelpunkt ihrer Schwierigkeiten steht"
                Axe.INSTINCTS -> "Eine Katze, deren instinktive Bedürfnisse nicht ausreichend ausgelebt werden"
                Axe.COHABITATION -> "Eine Katze mit Schwierigkeiten im Umgang mit ihrem Umfeld"
            }
        }
        else -> {
            when (axe) {
                Axe.SECURITE -> "Un chat qui peine à se sentir en sécurité dans son environnement"
                Axe.LIEN -> "Un chat dont la relation avec son humain est au cœur de ses difficultés"
                Axe.INSTINCTS -> "Un chat dont les besoins instinctifs ne sont pas suffisamment exprimés"
                Axe.COHABITATION -> "Un chat en difficulté dans ses relations avec son entourage"
            }
        }
    }
}
fun intentionChatTraduit(axe: Axe): String = when (appLang()) {
    AppLang.EN -> {
        when (axe) {
            Axe.SECURITE -> "Its reactions reflect an attempt to protect itself from what it perceives as threatening."
            Axe.LIEN -> "Its behavior reflects a need for connection or difficulty managing closeness."
            Axe.INSTINCTS -> "Its behaviors are often the expression of natural instincts that haven't found an appropriate outlet."
            Axe.COHABITATION -> "Its reactions are often an attempt to manage a social situation that overwhelms it."
        }
    }
    AppLang.DE -> {
        when (axe) {
            Axe.SECURITE -> "Ihre Reaktionen zeigen den Versuch, sich vor dem zu schützen, was sie als bedrohlich wahrnimmt."
            Axe.LIEN -> "Ihr Verhalten spiegelt ein Bedürfnis nach Verbindung oder im Gegenteil eine Schwierigkeit im Umgang mit Nähe wider."
            Axe.INSTINCTS -> "Ihre Verhaltensweisen sind oft Ausdruck natürlicher Instinkte, die kein passendes Ventil gefunden haben."
            Axe.COHABITATION -> "Ihre Reaktionen sind oft ein Versuch, mit einer sozialen Situation umzugehen, die sie überfordert."
        }
    }
    else -> {
        when (axe) {
            Axe.SECURITE -> "Ses réactions traduisent une tentative de se protéger face à ce qu'il perçoit comme menaçant."
            Axe.LIEN -> "Son comportement reflète un besoin de connexion ou au contraire une difficulté à gérer la proximité."
            Axe.INSTINCTS -> "Ses comportements sont souvent l'expression d'instincts naturels qui n'ont pas trouvé de débouché adapté."
            Axe.COHABITATION -> "Ses réactions sont souvent une tentative de gérer une situation sociale qui le dépasse."
        }
    }
}

fun besoinPrincipalTraduit(axe: Axe): String = when (appLang()) {
    AppLang.EN -> {
        when (axe) {
            Axe.SECURITE -> "Main need: predictability, available refuges and respect for its thresholds."
            Axe.LIEN -> "Main need: finding the right balance between reassuring presence and autonomy."
            Axe.INSTINCTS -> "Main need: environmental enrichment and channeled expression of its instincts."
            Axe.COHABITATION -> "Main need: space and resource management to reduce tensions."
        }
    }
    AppLang.DE -> {
        when (axe) {
            Axe.SECURITE -> "Hauptbedürfnis: Vorhersehbarkeit, verfügbare Rückzugsorte und Respekt ihrer Grenzen."
            Axe.LIEN -> "Hauptbedürfnis: das richtige Gleichgewicht zwischen beruhigender Nähe und Selbstständigkeit finden."
            Axe.INSTINCTS -> "Hauptbedürfnis: eine bereicherte Umgebung und gelenktes Ausleben ihrer Instinkte."
            Axe.COHABITATION -> "Hauptbedürfnis: ein Umgang mit Raum und Ressourcen, der Spannungen verringert."
        }
    }
    else -> {
        when (axe) {
            Axe.SECURITE -> "Besoin principal : prévisibilité, refuges disponibles et respect de ses seuils."
            Axe.LIEN -> "Besoin principal : trouver le juste équilibre entre présence rassurante et autonomie."
            Axe.INSTINCTS -> "Besoin principal : enrichissement environnemental et expression canalisée de ses instincts."
            Axe.COHABITATION -> "Besoin principal : gestion de l'espace et des ressources pour réduire les tensions."
        }
    }
}

fun phraseFinTraduit(nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return when (appLang()) {
        AppLang.EN -> {
            "The goal is not to label $nom, but to understand them better so you can move forward together more serenely."
        }
        AppLang.DE -> {
            "Ziel ist es nicht, $nom ein Etikett aufzudrücken, sondern sie besser zu verstehen, um gemeinsam gelassener voranzukommen."
        }
        else -> {
            "L'objectif n'est pas d'étiqueter $nom, mais de mieux le comprendre pour avancer ensemble de façon plus sereine."
        }
    }
}

fun libelleNiveauAxeTraduit(niveau: NiveauAxe): String = when (appLang()) {
    AppLang.EN -> {
        when (niveau) {
            NiveauAxe.PEU_MARQUE -> "Low"
            NiveauAxe.A_SURVEILLER -> "To monitor"
            NiveauAxe.MARQUE -> "Marked"
            NiveauAxe.TRES_MARQUE -> "Very marked"
        }
    }
    AppLang.DE -> {
        when (niveau) {
            NiveauAxe.PEU_MARQUE -> "Wenig ausgeprägt"
            NiveauAxe.A_SURVEILLER -> "Zu beobachten"
            NiveauAxe.MARQUE -> "Ausgeprägt"
            NiveauAxe.TRES_MARQUE -> "Stark ausgeprägt"
        }
    }
    else -> {
        when (niveau) {
            NiveauAxe.PEU_MARQUE -> "Peu marqué"
            NiveauAxe.A_SURVEILLER -> "À surveiller"
            NiveauAxe.MARQUE -> "Marqué"
            NiveauAxe.TRES_MARQUE -> "Très marqué"
        }
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
    return when (appLang()) {
        AppLang.EN -> {
            if (firstScore <= 25) return "Well-balanced and fulfilled cat"
            when (first) {
                Axe.SECURITE -> if (firstScore >= 75) "Anxious cat" else "Sensitive cat"
                Axe.LIEN -> if (firstScore >= 75) "Over-attached cat" else "Dependent cat"
                Axe.INSTINCTS -> if (firstScore >= 75) "Under-stimulated cat" else "Insufficiently stimulated cat"
                Axe.COHABITATION -> if (firstScore >= 75) "Conflicted cat" else "Territorial cat"
            }
        }
        AppLang.DE -> {
            if (firstScore <= 25) return "Ausgeglichene, zufriedene Katze"
            when (first) {
                Axe.SECURITE -> if (firstScore >= 75) "Ängstliche Katze" else "Sensible Katze"
                Axe.LIEN -> if (firstScore >= 75) "Übermäßig anhängliche Katze" else "Eng verbundene Katze"
                Axe.INSTINCTS -> if (firstScore >= 75) "Katze mit Beschäftigungsmangel" else "Unterforderte Katze"
                Axe.COHABITATION -> if (firstScore >= 75) "Katze im Konflikt" else "Revierbetonte Katze"
            }
        }
        else -> {
            if (firstScore <= 25) return "Chat épanoui et équilibré"
            when (first) {
                Axe.SECURITE -> if (firstScore >= 75) "Chat anxieux" else "Chat sensible"
                Axe.LIEN -> if (firstScore >= 75) "Chat hyperattaché" else "Chat fusionnel"
                Axe.INSTINCTS -> if (firstScore >= 75) "Chat en manque de stimulation" else "Chat sous-stimulé"
                Axe.COHABITATION -> if (firstScore >= 75) "Chat en conflit" else "Chat territorial"
            }
        }
    }
}

fun phraseHumaineTraduit(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
    val maxAxe = maxOf(securite, lien, instincts, cohabitation)
    val nom = nomChatAffiche(nomChat)
    return when (appLang()) {
        AppLang.EN -> {
            when {
                maxAxe <= 25 -> "$nom seems to be evolving on an overall stable and serene foundation."
                maxAxe <= 50 -> "$nom shows some vulnerabilities that deserve attention."
                maxAxe <= 75 -> "$nom seems to be going through a difficult period in some areas."
                else -> "$nom is showing significant signals that require particular attention."
            }
        }
        AppLang.DE -> {
            when {
                maxAxe <= 25 -> "$nom scheint sich auf einer insgesamt stabilen und gelassenen Grundlage zu entwickeln."
                maxAxe <= 50 -> "$nom zeigt einige Schwachstellen, die Aufmerksamkeit verdienen."
                maxAxe <= 75 -> "$nom scheint in manchen Bereichen eine schwierige Phase zu durchleben."
                else -> "$nom zeigt deutliche Signale, die besondere Aufmerksamkeit erfordern."
            }
        }
        else -> {
            when {
                maxAxe <= 25 -> "$nom semble évoluer sur une base globalement stable et sereine."
                maxAxe <= 50 -> "$nom présente quelques fragilités qui méritent attention."
                maxAxe <= 75 -> "$nom semble traverser une période de difficulté sur certains aspects."
                else -> "$nom présente des signaux importants qui nécessitent une attention particulière."
            }
        }
    }
}

fun genererProfilGlobalTraduit(nomChat: String, securite: Int, lien: Int, instincts: Int, cohabitation: Int): ProfilGlobal {
    val scoreGlobal = ((securite + lien + instincts + cohabitation) / 4.0).toInt()
    val profilType = determinerProfilTypeTraduit(securite, lien, instincts, cohabitation)
    val phraseHumaine = phraseHumaineTraduit(nomChat, securite, lien, instincts, cohabitation)
    val maxAxe = maxOf(securite, lien, instincts, cohabitation)
    return when (appLang()) {
        AppLang.EN -> {
            when {
                maxAxe <= 25 -> ProfilGlobal("Overall balanced profile", "The answers suggest a cat that is comfortable in its own skin.", profilType, scoreGlobal, phraseHumaine)
                securite >= 65 && lien >= 65 -> ProfilGlobal("Emotional insecurity and dependency", "The profile suggests a cat constantly seeking reassurance.", profilType, scoreGlobal, phraseHumaine)
                securite >= 65 -> ProfilGlobal("Marked emotional insecurity", "The answers suggest a cat struggling to feel safe.", profilType, scoreGlobal, phraseHumaine)
                lien >= 65 -> ProfilGlobal("Over-attachment or relational difficulties", "The bond with humans seems central to the difficulties.", profilType, scoreGlobal, phraseHumaine)
                instincts >= 65 -> ProfilGlobal("Insufficiently channeled instincts", "The cat's natural needs are not finding an appropriate outlet.", profilType, scoreGlobal, phraseHumaine)
                cohabitation >= 65 -> ProfilGlobal("Cohabitation difficulties", "Relationships with others are a source of tension.", profilType, scoreGlobal, phraseHumaine)
                else -> ProfilGlobal("Profile to nuance", "A few points of vigilance without one aspect clearly dominating.", profilType, scoreGlobal, phraseHumaine)
            }
        }
        AppLang.DE -> {
            when {
                maxAxe <= 25 -> ProfilGlobal("Insgesamt ausgeglichenes Profil", "Die Antworten deuten auf eine Katze hin, die sich rundum wohlfühlt.", profilType, scoreGlobal, phraseHumaine)
                securite >= 65 && lien >= 65 -> ProfilGlobal("Emotionale Unsicherheit und Abhängigkeit", "Das Profil deutet auf eine Katze hin, die ständig nach Beruhigung sucht.", profilType, scoreGlobal, phraseHumaine)
                securite >= 65 -> ProfilGlobal("Ausgeprägte emotionale Unsicherheit", "Die Antworten deuten auf eine Katze hin, die sich schwer sicher fühlt.", profilType, scoreGlobal, phraseHumaine)
                lien >= 65 -> ProfilGlobal("Übermäßige Anhänglichkeit oder Schwierigkeiten in der Beziehung", "Die Bindung zum Menschen scheint im Mittelpunkt der Schwierigkeiten zu stehen.", profilType, scoreGlobal, phraseHumaine)
                instincts >= 65 -> ProfilGlobal("Zu wenig ausgelebte Instinkte", "Die natürlichen Bedürfnisse der Katze finden kein passendes Ventil.", profilType, scoreGlobal, phraseHumaine)
                cohabitation >= 65 -> ProfilGlobal("Schwierigkeiten im Zusammenleben", "Die Beziehungen zum Umfeld sorgen für Spannungen.", profilType, scoreGlobal, phraseHumaine)
                else -> ProfilGlobal("Differenziert zu betrachtendes Profil", "Einige Punkte verdienen Aufmerksamkeit, ohne dass ein Aspekt klar überwiegt.", profilType, scoreGlobal, phraseHumaine)
            }
        }
        else -> {
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
}

fun explicationProblemeTraduit(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int, reponsesChoix: Map<String, Int> = emptyMap()): String {
    if (reponsesChoix["proprete_type"] == 0) return tr("La malpropreté urinaire peut avoir des causes purement médicales (infection urinaire, problèmes rénaux ou vésicaux, calculs) qui produisent exactement les mêmes symptômes qu'un problème comportemental : le chat en vient à éviter le bac parce qu'il associe le geste d'y uriner à une douleur, sans lien avec un stress émotionnel.", "Urinary house-soiling can have purely medical causes (urinary infection, kidney or bladder issues, bladder stones) that produce the exact same symptoms as a behavioral problem: the cat starts avoiding the litter box because it associates the box itself with pain, with no link to emotional stress.", "Harn-Unsauberkeit kann rein medizinische Ursachen haben (Harnwegsinfektion, Nieren- oder Blasenprobleme, Blasensteine), die genau dieselben Symptome hervorrufen wie ein Verhaltensproblem: Die Katze meidet schließlich das Katzenklo, weil sie das Urinieren darin mit Schmerzen verbindet – ohne Zusammenhang mit emotionalem Stress.")
    if (reponsesChoix["proprete_type"] == 2) return tr("Quand l'urine et les selles sont concernées à la fois, les deux aspects n'ont généralement pas la même origine. La partie urinaire peut être purement médicale (infection, calculs, douleur), tandis que l'aspect selles est plus souvent lié au bac lui-même — propreté, taille ou emplacement — ou au stress, une fois la cause médicale écartée.", "When both urine and stools are involved, the two aspects usually don't share the same origin. The urinary part can be purely medical (infection, bladder stones, pain), while the stool aspect is more often linked to the litter box itself — cleanliness, size, or location — or to stress, once a medical cause has been ruled out.", "Wenn Urin und Kot gleichzeitig betroffen sind, haben beide meist nicht dieselbe Ursache. Der Harnanteil kann rein medizinisch sein (Infektion, Blasensteine, Schmerzen), während der Kotabsatz eher mit dem Katzenklo selbst zusammenhängt – Sauberkeit, Größe oder Standort – oder, sobald eine medizinische Ursache ausgeschlossen ist, mit Stress.")
    if (reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2)) return tr("Chez un chat âgé, la désorientation et les vocalises nocturnes inexpliquées peuvent refléter un vieillissement cérébral normal (déclin cognitif lié à l'âge) plutôt qu'un problème comportemental à corriger — un phénomène assez proche de ce que l'on observe parfois chez l'humain vieillissant.", "In an older cat, disorientation and unexplained night vocalizing can reflect normal brain aging (age-related cognitive decline) rather than a behavioral problem to correct — a phenomenon fairly close to what is sometimes observed in aging humans.", "Bei einer älteren Katze können Orientierungslosigkeit und unerklärliches nächtliches Miauen eher auf ein normales Altern des Gehirns (altersbedingter kognitiver Abbau) hinweisen als auf ein Verhaltensproblem, das korrigiert werden muss – ähnlich wie man es manchmal bei älteren Menschen beobachtet.")
    if (reponsesChoix["marquage_habitude_post_sterilisation"] == 0) return tr("Concrètement, cela signifie que ce comportement ne peut plus se résoudre en attendant que les hormones se stabilisent — c'est le geste en lui-même qu'il faut désapprendre, comme n'importe quelle habitude acquise. Cela demande généralement plus de patience et de constance qu'un marquage territorial ou lié au stress, mais reste tout à fait travaillable.", "In practice, this means the behavior can no longer be resolved simply by waiting for hormones to settle — the gesture itself needs to be unlearned, much like any acquired habit. This generally takes more patience and consistency than territorial or stress-related marking, but it is very much possible to work on.", "Konkret bedeutet das, dass sich dieses Verhalten nicht mehr lösen lässt, indem man abwartet, bis sich die Hormone stabilisieren – das Verhalten selbst muss verlernt werden, wie jede erlernte Gewohnheit. Das erfordert meist mehr Geduld und Beständigkeit als eine revier- oder stressbedingte Markierung, lässt sich aber durchaus bearbeiten.")
    if (maxOf(securite, lien, instincts, cohabitation) <= 25) return tr("Les éléments recueillis ne mettent pas en évidence de difficulté marquée à ce stade.", "The information collected does not highlight any marked difficulty at this stage.", "Die gesammelten Angaben zeigen derzeit keine ausgeprägte Schwierigkeit.")
    return when (appLang()) {
        AppLang.EN -> {
            when (axe) {
                Axe.SECURITE -> "The answers suggest your cat has difficulty feeling safe. It may perceive its environment as unpredictable or threatening, generating exhausting permanent vigilance."
                Axe.LIEN -> "The bond with you seems to play a central role in your cat's difficulties. Whether too strong (over-attachment) or too fragile, this can generate disruptive behaviors."
                Axe.INSTINCTS -> "Your cat's instinctive needs — hunting, exploration, scratching — are not finding sufficient appropriate outlets. This frustration can be expressed through undesirable behaviors."
                Axe.COHABITATION -> "Your cat seems to struggle in its relationships with those around it, whether other animals or certain household members. Space and resource management is likely at play."
            }
        }
        AppLang.DE -> {
            when (axe) {
                Axe.SECURITE -> "Die Antworten deuten darauf hin, dass sich Ihre Katze schwer sicher fühlt. Vielleicht nimmt sie ihre Umgebung als unberechenbar oder bedrohlich wahr, was eine anstrengende ständige Wachsamkeit erzeugt."
                Axe.LIEN -> "Die Bindung zu Ihnen scheint bei den Schwierigkeiten Ihrer Katze eine zentrale Rolle zu spielen. Ob sie zu stark (übermäßige Anhänglichkeit) oder zu brüchig ist – beides kann störende Verhaltensweisen auslösen."
                Axe.INSTINCTS -> "Die instinktiven Bedürfnisse Ihrer Katze – Jagen, Erkunden, Kratzen – finden kein ausreichendes Ventil. Dieser Frust kann sich in unerwünschtem Verhalten äußern."
                Axe.COHABITATION -> "Ihre Katze scheint Schwierigkeiten im Umgang mit ihrem Umfeld zu haben, sei es mit anderen Tieren oder mit bestimmten Mitgliedern des Haushalts. Wahrscheinlich spielt der Umgang mit Raum und Ressourcen eine Rolle."
            }
        }
        else -> {
            when (axe) {
                Axe.SECURITE -> "Les réponses suggèrent que votre chat éprouve des difficultés à se sentir en sécurité. Il perçoit peut-être son environnement comme imprévisible ou menaçant, ce qui génère une vigilance permanente épuisante."
                Axe.LIEN -> "Le lien avec vous semble occuper une place centrale dans les difficultés de votre chat. Qu'il soit trop fort (hyperattachement) ou trop fragile, cela peut générer des comportements perturbants."
                Axe.INSTINCTS -> "Les besoins instinctifs de votre chat — chasse, exploration, griffage — ne trouvent pas suffisamment de débouché adapté. Cette frustration peut s'exprimer à travers des comportements indésirables."
                Axe.COHABITATION -> "Votre chat semble en difficulté dans ses relations avec son entourage, qu'il s'agisse d'autres animaux ou de certains membres du foyer. La gestion de l'espace et des ressources est probablement en jeu."
            }
        }
    }
}

fun conseilPrincipalTraduit(axe: Axe, securite: Int, lien: Int, instincts: Int, cohabitation: Int): String {
    if (maxOf(securite, lien, instincts, cohabitation) <= 25) return tr("Maintenir un cadre stable, cohérent et prévisible — c'est la base du bien-être félin.", "Maintain a stable, consistent and predictable framework — this is the foundation of feline well-being.", "Einen stabilen, stimmigen und vorhersehbaren Rahmen bewahren – das ist die Grundlage für das Wohlbefinden einer Katze.")
    return when (appLang()) {
        AppLang.EN -> {
            when (axe) {
                Axe.SECURITE -> "Enrich the environment with varied refuges and ensure a stable, predictable routine. Security is built through reassuring repetition."
                Axe.LIEN -> "Gradually work on autonomy with short, emotionally neutral departures, while maintaining moments of contact chosen by the cat."
                Axe.INSTINCTS -> "Introduce daily interactive play sessions (feather wand, prey toys) and food puzzles to mentally and physically stimulate your cat."
                Axe.COHABITATION -> "Review resource and space management — each cat must have access to its own resources without having to defend them."
            }
        }
        AppLang.DE -> {
            when (axe) {
                Axe.SECURITE -> "Die Umgebung mit vielfältigen Rückzugsorten bereichern und einen stabilen, vorhersehbaren Tagesablauf sicherstellen. Sicherheit entsteht durch beruhigende Wiederholung."
                Axe.LIEN -> "Schrittweise an der Selbstständigkeit arbeiten, mit kurzen Abschieden ohne emotionale Rituale, und dabei Kontaktmomente beibehalten, die die Katze selbst wählt."
                Axe.INSTINCTS -> "Tägliche interaktive Spieleinheiten (Federangel, Beutespielzeug) und Futterpuzzles einführen, um Ihre Katze geistig und körperlich zu fördern."
                Axe.COHABITATION -> "Den Umgang mit Ressourcen und Raum überdenken – jede Katze sollte Zugang zu ihren eigenen Ressourcen haben, ohne sie verteidigen zu müssen."
            }
        }
        else -> {
            when (axe) {
                Axe.SECURITE -> "Enrichir l'environnement de refuges variés et garantir une routine stable et prévisible. La sécurité se construit dans la répétition rassurante."
                Axe.LIEN -> "Travailler progressivement l'autonomie avec des départs courts et sans rituel émotionnel, tout en maintenant des moments de contact choisis par le chat."
                Axe.INSTINCTS -> "Introduire des sessions de jeu interactif quotidiennes (canne à plume, jouets proies) et des food puzzles pour stimuler mentalement et physiquement votre chat."
                Axe.COHABITATION -> "Revoir la gestion des ressources et de l'espace — chaque chat doit avoir accès à ses propres ressources sans avoir à les défendre."
            }
        }
    }
}

fun genererPlanActionTraduit(axe: Axe, reponsesChoix: Map<String, Int>, nomChat: String): PlanAction {
    val aFaire = mutableListOf<String>()
    val aEviter = mutableListOf<String>()
    val aObserver = mutableListOf<String>()
    when (appLang()) {
        AppLang.EN -> {
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
        }
        AppLang.DE -> {
            when (axe) {
                Axe.SECURITE -> {
                    aFaire += "Rückzugsorte schaffen oder verbessern (Kartons, erhöhte Verstecke, Tunnel)."
                    aFaire += "Einen stabilen Tagesablauf für Mahlzeiten, Spiel und Interaktionen beibehalten."
                    aFaire += "Die Katze den Kontakt suchen lassen, statt ihn aufzuzwingen."
                    aEviter += "Plötzliche Veränderungen der Umgebung oder des Tagesablaufs."
                    aEviter += "Kontakt erzwingen, wenn die Katze zeigt, dass sie allein sein möchte."
                    aObserver += "Die Momente und Situationen, die Angst oder Rückzug auslösen."
                    aObserver += "Wie sich die Nutzung der Rückzugsorte entwickelt."
                }
                Axe.LIEN -> {
                    aFaire += "Kurze, neutrale Abschiede üben, ohne große Gefühlsbekundungen."
                    aFaire += "Getragene Kleidungsstücke liegen lassen, um sie in Ihrer Abwesenheit zu beruhigen."
                    aFaire += "Beschäftigungsspielzeug anbieten (Futterspender, Futterpuzzles)."
                    aEviter += "Sehr betonte Abschiedsrituale, die die Angst verstärken."
                    aEviter += "Aufmerksamkeitsforderungen jedes Mal nachgeben."
                    aObserver += "Das Verhalten in den Minuten nach Ihrem Weggehen."
                    aObserver += "Die Fähigkeit, allein zur Ruhe zu kommen und sich zu entspannen."
                }
                Axe.INSTINCTS -> {
                    aFaire += "2 bis 3 interaktive Spieleinheiten pro Tag von 10 bis 15 Minuten einführen."
                    aFaire += "Das Spielzeug regelmäßig wechseln, damit das Interesse erhalten bleibt."
                    aFaire += "Futterpuzzles nutzen, um den Jagdinstinkt zu fördern."
                    aEviter += "Dauerhaft herumliegendes Spielzeug, das seinen Reiz verliert."
                    aEviter += "Zu kurze oder zu vorhersehbare Interaktionen."
                    aObserver += "Das Energieniveau und das Interesse am Spielen."
                    aObserver += "Verhaltensweisen, die sich nach den Spieleinheiten verbessern."
                }
                Axe.COHABITATION -> {
                    aFaire += "Alle Ressourcen verdoppeln (Näpfe, Katzenklos, Kratzbäume, Liegeplätze)."
                    aFaire += "Für jedes Tier eigene Bereiche mit sicherem Zugang schaffen."
                    aFaire += "Positive Begegnungen beim Füttern oder Spielen fördern."
                    aEviter += "Begegnungen zwischen angespannten Tieren erzwingen."
                    aEviter += "Situationen, in denen um Ressourcen konkurriert wird."
                    aObserver += "Die Warnsignale von Anspannung vor den Konflikten."
                    aObserver += "Die Bereiche, die sich jedes Tier aneignet."
                }
            }
            if (reponsesChoix["signe_physique"] == 2 || reponsesChoix["signe_physique"] == 3) {
                aFaire += "Einen Tierarzt aufsuchen, um eine körperliche Ursache für das beobachtete Verhalten auszuschließen."
            }
        }
        else -> {
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
    }
    return PlanAction(aFaire.take(3), aEviter.take(3), aObserver.take(3))
}

fun genererMessageSituationTraduit(niveauSituation: NiveauSituation, nomChat: String): String {
    val nom = nomChatAffiche(nomChat)
    return when (appLang()) {
        AppLang.EN -> {
            when (niveauSituation) {
                NiveauSituation.STABLE -> "At this stage, the situation seems fairly stable for $nom."
                NiveauSituation.A_TRAVAILLER -> "The situation deserves to be worked on progressively for $nom."
                NiveauSituation.SENSIBLE -> "The situation seems more sensitive for $nom and warrants special attention."
            }
        }
        AppLang.DE -> {
            when (niveauSituation) {
                NiveauSituation.STABLE -> "Im Moment scheint die Situation für $nom eher stabil zu sein."
                NiveauSituation.A_TRAVAILLER -> "An der Situation von $nom sollte schrittweise gearbeitet werden."
                NiveauSituation.SENSIBLE -> "Die Situation von $nom wirkt heikler und verdient besondere Aufmerksamkeit."
            }
        }
        else -> {
            when (niveauSituation) {
                NiveauSituation.STABLE -> "À ce stade, la situation semble plutôt stable pour $nom."
                NiveauSituation.A_TRAVAILLER -> "La situation mérite d'être travaillée progressivement pour $nom."
                NiveauSituation.SENSIBLE -> "La situation paraît plus sensible pour $nom et justifie une attention particulière."
            }
        }
    }
}

fun genererRaisonSituationTraduit(
    reponsesChoix: Map<String, Int>,
    contexte: ContexteAnalyse,
    niveauSituation: NiveauSituation
): String {
    if (niveauSituation == NiveauSituation.STABLE) {
        return tr(
            "Les réponses globales invitent à avancer progressivement.",
            "The overall answers suggest moving forward progressively.",
            "Die Antworten insgesamt legen ein schrittweises Vorgehen nahe."
        )
    }
    return when (appLang()) {
        AppLang.EN -> {
            when {
                reponsesChoix["duree_probleme"] == 0 -> "The very recent nature of the behavior calls for particular vigilance."
                reponsesChoix["evolution_probleme"] == 2 -> "The fact that it seems to be worsening may indicate the problem is taking up more space."
                contexte.physique >= 4 -> "Physical signs or possible discomfort call for consulting a veterinarian as a priority."
                else -> "The overall answers suggest moving forward progressively."
            }
        }
        AppLang.DE -> {
            when {
                reponsesChoix["duree_probleme"] == 0 -> "Dass das Verhalten erst ganz neu ist, erfordert besondere Aufmerksamkeit."
                reponsesChoix["evolution_probleme"] == 2 -> "Dass es sich zu verschlimmern scheint, kann darauf hindeuten, dass das Problem mehr Raum einnimmt."
                contexte.physique >= 4 -> "Körperliche Anzeichen oder ein mögliches Unwohlsein legen nahe, vorrangig einen Tierarzt aufzusuchen."
                else -> "Die Antworten insgesamt legen ein schrittweises Vorgehen nahe."
            }
        }
        else -> {
            when {
                reponsesChoix["duree_probleme"] == 0 -> "Le caractère très récent du comportement invite à une vigilance particulière."
                reponsesChoix["evolution_probleme"] == 2 -> "Le fait que cela semble s'aggraver peut indiquer que le problème prend davantage de place."
                contexte.physique >= 4 -> "Des signes physiques ou un possible inconfort invitent à consulter un vétérinaire en priorité."
                else -> "Les réponses globales invitent à avancer progressivement."
            }
        }
    }
}

fun genererConseilsPratiquesToTraduit(nomChat: String, reponsesChoix: Map<String, Int>,
                                      securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> {
    val conseils = mutableListOf<String>()
    val nom = nomChatAffiche(nomChat)
    when (appLang()) {
        AppLang.EN -> {
            if (reponsesChoix["proprete_type"] == 0)
                conseils += "Write down the location and time of each accident, along with $nom's behavior while urinating (crying, posture, quantity) — these notes will help your vet's diagnosis."
            if (reponsesChoix["proprete_type"] == 2)
                conseils += "Write down the location and time of each accident, along with $nom's behavior while urinating — and also check the litter box upkeep (daily cleaning, size, no hood if possible), which can be enough to resolve the stool aspect once the urinary cause is medically explained."
            if (reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2))
                conseils += "Keep $nom's environment as stable and predictable as possible — avoid moving furniture, food and litter box locations."
            if (securite >= 50) conseils += "Multiply refuges and hiding spots so $nom can feel safe at all times."
            if (lien >= 50) conseils += "Gradually work on autonomy while maintaining stable, predictable rituals."
            if (instincts >= 50) conseils += "Offer daily interactive play sessions to channel natural instincts."
            if (cohabitation >= 50) conseils += "Ensure duplicate resources (bowls, litter boxes, scratching posts) to reduce competition."
            if (reponsesChoix["surtoilettage"] == 1) conseils += "Over-grooming is often a sign of chronic stress — identify and reduce sources of tension."
            if (reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix)) conseils += "To ease the territorial stress behind the marking, multiply resources (litter boxes, water points, scratching posts) and limit sudden changes in $nom's environment."
            if (estMaleEntier(reponsesChoix) && cohabitation >= 40) conseils += "In an intact male, marking and territorial tensions are more frequent — neutering can be discussed with your vet."
            if (estFemelleEntiere(reponsesChoix) && securite >= 40) conseils += "In an intact female, some behaviors may vary with the cycle — observe whether tensions increase at certain times."
            if (conseils.isEmpty()) conseils += "Continue observing daily life and maintain the benchmarks already in place."
        }
        AppLang.DE -> {
            if (reponsesChoix["proprete_type"] == 0)
                conseils += "Notieren Sie Ort und Uhrzeit jedes Missgeschicks sowie das Verhalten von $nom beim Urinieren (Miauen, Haltung, Menge) – diese Beobachtungen helfen Ihrem Tierarzt bei der Diagnose."
            if (reponsesChoix["proprete_type"] == 2)
                conseils += "Notieren Sie Ort und Uhrzeit jedes Missgeschicks sowie das Verhalten von $nom beim Urinieren – und prüfen Sie auch die Pflege des Katzenklos (tägliche Reinigung, passende Größe, möglichst ohne Haube), was das Problem mit dem Kot lösen kann, sobald die Harnursache medizinisch ausgeschlossen ist."
            if (reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2))
                conseils += "Halten Sie die Umgebung von $nom so stabil und vorhersehbar wie möglich – vermeiden Sie es, Möbel, Näpfe und Katzenklo umzustellen."
            if (securite >= 50) conseils += "Mehr Rückzugsorte und Verstecke schaffen, damit sich $nom jederzeit sicher fühlen kann."
            if (lien >= 50) conseils += "Schrittweise an der Selbstständigkeit arbeiten und dabei stabile, vorhersehbare Rituale beibehalten."
            if (instincts >= 50) conseils += "Täglich interaktive Spieleinheiten anbieten, um die natürlichen Instinkte auszuleben."
            if (cohabitation >= 50) conseils += "Ressourcen doppelt bereitstellen (Näpfe, Katzenklos, Kratzbäume), um die Konkurrenz zu verringern."
            if (reponsesChoix["surtoilettage"] == 1) conseils += "Übermäßige Fellpflege ist oft ein Zeichen von chronischem Stress – die Stressquellen erkennen und verringern."
            if (reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix)) conseils += "Um den Revierstress zu lindern, der dem Markieren zugrunde liegt, stellen Sie mehr Ressourcen bereit (Katzenklos, Wasserstellen, Kratzbäume) und vermeiden Sie plötzliche Veränderungen in der Umgebung von $nom."
            if (estMaleEntier(reponsesChoix) && cohabitation >= 40) conseils += "Bei einem unkastrierten Kater sind Markieren und Revierspannungen häufiger – eine Kastration kann mit Ihrem Tierarzt besprochen werden."
            if (estFemelleEntiere(reponsesChoix) && securite >= 40) conseils += "Bei einer unkastrierten Katze können manche Verhaltensweisen je nach Zyklus schwanken – beobachten Sie, ob die Spannungen in bestimmten Phasen zunehmen."
            if (conseils.isEmpty()) conseils += "Den Alltag weiter beobachten und die bestehenden Orientierungspunkte beibehalten."
        }
        else -> {
            if (reponsesChoix["proprete_type"] == 0)
                conseils += "Notez les lieux et horaires de chaque accident, ainsi que le comportement de $nom au moment d'uriner (miaulements, position, quantité) — ces observations aideront votre vétérinaire à orienter son diagnostic."
            if (reponsesChoix["proprete_type"] == 2)
                conseils += "Notez les lieux et horaires de chaque accident, ainsi que le comportement de $nom au moment d'uriner — et vérifiez aussi l'entretien du bac (nettoyage quotidien, taille adaptée, retrait du capot si possible), qui peut suffire à résoudre l'aspect selles une fois la cause urinaire écartée médicalement."
            if (reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2))
                conseils += "Gardez l'environnement de $nom aussi stable et prévisible que possible — évitez de déplacer meubles, gamelles et litière."
            if (securite >= 50) conseils += "Multiplier les refuges et cachettes pour que $nom puisse se sentir en sécurité à tout moment."
            if (lien >= 50) conseils += "Travailler progressivement l'autonomie en gardant des rituels stables et prévisibles."
            if (instincts >= 50) conseils += "Proposer des sessions de jeu interactif quotidiennes pour canaliser les instincts naturels."
            if (cohabitation >= 50) conseils += "Assurer des ressources en double (gamelles, litières, griffoirs) pour réduire la compétition."
            if (reponsesChoix["surtoilettage"] == 1) conseils += "Le surtoilettage est souvent un signe de stress chronique — identifier et réduire les sources de tension."
            if (reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix)) conseils += "Pour apaiser le stress territorial à l'origine du marquage, multipliez les ressources (litières, points d'eau, griffoirs) et limitez les changements soudains dans l'environnement de $nom."
            if (estMaleEntier(reponsesChoix) && cohabitation >= 40) conseils += "Chez un mâle entier, le marquage et les tensions territoriales sont plus fréquents — la stérilisation peut être discutée avec votre vétérinaire."
            if (estFemelleEntiere(reponsesChoix) && securite >= 40) conseils += "Chez une femelle entière, certains comportements peuvent varier selon le cycle — observer si les tensions augmentent à certaines périodes."
            if (conseils.isEmpty()) conseils += "Continuer l'observation du quotidien et maintenir les repères déjà en place."
        }
    }
    return conseils.take(4)
}

fun genererMessageAideTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                              niveauSituation: NiveauSituation, nomChat: String,
                              securite: Int, lien: Int, instincts: Int, cohabitation: Int): String? {
    val nom = nomChatAffiche(nomChat)
    return when (appLang()) {
        AppLang.EN -> {
            when {
                reponsesChoix["proprete_type"] == 0 -> "Seek care urgently if you notice emergency signs in $nom: crying in pain while urinating, frequent unsuccessful trips to the litter box, or no urine at all for over 24 hours. These signs can indicate a urinary blockage, which is a veterinary emergency."
                reponsesChoix["proprete_type"] == 2 -> "Seek care urgently if you notice signs of a urinary blockage in $nom (crying in pain while urinating, unsuccessful trips to the litter box, no urine for over 24 hours). For the stool aspect, a vet visit is also worth it if diarrhea persists, or if you notice blood or unexplained weight loss."
                reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2) -> "Given $nom's age and the signs described, a veterinary check-up is recommended as a priority to rule out or confirm age-related cognitive decline before considering a behavioral approach."
                reponsesChoix["marquage_habitude_post_sterilisation"] == 0 -> "This marking behavior has become a learned habit rather than a hormonal one — a feline behaviorist can help $nom unlearn this specific gesture, which often takes longer than addressing stress-related marking."
                reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> "A scratch or bite toward another animal has been reported. This often points to a lack of socialization or a cohabitation that needs to be reframed — a feline behaviorist can help you rebuild a safer, more gradual introduction between animals."
                reponsesChoix["a_deja_griffe_mordu"] == 1 -> "A scratch or bite toward a person has been reported — support from a veterinary behaviorist or feline behaviorist is recommended for $nom, both for your safety and $nom's well-being."
                contexte.physique >= 4 -> "Physical signs have been noted — a veterinary consultation is recommended as a priority for $nom before any behavioral approach."
                reponsesChoix["surtoilettage"] == 1 -> "Over-grooming can have a medical origin — veterinary advice is recommended for $nom before acting on the behavioral level."
                reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix) -> "Urine marking in a neutered cat warrants a veterinary check-up for $nom first to rule out a urinary infection."
                niveauSituation == NiveauSituation.SENSIBLE -> "The situation warrants the attention of a feline behaviorist who can support $nom and guide you concretely."
                maxOf(securite, lien, instincts, cohabitation) >= 75 -> "The intensity of the observed difficulties suggests that a feline behaviorist could provide valuable help for $nom."
                else -> null
            }
        }
        AppLang.DE -> {
            when {
                reponsesChoix["proprete_type"] == 0 -> "Suchen Sie rasch einen Tierarzt auf, wenn Sie bei $nom Anzeichen eines Notfalls bemerken: schmerzhaftes Miauen beim Urinieren, häufige Gänge zum Katzenklo ohne Ergebnis oder seit mehr als 24 Stunden kein Urin. Diese Anzeichen können auf einen Harnverschluss hinweisen, der ein tierärztlicher Notfall ist."
                reponsesChoix["proprete_type"] == 2 -> "Suchen Sie rasch einen Tierarzt auf, wenn Sie bei $nom Anzeichen eines Harnverschlusses bemerken (schmerzhaftes Miauen beim Urinieren, erfolglose Gänge zum Katzenklo, seit mehr als 24 Stunden kein Urin). Beim Kotabsatz bleibt ein Tierarztbesuch sinnvoll bei anhaltendem Durchfall, Blut oder unerklärlichem Gewichtsverlust."
                reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2) -> "Angesichts des Alters von $nom und der beschriebenen Anzeichen wird vorrangig eine tierärztliche Untersuchung empfohlen, um einen altersbedingten kognitiven Abbau auszuschließen oder zu bestätigen, bevor an einen verhaltensbezogenen Ansatz gedacht wird."
                reponsesChoix["marquage_habitude_post_sterilisation"] == 0 -> "Diese Markierung ist eher zu einer erlernten Gewohnheit als zu einem hormonellen Verhalten geworden – eine Fachperson für Katzenverhalten kann $nom helfen, dieses Verhalten zu verlernen, was oft länger dauert als bei einer stressbedingten Markierung."
                reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> "Es wurde ein Kratzer oder Biss gegenüber einem anderen Tier gemeldet. Das deutet oft auf mangelnde Sozialisierung oder ein Zusammenleben hin, das neu aufgebaut werden sollte – eine Fachperson für Katzenverhalten kann Ihnen helfen, eine schrittweisere und sicherere Zusammenführung der Tiere zu gestalten."
                reponsesChoix["a_deja_griffe_mordu"] == 1 -> "Es wurde ein Kratzer oder Biss gegenüber einem Menschen gemeldet – eine Begleitung durch einen verhaltensmedizinisch ausgebildeten Tierarzt oder eine Fachperson für Katzenverhalten wird für $nom empfohlen, sowohl für Ihre Sicherheit als auch für ihr Wohlbefinden."
                contexte.physique >= 4 -> "Es wurden körperliche Anzeichen festgestellt – für $nom wird vorrangig ein Tierarztbesuch empfohlen, vor jedem verhaltensbezogenen Ansatz."
                reponsesChoix["surtoilettage"] == 1 -> "Übermäßige Fellpflege kann eine medizinische Ursache haben – für $nom wird tierärztlicher Rat empfohlen, bevor verhaltensbezogen gehandelt wird."
                reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix) -> "Harnmarkieren bei einer kastrierten Katze sollte bei $nom zuerst tierärztlich abgeklärt werden, um eine Harnwegsinfektion auszuschließen."
                niveauSituation == NiveauSituation.SENSIBLE -> "Die Situation verdient den Blick einer Fachperson für Katzenverhalten, die $nom begleiten und Sie konkret anleiten kann."
                maxOf(securite, lien, instincts, cohabitation) >= 75 -> "Die Intensität der beobachteten Schwierigkeiten legt nahe, dass eine Fachperson für Katzenverhalten für $nom eine wertvolle Hilfe sein könnte."
                else -> null
            }
        }
        else -> {
            when {
                reponsesChoix["proprete_type"] == 0 -> "Consultez rapidement si vous observez des signes d'urgence chez $nom : miaulements de douleur en urinant, allers-retours fréquents à la litière sans résultat, ou absence totale d'urine depuis plus de 24h. Ces signes peuvent indiquer un blocage urinaire, qui est une urgence vétérinaire."
                reponsesChoix["proprete_type"] == 2 -> "Consultez rapidement si vous observez chez $nom des signes de blocage urinaire (miaulements de douleur en urinant, allers-retours infructueux à la litière, absence d'urine depuis plus de 24h). Pour l'aspect selles, une consultation reste utile en cas de diarrhée persistante, de sang, ou de perte de poids inexpliquée."
                reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2) -> "Compte tenu de l'âge de $nom et des signes décrits, un bilan vétérinaire est recommandé en priorité pour écarter ou confirmer un déclin cognitif lié à l'âge avant d'envisager une approche comportementale."
                reponsesChoix["marquage_habitude_post_sterilisation"] == 0 -> "Ce marquage est devenu une habitude acquise plutôt qu'un comportement hormonal — un comportementaliste félin peut aider $nom à désapprendre ce geste précis, ce qui prend souvent plus de temps qu'un marquage lié au stress."
                reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> "Une griffure ou morsure envers un autre animal a été signalée. Cela évoque souvent un manque de sociabilisation ou une cohabitation à reprendre autrement — un comportementaliste félin peut vous aider à reconstruire une présentation plus progressive et sécurisée entre les animaux."
                reponsesChoix["a_deja_griffe_mordu"] == 1 -> "Une griffure ou morsure envers une personne a été signalée — un accompagnement par un vétérinaire comportementaliste ou un comportementaliste félin est recommandé pour $nom, à la fois pour votre sécurité et pour son bien-être."
                contexte.physique >= 4 -> "Des signes physiques ont été relevés — une consultation vétérinaire est recommandée en priorité pour $nom avant toute approche comportementale."
                reponsesChoix["surtoilettage"] == 1 -> "Le surtoilettage peut avoir une origine médicale — un avis vétérinaire est conseillé pour $nom avant d'agir sur le plan comportemental."
                reponsesChoix["marquage_urinaire"] == 1 && estSterilise(reponsesChoix) -> "Le marquage urinaire chez un chat stérilisé mérite d'abord un bilan vétérinaire pour $nom pour écarter une infection urinaire."
                niveauSituation == NiveauSituation.SENSIBLE -> "La situation mérite le regard d'un comportementaliste félin qui pourra accompagner $nom et vous guider concrètement."
                maxOf(securite, lien, instincts, cohabitation) >= 75 -> "L'intensité des difficultés observées suggère qu'un comportementaliste félin pourrait apporter une aide précieuse pour $nom."
                else -> null
            }
        }
    }
}

fun detecterHypothesePrincipaleTraduit(reponsesChoix: Map<String, Int>,
                                       securite: Int, lien: Int, instincts: Int, cohabitation: Int, contexte: ContexteAnalyse): String {
    return when (appLang()) {
        AppLang.EN -> {
            when {
                reponsesChoix["proprete_type"] == 0 -> "Since this house-soiling involves urine, a medical cause should be ruled out first with a veterinarian."
                reponsesChoix["proprete_type"] == 2 -> "Since this house-soiling involves both urine and stools, the medical cause for the urinary part should be ruled out first, before considering behavioral work on the stool aspect."
                reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2) -> "Some signs (disorientation, unexplained night vocalizing) may suggest age-related cognitive changes rather than a purely behavioral issue."
                reponsesChoix["marquage_habitude_post_sterilisation"] == 0 -> "This urine marking seems to have started during heat periods, before spaying, and has since become a learned habit rather than a hormonal behavior."
                contexte.physique >= 4 -> "The reported elements suggest considering a physical or medical component before going further on the behavioral level."
                reponsesChoix["surtoilettage"] == 1 && securite >= 50 -> "Over-grooming combined with emotional insecurity suggests chronic stress expressing itself physically."
                reponsesChoix["marquage_urinaire"] == 1 -> "Urine marking suggests territorial or hormonal stress, depending on neutering status."
                securite >= 65 && lien >= 65 -> "Anxious attachment combined with emotional insecurity — your cat is constantly seeking reassurance."
                securite >= 65 -> "Significant emotional insecurity translating into permanent vigilance and fear reactions."
                lien >= 65 -> "Over-attachment or difficulty managing separation generating distress in your absence."
                instincts >= 65 -> "Natural instincts (hunting, exploration, scratching) insufficiently channeled, seeking expression."
                cohabitation >= 65 -> "Cohabitation tensions generating daily stress and conflict."
                else -> "Several factors seem involved without one axis clearly dominating — a holistic approach is recommended."
            }
        }
        AppLang.DE -> {
            when {
                reponsesChoix["proprete_type"] == 0 -> "Da diese Unsauberkeit Urin betrifft, sollte zuerst mit einem Tierarzt eine medizinische Ursache ausgeschlossen werden."
                reponsesChoix["proprete_type"] == 2 -> "Da diese Unsauberkeit sowohl Urin als auch Kot betrifft, sollte zuerst die medizinische Ursache des Harnanteils ausgeschlossen werden, bevor man verhaltensbezogen am Kotabsatz arbeitet."
                reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2) -> "Manche Anzeichen (Orientierungslosigkeit, unerklärliches nächtliches Miauen) können eher auf einen altersbedingten kognitiven Abbau hinweisen als auf ein reines Verhaltensproblem."
                reponsesChoix["marquage_habitude_post_sterilisation"] == 0 -> "Dieses Harnmarkieren scheint während der Rolligkeit vor der Kastration begonnen zu haben und hat sich seitdem eher zu einer erlernten Gewohnheit als zu einem hormonellen Verhalten entwickelt."
                contexte.physique >= 4 -> "Die gemeldeten Anzeichen legen nahe, eine körperliche oder medizinische Ursache zu berücksichtigen, bevor man verhaltensbezogen weitergeht."
                reponsesChoix["surtoilettage"] == 1 && securite >= 50 -> "Übermäßige Fellpflege in Verbindung mit emotionaler Unsicherheit deutet auf chronischen Stress hin, der sich über den Körper äußert."
                reponsesChoix["marquage_urinaire"] == 1 -> "Das Harnmarkieren deutet je nach Kastrationsstatus auf Revierstress oder einen hormonellen Einfluss hin."
                securite >= 65 && lien >= 65 -> "Eine ängstliche Bindung verbunden mit emotionaler Unsicherheit – Ihre Katze sucht ständig nach Beruhigung."
                securite >= 65 -> "Eine ausgeprägte emotionale Unsicherheit, die sich in ständiger Wachsamkeit und Angstreaktionen zeigt."
                lien >= 65 -> "Eine übermäßige Anhänglichkeit oder Schwierigkeiten mit der Trennung, die in Ihrer Abwesenheit Not auslösen."
                instincts >= 65 -> "Natürliche Instinkte (Jagen, Erkunden, Kratzen), die zu wenig ausgelebt werden und nach einem Ventil suchen."
                cohabitation >= 65 -> "Spannungen im Zusammenleben, die im Alltag Stress und Konflikte erzeugen."
                else -> "Mehrere Faktoren scheinen beteiligt, ohne dass eine Achse klar überwiegt – ein ganzheitlicher Ansatz wird empfohlen."
            }
        }
        else -> {
            when {
                reponsesChoix["proprete_type"] == 0 -> "Puisque cette malpropreté concerne de l'urine, une cause médicale doit d'abord être écartée avec un vétérinaire."
                reponsesChoix["proprete_type"] == 2 -> "Puisque cette malpropreté concerne à la fois de l'urine et des selles, la cause médicale de la partie urinaire doit d'abord être écartée, avant d'envisager un travail comportemental sur l'aspect selles."
                reponsesChoix["age"] == 3 && (reponsesChoix["senior_desorientation"] == 2 || reponsesChoix["senior_vocalise_nocturne"] == 2) -> "Certains signes (désorientation, vocalises nocturnes inexpliquées) peuvent évoquer un déclin cognitif lié à l'âge plutôt qu'un souci purement comportemental."
                reponsesChoix["marquage_habitude_post_sterilisation"] == 0 -> "Ce marquage urinaire semble avoir débuté pendant les chaleurs, avant la stérilisation, et s'est transformé depuis en habitude acquise plutôt qu'en comportement hormonal."
                contexte.physique >= 4 -> "Les éléments signalés invitent à considérer une composante physique ou médicale avant d'aller plus loin sur le plan comportemental."
                reponsesChoix["surtoilettage"] == 1 && securite >= 50 -> "Le surtoilettage associé à une insécurité émotionnelle évoque un stress chronique qui s'exprime corporellement."
                reponsesChoix["marquage_urinaire"] == 1 -> "Le marquage urinaire évoque un stress territorial ou hormonal, selon le statut de stérilisation."
                securite >= 65 && lien >= 65 -> "Un attachement anxieux doublé d'une insécurité émotionnelle — votre chat cherche constamment à se rassurer."
                securite >= 65 -> "Une insécurité émotionnelle importante qui se traduit par une vigilance permanente et des réactions de peur."
                lien >= 65 -> "Un hyperattachement ou une difficulté à gérer la séparation qui génère de la détresse en votre absence."
                instincts >= 65 -> "Des instincts naturels (chasse, exploration, griffage) insuffisamment canalisés qui cherchent à s'exprimer."
                cohabitation >= 65 -> "Des tensions de cohabitation qui génèrent stress et conflits au quotidien."
                else -> "Plusieurs facteurs semblent impliqués sans qu'un axe ne domine clairement — une approche globale est recommandée."
            }
        }
    }
}

fun construirePrioriteImmediateTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                       priorite: PrioriteAction, niveauSituation: NiveauSituation, nomChat: String): PrioriteImmediate {
    val nom = nomChatAffiche(nomChat)
    return when (appLang()) {
        AppLang.EN -> {
            when {
                reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> PrioriteImmediate(PrioriteAction.ELEVEE, "Immediate priority: rework the introduction with other animals",
                    "Since there has already been a scratch or bite toward another animal, cohabitation should be reframed calmly for $nom.",
                    listOf("Temporarily separate the animals in conflict.", "Consider support from a feline behaviorist for a gradual reintroduction."))
                reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteImmediate(PrioriteAction.URGENTE, "Immediate priority: consult a professional",
                    "Since there has already been a scratch or bite toward a person, the situation should not be minimized for $nom.",
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
        }
        AppLang.DE -> {
            when {
                reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> PrioriteImmediate(PrioriteAction.ELEVEE, "Sofortige Priorität: die Zusammenführung mit den anderen Tieren neu aufbauen",
                    "Da es bereits einen Kratzer oder Biss gegenüber einem anderen Tier gab, sollte das Zusammenleben für $nom in Ruhe neu aufgebaut werden.",
                    listOf("Die Tiere im Konflikt vorübergehend trennen.", "Eine Begleitung durch eine Fachperson für Katzenverhalten für eine schrittweise Wiederzusammenführung in Betracht ziehen."))
                reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteImmediate(PrioriteAction.URGENTE, "Sofortige Priorität: eine Fachperson aufsuchen",
                    "Da es bereits einen Kratzer oder Biss gegenüber einem Menschen gab, sollte die Situation von $nom nicht verharmlost werden.",
                    listOf("Die erkannten Risikosituationen vermeiden.", "Einen verhaltensmedizinisch ausgebildeten Tierarzt oder eine Fachperson für Katzenverhalten aufsuchen."))
                contexte.physique >= 4 -> PrioriteImmediate(PrioriteAction.URGENTE, "Sofortige Priorität: einen Tierarzt aufsuchen",
                    "Bei $nom werden körperliche Anzeichen gemeldet – die Priorität ist medizinisch.",
                    listOf("Rasch einen Termin beim Tierarzt vereinbaren.", "Nicht warten, bis sich die Symptome verschlimmern."))
                priorite == PrioriteAction.ELEVEE -> PrioriteImmediate(PrioriteAction.ELEVEE, "Sofortige Priorität: ohne Verzögerung handeln",
                    "Die Situation rechtfertigt für $nom schnelles Handeln.",
                    listOf("Die schwierigen Situationen entlasten.", "Eine Begleitung durch eine Fachperson für Katzenverhalten in Betracht ziehen."))
                priorite == PrioriteAction.MODEREE -> PrioriteImmediate(PrioriteAction.MODEREE, "Sofortige Priorität: schrittweise vorgehen",
                    "Die Situation von $nom verdient Aufmerksamkeit.",
                    listOf("Schrittweise an der Umgebung arbeiten.", "Häufigkeit und Intensität der Verhaltensweisen beobachten."))
                else -> PrioriteImmediate(PrioriteAction.FAIBLE, "Sofortige Priorität: in Ruhe beobachten",
                    "Nichts Dringendes bei $nom – beobachten Sie weiter.",
                    listOf("Einen stabilen und vorhersehbaren Rahmen bewahren.", "Die Umgebung nach und nach bereichern."))
            }
        }
        else -> {
            when {
                reponsesChoix["a_deja_griffe_mordu"] == 1 && reponsesChoix["cible_agression"] == 1 -> PrioriteImmediate(PrioriteAction.ELEVEE, "Priorité immédiate : reprendre la présentation avec les autres animaux",
                    "Comme il y a déjà eu griffure ou morsure envers un autre animal, la cohabitation doit être reprise calmement pour $nom.",
                    listOf("Séparer temporairement les animaux en conflit.", "Envisager un accompagnement par un comportementaliste félin pour une réintroduction progressive."))
                reponsesChoix["a_deja_griffe_mordu"] == 1 -> PrioriteImmediate(PrioriteAction.URGENTE, "Priorité immédiate : consulter un professionnel",
                    "Comme il y a déjà eu griffure ou morsure envers une personne, la situation ne doit pas être banalisée pour $nom.",
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
}

fun detecterFacteursAggravantsTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse,
                                      securite: Int, lien: Int, instincts: Int, cohabitation: Int): List<String> {
    val facteurs = mutableListOf<String>()
    when (appLang()) {
        AppLang.EN -> {
            if (reponsesChoix["apparition"] == 1) facteurs += "Sudden onset of the behavior"
            if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Worsening behavior"
            if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Very high intensity"
            if (contexte.physique >= 4) facteurs += "Suspected physical or medical cause"
            if (reponsesChoix["acces_exterieur"] == 0 && instincts >= 50) facteurs += "Indoor cat with poorly channeled instincts"
            if (maxOf(securite, lien, instincts, cohabitation) >= 75) facteurs += "High level on at least one axis"
            if (reponsesChoix["plusieurs_chats"] == 1 && cohabitation >= 50) facteurs += "Conflictual multi-cat cohabitation"
            if (estMaleEntier(reponsesChoix) && cohabitation >= 40) facteurs += "Intact male — marking and territorial tensions more frequent"
        }
        AppLang.DE -> {
            if (reponsesChoix["apparition"] == 1) facteurs += "Plötzliches Auftreten des Verhaltens"
            if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Verhalten verschlimmert sich"
            if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Sehr hohe Intensität"
            if (contexte.physique >= 4) facteurs += "Verdacht auf eine körperliche oder medizinische Ursache"
            if (reponsesChoix["acces_exterieur"] == 0 && instincts >= 50) facteurs += "Wohnungskatze mit wenig ausgelebten Instinkten"
            if (maxOf(securite, lien, instincts, cohabitation) >= 75) facteurs += "Hoher Wert auf mindestens einer Achse"
            if (reponsesChoix["plusieurs_chats"] == 1 && cohabitation >= 50) facteurs += "Konfliktreiches Zusammenleben mehrerer Katzen"
            if (estMaleEntier(reponsesChoix) && cohabitation >= 40) facteurs += "Unkastrierter Kater – Markieren und Revierspannungen häufiger"
        }
        else -> {
            if (reponsesChoix["apparition"] == 1) facteurs += "Apparition brutale du comportement"
            if (reponsesChoix["evolution_probleme"] == 2) facteurs += "Comportement en aggravation"
            if (reponsesChoix["intensite_probleme"] == 3) facteurs += "Intensité très forte"
            if (contexte.physique >= 4) facteurs += "Suspicion de cause physique ou médicale"
            if (reponsesChoix["acces_exterieur"] == 0 && instincts >= 50) facteurs += "Chat d'intérieur avec instincts peu canalisés"
            if (maxOf(securite, lien, instincts, cohabitation) >= 75) facteurs += "Niveau élevé sur au moins un axe"
            if (reponsesChoix["plusieurs_chats"] == 1 && cohabitation >= 50) facteurs += "Cohabitation multi-chats conflictuelle"
            if (estMaleEntier(reponsesChoix) && cohabitation >= 40) facteurs += "Mâle entier — marquage et tensions territoriales plus fréquentes"
        }
    }
    return facteurs.distinct()
}

fun detecterFacteursProtecteursTraduit(reponsesChoix: Map<String, Int>, contexte: ContexteAnalyse): List<String> {
    val facteurs = mutableListOf<String>()
    when (appLang()) {
        AppLang.EN -> {
            if (reponsesChoix["evolution_probleme"] == 0) facteurs += "An improvement already seems present"
            if (reponsesChoix["frequence_probleme"] == 0) facteurs += "The behavior remains infrequent"
            if (reponsesChoix["acces_exterieur"] == 1) facteurs += "Access to the outdoors available"
            if (estSterilise(reponsesChoix)) facteurs += "Neutered cat — stabilizing factor"
            if (contexte.scoreContexte <= 3) facteurs += "The overall context does not suggest a degraded situation"
        }
        AppLang.DE -> {
            if (reponsesChoix["evolution_probleme"] == 0) facteurs += "Eine Verbesserung scheint bereits eingetreten"
            if (reponsesChoix["frequence_probleme"] == 0) facteurs += "Das Verhalten tritt eher selten auf"
            if (reponsesChoix["acces_exterieur"] == 1) facteurs += "Freigang möglich"
            if (estSterilise(reponsesChoix)) facteurs += "Kastrierte Katze – stabilisierender Faktor"
            if (contexte.scoreContexte <= 3) facteurs += "Die Gesamtsituation deutet nicht auf eine verschlechterte Lage hin"
        }
        else -> {
            if (reponsesChoix["evolution_probleme"] == 0) facteurs += "Une amélioration semble déjà présente"
            if (reponsesChoix["frequence_probleme"] == 0) facteurs += "Le comportement reste peu fréquent"
            if (reponsesChoix["acces_exterieur"] == 1) facteurs += "Accès à l'extérieur disponible"
            if (estSterilise(reponsesChoix)) facteurs += "Chat stérilisé — facteur de stabilité"
            if (contexte.scoreContexte <= 3) facteurs += "Le contexte global ne suggère pas une situation dégradée"
        }
    }
    return facteurs.distinct()
}

// ═══════════════════════════════════════════════════════════
// SECTIONS DU QUESTIONNAIRE
// ═══════════════════════════════════════════════════════════

fun titreSectionTraduit(questionId: String): String {
    return when (appLang()) {
        AppLang.EN -> {
            when (questionId) {
                "nom_chat", "age", "sterilise", "acces_exterieur", "vie_interieur", "senior_desorientation", "senior_vocalise_nocturne" -> "Your cat"
                "race_categorie" -> "Breed profile"
                "reaction_stress_ponctuel", "reaction_inconnu", "cache_souvent", "adaptation_changement",
                "surtoilettage" -> "Emotional security"
                "recherche_proximite", "reaction_absence", "proprete_stress", "proprete_type",
                "demande_attention_vocale" -> "Human bond"
                "jeu_chasse", "griffage_surfaces", "hyperactivite_nocturne",
                "comportement_alimentaire", "destruction_ennui", "marquage_urinaire" -> "Expression of instincts"
                "relation_autres_chats", "relation_chien", "relation_enfants", "agressivite_caresses",
                "a_deja_griffe_mordu", "cible_agression", "defense_ressources" -> "Cohabitation"
                "a_un_probleme" -> "Going further"
                else -> "Current context"
            }
        }
        AppLang.DE -> {
            when (questionId) {
                "nom_chat", "age", "sterilise", "acces_exterieur", "vie_interieur", "senior_desorientation", "senior_vocalise_nocturne" -> "Ihre Katze"
                "race_categorie" -> "Rasseprofil"
                "reaction_stress_ponctuel", "reaction_inconnu", "cache_souvent", "adaptation_changement",
                "surtoilettage" -> "Emotionale Sicherheit"
                "recherche_proximite", "reaction_absence", "proprete_stress", "proprete_type",
                "demande_attention_vocale" -> "Bindung zum Menschen"
                "jeu_chasse", "griffage_surfaces", "hyperactivite_nocturne",
                "comportement_alimentaire", "destruction_ennui", "marquage_urinaire" -> "Ausdruck der Instinkte"
                "relation_autres_chats", "relation_chien", "relation_enfants", "agressivite_caresses",
                "a_deja_griffe_mordu", "cible_agression", "defense_ressources" -> "Zusammenleben und Revier"
                "a_un_probleme" -> "Weiterführendes"
                else -> "Aktuelle Situation"
            }
        }
        else -> {
            when (questionId) {
                "nom_chat", "age", "sterilise", "acces_exterieur", "vie_interieur", "senior_desorientation", "senior_vocalise_nocturne" -> "Votre chat"
                "race_categorie" -> "Profil de race"
                "reaction_stress_ponctuel", "reaction_inconnu", "cache_souvent", "adaptation_changement",
                "surtoilettage" -> "Sécurité émotionnelle"
                "recherche_proximite", "reaction_absence", "proprete_stress", "proprete_type",
                "demande_attention_vocale" -> "Lien humain"
                "jeu_chasse", "griffage_surfaces", "hyperactivite_nocturne",
                "comportement_alimentaire", "destruction_ennui", "marquage_urinaire" -> "Expression des instincts"
                "relation_autres_chats", "relation_chien", "relation_enfants", "agressivite_caresses",
                "a_deja_griffe_mordu", "cible_agression", "defense_ressources" -> "Cohabitation"
                "a_un_probleme" -> "Pour aller plus loin"
                else -> "Contexte actuel"
            }
        }
    }
}

fun aideQuestionTraduit(questionId: String): String? {
    return when (appLang()) {
        AppLang.EN -> {
            when (questionId) {
                "race_categorie" -> "Choose the family that most resembles your cat."
                "sterilise" -> "Neutering influences certain behaviors such as marking or territorial tensions."
                "surtoilettage" -> "Over-grooming manifests as sparse fur areas or patches without hair."
                "reaction_absence" -> "Think about what you observe on your return or what your neighbors report."
                "agressivite_caresses" -> "For example, it bites or scratches suddenly while you are petting it."
                "a_deja_griffe_mordu" -> "Even a one-time or minor scratch or bite counts."
                "signe_physique" -> "Even a doubt or suspicion is worth reporting."
                "marquage_urinaire" -> "Urine marking is done standing up, tail raised, on vertical surfaces."
                "senior_desorientation" -> "For example, it seems lost near its bowl, litter box or a familiar door."
                "senior_vocalise_nocturne" -> "This means loud, insistent meowing with no obvious trigger."
                "proprete_type" -> "This distinction helps identify whether a medical cause should be checked first."
                "cible_agression" -> "This helps tell apart a safety concern toward people from a socialization issue with other animals."
                else -> null
            }
        }
        AppLang.DE -> {
            when (questionId) {
                "race_categorie" -> "Wählen Sie die Gruppe, die Ihrer Katze am ähnlichsten ist."
                "sterilise" -> "Die Kastration beeinflusst manche Verhaltensweisen wie das Markieren oder Spannungen im Revier."
                "surtoilettage" -> "Übermäßige Fellpflege zeigt sich durch Stellen mit schütterem Fell oder kahle Flecken."
                "reaction_absence" -> "Denken Sie an das, was Sie bei Ihrer Rückkehr feststellen oder was Ihre Nachbarn Ihnen berichten."
                "agressivite_caresses" -> "Zum Beispiel beißt oder kratzt sie plötzlich, während Sie sie streicheln."
                "a_deja_griffe_mordu" -> "Auch ein einzelner Kratzer oder Biss, selbst ein leichter, zählt."
                "signe_physique" -> "Auch ein Zweifel oder ein Verdacht sollte gemeldet werden."
                "marquage_urinaire" -> "Harnmarkieren geschieht im Stehen, mit aufgerichtetem Schwanz, an senkrechten Flächen."
                "senior_desorientation" -> "Zum Beispiel wirkt sie verloren in der Nähe ihres Napfes, ihres Katzenklos oder einer vertrauten Tür."
                "senior_vocalise_nocturne" -> "Gemeint ist lautes und beharrliches Miauen ohne erkennbaren Auslöser."
                "proprete_type" -> "Diese Angabe hilft zu erkennen, ob zuerst eine medizinische Ursache abgeklärt werden sollte."
                "cible_agression" -> "So lässt sich ein Sicherheitsproblem gegenüber Menschen von einer Schwierigkeit im Umgang mit anderen Tieren unterscheiden."
                else -> null
            }
        }
        else -> {
            when (questionId) {
                "race_categorie" -> "Choisissez la famille qui ressemble le plus à votre chat."
                "sterilise" -> "La stérilisation influence certains comportements comme le marquage ou les tensions territoriales."
                "surtoilettage" -> "Le surtoilettage se manifeste par des zones de poils clairsemés ou des plaques sans poils."
                "reaction_absence" -> "Pensez à ce que vous observez à votre retour ou ce que vos voisins vous rapportent."
                "agressivite_caresses" -> "Par exemple, il mord ou griffe soudainement pendant que vous le caressez."
                "a_deja_griffe_mordu" -> "Même une griffure ou morsure ponctuelle, même légère, compte."
                "signe_physique" -> "Même un doute ou une suspicion mérite d'être signalé."
                "marquage_urinaire" -> "Le marquage urinaire se fait debout, queue dressée, sur des surfaces verticales."
                "senior_desorientation" -> "Par exemple, il semble perdu près de sa gamelle, de sa litière ou d'une porte familière."
                "senior_vocalise_nocturne" -> "Il s'agit de miaulements forts et insistants, sans déclencheur évident."
                "proprete_type" -> "Cette précision aide à savoir s'il faut d'abord vérifier une cause médicale."
                "cible_agression" -> "Cela permet de distinguer un enjeu de sécurité envers des personnes d'une difficulté de sociabilisation avec d'autres animaux."
                else -> null
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════
// QUESTIONS TRADUITES
// ═══════════════════════════════════════════════════════════

fun questionsApplicationTraduites(): List<Question> {
    return trList(
        questionsApplication(),
        listOf(
            QuestionTexte("nom_chat", "What is your cat's name?"),
            QuestionChoix("race_categorie", "Which breed family does your cat belong to?",
                listOf("European / Mixed breed", "Maine Coon", "Persian", "Siamese", "Ragdoll",
                    "Bengal", "British Shorthair", "Abyssinian", "Sacred Birman", "Other breed / unknown")),
            QuestionChoix("age", "How old is your cat?",
                listOf("Under 1 year (kitten)", "Between 1 and 3 years", "Between 4 and 8 years", "9 years and over (senior)")),
            QuestionChoix("senior_desorientation", "Does your cat sometimes seem disoriented or lost in places it knows well?",
                listOf("No, never", "Sometimes, occasionally", "Yes, regularly")),
            QuestionChoix("senior_vocalise_nocturne", "Has your cat recently been vocalizing or wandering at night without an apparent reason (not hungry, no identifiable demand for attention)?",
                listOf("No, never", "Sometimes, occasionally", "Yes, regularly")),
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
                    "Extremely difficult — it is traumatic every time",
                    "My cat never goes to the vet"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4, 0)),
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
                    "It shows signs of distress (destruction, accidents, neighbors alerted)",
                    "I don't know"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 1, 2, 4, 0), signalAlerte = true),
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
            QuestionChoix("proprete_type", "It is rather:",
                listOf("Urine", "Stools", "Both")),
            QuestionChoix("precision_malproprete", "When it happens, is it rather:",
                listOf("Always in the same spot", "In different spots")),
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
            QuestionChoix("chaleur_marquage", "Does this marking happen mainly during her heat periods (times when she calls, meows loudly, rubs a lot)?",
                listOf("Yes, mainly during heat periods", "No, at other times too", "I don't know")),
            QuestionChoix("marquage_habitude_post_sterilisation", "Did this marking start before she was spayed?",
                listOf("Yes, and it has continued since", "No, it appeared after spaying", "I don't know / I adopted her already spayed")),
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
            QuestionChoix("cible_agression", "Who was it directed at?",
                listOf("A person", "Another animal (cat, dog...)", "Both"),
                axe = Axe.COHABITATION),
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
        ),

        listOf(
            QuestionTexte("nom_chat", "Wie heißt Ihre Katze?"),
            QuestionChoix("race_categorie", "Zu welcher Rassegruppe gehört Ihre Katze?",
                listOf("Europäisch Kurzhaar / Mischling", "Maine Coon", "Perser", "Siam", "Ragdoll",
                    "Bengal", "Britisch Kurzhaar", "Abessinier", "Heilige Birma", "Andere Rasse / unbekannt")),
            QuestionChoix("age", "Wie alt ist Ihre Katze?",
                listOf("Unter 1 Jahr (Kätzchen)", "Zwischen 1 und 3 Jahren", "Zwischen 4 und 8 Jahren", "9 Jahre und älter (Senior)")),
            QuestionChoix("senior_desorientation", "Wirkt Ihre Katze manchmal orientierungslos oder verloren an Orten, die sie gut kennt?",
                listOf("Nein, nie", "Manchmal, gelegentlich", "Ja, regelmäßig")),
            QuestionChoix("senior_vocalise_nocturne", "Miaut oder wandert Ihre Katze seit einiger Zeit nachts ohne erkennbaren Grund umher (kein Hunger, kein erkennbarer Wunsch nach Aufmerksamkeit)?",
                listOf("Nein, nie", "Manchmal, gelegentlich", "Ja, regelmäßig")),
            QuestionChoix("sterilise", "Ihre Katze ist:",
                listOf("Ein kastrierter Kater", "Eine kastrierte Katze", "Ein unkastrierter Kater", "Eine unkastrierte Katze")),
            QuestionChoix("acces_exterieur", "Hat Ihre Katze Freigang?",
                listOf("Ja, frei", "Ja, kontrolliert (gesicherter Balkon, beaufsichtigter Garten)", "Nein, nur in der Wohnung")),
            QuestionChoix("vie_interieur", "Wenn Ihre Katze in der Wohnung lebt, wie würden Sie ihre Umgebung beschreiben?",
                listOf("Bereichert (Kratzbäume, erhöhte Plätze, abwechslungsreiches Spielzeug, zugängliche Fenster)",
                    "Ordentlich, aber ausbaufähig", "Wenig anregend",
                    "Ich weiß es nicht genau", "Meine Katze hat Freigang")),
            QuestionChoix("reaction_bruit", "Wie reagiert Ihre Katze auf plötzliche oder laute Geräusche (Staubsauger, Gewitter, Bauarbeiten)?",
                listOf("Sie bleibt ruhig oder ist leicht überrascht und erholt sich schnell",
                    "Sie erschrickt und entfernt sich, erholt sich aber innerhalb weniger Minuten",
                    "Sie versteckt sich und braucht lange, um wiederzukommen",
                    "Sie gerät völlig in Panik und bleibt lange verunsichert"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
            QuestionChoix("reaction_inconnu", "Wie reagiert Ihre Katze auf einen fremden Menschen?",
                listOf("Sie nähert sich neugierig oder bleibt gleichgültig",
                    "Sie beobachtet vorsichtig aus der Ferne und nähert sich manchmal",
                    "Sie versteckt sich während des ganzen Besuchs",
                    "Sie zeigt Anzeichen von Unruhe oder Aggression"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),
            QuestionChoix("cache_souvent", "Wie oft versteckt oder isoliert sich Ihre Katze?",
                listOf("Selten – sie ist meist sichtbar und zugänglich",
                    "Manchmal, vor allem wenn Menschen da sind oder es laut ist",
                    "Oft, mehrmals am Tag",
                    "Die meiste Zeit – sie ist schwer zu finden"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 2, 4)),
            QuestionChoix("adaptation_changement", "Wie passt sich Ihre Katze an Veränderungen an (Umzug, neue Möbel, Besuch)?",
                listOf("Sehr gut – sie passt sich schnell an",
                    "Gut – ein paar Tage Vorsicht, dann legt es sich",
                    "Schwer – sie braucht mehrere Wochen, um sich zu erholen",
                    "Sehr schlecht – jede Veränderung löst eine anhaltende Krise aus"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4)),
            QuestionChoix("reaction_veterinaire", "Wie verläuft ein Tierarztbesuch?",
                listOf("Relativ gut, sie verträgt den Transport und die Untersuchung",
                    "Stressig, aber machbar",
                    "Sehr schwierig – sie gerät in der Transportbox oder beim Tierarzt in Panik",
                    "Äußerst schwierig – es ist jedes Mal traumatisch",
                    "Meine Katze geht nie zum Tierarzt"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3, 4, 0)),
            QuestionChoix("surtoilettage", "Haben Sie übermäßige Fellpflege bemerkt (Stellen mit schütterem Fell, wiederholtes übermäßiges Lecken)?",
                listOf("Nein, ihr Fell ist normal",
                    "Manchmal, ohne sichtbare Spuren",
                    "Ja, mit leicht schütteren Stellen"),
                axe = Axe.SECURITE, scoreParOption = listOf(0, 1, 3), signalAlerte = true),
            QuestionChoix("suit_partout", "Folgt Ihnen Ihre Katze in der Wohnung überallhin?",
                listOf("Nein, sie ist eher selbstständig",
                    "Manchmal, je nach Laune",
                    "Oft – sie ist gern im selben Raum wie Sie",
                    "Immer – sie weicht Ihnen kaum von der Seite"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 3)),
            QuestionChoix("reaction_absence", "Wie verhält sich Ihre Katze, wenn Sie nicht da sind?",
                listOf("Sie scheint ruhig damit zurechtzukommen",
                    "Sie miaut vielleicht ein wenig, wenn Sie gehen, beruhigt sich aber",
                    "Sie miaut oder wird deutlich unruhig",
                    "Sie zeigt Anzeichen von Not (Zerstörung, Unsauberkeit, Nachbarn alarmiert)",
                    "Ich weiß es nicht"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 1, 2, 4, 0), signalAlerte = true),
            QuestionChoix("vocalise_absence", "Miaut Ihre Katze übermäßig (wiederholtes, beharrliches Miauen)?",
                listOf("Nein, sie ist ruhig oder miaut normal",
                    "Manchmal, vor allem zu den Mahlzeiten",
                    "Oft, um Ihre Aufmerksamkeit einzufordern",
                    "Sehr oft, auf eine erdrückende Weise"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),
            QuestionChoix("proprete_stress", "Hat sich Ihre Katze schon einmal außerhalb ihres Katzenklos gelöst?",
                listOf("Nein, nie",
                    "Sehr selten, unter außergewöhnlichen Umständen",
                    "Gelegentlich, oft im Zusammenhang mit einem stressigen Ereignis",
                    "Regelmäßig"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 4), signalAlerte = true),
            QuestionChoix("proprete_type", "Es handelt sich eher um:",
                listOf("Urin", "Kot", "Beides")),
            QuestionChoix("precision_malproprete", "Wenn es passiert, ist es eher:",
                listOf("Immer an derselben Stelle", "An verschiedenen Stellen")),
            QuestionChoix("demande_attention", "Wie reagiert Ihre Katze, wenn Sie ihr keine Aufmerksamkeit schenken?",
                listOf("Sie akzeptiert es leicht und geht ihren Beschäftigungen nach",
                    "Sie bleibt ein wenig hartnäckig und beruhigt sich dann",
                    "Sie bleibt sehr hartnäckig, miaut oder stellt etwas an, um Aufmerksamkeit zu bekommen",
                    "Sie kann unruhig oder aggressiv werden, wenn sie ignoriert wird"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 0, 2, 3)),
            QuestionChoix("dort_avec_vous", "Schläft Ihre Katze bei Ihnen oder versucht sie, nachts in Ihrer Nähe zu bleiben?",
                listOf("Nein, sie hat ihre eigenen Plätze",
                    "Manchmal, je nach Laune",
                    "Oft – sie liegt lieber auf Ihrem Bett",
                    "Sie wird unruhig oder miaut, wenn Sie die Schlafzimmertür schließen"),
                axe = Axe.LIEN, scoreParOption = listOf(0, 0, 1, 2)),
            QuestionChoix("joue_activement", "Spielt Ihre Katze aktiv mit Spielzeug?",
                listOf("Ja, begeistert – sie beginnt selbst Spielrunden",
                    "Ja, wenn man sie dazu anregt",
                    "Ein wenig – sie langweilt sich schnell oder zeigt wenig Interesse",
                    "Nein, überhaupt kein Interesse am Spielen"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
            QuestionChoix("chasse_interieur", "Zeigt Ihre Katze in der Wohnung Jagdverhalten (Anschleichen, Anspringen, Fangen)?",
                listOf("Ja, regelmäßig mit Spielzeug oder kleinen Gegenständen",
                    "Manchmal", "Selten",
                    "Nie – dieses Verhalten fehlt völlig"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
            QuestionChoix("griffage_surfaces", "Kratzt Ihre Katze an nicht erlaubten Flächen (Möbel, Sofa, Teppich)?",
                listOf("Nein oder sehr selten – sie nutzt ihre Kratzbäume",
                    "Manchmal an Möbeln, zusätzlich zu den Kratzbäumen",
                    "Oft an Möbeln, obwohl Kratzbäume vorhanden sind",
                    "Sie kratzt nur an Möbeln, Kratzbäume interessieren sie nicht"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
            QuestionChoix("hyperactivite_nocturne", "Ist Ihre Katze nachts überaktiv (Rennen, Springen, Miauen in der Nacht)?",
                listOf("Nein, sie ist nachts ruhig",
                    "Manchmal, gelegentlich",
                    "Oft – sie stört regelmäßig Ihren Schlaf",
                    "Jede Nacht – das ist ein großes Problem"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 3, 4), signalAlerte = true),
            QuestionChoix("comportement_alimentaire", "Wie würden Sie das Fressverhalten Ihrer Katze beschreiben?",
                listOf("Normal – sie frisst gut, in ihrem eigenen Tempo",
                    "Sie frisst sehr schnell oder bettelt oft",
                    "Sie stiehlt Futter oder durchwühlt den Mülleimer",
                    "Sie hat starke Appetitschwankungen (verweigert das Futter oder frisst zwanghaft)"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
            QuestionChoix("destruction_ennui", "Richtet Ihre Katze Zerstörung oder Schäden an, vor allem in Ihrer Abwesenheit?",
                listOf("Nein, nie", "Selten, ein paar kleinere Vorfälle",
                    "Manchmal – umgeworfene Gegenstände, beschädigte Pflanzen",
                    "Oft – die Schäden sind erheblich und regelmäßig"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 3)),
            QuestionChoix("marquage_urinaire", "Markiert Ihre Katze mit Urin (im Stehen, an senkrechten Flächen)?",
                listOf("Nein, nie",
                    "Selten, in erkennbar stressigen Situationen",
                    "Ja, ab und zu",
                    "Ja, häufig"),
                axe = Axe.INSTINCTS, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
            QuestionChoix("chaleur_marquage", "Geschieht dieses Markieren vor allem während ihrer Rolligkeit (Phasen, in denen sie ruft, laut miaut, sich viel reibt)?",
                listOf("Ja, vor allem während der Rolligkeit", "Nein, auch zu anderen Zeiten", "Ich weiß es nicht")),
            QuestionChoix("marquage_habitude_post_sterilisation", "Hat dieses Markieren vor ihrer Kastration begonnen?",
                listOf("Ja, und es hat seitdem angehalten", "Nein, es ist nach der Kastration aufgetreten", "Ich weiß es nicht / ich habe sie bereits kastriert übernommen")),
            QuestionChoix("relation_autres_chats", "Wenn Sie mehrere Katzen haben, wie ist ihr Verhältnis zueinander?",
                listOf("Meist gutes Einvernehmen, sogar gegenseitige Zuneigung",
                    "Neutrales Nebeneinander – sie ignorieren sich",
                    "Häufige Spannungen, aber keine körperlichen Angriffe",
                    "Regelmäßige Konflikte mit Angriffen",
                    "Ich habe nur eine Katze"),
                axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),
            QuestionChoix("relation_enfants", "Wenn Kinder im Haushalt leben, wie reagiert Ihre Katze?",
                listOf("Sehr gut – sie spielt mit ihnen oder duldet sie gelassen",
                    "Gut – sie hält Abstand, aber ohne Anspannung",
                    "Sie flieht oder zieht sich zurück, wenn Kinder da sind",
                    "Sie kann aggressiv reagieren (Kratzen, Beißen)",
                    "Keine Kinder im Haushalt"),
                axe = Axe.COHABITATION, scoreParOption = listOf(0, 0, 2, 4, 0), signalAlerte = true),
            QuestionChoix("agressivite_caresses", "Beißt oder kratzt Ihre Katze beim Streicheln oder Spielen?",
                listOf("Nein, nie",
                    "Selten – nur wenn Warnsignale übersehen wurden",
                    "Manchmal, unvorhersehbar",
                    "Oft – körperliche Kontakte sind schwer zu handhaben"),
                axe = Axe.COHABITATION, scoreParOption = listOf(0, 1, 2, 4), signalAlerte = true),
            QuestionChoix("a_deja_griffe_mordu", "Hat Ihre Katze schon einmal jemanden gekratzt oder gebissen (Sie, ein Familienmitglied, ein Kind)?",
                listOf("Nein, nie", "Ja, das ist schon vorgekommen"),
                axe = Axe.COHABITATION, scoreParOption = listOf(0, 4), poids = 2, signalCritique = true),
            QuestionChoix("cible_agression", "Gegen wen hat sich das gerichtet?",
                listOf("Einen Menschen", "Ein anderes Tier (Katze, Hund …)", "Beides"),
                axe = Axe.COHABITATION),
            QuestionChoix("defense_ressources", "Verteidigt Ihre Katze ihre Ressourcen (Napf, Katzenklo, Liegeplatz) aggressiv?",
                listOf("Nein, nie",
                    "Manchmal – sie knurrt oder faucht, wenn man sich nähert",
                    "Ja, häufig – sie mag es nicht, wenn sich jemand ihren Sachen nähert"),
                axe = Axe.COHABITATION, scoreParOption = listOf(0, 2, 4), signalAlerte = true),
            QuestionChoix("a_un_probleme", "Gibt es derzeit ein bestimmtes Verhalten, das Sie beschäftigt?",
                listOf("Ja, ich möchte mehr darüber erfahren", "Nein, insgesamt ist alles in Ordnung")),
            QuestionChoix("apparition", "Dieses Verhalten ist aufgetreten:",
                listOf("Nach und nach", "Plötzlich, von einem Tag auf den anderen", "Ich weiß es nicht genau")),
            QuestionChoix("duree_probleme", "Seit wann beobachten Sie dieses Verhalten?",
                listOf("Weniger als eine Woche", "Zwischen 1 Woche und 1 Monat", "Seit mehreren Monaten", "Schon immer oder seit sehr langer Zeit")),
            QuestionChoix("evolution_probleme", "Entwickelt sich dieses Verhalten?",
                listOf("Es wird besser", "Es bleibt stabil", "Es verschlimmert sich")),
            QuestionChoix("frequence_probleme", "Wie oft tritt dieses Verhalten auf?",
                listOf("Selten – ein paar Mal im Monat", "Einige Male pro Woche", "Jeden Tag", "Mehrmals täglich")),
            QuestionChoix("intensite_probleme", "Wenn es passiert, ist es eher:",
                listOf("Leicht zu handhaben", "Störend, aber erträglich", "Schwer zu handhaben", "Sehr intensiv, unkontrollierbar")),
            QuestionChoix("generalisation_probleme", "Dieses Verhalten tritt auf:",
                listOf("In einer ganz bestimmten Situation", "In mehreren verschiedenen Situationen", "In den meisten Alltagssituationen")),
            QuestionChoix("changement_recent", "Gab es kürzlich eine wichtige Veränderung im Leben Ihrer Katze?",
                listOf("Keine nennenswerte Veränderung",
                    "Eine kleine Veränderung (neue Möbel, neuer Tagesablauf)",
                    "Eine große Veränderung (Umzug, neues Tier, Geburt, Trennung)")),
            QuestionChoix("signe_physique", "Haben Sie körperliche Veränderungen bei Ihrer Katze bemerkt (Appetit, Gewicht, Fell, Ausscheidungen)?",
                listOf("Nein, nichts Besonderes", "Vielleicht – ich bin mir nicht sicher",
                    "Ja, eine deutliche Veränderung", "Ja, etwas, das mich wirklich beunruhigt"))
        )
    )
}

// ═══════════════════════════════════════════════════════════
// CONSULTATION PERSONNALISÉE (FR uniquement)
// ═══════════════════════════════════════════════════════════

fun showConsultation(): Boolean = appLang() == AppLang.FR

const val CONSULTATION_BOOKING_URL = "https://tidycal.com/laurenaharoy/30-minute-meeting"

const val CONSULTATION_BOOKING_URL_1H = "https://tidycal.com/laurenaharoy/consultation-comportementale-1-heure"

const val CGV_URL = "https://laurenaharoy-ctrl.github.io/comprendremonchat/cgv.html"

const val WEBSITE_URL = "https://comportementaliste91.fr"

fun strConsultationFormule30() = "Consultation conseil — 30 min — 35 €"

fun strConsultationFormule60() = "Consultation comportementale — 1 heure — 50 €"

fun strConsultationCGV() = "Consulter les Conditions Générales de Vente"

fun strConsultationSite() = "Visiter mon site internet"

fun strConsultationTitre() = "Besoin d'aide pour interpréter ce bilan ?"

fun strConsultationSousTitre() = "Consultation personnalisée du bilan émotionnel de votre chat"

fun strConsultationDescription() = "Vous avez reçu le bilan émotionnel de votre animal et vous souhaitez mieux comprendre ses résultats ?\n\nJe vous propose deux formats de consultation personnalisée en visio, selon vos besoins : un échange conseil de 30 minutes pour une première orientation, ou une consultation comportementale d'1 heure pour construire un plan d'accompagnement plus approfondi.\n\nPensez à m'envoyer votre bilan PDF par email avant notre rendez-vous, via le bouton Partager de l'application, à l'adresse laurena.haroy@gmail.com."

fun strConsultationDisclaimer() = "Cette consultation ne remplace pas une consultation vétérinaire et ne constitue pas un accompagnement comportemental complet à elle seule.\n\nEn cas de changement brutal de comportement, douleur, malpropreté soudaine, agressivité inhabituelle ou symptôme physique, consultez d'abord un vétérinaire."

fun strConsultationPrix() = "35 € / 30 minutes"

fun strConsultationBouton() = "Réserver ma consultation"