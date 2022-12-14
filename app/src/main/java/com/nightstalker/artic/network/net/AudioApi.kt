package com.nightstalker.artic.network.net

import com.nightstalker.artic.core.data.model.audio.MobileSoundData
import com.nightstalker.artic.core.data.model.common.ItemsListResultModel
import com.nightstalker.artic.core.data.model.common.SingeItemResultModel
import com.nightstalker.artic.network.ApiConstants.ID
import com.nightstalker.artic.network.ApiConstants.PARAMS
import com.nightstalker.artic.network.ApiConstants.TITLE
import com.nightstalker.artic.network.ApiConstants.TRANSCRIPT
import com.nightstalker.artic.network.ApiConstants.WEB_URL
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API для получения аудио
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
interface AudioApi {
    /**
     * Получение аудио по номеру
     *
     * @param id    номер
     */
    @GET("mobile-sounds/{$ID}/?fields=$ID,$TITLE,$WEB_URL,$TRANSCRIPT")
    suspend fun getSoundById(@Path(ID) id: Int): SingeItemResultModel<MobileSoundData>

    /**
     * Получение аудио по названию экспоната
     *
     * @param query название
     */
    // @GET("mobile-sounds/search?query[match][title]={$TITLE}&limit=1")
    @GET("mobile-sounds/search?")
    suspend fun getSoundByArtworkTitle(@Query(PARAMS) query: String): ItemsListResultModel<MobileSoundData>
}