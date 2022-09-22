package com.nightstalker.artic.features.artwork.data

import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.domain.repo.ArtWorkRepo
import com.nightstalker.artic.network.ArtworksApiMapper

class ArtWorkRepoImpl(
    private val apiMapper: ArtworksApiMapper
) : ArtWorkRepo {
    override suspend fun getArtworkById(id: Int): Artwork =
        apiMapper.getArtWorkById(id)

    override suspend fun getArtworks(): List<Artwork> = apiMapper.getArtworks()
}