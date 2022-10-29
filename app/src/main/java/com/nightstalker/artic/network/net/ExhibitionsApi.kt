package com.nightstalker.artic.network.net

import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionModel
import com.nightstalker.artic.core.data.model.exhibition.list.ExhibitionsListResult
import com.nightstalker.artic.network.ApiConstants.ALT_IMAGE_IDS
import com.nightstalker.artic.network.ApiConstants.GALLERY_TITLE
import com.nightstalker.artic.network.ApiConstants.ID
import com.nightstalker.artic.network.ApiConstants.IMAGE_URL
import com.nightstalker.artic.network.ApiConstants.SHORT_DESCRIPTION
import com.nightstalker.artic.network.ApiConstants.STATUS
import com.nightstalker.artic.network.ApiConstants.TITLE
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Запросы получения выставок
 *
 * @author Tamerlan Mamukhov on 2022-09-16
 */
interface ExhibitionsApi {
    @GET("exhibitions/{$ID}?fields=$ID,$IMAGE_URL,$GALLERY_TITLE,$TITLE,$ALT_IMAGE_IDS,$STATUS,$SHORT_DESCRIPTION")
    suspend fun getExhibitionById(@Path(ID) id: Int): ExhibitionModel

    // @GET("exhibitions?fields=$ID,$IMAGE_URL,$GALLERY_TITLE,$TITLE,$ALT_IMAGE_IDS,$STATUS,$SHORT_DESCRIPTION")
    @GET("exhibitions")
    // suspend fun getExhibitions(): SearchResultsModel<ExhibitionData>
    suspend fun getExhibitions(): ExhibitionsListResult
}