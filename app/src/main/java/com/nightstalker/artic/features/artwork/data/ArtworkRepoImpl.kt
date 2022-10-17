package com.nightstalker.artic.features.artwork.data

import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkManifest
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo
import com.nightstalker.artic.network.ArtworksApiMapper

class ArtworkRepoImpl(
    private val apiMapper: ArtworksApiMapper
) : ArtworkRepo {
    override suspend fun getArtworkById(id: Int): Artwork =
        apiMapper.getArtWorkById(id)

    override suspend fun getArtworks(): List<Artwork> = apiMapper.getArtworks()

    override suspend fun getArtworkManifest(id: Int): ArtworkManifest = apiMapper.getArtworkManifest(id)

    override suspend fun getArtworksByQuery(search: String): List<Artwork> = apiMapper.getArtworksByQuery(search)
}