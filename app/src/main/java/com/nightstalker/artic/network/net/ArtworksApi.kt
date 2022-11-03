package com.nightstalker.artic.network.net

import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkData
import com.nightstalker.artic.core.data.model.artwork.detail.ArtworkModel
import com.nightstalker.artic.core.data.model.artwork.detail.information.ArtworkInformationModel
import com.nightstalker.artic.core.data.model.common.SearchResultsModel
import com.nightstalker.artic.network.ApiConstants.ARTIST_DISPLAY
import com.nightstalker.artic.network.ApiConstants.ID
import com.nightstalker.artic.network.ApiConstants.IMAGE_ID
import com.nightstalker.artic.network.ApiConstants.SOUND_IDS
import com.nightstalker.artic.network.ApiConstants.TITLE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Запросы получения произведений искусства
 *
 * @author Tamerlan Mamukhov on 2022-09-13
 */
const val TYPE = "type"
const val PLACE = "place"

interface ArtworksApi {
    @GET("artworks/{$ID}?fields=$ID,$TITLE,$IMAGE_ID,$ARTIST_DISPLAY,$SOUND_IDS")
    suspend fun getArtworkById(@Path(ID) id: Int): ArtworkModel

    @GET("artworks?fields=$ID,$TITLE,$IMAGE_ID,$ARTIST_DISPLAY")
    suspend fun getArtworks(): SearchResultsModel<ArtworkData>

    @GET("artworks/{$ID}/manifest.json")
    suspend fun getArtworkInformation(@Path(ID) id: Int): ArtworkInformationModel

    @GET("artworks/search")
    suspend fun getArtworksByQuery(@Query(value = "q", encoded = true) search: String): SearchResultsModel<ArtworkData>

    @GET("/search?query[match][artwork_type_title]={$TYPE}")
    suspend fun getArtworksByKind(@Path(TYPE) type: String): SearchResultsModel<ArtworkData>

    @GET("/search?query[match][place_of_origin]={$PLACE}")
    suspend fun getArtworksByPlace(@Path(PLACE) type: String): SearchResultsModel<ArtworkData>
}