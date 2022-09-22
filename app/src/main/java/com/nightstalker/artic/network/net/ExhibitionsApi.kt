package com.nightstalker.artic.network.net

import com.nightstalker.artic.core.data.model.exhibition.ExhibitionModel
import com.nightstalker.artic.network.ApiConstants.ID
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-16
 */
interface ExhibitionsApi {
    @GET("exhibitions/{$ID}/")
    suspend fun getExhibitionById(@Path(ID) id: Int): ExhibitionModel
}