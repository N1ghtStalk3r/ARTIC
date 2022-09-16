package com.nightstalker.artic.features.artwork.data.mappers

import com.nightstalker.artic.core.data.model.artwork.ArtworkDefaultModel
import com.nightstalker.artic.core.data.model.exhibition.ExhibitionModel
import com.nightstalker.artic.core.domain.model.ArtworkDefault
import com.nightstalker.artic.features.exhibition.domain.Exhibition

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */

fun ArtworkDefaultModel.toDomain(): ArtworkDefault =
    ArtworkDefault(id = data.id, title = data.title, imageId = data.imageId)

fun ExhibitionModel.toDomain(): Exhibition =
    Exhibition(
        imageUrl = data.imageUrl,
        galleryTitle = data.galleryTitle,
        title = data.title,
        altImageIds = listOf(),
        status = data.status,
        shortDescription = data.shortDescription.toString()
    )