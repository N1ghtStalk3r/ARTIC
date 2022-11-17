package com.nightstalker.artic.network

import com.nightstalker.artic.core.network.BaseApiMapper
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.toArtwork
import com.nightstalker.artic.features.toArtworkInformation
import com.nightstalker.artic.features.toListOfArtworks
import com.nightstalker.artic.network.net.ArtworksApi

/**
 * Класс получения данных с помощью [ArtworksApi]
 *
 * @property api    API
 * @author Tamerlan Mamukhov on 2022-09-16
 */
class ArtworksApiMapper(private val api: ArtworksApi) : BaseApiMapper() {
    suspend fun getArtWorkById(id: Int): Artwork = api.getArtworkById(id).toArtwork()

    suspend fun getArtworks(): List<Artwork> = api.getArtworks().data.toListOfArtworks()

    suspend fun getArtworkInformation(id: Int): ArtworkInformation =
        api.getArtworkInformation(id).toArtworkInformation()

    suspend fun getArtworksByQuery(search: String): List<Artwork> =
        api.getArtworksByQuery(search).data.toListOfArtworks()

    suspend fun getArtworksByKind(type: String): List<Artwork> =
        api.getArtworksByKind(type).data.toListOfArtworks()

    suspend fun getArtworksByPlace(type: String): List<Artwork> =
        api.getArtworksByPlace(type).data.toListOfArtworks()
}