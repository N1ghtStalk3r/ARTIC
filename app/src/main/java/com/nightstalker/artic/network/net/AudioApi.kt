package com.nightstalker.artic.network.net

import com.nightstalker.artic.core.data.model.audio.MobileSoundData
import com.nightstalker.artic.core.data.model.common.SearchResultsModel
import com.nightstalker.artic.network.ApiConstants.ID
import com.nightstalker.artic.network.ApiConstants.TITLE
import com.nightstalker.artic.network.ApiConstants.TRANSCRIPT
import com.nightstalker.artic.network.ApiConstants.WEB_URL
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
interface AudioApi {
    @GET("mobile-sounds/${ID}/?fields=$ID,$TITLE,$WEB_URL,$TRANSCRIPT")
    suspend fun getSoundById(@Path(ID) id: Int): SearchResultsModel<MobileSoundData>
}