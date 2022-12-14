package com.nightstalker.artic.features.artwork.data.api

import com.nightstalker.artic.core.data.model.artwork.ArtworkData
import com.nightstalker.artic.core.data.model.artwork.information.ArtworkInformationModel
import com.nightstalker.artic.core.data.model.common.ItemsListResultModel
import com.nightstalker.artic.core.data.model.common.SingeItemResultModel
import com.nightstalker.artic.network.ApiConstants.ARTIST_DISPLAY
import com.nightstalker.artic.network.ApiConstants.ID
import com.nightstalker.artic.network.ApiConstants.IMAGE_ID
import com.nightstalker.artic.network.ApiConstants.PARAMS
import com.nightstalker.artic.network.ApiConstants.PLACE_OF_ORIGIN
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
interface ArtworksApi {
    @GET("artworks/{$ID}?fields=$ID,$TITLE,$IMAGE_ID,$ARTIST_DISPLAY,$SOUND_IDS,$PLACE_OF_ORIGIN")
    suspend fun getArtworkById(@Path(ID) id: Int): SingeItemResultModel<ArtworkData>

    @GET("artworks?fields=$ID,$TITLE,$IMAGE_ID,$ARTIST_DISPLAY")
    suspend fun getArtworks(): ItemsListResultModel<ArtworkData>

    @GET("artworks/{$ID}/manifest.json")
    suspend fun getArtworkInformation(@Path(ID) id: Int): ArtworkInformationModel

    @GET("artworks/search?")
    suspend fun getArtworksByQuery(
        @Query(PARAMS) search: String,
    ): ItemsListResultModel<ArtworkData>
}