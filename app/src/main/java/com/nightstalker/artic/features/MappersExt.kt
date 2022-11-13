package com.nightstalker.artic.features


import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.R
import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkData
import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkModel
import com.nightstalker.artic.core.data.model.artwork.detail.information.ArtworkInformationModel
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionData
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionModel
import com.nightstalker.artic.core.local.ticket.LocalTicket
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.ticket.domain.TicketUseCase
import com.nightstalker.artic.network.ApiConstants.ARTIC_LOCATION
import com.nightstalker.artic.network.ApiConstants.ARTIC_TITLE
import com.nightstalker.artic.network.ApiConstants.EVENT_ALLDAY
import com.nightstalker.artic.network.ApiConstants.EVENT_BEGIN
import com.nightstalker.artic.network.ApiConstants.EVENT_CALENDAR_RRULE
import com.nightstalker.artic.network.ApiConstants.EVENT_DESCRIPTION
import com.nightstalker.artic.network.ApiConstants.EVENT_END
import com.nightstalker.artic.network.ApiConstants.EVENT_LOCATION
import com.nightstalker.artic.network.ApiConstants.EVENT_RULE
import com.nightstalker.artic.network.ApiConstants.EVENT_TITLE
import com.nightstalker.artic.network.ApiConstants.USER_FORMAT_DATE
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun ArtworkModel.toArtwork(): Artwork =
    Artwork(
        id = data.id,
        title = data.title,
        imageId = data.imageId,
        artist = data.artistDisplay,
        audioUrl = data.soundIds.firstOrNull()
    )

fun List<ArtworkData>.toListOfArtworks(): List<Artwork> =
    map {
        Artwork(
            id = it.id,
            title = it.title,
            imageId = it.imageId,
            artist = it.artistDisplay,
            audioUrl = it.objectSelectorNumber
        )
    }

fun ArtworkInformationModel.toArtworkInformation(): ArtworkInformation =
    ArtworkInformation(
        description = description.first()?.value
    )

fun ExhibitionModel.toExhibition(): Exhibition =
    Exhibition(
        id = data.id,
        imageUrl = data.imageUrl,
        galleryTitle = data.galleryTitle,
        title = data.title,
        altImageIds = listOf(),
        status = data.status,
        shortDescription = data.shortDescription,
        aicEndAt = data.aicEndAt,
        aicStartAt = data.aicStartAt,
    )

fun List<ExhibitionData>.toListOfExhibitions(): List<Exhibition> =
    map {
        Exhibition(
            id = it.id,
            imageUrl = it.imageUrl,
            galleryTitle = it.galleryTitle,
            title = it.title,
            altImageIds = listOf(),
            status = it.status,
            shortDescription = it.shortDescription,
            aicEndAt = it.aicEndAt,
            aicStartAt = it.aicStartAt,
        )
    }

fun Exhibition.toTicketUseCase(): TicketUseCase =
    TicketUseCase(
        title = title ?: "",
        exhibitionId = id.toString(),
        imageUrl = imageUrl ?: "",
        galleryTitle = galleryTitle ?: "",
        shortDescription = shortDescription ?: "",
        numberOfPersons = 1,
        aicEndAt = aicEndAt ?: "",
        aicStartAt = aicStartAt ?: "",
        timestamp = Date().time
    )


fun TicketUseCase.toLocalTicket(): LocalTicket =
    LocalTicket(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        imageUrl = imageUrl,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons = numberOfPersons,
        timestamp = timestamp,
    )

fun String.toCalendarInMillis(): Long {
    if (this.isEmpty()) return Date().time
    val localDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    val zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
    val planDate = Date.from(zdt.toInstant()).time
    return planDate
}

// Время нового события в календаре может быть только в будующем
fun Long.normalizeEventDateTime(): Long =
    if (this < Date().time) Date().time else this

// Перевод даты ISO 8601 в заданной формат
fun String.reformatIso8601(): String = SimpleDateFormat(USER_FORMAT_DATE, Locale.getDefault())
    .format(
        this.toCalendarInMillis()
    )

fun TicketUseCase.toCalendarEvent(): Map<String, String> = mapOf(
    EVENT_BEGIN to aicStartAt
        .toCalendarInMillis()
        .normalizeEventDateTime()
        .toString(),
    EVENT_ALLDAY to "false",
    EVENT_RULE to EVENT_CALENDAR_RRULE,
    EVENT_END to aicEndAt
        .toCalendarInMillis()
        .normalizeEventDateTime()
        .toString(),
    EVENT_TITLE to "Exhibition: ${title}",
    EVENT_DESCRIPTION to "Place:  ${galleryTitle} of $ARTIC_TITLE",
    EVENT_LOCATION to ARTIC_LOCATION,
)


fun LocalTicket.toTicketUseCase(): TicketUseCase =
    TicketUseCase(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        imageUrl = imageUrl,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons = numberOfPersons,
        timestamp = timestamp,
    )

fun List<LocalTicket>.toListOfTicketUseCase(): List<TicketUseCase> =
    map {
        TicketUseCase(
            id = it.id,
            title = it.title,
            exhibitionId = it.exhibitionId,
            imageUrl = it.imageUrl,
            galleryId = it.galleryId,
            galleryTitle = it.galleryTitle,
            aicEndAt = it.aicEndAt,
            aicStartAt = it.aicStartAt,
            shortDescription = it.shortDescription,
            numberOfPersons = it.numberOfPersons,
            timestamp = it.timestamp,
        )
    }