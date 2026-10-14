package com.laurena.comprendremonchat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

object PdfExporter {

    private val COLOR_PRIMARY = Color.parseColor("#8E4A2D")
    private val COLOR_PRIMARY_SOFT = Color.parseColor("#B86A4A")
    private val COLOR_ACCENT = Color.parseColor("#D9A58F")
    private val COLOR_INK = Color.parseColor("#33231D")
    private val COLOR_INK_SOFT = Color.parseColor("#75584C")
    private val COLOR_BORDER = Color.parseColor("#E0D2C6")
    private val COLOR_WARM_BG = Color.parseColor("#FCF8F5")
    private val COLOR_WARM_BG_ALT = Color.parseColor("#F4ECE5")
    private val COLOR_WHITE = Color.WHITE
    private val COLOR_PRIORITE_FAIBLE = Color.parseColor("#9E8572")
    private val COLOR_PRIORITE_MODEREE = Color.parseColor("#B8845A")
    private val COLOR_PRIORITE_ELEVEE = Color.parseColor("#8E4A2D")
    private val COLOR_PRIORITE_URGENTE = Color.parseColor("#6B2D1A")
    private val COLOR_PRIORITE_ELEVEE_BG = Color.parseColor("#F2E0D6")
    private val COLOR_MORSURE_TEXTE = Color.parseColor("#B8845A")
    private val COLOR_MORSURE_BG = Color.parseColor("#F5E8DC")
    private val COLOR_LIEN = Color.parseColor("#1155CC")

    private const val PAGE_W = 595
    private const val PAGE_H = 842
    private const val MARGIN = 44f
    private val CONTENT_W = PAGE_W - MARGIN * 2
    private const val FOOTER_H = 60f
    private val CONTENT_BOTTOM = PAGE_H - MARGIN - FOOTER_H - 10f
    private val QR_FOOTER_TOP = PAGE_H - MARGIN - 90f
    private val CONTENT_BOTTOM_QR = QR_FOOTER_TOP - 12f

    private fun t(fr: String, en: String, de: String, es: String) = tr(fr, en, de, es)
    private fun appName() = t("Comprendre mon chat", "Understanding My Cat", "Meine Katze verstehen", "Comprender a mi gato")

    fun exporterBilanPdf(context: Context, nomChat: String, analyse: ResultatAnalyse): File {
        val nom = nomChatAffiche(nomChat)
        val date = when (appLang()) {
            AppLang.EN -> SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(Date())
            AppLang.DE -> SimpleDateFormat("dd. MMMM yyyy", Locale.GERMAN).format(Date())
            AppLang.ES -> SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es")).format(Date())
            else -> SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(Date())
        }
        val couleurPriorite = couleurPourPriorite(analyse.prioriteAction)
        val libellePriorite = libellePourPriorite(analyse.prioriteAction)

        // 1er passage : brouillon jetable, uniquement pour compter les pages
        val brouillon = PdfDocument()
        val totalPages = dessineTout(brouillon, nom, date, analyse, couleurPriorite, libellePriorite, 0)
        brouillon.close()

        // 2e passage : le vrai document, avec « Page X / total » correct
        val document = PdfDocument()
        dessineTout(document, nom, date, analyse, couleurPriorite, libellePriorite, totalPages)

        val nomFichierSafe = nom.lowercase(Locale.getDefault())
            .replace("\\s+".toRegex(), "_")
            .replace("[^a-z0-9_]+".toRegex(), "")

        val prefix = t("bilan", "assessment", "einschaetzung", "evaluacion")
        val file = File(context.cacheDir, "${prefix}_${nomFichierSafe.ifBlank { t("chat", "cat", "katze", "gato") }}.pdf")
        FileOutputStream(file).use { document.writeTo(it) }
        document.close()
        return file
    }

    private fun dessineTout(
        document: PdfDocument, nom: String, date: String, analyse: ResultatAnalyse,
        couleurPriorite: Int, libellePriorite: String, totalPages: Int
    ): Int {
        val pager = Pager(document, totalPages)
        dessineCouverture(pager, nom, date, analyse, couleurPriorite, libellePriorite)
        dessineProfil(pager, nom, analyse)
        dessinePlan(pager, nom, analyse)
        dessineARetenir(pager, nom, analyse, couleurPriorite)
        if (showConsultation()) {
            dessineConsultation(pager)
        }
        pager.closePage()
        return pager.pageNum
    }

    // ════════════════════════════════════════════════════════════
    // PAGINATION — passe automatiquement à la page suivante
    // quand un bloc ne tient plus, au lieu de le couper
    // ════════════════════════════════════════════════════════════
    private class Pager(val document: PdfDocument, val totalPages: Int) {
        var pageNum = 0
        private var page: PdfDocument.Page? = null
        val canvas: Canvas get() = page!!.canvas
        var y = MARGIN
        var bottom = CONTENT_BOTTOM
        var header: String? = null
        private var startY = MARGIN

