package com.nightstalker.artic.features

import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkData
import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkModel
import com.nightstalker.artic.core.data.model.artwork.detail.manifest.ArtworkManifestModel
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionData
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionModel
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.domain.ArtworkManifest
import com.nightstalker.artic.features.exhibition.domain.Exhibition

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun ArtworkModel.toArtwork(): Artwork =
    Artwork(id = data.id, title = data.title, imageId = data.imageId, artist = data.artistDisplay)

fun List<ArtworkData>.toListOfArtworks(): List<Artwork> =
    map { Artwork(id = it.id, title = it.title, imageId = it.imageId, artist = it.artistDisplay) }

fun ArtworkManifestModel.toArtworkManifest(): ArtworkManifest =
    ArtworkManifest(
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
