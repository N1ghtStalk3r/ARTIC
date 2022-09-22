package com.nightstalker.artic.features.artwork.data.mappers

import com.nightstalker.artic.core.data.model.artwork.ArtworkModel
import com.nightstalker.artic.core.data.model.artwork.Data
import com.nightstalker.artic.core.data.model.exhibition.ExhibitionModel
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.exhibition.domain.Exhibition

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */

fun ArtworkModel.toDomain(): Artwork =
    Artwork(id = data.id, title = data.title, imageId = data.imageId)

fun List<Data>.toDomain(): List<Artwork> =
    map { Artwork(id = it.id, title = it.title, imageId = it.imageId) }

fun ExhibitionModel.toDomain(): Exhibition =
    Exhibition(
        imageUrl = data.imageUrl,
        galleryTitle = data.galleryTitle,
        title = data.title,
        altImageIds = listOf(),
        status = data.status,
//        shortDescription = data.shortDescription.toString()
        shortDescription = ""
    )