        fun newPage(title: String?) {
            closePage()
            pageNum++
            page = demarrerPage(document, pageNum)
            header = title
            y = MARGIN
            if (title != null) {
                drawPageHeader(page!!.canvas, title)
                y += 48f
            }
            startY = y
        }

        /** Renvoie true si une nouvelle page a été ouverte. */
        fun ensure(height: Float): Boolean {
            if (y + height > bottom && y > startY) {
                newPage(header)
                return true
            }
            return false
        }

        fun closePage(withQr: Boolean = false) {
            val p = page ?: return
            if (withQr) dessineFooterAvecQr(p.canvas, pageNum, totalPages)
            else dessineFooter(p.canvas, pageNum, totalPages)
            document.finishPage(p)
            page = null
        }
    }

    private fun Pager.sectionTitle(title: String, keepWith: Float = 40f) {
        ensure(48f + keepWith)
        drawSectionTitle(canvas, y, title)
        y += 48f
    }

    /** Hauteur d'une carte (pour garder un titre avec son contenu). */
    private fun cardHeight(text: String, paint: TextPaint): Float =
        measureStaticTextHeight(text, (CONTENT_W - 32f).toInt(), paint) + 32f

    /** Hauteur d'un paragraphe (pour garder un titre avec son contenu). */
    private fun paragraphHeight(text: String, paint: TextPaint): Float =
        measureStaticTextHeight(text, CONTENT_W.toInt(), paint)

