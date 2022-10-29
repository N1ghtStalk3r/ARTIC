package com.nightstalker.artic.features

import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkData
import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkModel
import com.nightstalker.artic.core.data.model.artwork.detail.information.ArtworkInformationModel
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionData
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionModel
import com.nightstalker.artic.core.local.ticket.LocalTicket
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.ticket.domain.Ticket

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun ArtworkModel.toArtwork(): Artwork =
    Artwork(id = data.id, title = data.title, imageId = data.imageId, artist = data.artistDisplay)

fun List<ArtworkData>.toListOfArtworks(): List<Artwork> =
    map { Artwork(id = it.id, title = it.title, imageId = it.imageId, artist = it.artistDisplay) }

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
        shortDescription = data.shortDescription
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
            shortDescription = it.shortDescription
        )
    }

fun LocalTicket.toTicket(): Ticket =
    Ticket(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons  = numberOfPersons,
        timestamp = timestamp,
    )

fun List<LocalTicket>.toListOfTickets(): List<Ticket> =
    map {
        Ticket(
            id = it.id,
            title = it.title,
            exhibitionId = it.exhibitionId,
            galleryId = it.galleryId,
            galleryTitle = it.galleryTitle,
            aicEndAt = it.aicEndAt,
            aicStartAt = it.aicStartAt,
            shortDescription = it.shortDescription,
            numberOfPersons  = it.numberOfPersons,
            timestamp = it.timestamp,
        )
}