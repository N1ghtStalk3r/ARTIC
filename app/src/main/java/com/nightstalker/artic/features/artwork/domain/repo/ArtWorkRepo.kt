package com.nightstalker.artic.features.artwork.domain.repo

import com.nightstalker.artic.core.data.model.artwork.ArtworkModel
import com.nightstalker.artic.features.artwork.domain.Artwork

interface ArtWorkRepo {
   suspend fun getArtworkById(id: Int): Artwork


   suspend fun getArtworks(): List<Artwork>
}