    private fun Pager.card(text: String, fill: Int, border: Int, paint: TextPaint, spaceAfter: Float = 24f) {
        val h = cardHeight(text, paint)
        ensure(h)
        drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + h, fill, border, 14f)
        drawStaticText(canvas, text, MARGIN + 16f, y + 16f, (CONTENT_W - 32f).toInt(), paint)
        y += h + spaceAfter
    }

    private fun Pager.paragraph(text: String, paint: TextPaint, spaceAfter: Float = 24f) {
        if (text.isBlank()) return
        val h = paragraphHeight(text, paint)
        ensure(h)
        drawStaticText(canvas, text, MARGIN, y, CONTENT_W.toInt(), paint)
        y += h + spaceAfter
    }

    private fun Pager.label(text: String) {
        ensure(20f + 30f)
        canvas.drawText(text, MARGIN, y, makePaint(11f, COLOR_PRIMARY, bold = true))
        y += 20f
    }

    private fun Pager.bullet(text: String) {
        val h = makeStaticLayout(text, (CONTENT_W - 18f).toInt(), makePaint(10.5f, COLOR_INK)).height.toFloat()
        if (ensure(h)) y += 20f
        y = drawBullet(canvas, y, text)
    }

    // ════════════════════════════════════════════════════════════
    // PAGE 1 — Couverture
    // ════════════════════════════════════════════════════════════
    private fun dessineCouverture(
        pager: Pager, nom: String, date: String,
        analyse: ResultatAnalyse, couleurPriorite: Int, libellePriorite: String
    ) {
        pager.newPage(null)
        val canvas = pager.canvas

        drawRect(canvas, 0f, 0f, PAGE_W.toFloat(), 190f, COLOR_WARM_BG)
        drawLine(canvas, MARGIN, 26f, PAGE_W - MARGIN, 26f, COLOR_ACCENT, 0.8f)

        val appTitlePaint = makePaint(11f, COLOR_INK_SOFT, italic = true)
        val appTitle = appName()
        canvas.drawText(appTitle, (PAGE_W - appTitlePaint.measureText(appTitle)) / 2f, 44f, appTitlePaint)

        val nomPaint = makePaint(34f, COLOR_PRIMARY, bold = true)
        canvas.drawText(nom, (PAGE_W - nomPaint.measureText(nom)) / 2f, 96f, nomPaint)

        val sousTitrePaint = makePaint(12f, COLOR_INK_SOFT, italic = true)
        val sousTitre = t("Bilan émotionnel", "Emotional assessment", "Emotionale Einschätzung", "Evaluación emocional")
        canvas.drawText(sousTitre, (PAGE_W - sousTitrePaint.measureText(sousTitre)) / 2f, 118f, sousTitrePaint)

        drawLine(canvas, MARGIN + 40f, 130f, PAGE_W - MARGIN - 40f, 130f, COLOR_BORDER, 0.8f)

        val badgeText = libellePriorite.uppercase(Locale.getDefault())
        val badgePaint = makePaint(9f, COLOR_WHITE, bold = true)
        val badgeTextW = badgePaint.measureText(badgeText)
        val badgeW = max(120f, badgeTextW + 36f)
        val badgeLeft = (PAGE_W - badgeW) / 2f
        drawRoundRect(canvas, badgeLeft, 140f, badgeLeft + badgeW, 162f, 11f, couleurPriorite)
        canvas.drawText(badgeText, (PAGE_W - badgeTextW) / 2f, 155f, badgePaint)

        drawStaticText(canvas, analyse.profil.phraseHumaine, MARGIN, 170f, CONTENT_W.toInt(),
            makePaint(11f, COLOR_INK, italic = true), Layout.Alignment.ALIGN_CENTER)

        // Si la couverture déborde, la page suivante reçoit un en-tête
        pager.header = sousTitre
        pager.y = 210f
        drawLine(canvas, MARGIN, pager.y, PAGE_W - MARGIN, pager.y, COLOR_BORDER, 0.5f)
        pager.y += 24f

        pager.sectionTitle(t("En un coup d\u2019\u0153il", "At a glance", "Auf einen Blick", "De un vistazo"), keepWith = 200f)

        val gridItems = listOf(
            t("Axe principal", "Main axis", "Hauptachse", "Eje principal") to libelleAxe(analyse.problemePrincipal),
            t("Situation", "Situation", "Situation", "Situación") to texteNiveauSituation(analyse.niveauSituation),
            t("Besoin principal", "Main need", "Hauptbedürfnis", "Necesidad principal") to besoinPrincipal(analyse.problemePrincipal, niveauAxePrincipal(analyse))
                .removePrefix("Besoin principal : ")
                .removePrefix("Main need: ")
                .removePrefix("Hauptbedürfnis: ")
                .removeSuffix("."),
            t("Aide à envisager", "Support to consider", "Mögliche Unterstützung", "Ayuda a considerar") to aideAEnvisager(analyse)
        )
        pager.y = drawInfoGrid(pager.canvas, pager.y, gridItems)
        pager.y += 20f

        pager.card(analyse.messageSituation, COLOR_WARM_BG_ALT, COLOR_BORDER, makePaint(11f, COLOR_INK))
    }

    // ════════════════════════════════════════════════════════════
    // PAGE 2 — Profil & Analyse
    // ════════════════════════════════════════════════════════════
    private fun dessineProfil(pager: Pager, nom: String, analyse: ResultatAnalyse) {
        pager.newPage(t("Profil de $nom", "$nom's profile", "Profil von $nom", "Perfil de $nom"))

        pager.sectionTitle(t("Les 4 dimensions", "The 4 dimensions", "Die 4 Dimensionen", "Las 4 dimensiones"))

        val axes = listOf(
            Triple(t("Sécurité émotionnelle", "Emotional security", "Emotionale Sicherheit", "Seguridad emocional"), analyse.niveauPeur, analyse.peur),
            Triple(t("Lien humain", "Human bond", "Bindung zum Menschen", "Vínculo con el humano"), analyse.niveauAttachement, analyse.attachement),
            Triple(t("Instincts", "Instincts", "Instinkte", "Instintos"), analyse.niveauImpulsivite, analyse.impulsivite),
            Triple(t("Cohabitation", "Cohabitation", "Zusammenleben", "Convivencia"), analyse.niveauReactivite, analyse.reactivite)
        )
        axes.forEach { (label, niveau, score) ->
            pager.ensure(46f)
            pager.y = drawAxeBar(pager.canvas, pager.y, label, niveau, score)
            pager.y += 12f
        }
        pager.y += 14f

        val texte11 = makePaint(11f, COLOR_INK)

        pager.sectionTitle(
            t("Hypoth\u00e8se de lecture", "Main hypothesis", "Deutungshypothese", "Hipótesis de lectura"),
            keepWith = minOf(cardHeight(analyse.hypothesePrincipale, texte11), 300f)
        )
        pager.card(analyse.hypothesePrincipale, COLOR_WHITE, COLOR_ACCENT, texte11)

        if (analyse.explicationPrincipale.isNotBlank()) {
            pager.sectionTitle(
                t("Ce qui se passe probablement", "What is probably happening", "Was wahrscheinlich geschieht", "Lo que probablemente está pasando"),
                keepWith = minOf(paragraphHeight(analyse.explicationPrincipale, texte11), 300f)
            )
            pager.paragraph(analyse.explicationPrincipale, texte11)
        }

        if (analyse.marquageHabitudePostSterilisation) {
            val texteHabitude = t("Ce marquage semble avoir débuté pendant les chaleurs, avant la stérilisation, et s'est transformé depuis en habitude acquise. La cause hormonale a disparu, mais le geste reste ancré — ce type de marquage devenu habituel est souvent plus long à corriger qu'un marquage lié au stress.", "This marking seems to have started during heat periods, before spaying, and has since turned into a learned habit. The hormonal cause is gone, but the behavior remains ingrained — this kind of habitual marking often takes longer to correct than stress-related marking.", "Dieses Markieren scheint während der Rolligkeit vor der Kastration begonnen zu haben und hat sich seitdem zu einer erlernten Gewohnheit entwickelt. Die hormonelle Ursache ist verschwunden, doch das Verhalten bleibt bestehen – eine solche zur Gewohnheit gewordene Markierung lässt sich oft langsamer korrigieren als eine stressbedingte.", "Este marcaje parece haber empezado durante el celo, antes de la esterilización, y desde entonces se ha convertido en un hábito adquirido. La causa hormonal ha desaparecido, pero el gesto sigue arraigado: este tipo de marcaje convertido en hábito suele tardar más en corregirse que un marcaje relacionado con el estrés.")
            pager.card(texteHabitude, COLOR_WARM_BG_ALT, COLOR_PRIMARY_SOFT, makePaint(10.5f, COLOR_PRIMARY_SOFT, bold = true))
        }
    }

    // ════════════════════════════════════════════════════════════
    // PAGE 3 — Plan d'action
    // ════════════════════════════════════════════════════════════
    private fun dessinePlan(pager: Pager, nom: String, analyse: ResultatAnalyse) {
        pager.newPage(t("Plan d\u2019action pour $nom", "Action plan for $nom", "Aktionsplan für $nom", "Plan de acción para $nom"))

        val texte11 = makePaint(11f, COLOR_INK)
        pager.sectionTitle(
            t("Premier levier utile", "First useful step", "Erster hilfreicher Schritt", "Primer paso útil"),
            keepWith = minOf(cardHeight(analyse.conseilPrincipal, texte11), 300f)
        )
        pager.card(analyse.conseilPrincipal, COLOR_WARM_BG_ALT, COLOR_ACCENT, texte11)

        pager.sectionTitle(t("Les prochains jours", "The next few days", "Die nächsten Tage", "Los próximos días"))

        pager.label(t("\u00c0 faire", "To do", "Zu tun", "Qué hacer"))
        analyse.planAction.aFaire.forEach { pager.bullet(it) }
        pager.y += 12f

        pager.label(t("\u00c0 \u00e9viter", "To avoid", "Zu vermeiden", "Qué evitar"))
        analyse.planAction.aEviter.forEach { pager.bullet(it) }
        pager.y += 12f

        pager.label(t("\u00c0 observer", "To observe", "Zu beobachten", "Qué observar"))
        analyse.planAction.aObserver.forEach { pager.bullet(it) }
        pager.y += 18f

        if (analyse.aDejaMordu) {
            val morsuText = if (analyse.cibleAgressionAnimal) t("Une griffure ou morsure envers un autre animal a \u00e9t\u00e9 signal\u00e9e. Un accompagnement par un comportementaliste f\u00e9lin est recommand\u00e9 pour reprendre la cohabitation de fa\u00e7on progressive et s\u00e9curis\u00e9e.", "A scratch or bite toward another animal has been reported. Support from a feline behaviorist is recommended to rebuild cohabitation gradually and safely.", "Es wurde ein Kratzer oder Biss gegenüber einem anderen Tier gemeldet. Eine Begleitung durch eine Fachperson für Katzenverhalten wird empfohlen, um das Zusammenleben schrittweise und sicher neu aufzubauen.", "Se ha señalado un arañazo o una mordedura a otro animal. Se recomienda el acompañamiento de un especialista en comportamiento felino para retomar la convivencia de forma progresiva y segura.") else t("Une griffure ou morsure envers une personne a \u00e9t\u00e9 signal\u00e9e. Un accompagnement v\u00e9t\u00e9rinaire comportemental est recommand\u00e9 pour \u00e9valuer la situation.", "A scratch or bite toward a person has been reported. Support from a veterinary behaviorist is recommended to assess the situation.", "Es wurde ein Kratzer oder Biss gegenüber einem Menschen gemeldet. Eine verhaltensmedizinische tierärztliche Begleitung wird empfohlen, um die Situation zu beurteilen.", "Se ha señalado un arañazo o una mordedura a una persona. Se recomienda el acompañamiento de un veterinario especialista en comportamiento para valorar la situación.")
            pager.card(
                morsuText, COLOR_MORSURE_BG, COLOR_MORSURE_TEXTE,
                makePaint(11f, COLOR_MORSURE_TEXTE, bold = true), spaceAfter = 10f
            )
        }
    }

    // ════════════════════════════════════════════════════════════
    // PAGE 4 — À retenir (avec pied de page QR code)
    // ════════════════════════════════════════════════════════════
    private fun dessineARetenir(pager: Pager, nom: String, analyse: ResultatAnalyse, couleurPriorite: Int) {
        pager.newPage(t("\u00c0 retenir", "Key takeaways", "Das Wichtigste", "Lo esencial"))
        pager.bottom = CONTENT_BOTTOM_QR

        val recapText = when (appLang()) {
            AppLang.DE -> buildString {
                append("$nom zeigt vor allem ein Profil „${analyse.profil.profilType}“.\n\n")
                append("Situation: ${texteNiveauSituation(analyse.niveauSituation)}.\n\n")
                append("Hauptachse: ${libelleAxe(analyse.problemePrincipal)}.\n\n")
                append(besoinPrincipal(analyse.problemePrincipal, niveauAxePrincipal(analyse)))
            }
            AppLang.ES -> buildString {
                append("$nom muestra sobre todo un perfil «${analyse.profil.profilType.lowercase(Locale("es"))}».\n\n")
                append("Situación: ${texteNiveauSituation(analyse.niveauSituation).lowercase(Locale("es"))}.\n\n")
                append("Eje principal: ${libelleAxe(analyse.problemePrincipal).lowercase(Locale("es"))}.\n\n")
                append(besoinPrincipal(analyse.problemePrincipal, niveauAxePrincipal(analyse)))
            }
            AppLang.EN -> buildString {
                append("$nom primarily presents a ${analyse.profil.profilType.lowercase(Locale.ENGLISH)} profile.\n\n")
                append("Situation: ${texteNiveauSituation(analyse.niveauSituation).lowercase(Locale.ENGLISH)}.\n\n")
                append("Main axis: ${libelleAxe(analyse.problemePrincipal).lowercase(Locale.ENGLISH)}.\n\n")
                append(besoinPrincipal(analyse.problemePrincipal, niveauAxePrincipal(analyse)))
            }
            else -> buildString {
                append("$nom pr\u00e9sente surtout un profil ${analyse.profil.profilType.lowercase(Locale.FRENCH)}.\n\n")
                append("Situation\u00a0: ${texteNiveauSituation(analyse.niveauSituation).lowercase(Locale.FRENCH)}.\n\n")
                append("Axe principal\u00a0: ${libelleAxe(analyse.problemePrincipal).lowercase(Locale.FRENCH)}.\n\n")
                append(besoinPrincipal(analyse.problemePrincipal, niveauAxePrincipal(analyse)))
            }
        }
        val recapH = measureStaticTextHeight(recapText, (CONTENT_W - 48f).toInt(), makePaint(12f, COLOR_INK)) + 48f
        pager.ensure(recapH)
        val canvas = pager.canvas
        drawCard(canvas, MARGIN, pager.y, PAGE_W - MARGIN, pager.y + recapH, COLOR_WARM_BG, COLOR_BORDER, 16f)
        drawRoundRect(canvas, MARGIN, pager.y, MARGIN + 5f, pager.y + recapH, 16f, couleurPriorite)
        drawStaticText(canvas, recapText, MARGIN + 24f, pager.y + 24f, (CONTENT_W - 48f).toInt(), makePaint(12f, COLOR_INK))
        pager.y += recapH + 32f

        val conclusion = tr("L\u2019objectif n\u2019est pas d\u2019\u00e9tiqueter $nom, mais d\u2019aider \u00e0 mieux lire ce qui se passe et \u00e0 avancer de mani\u00e8re plus adapt\u00e9e.", "The goal is not to label $nom, but to better understand what is happening and move forward in a more suitable way.", "Ziel ist es nicht, $nom ein Etikett aufzudrücken, sondern besser zu verstehen, was geschieht, und auf eine passendere Weise voranzukommen.", "El objetivo no es etiquetar a $nom, sino ayudar a entender mejor lo que pasa y avanzar de forma más adecuada.")
        val conclusionPaint = makePaint(11f, COLOR_INK)
        pager.sectionTitle(t("Conclusion", "Conclusion", "Fazit", "Conclusión"), keepWith = minOf(paragraphHeight(conclusion, conclusionPaint), 300f))
        pager.paragraph(conclusion, conclusionPaint, spaceAfter = 32f)

        val disclaimer = tr("Ce bilan est indicatif. Il ne remplace pas l\u2019avis d\u2019un v\u00e9t\u00e9rinaire ni d\u2019un comportementaliste f\u00e9lin. Il peut servir de base de discussion lors d\u2019une consultation.", "This assessment is indicative. It does not replace the advice of a veterinarian or feline behaviorist. It can serve as a basis for discussion during a consultation.", "Diese Einschätzung ist unverbindlich. Sie ersetzt nicht den Rat eines Tierarztes oder einer Fachperson für Katzenverhalten. Sie kann als Gesprächsgrundlage bei einer Beratung dienen.", "Esta evaluación es orientativa. No sustituye la opinión de un veterinario ni de un especialista en comportamiento felino. Puede servir como base de conversación en una consulta.")
        val disclaimerPaint = makePaint(9.5f, COLOR_INK_SOFT)
        val disclaimerH = measureStaticTextHeight(disclaimer, CONTENT_W.toInt(), disclaimerPaint)
        pager.ensure(16f + disclaimerH)
        drawLine(pager.canvas, MARGIN, pager.y, PAGE_W - MARGIN, pager.y, COLOR_BORDER, 0.5f)
        pager.y += 16f
        drawStaticText(pager.canvas, disclaimer, MARGIN, pager.y, CONTENT_W.toInt(), disclaimerPaint)
        pager.y += disclaimerH

        pager.closePage(withQr = true)
        pager.bottom = CONTENT_BOTTOM
    }

    // ════════════════════════════════════════════════════════════
    // PAGE 5 — Consultation personnalisée (FR uniquement, page dédiée)
    // ════════════════════════════════════════════════════════════
    private fun dessineConsultation(pager: Pager) {
        pager.newPage(strConsultationTitre())
        val canvas = pager.canvas
        val y = MARGIN + 60f

        val titre = strConsultationTitre()
        val sousTitre = strConsultationSousTitre()
        val description = strConsultationDescription()
        val disclaimer = strConsultationDisclaimer()
        val prix = strConsultationPrix()
        val bouton = strConsultationAppel()
        val url = strConsultationTelephonePdf()

        val innerW = (CONTENT_W - 40f).toInt()
        val paintTitre = makePaint(16f, COLOR_PRIMARY, bold = true)
        val paintSousTitre = makePaint(12f, COLOR_PRIMARY_SOFT, bold = true)
        val paintDescription = makePaint(11f, COLOR_INK)
        val paintDisclaimer = makePaint(9.5f, COLOR_INK_SOFT)
        val paintPrix = makePaint(14f, COLOR_INK, bold = true)
        val paintLienLabel = makePaint(10.5f, COLOR_INK, bold = true)
        val paintLien = makePaint(11f, COLOR_LIEN, bold = true)

        val titreH = measureStaticTextHeight(titre, innerW, paintTitre)
        val sousTitreH = measureStaticTextHeight(sousTitre, innerW, paintSousTitre)
        val descriptionH = measureStaticTextHeight(description, innerW, paintDescription)
        val disclaimerH = measureStaticTextHeight(disclaimer, innerW, paintDisclaimer)

        val totalH = 24f + titreH + 12f + sousTitreH + 18f + descriptionH + 20f +
                disclaimerH + 24f + 24f + 18f + 20f + 24f

        val cardTop = y
        val cardBottom = y + totalH
        drawCard(canvas, MARGIN, cardTop, PAGE_W - MARGIN, cardBottom, COLOR_WARM_BG_ALT, COLOR_ACCENT, 18f)

        var cy = cardTop + 24f
        val cx = MARGIN + 20f

        drawStaticText(canvas, titre, cx, cy, innerW, paintTitre)
        cy += titreH + 12f

        drawStaticText(canvas, sousTitre, cx, cy, innerW, paintSousTitre)
        cy += sousTitreH + 18f

        drawStaticText(canvas, description, cx, cy, innerW, paintDescription)
        cy += descriptionH + 20f

        drawStaticText(canvas, disclaimer, cx, cy, innerW, paintDisclaimer)
        cy += disclaimerH + 24f

        canvas.drawText(prix, cx, cy, paintPrix)
        cy += 24f

        val lienLabel = "$bouton :"
        canvas.drawText(lienLabel, cx, cy, paintLienLabel)
        cy += 18f

        canvas.drawText(url, cx, cy, paintLien)
        val underlineY = cy + 2f
        canvas.drawLine(cx, underlineY, cx + paintLien.measureText(url), underlineY,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = COLOR_LIEN; strokeWidth = 0.8f })
    }

    // ════════════════════════════════════════════════════════════
    // HELPERS — Dessin
    // ════════════════════════════════════════════════════════════

    private fun demarrerPage(document: PdfDocument, num: Int): PdfDocument.Page {
        val info = PdfDocument.PageInfo.Builder(PAGE_W, PAGE_H, num).create()
        val page = document.startPage(info)
        page.canvas.drawColor(COLOR_WHITE)
        return page
    }

    private fun drawPageHeader(canvas: Canvas, title: String) {
        drawRect(canvas, 0f, 0f, PAGE_W.toFloat(), MARGIN + 28f, COLOR_WARM_BG)
        drawLine(canvas, 0f, MARGIN + 28f, PAGE_W.toFloat(), MARGIN + 28f, COLOR_BORDER, 0.5f)
        drawStaticText(canvas, title, MARGIN, MARGIN + 6f, (CONTENT_W / 2).toInt(), makePaint(9f, COLOR_INK_SOFT, bold = true))
        val appLabel = appName()
        val appLabelPaint = makePaint(9f, COLOR_INK_SOFT, italic = true)
        canvas.drawText(appLabel, PAGE_W - MARGIN - appLabelPaint.measureText(appLabel), MARGIN + 18f, appLabelPaint)
    }

    private fun drawSectionTitle(canvas: Canvas, y: Float, title: String) {
        drawStaticText(canvas, title, MARGIN, y, CONTENT_W.toInt(), makePaint(13f, COLOR_PRIMARY, bold = true))
        drawLine(canvas, MARGIN, y + 22f, PAGE_W - MARGIN, y + 22f, COLOR_BORDER, 0.6f)
    }

    private fun drawAxeBar(canvas: Canvas, y: Float, label: String, niveau: NiveauAxe, score: Int): Float {
        val libelleNiveau = QuestionnaireEngine.libelleNiveauAxe(niveau)
        val fillRatio = (score / 100f).coerceIn(0f, 1f)
        val barH = 7f
        drawStaticText(canvas, label, MARGIN, y, (CONTENT_W * 0.6f).toInt(), makePaint(10.5f, COLOR_INK))
        val niveauPaint = makePaint(10.5f, COLOR_PRIMARY_SOFT, bold = true)
        canvas.drawText(libelleNiveau, PAGE_W - MARGIN - niveauPaint.measureText(libelleNiveau), y + 13f, niveauPaint)
        val barY = y + 18f
        canvas.drawRoundRect(RectF(MARGIN, barY, MARGIN + CONTENT_W, barY + barH), barH / 2, barH / 2,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#EDE3DB"); style = Paint.Style.FILL })
        if (fillRatio > 0f) {
            canvas.drawRoundRect(RectF(MARGIN, barY, MARGIN + CONTENT_W * fillRatio, barY + barH), barH / 2, barH / 2,
                Paint(Paint.ANTI_ALIAS_FLAG).apply { color = COLOR_PRIMARY_SOFT; style = Paint.Style.FILL })
        }
        return y + 34f
    }

    private fun drawBullet(canvas: Canvas, y: Float, text: String): Float {
        val textX = MARGIN + 18f
        val paint = makePaint(10.5f, COLOR_INK)
        canvas.drawCircle(MARGIN + 6f, y - 2f, 2.5f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = COLOR_PRIMARY_SOFT; style = Paint.Style.FILL })
        val sl = makeStaticLayout(text, (CONTENT_W - 18f).toInt(), paint)
        canvas.save()
        canvas.translate(textX, y - paint.textSize * 1.33f)
        sl.draw(canvas)
        canvas.restore()
        return y + sl.height + 5f
    }

    private fun drawInfoGrid(canvas: Canvas, startY: Float, items: List<Pair<String, String>>): Float {
        val colGap = 12f
        val cardW = (CONTENT_W - colGap) / 2f
        var y = startY
        items.chunked(2).forEach { row ->
            val cardH = 90f
            row.forEachIndexed { index, (label, value) ->
                val left = MARGIN + index * (cardW + colGap)
                drawCard(canvas, left, y, left + cardW, y + cardH, COLOR_WARM_BG, COLOR_BORDER, 12f)
                drawStaticText(canvas, label.uppercase(Locale.getDefault()), left + 12f, y + 4f, (cardW - 24f).toInt(), makePaint(8f, COLOR_INK_SOFT, bold = true))
                val valueLayout = makeStaticLayout(value, (cardW - 24f).toInt(), makePaint(11f, COLOR_PRIMARY, bold = true))
                canvas.save()
                canvas.translate(left + 12f, y + 28f)
                valueLayout.draw(canvas)
                canvas.restore()
            }
            y += cardH + 10f
        }
        return y
    }

    private fun dessineFooter(canvas: Canvas, pageNum: Int, totalPages: Int) {
        val footerY = PAGE_H - MARGIN - 14f
        drawLine(canvas, MARGIN, footerY - 10f, PAGE_W - MARGIN, footerY - 10f, COLOR_BORDER, 0.5f)
        val footerText = t("${appName()}  \u2022  Bilan émotionnel indicatif", "${appName()}  \u2022  Indicative emotional assessment", "${appName()}  •  Unverbindliche emotionale Einschätzung", "${appName()}  •  Evaluación emocional orientativa")
        canvas.drawText(footerText, MARGIN, footerY, makePaint(8f, COLOR_INK_SOFT))
        val pageLabel = t("Page $pageNum / $totalPages", "Page $pageNum / $totalPages", "Seite $pageNum / $totalPages", "Página $pageNum / $totalPages")
        val pagePaint = makePaint(8f, COLOR_INK_SOFT)
        canvas.drawText(pageLabel, PAGE_W - MARGIN - pagePaint.measureText(pageLabel), footerY, pagePaint)
    }

    private fun dessineFooterAvecQr(canvas: Canvas, pageNum: Int, totalPages: Int) {
        val footerTop = QR_FOOTER_TOP
        drawCard(canvas, MARGIN, footerTop, PAGE_W - MARGIN, PAGE_H - MARGIN + 2f, COLOR_WARM_BG, COLOR_BORDER, 14f)
        val qrSize = 62
        val qrBitmap = generateQrCode("https://comprendremonchat.fr", 300)
        val qrLeft = (PAGE_W - MARGIN - qrSize - 14f).toInt()
        val qrTop = (footerTop + 14f).toInt()
        canvas.drawBitmap(qrBitmap, null, Rect(qrLeft, qrTop, qrLeft + qrSize, qrTop + qrSize), null)
        val tx = MARGIN + 14f
        canvas.drawText(
            t("Document g\u00e9n\u00e9r\u00e9 automatiquement", "Automatically generated document", "Automatisch erstelltes Dokument", "Documento generado automáticamente"),
            tx, footerTop + 22f, makePaint(9f, COLOR_INK_SOFT)
        )
        canvas.drawText(
            t("Suivez l\u2019\u00e9volution de votre chat avec l\u2019application.", "Track your cat's progress with the app.", "Verfolgen Sie die Entwicklung Ihrer Katze mit der App.", "Sigue la evolución de tu gato con la aplicación."),
            tx, footerTop + 40f, makePaint(8.5f, COLOR_INK_SOFT)
        )
        val pageLabel = t("Page $pageNum / $totalPages", "Page $pageNum / $totalPages", "Seite $pageNum / $totalPages", "Página $pageNum / $totalPages")
        val pagePaint = makePaint(8f, COLOR_INK_SOFT)
        canvas.drawText(pageLabel, PAGE_W - MARGIN - pagePaint.measureText(pageLabel), PAGE_H - MARGIN - 4f, pagePaint)
    }

    private fun drawStaticText(canvas: Canvas, text: String, x: Float, y: Float, maxWidth: Int, paint: Paint, alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL) {
        val tp = if (paint is TextPaint) paint else TextPaint(paint)
        val sl = makeStaticLayout(text, maxWidth, tp, alignment)
        canvas.save()
        canvas.translate(x, y)
        sl.draw(canvas)
        canvas.restore()
    }

    private fun measureStaticTextHeight(text: String, maxWidth: Int, paint: Paint): Float {
        val tp = if (paint is TextPaint) paint else TextPaint(paint)
        return makeStaticLayout(text, maxWidth, tp).height.toFloat()
    }

    @Suppress("DEPRECATION")
    private fun makeStaticLayout(text: String, maxWidth: Int, paint: Paint, alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL): StaticLayout {
        val tp = if (paint is TextPaint) paint else TextPaint(paint)
        return StaticLayout(text, tp, maxWidth.coerceAtLeast(1), alignment, 1.3f, 0f, false)
    }

    private fun drawCard(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, fillColor: Int, borderColor: Int, radius: Float) {
        val rect = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rect, radius, radius, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = fillColor; style = Paint.Style.FILL })
        canvas.drawRoundRect(rect, radius, radius, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = borderColor; style = Paint.Style.STROKE; strokeWidth = 1f })
    }

    private fun drawRoundRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, radius: Float, color: Int) {
        canvas.drawRoundRect(RectF(left, top, right, bottom), radius, radius, Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color; style = Paint.Style.FILL })
    }

    private fun drawRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, color: Int) {
        canvas.drawRect(left, top, right, bottom, Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color; style = Paint.Style.FILL })
    }

    private fun drawLine(canvas: Canvas, x1: Float, y1: Float, x2: Float, y2: Float, color: Int, width: Float) {
        canvas.drawLine(x1, y1, x2, y2, Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color; strokeWidth = width })
    }

    private fun makePaint(size: Float, color: Int, bold: Boolean = false, italic: Boolean = false): TextPaint {
        return TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = size * 1.33f
            this.color = color
            typeface = when {
                bold && italic -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
                bold -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                italic -> Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
                else -> Typeface.DEFAULT
            }
        }
    }

    private fun couleurPourPriorite(p: PrioriteAction) = when (p) {
        PrioriteAction.FAIBLE -> COLOR_PRIORITE_FAIBLE
        PrioriteAction.MODEREE -> COLOR_PRIORITE_MODEREE
        PrioriteAction.ELEVEE -> COLOR_PRIORITE_ELEVEE
        PrioriteAction.URGENTE -> COLOR_PRIORITE_URGENTE
    }

    private fun libellePourPriorite(p: PrioriteAction) = when (p) {
        PrioriteAction.FAIBLE -> t("Priorit\u00e9 faible", "Low priority", "Geringe Priorität", "Prioridad baja")
        PrioriteAction.MODEREE -> t("\u00c0 surveiller", "Worth monitoring", "Zu beobachten", "A vigilar")
        PrioriteAction.ELEVEE -> t("Vigilance renforc\u00e9e", "Heightened attention", "Erhöhte Aufmerksamkeit", "Vigilancia reforzada")
        PrioriteAction.URGENTE -> t("Action rapide", "Prompt action", "Rasches Handeln", "Acción rápida")
    }

    private fun aideAEnvisager(analyse: ResultatAnalyse) = when {
        analyse.aDejaMordu -> t("V\u00e9t\u00e9rinaire comportemental", "Veterinary behaviorist", "Verhaltenstierarzt", "Veterinario especialista en comportamiento")
        analyse.prioriteAction == PrioriteAction.URGENTE -> t("Professionnel rapidement", "See a professional soon", "Zeitnah Fachperson hinzuziehen", "Profesional pronto")
        analyse.prioriteAction == PrioriteAction.ELEVEE -> t("V\u00e9t\u00e9rinaire comportemental", "Veterinary behaviorist", "Verhaltenstierarzt", "Veterinario especialista en comportamiento")
        analyse.niveauSituation == NiveauSituation.SENSIBLE -> t("V\u00e9t\u00e9rinaire comportemental", "Veterinary behaviorist", "Verhaltenstierarzt", "Veterinario especialista en comportamiento")
        else -> t("V\u00e9t\u00e9rinaire si besoin", "Vet if needed", "Tierarzt bei Bedarf", "Veterinario si hace falta")
    }

    private fun generateQrCode(text: String, size: Int): Bitmap {
        val bitMatrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) COLOR_PRIMARY else COLOR_WHITE)
            }
        }
        return bitmap
    }
}