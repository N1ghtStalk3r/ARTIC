package com.nightstalker.artic.features.artwork.domain.usecase

import com.nightstalker.artic.core.domain.ResultState
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkManifest
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo

/**
 *
 * Юз кейс для получения результата с данными экспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-10-16
 */
class ArtworksUseCase(
    private val repo: ArtworkRepo,
) {
    suspend fun getArtworkById(id: Int): ResultState<Artwork> = safeCall { repo.getArtworkById(id) }
    suspend fun getArtworks(): ResultState<List<Artwork>> = safeCall { repo.getArtworks() }
    suspend fun getArtworkManifest(id: Int): ResultState<ArtworkManifest> = safeCall { repo.getArtworkManifest(id) }
    suspend fun getArtworksByQuery(query: String): ResultState<List<Artwork>> =
        safeCall { repo.getArtworksByQuery(query) }
}