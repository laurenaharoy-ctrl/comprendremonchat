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

    private fun t(fr: String, en: String) = if (isEnglish()) en else fr
    private fun appName() = t("Comprendre mon chat", "Understanding My Cat")
    private fun totalPagesActuel(): Int = if (showConsultation()) 5 else 4

    fun exporterBilanPdf(context: Context, nomChat: String, analyse: ResultatAnalyse): File {
        val document = PdfDocument()
        val nom = nomChatAffiche(nomChat)
        val date = if (isEnglish())
            SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(Date())
        else
            SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(Date())
        val couleurPriorite = couleurPourPriorite(analyse.prioriteAction)
        val libellePriorite = libellePourPriorite(analyse.prioriteAction)

        dessinePage1(document, nom, date, analyse, couleurPriorite, libellePriorite)
        dessinePage2(document, nom, analyse)
        dessinePage3(document, nom, analyse)
        dessinePage4(document, nom, analyse, couleurPriorite)
        if (showConsultation()) {
            dessinePage5Consultation(document)
        }

        val nomFichierSafe = nom.lowercase(Locale.getDefault())
            .replace("\\s+".toRegex(), "_")
            .replace("[^a-z0-9_]+".toRegex(), "")

        val prefix = t("bilan", "report")
        val file = File(context.cacheDir, "${prefix}_${nomFichierSafe.ifBlank { t("chat", "cat") }}.pdf")
        FileOutputStream(file).use { document.writeTo(it) }
        document.close()
        return file
    }

    private fun dessinePage1(
        document: PdfDocument, nom: String, date: String,
        analyse: ResultatAnalyse, couleurPriorite: Int, libellePriorite: String
    ) {
        val page = demarrerPage(document, 1)
        val canvas = page.canvas
        var y: Float

        drawRect(canvas, 0f, 0f, PAGE_W.toFloat(), 190f, COLOR_WARM_BG)
        drawLine(canvas, MARGIN, 26f, PAGE_W - MARGIN, 26f, COLOR_ACCENT, 0.8f)

        val appTitlePaint = makePaint(11f, COLOR_INK_SOFT, italic = true)
        val appTitle = appName()
        canvas.drawText(appTitle, (PAGE_W - appTitlePaint.measureText(appTitle)) / 2f, 44f, appTitlePaint)

        val nomPaint = makePaint(34f, COLOR_PRIMARY, bold = true)
        canvas.drawText(nom, (PAGE_W - nomPaint.measureText(nom)) / 2f, 96f, nomPaint)

        val sousTitrePaint = makePaint(12f, COLOR_INK_SOFT, italic = true)
        val sousTitre = t("Bilan émotionnel", "Emotional report")
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

        y = 210f
        drawLine(canvas, MARGIN, y, PAGE_W - MARGIN, y, COLOR_BORDER, 0.5f)
        y += 24f

        drawSectionTitle(canvas, y, t("En un coup d\u2019\u0153il", "At a glance"))
        y += 48f

        val gridItems = listOf(
            t("Axe principal", "Main axis") to libelleAxe(analyse.problemePrincipal),
            t("Situation", "Situation") to texteNiveauSituation(analyse.niveauSituation),
            t("Besoin principal", "Main need") to besoinPrincipal(analyse.problemePrincipal)
                .removePrefix("Besoin principal : ")
                .removePrefix("Main need: ")
                .removeSuffix("."),
            t("Aide à envisager", "Support to consider") to aideAEnvisager(analyse)
        )
        y = drawInfoGrid(canvas, y, gridItems)
        y += 20f

        val situationH = measureStaticTextHeight(analyse.messageSituation, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_INK)) + 32f
        if (y + situationH < CONTENT_BOTTOM) {
            drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + situationH, COLOR_WARM_BG_ALT, COLOR_BORDER, 14f)
            drawStaticText(canvas, analyse.messageSituation, MARGIN + 16f, y + 16f, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_INK))
        }

        dessineFooter(canvas, 1)
        document.finishPage(page)
    }

    private fun dessinePage2(document: PdfDocument, nom: String, analyse: ResultatAnalyse) {
        val page = demarrerPage(document, 2)
        val canvas = page.canvas
        var y = MARGIN

        drawPageHeader(canvas, t("Profil de $nom", "Profile of $nom"))
        y += 48f

        drawSectionTitle(canvas, y, t("Les 4 dimensions", "The 4 dimensions"))
        y += 48f

        val axes = listOf(
            Triple(t("Sécurité émotionnelle", "Emotional security"), analyse.niveauPeur, analyse.peur),
            Triple(t("Lien humain", "Human bond"), analyse.niveauAttachement, analyse.attachement),
            Triple(t("Instincts", "Instincts"), analyse.niveauImpulsivite, analyse.impulsivite),
            Triple(t("Cohabitation", "Cohabitation"), analyse.niveauReactivite, analyse.reactivite)
        )
        axes.forEach { (label, niveau, score) ->
            y = drawAxeBar(canvas, y, label, niveau, score)
            y += 12f
        }
        y += 14f

        if (y < CONTENT_BOTTOM - 60f) {
            drawSectionTitle(canvas, y, t("Hypoth\u00e8se de lecture", "Main hypothesis"))
            y += 48f
            val hypotheseH = measureStaticTextHeight(analyse.hypothesePrincipale, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_INK)) + 32f
            if (y + hypotheseH < CONTENT_BOTTOM) {
                drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + hypotheseH, COLOR_WHITE, COLOR_ACCENT, 14f)
                drawStaticText(canvas, analyse.hypothesePrincipale, MARGIN + 16f, y + 16f, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_INK))
                y += hypotheseH + 24f
            }
        }

        if (y < CONTENT_BOTTOM - 60f) {
            drawSectionTitle(canvas, y, t("Ce qui se passe probablement", "What is probably happening"))
            y += 48f
            val explicationH = measureStaticTextHeight(analyse.explicationPrincipale, CONTENT_W.toInt(), makePaint(11f, COLOR_INK))
            if (y + explicationH < CONTENT_BOTTOM) {
                drawStaticText(canvas, analyse.explicationPrincipale, MARGIN, y, CONTENT_W.toInt(), makePaint(11f, COLOR_INK))
                y += explicationH + 24f
            }
        }

        if (analyse.marquageHabitudePostSterilisation && y < CONTENT_BOTTOM - 40f) {
            val texteHabitude = t(
                "Ce marquage semble avoir débuté pendant les chaleurs, avant la stérilisation, et s'est transformé depuis en habitude acquise. La cause hormonale a disparu, mais le geste reste ancré — ce type de marquage devenu habituel est souvent plus long à corriger qu'un marquage lié au stress.",
                "This marking seems to have started during heat periods, before spaying, and has since turned into a learned habit. The hormonal cause is gone, but the gesture remains ingrained — this type of habitual marking is often longer to correct than stress-related marking."
            )
            val habitudeH = measureStaticTextHeight(texteHabitude, (CONTENT_W - 32f).toInt(), makePaint(10.5f, COLOR_PRIMARY_SOFT, bold = true)) + 32f
            if (y + habitudeH < CONTENT_BOTTOM) {
                drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + habitudeH, COLOR_WARM_BG_ALT, COLOR_PRIMARY_SOFT, 14f)
                drawStaticText(canvas, texteHabitude, MARGIN + 16f, y + 16f, (CONTENT_W - 32f).toInt(), makePaint(10.5f, COLOR_PRIMARY_SOFT, bold = true))
            }
        }

        dessineFooter(canvas, 2)
        document.finishPage(page)
    }

    private fun dessinePage3(document: PdfDocument, nom: String, analyse: ResultatAnalyse) {
        val page = demarrerPage(document, 3)
        val canvas = page.canvas
        var y = MARGIN

        drawPageHeader(canvas, t("Plan d\u2019action pour $nom", "Action plan for $nom"))
        y += 48f

        drawSectionTitle(canvas, y, t("Premier levier utile", "First useful lever"))
        y += 48f

        val levierH = measureStaticTextHeight(analyse.conseilPrincipal, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_INK)) + 32f
        if (y + levierH < CONTENT_BOTTOM) {
            drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + levierH, COLOR_WARM_BG_ALT, COLOR_ACCENT, 14f)
            drawStaticText(canvas, analyse.conseilPrincipal, MARGIN + 16f, y + 16f, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_INK))
            y += levierH + 24f
        }

        if (y < CONTENT_BOTTOM - 60f) {
            drawSectionTitle(canvas, y, t("Les prochains jours", "The next few days"))
            y += 48f

            if (y < CONTENT_BOTTOM - 20f) {
                canvas.drawText(t("\u00c0 faire", "To do"), MARGIN, y, makePaint(11f, COLOR_PRIMARY, bold = true))
                y += 20f
                analyse.planAction.aFaire.forEach { if (y < CONTENT_BOTTOM - 20f) y = drawBullet(canvas, y, it) }
                y += 12f
            }
            if (y < CONTENT_BOTTOM - 20f) {
                canvas.drawText(t("\u00c0 \u00e9viter", "To avoid"), MARGIN, y, makePaint(11f, COLOR_PRIMARY, bold = true))
                y += 20f
                analyse.planAction.aEviter.forEach { if (y < CONTENT_BOTTOM - 20f) y = drawBullet(canvas, y, it) }
                y += 12f
            }
            if (y < CONTENT_BOTTOM - 20f) {
                canvas.drawText(t("\u00c0 observer", "To observe"), MARGIN, y, makePaint(11f, COLOR_PRIMARY, bold = true))
                y += 20f
                analyse.planAction.aObserver.forEach { if (y < CONTENT_BOTTOM - 20f) y = drawBullet(canvas, y, it) }
                y += 18f
            }
        }

        if (analyse.aDejaMordu && y < CONTENT_BOTTOM - 40f) {
            val morsuText = if (analyse.cibleAgressionAnimal) t(
                "Une griffure ou morsure envers un autre animal a \u00e9t\u00e9 signal\u00e9e. Un accompagnement par un comportementaliste f\u00e9lin est recommand\u00e9 pour reprendre la cohabitation de fa\u00e7on progressive et s\u00e9curis\u00e9e.",
                "A scratch or bite toward another animal has been reported. Support from a feline behaviorist is recommended to rebuild cohabitation gradually and safely."
            ) else t(
                "Une griffure ou morsure envers une personne a \u00e9t\u00e9 signal\u00e9e. Un accompagnement v\u00e9t\u00e9rinaire comportemental est recommand\u00e9 pour \u00e9valuer la situation.",
                "A scratch or bite toward a person has been reported. Support from a veterinary behaviorist is recommended to assess the situation."
            )
            val morsuH = measureStaticTextHeight(morsuText, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_MORSURE_TEXTE, bold = true)) + 32f
            if (y + morsuH < CONTENT_BOTTOM) {
                drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + morsuH, COLOR_MORSURE_BG, COLOR_MORSURE_TEXTE, 14f)
                drawStaticText(canvas, morsuText, MARGIN + 16f, y + 16f, (CONTENT_W - 32f).toInt(), makePaint(11f, COLOR_MORSURE_TEXTE, bold = true))
            }
        }

        dessineFooter(canvas, 3)
        document.finishPage(page)
    }

    private fun dessinePage4(document: PdfDocument, nom: String, analyse: ResultatAnalyse, couleurPriorite: Int) {
        val page = demarrerPage(document, 4)
        val canvas = page.canvas
        var y = MARGIN

        drawPageHeader(canvas, t("\u00c0 retenir", "Key takeaways"))
        y += 48f

        val recapText = if (isEnglish()) buildString {
            append("$nom primarily presents a ${analyse.profil.profilType.lowercase(Locale.ENGLISH)} profile.\n\n")
            append("Situation: ${texteNiveauSituation(analyse.niveauSituation).lowercase(Locale.ENGLISH)}.\n\n")
            append("Main axis: ${libelleAxe(analyse.problemePrincipal).lowercase(Locale.ENGLISH)}.\n\n")
            append(besoinPrincipal(analyse.problemePrincipal))
        } else buildString {
            append("$nom pr\u00e9sente surtout un profil ${analyse.profil.profilType.lowercase(Locale.FRENCH)}.\n\n")
            append("Situation\u00a0: ${texteNiveauSituation(analyse.niveauSituation).lowercase(Locale.FRENCH)}.\n\n")
            append("Axe principal\u00a0: ${libelleAxe(analyse.problemePrincipal).lowercase(Locale.FRENCH)}.\n\n")
            append(besoinPrincipal(analyse.problemePrincipal))
        }

        val recapH = measureStaticTextHeight(recapText, (CONTENT_W - 48f).toInt(), makePaint(12f, COLOR_INK)) + 48f
        drawCard(canvas, MARGIN, y, PAGE_W - MARGIN, y + recapH, COLOR_WARM_BG, COLOR_BORDER, 16f)
        drawRoundRect(canvas, MARGIN, y, MARGIN + 5f, y + recapH, 16f, couleurPriorite)
        drawStaticText(canvas, recapText, MARGIN + 24f, y + 24f, (CONTENT_W - 48f).toInt(), makePaint(12f, COLOR_INK))
        y += recapH + 32f

        if (y < CONTENT_BOTTOM - 60f) {
            drawSectionTitle(canvas, y, t("Conclusion", "Conclusion"))
            y += 48f
            val conclusion = if (isEnglish())
                "The goal is not to label $nom, but to better understand what is happening and move forward in a more adapted way."
            else
                "L\u2019objectif n\u2019est pas d\u2019\u00e9tiqueter $nom, mais d\u2019aider \u00e0 mieux lire ce qui se passe et \u00e0 avancer de mani\u00e8re plus adapt\u00e9e."
            val conclusionH = measureStaticTextHeight(conclusion, CONTENT_W.toInt(), makePaint(11f, COLOR_INK))
            if (y + conclusionH < CONTENT_BOTTOM) {
                drawStaticText(canvas, conclusion, MARGIN, y, CONTENT_W.toInt(), makePaint(11f, COLOR_INK))
                y += conclusionH + 32f
            }
        }

        if (y < CONTENT_BOTTOM - 40f) {
            drawLine(canvas, MARGIN, y, PAGE_W - MARGIN, y, COLOR_BORDER, 0.5f)
            y += 16f
            val disclaimer = if (isEnglish())
                "This report is indicative. It does not replace the advice of a veterinarian or feline behaviorist. It can serve as a basis for discussion during a consultation."
            else
                "Ce bilan est indicatif. Il ne remplace pas l\u2019avis d\u2019un v\u00e9t\u00e9rinaire ni d\u2019un comportementaliste f\u00e9lin. Il peut servir de base de discussion lors d\u2019une consultation."
            val disclaimerH = measureStaticTextHeight(disclaimer, CONTENT_W.toInt(), makePaint(9.5f, COLOR_INK_SOFT))
            if (y + disclaimerH < CONTENT_BOTTOM) {
                drawStaticText(canvas, disclaimer, MARGIN, y, CONTENT_W.toInt(), makePaint(9.5f, COLOR_INK_SOFT))
            }
        }

        dessineFooterAvecQr(canvas)
        document.finishPage(page)
    }

    // ════════════════════════════════════════════════════════════
    // PAGE 5 — Consultation personnalisée (FR uniquement, page dédiée)
    // ════════════════════════════════════════════════════════════
    private fun dessinePage5Consultation(document: PdfDocument) {
        val page = demarrerPage(document, 5)
        val canvas = page.canvas
        val y = MARGIN + 60f

        drawPageHeader(canvas, strConsultationTitre())

        val titre = strConsultationTitre()
        val sousTitre = strConsultationSousTitre()
        val description = strConsultationDescription()
        val disclaimer = strConsultationDisclaimer()
        val prix = strConsultationPrix()
        val bouton = strConsultationBouton()
        val url = CONSULTATION_BOOKING_URL

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

        dessineFooter(canvas, 5)
        document.finishPage(page)
    }

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

    private fun dessineFooter(canvas: Canvas, pageNum: Int) {
        val footerY = PAGE_H - MARGIN - 14f
        drawLine(canvas, MARGIN, footerY - 10f, PAGE_W - MARGIN, footerY - 10f, COLOR_BORDER, 0.5f)
        val footerText = t(
            "${appName()}  \u2022  Bilan émotionnel indicatif",
            "${appName()}  \u2022  Indicative emotional report"
        )
        canvas.drawText(footerText, MARGIN, footerY, makePaint(8f, COLOR_INK_SOFT))
        val total = totalPagesActuel()
        val pageLabel = t("Page $pageNum / $total", "Page $pageNum / $total")
        val pagePaint = makePaint(8f, COLOR_INK_SOFT)
        canvas.drawText(pageLabel, PAGE_W - MARGIN - pagePaint.measureText(pageLabel), footerY, pagePaint)
    }

    private fun dessineFooterAvecQr(canvas: Canvas) {
        val footerTop = PAGE_H - MARGIN - 90f
        drawCard(canvas, MARGIN, footerTop, PAGE_W - MARGIN, PAGE_H - MARGIN + 2f, COLOR_WARM_BG, COLOR_BORDER, 14f)
        val qrSize = 62
        val qrBitmap = generateQrCode("https://comprendremonchat.fr", 300)
        val qrLeft = (PAGE_W - MARGIN - qrSize - 14f).toInt()
        val qrTop = (footerTop + 14f).toInt()
        canvas.drawBitmap(qrBitmap, null, Rect(qrLeft, qrTop, qrLeft + qrSize, qrTop + qrSize), null)
        val tx = MARGIN + 14f
        canvas.drawText(
            t("Document g\u00e9n\u00e9r\u00e9 automatiquement", "Automatically generated document"),
            tx, footerTop + 22f, makePaint(9f, COLOR_INK_SOFT)
        )
        canvas.drawText(
            t(
                "Suivez l\u2019\u00e9volution de votre chat avec l\u2019application.",
                "Track your cat's progress with the app."
            ),
            tx, footerTop + 40f, makePaint(8.5f, COLOR_INK_SOFT)
        )
        val total = totalPagesActuel()
        val pageLabel = t("Page 4 / $total", "Page 4 / $total")
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
        PrioriteAction.FAIBLE -> t("Priorit\u00e9 faible", "Low priority")
        PrioriteAction.MODEREE -> t("\u00c0 surveiller", "To monitor")
        PrioriteAction.ELEVEE -> t("Vigilance renforc\u00e9e", "Increased vigilance")
        PrioriteAction.URGENTE -> t("Action rapide", "Prompt action")
    }

    private fun aideAEnvisager(analyse: ResultatAnalyse) = when {
        analyse.aDejaMordu -> t("V\u00e9t\u00e9rinaire comportemental", "Veterinary behaviorist")
        analyse.prioriteAction == PrioriteAction.URGENTE -> t("Professionnel rapidement", "Professional promptly")
        analyse.prioriteAction == PrioriteAction.ELEVEE -> t("V\u00e9t\u00e9rinaire comportemental", "Veterinary behaviorist")
        analyse.niveauSituation == NiveauSituation.SENSIBLE -> t("V\u00e9t\u00e9rinaire comportemental", "Veterinary behaviorist")
        else -> t("V\u00e9t\u00e9rinaire si besoin", "Vet if needed")
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