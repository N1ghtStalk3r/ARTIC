package com.nightstalker.artic.features

import com.nightstalker.artic.features.ApiConstants.ARTIC_LOCATION
import com.nightstalker.artic.features.ApiConstants.ARTIC_TITLE
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_ALLDAY
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_BEGIN
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_CALENDAR_RRULE
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_DESCRIPTION
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_END
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_LOCATION
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_RULE
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_TITLE
import com.nightstalker.artic.features.ApiConstants.USER_FORMAT_DATE
import com.nightstalker.artic.features.artwork.data.model.ArtworkData
import com.nightstalker.artic.features.artwork.data.model.information.ArtworkInformationModel
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.audio.data.model.MobileSoundData
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.exhibition.data.model.ExhibitionData
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.ticket.data.room.LocalTicketDbEntity
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
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
fun ArtworkData.toArtwork(): Artwork =
    Artwork(
        id = id,
        title = title,
        imageId = imageId,
        artist = artistDisplay,
        audioUrl = soundIds.firstOrNull(),
        placeOfOrigin = placeOfOrigin
    )

fun List<ArtworkData>.toListOfArtworks(): List<Artwork> =
    map {
        Artwork(
            id = it.id,
            title = it.title,
            imageId = it.imageId,
            artist = it.artistDisplay,
            audioUrl = it.objectSelectorNumber,
            placeOfOrigin = it.placeOfOrigin
        )
    }

fun ArtworkInformationModel.toArtworkInformation(): ArtworkInformation =
    ArtworkInformation(
        description = description.first()?.value
    )

fun ExhibitionData.toExhibition(): Exhibition =
    Exhibition(
        id = id,
        imageUrl = imageUrl,
        galleryTitle = galleryTitle,
        title = title,
        altImageIds = listOf(),
        status = status,
        shortDescription = shortDescription,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
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

fun Exhibition.toExhibitionTicket(): ExhibitionTicket =
    ExhibitionTicket(
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

fun ExhibitionTicket.toLocalTicket(): LocalTicketDbEntity =
    LocalTicketDbEntity(
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

fun ExhibitionTicket.toCalendarEvent(): Map<String, String> = mapOf(
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

fun LocalTicketDbEntity.toExhibitionTicket(): ExhibitionTicket =
    ExhibitionTicket(
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

fun List<LocalTicketDbEntity>.toListOfTicketUseCase(): List<ExhibitionTicket> =
    map {
        ExhibitionTicket(
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

fun MobileSoundData.toAudioFileModel(): AudioFileModel =
    AudioFileModel(title, webUrl, transcript)