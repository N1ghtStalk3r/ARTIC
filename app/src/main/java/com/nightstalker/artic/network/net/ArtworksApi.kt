package com.nightstalker.artic.network.net

import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkModel
import com.nightstalker.artic.core.data.model.artwork.detail.manifest.ArtworkManifestModel
import com.nightstalker.artic.core.data.model.artwork.list.ArtworksListResult
import com.nightstalker.artic.network.ApiConstants.ARTIST_DISPLAY
import com.nightstalker.artic.network.ApiConstants.ID
import com.nightstalker.artic.network.ApiConstants.IMAGE_ID
import com.nightstalker.artic.network.ApiConstants.TITLE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Запросы получения произведений искусства
 *
 * @author Tamerlan Mamukhov on 2022-09-13
 */
interface ArtworksApi {
    @GET("artworks/{$ID}?fields=$ID,$TITLE,$IMAGE_ID,$ARTIST_DISPLAY")
    suspend fun getArtworkById(@Path(ID) id: Int): ArtworkModel

    @GET("artworks?fields=$ID,$TITLE,$IMAGE_ID,$ARTIST_DISPLAY")
    suspend fun getArtworks(): ArtworksListResult

    @GET("artworks/{$ID}/manifest.json")
    suspend fun getArtworkManifest(@Path(ID) id: Int): ArtworkManifestModel

    @GET("artworks/search")
    suspend fun getArtworksByQuery(@Query(value = "q", encoded = false) search: String): ArtworksListResult
}