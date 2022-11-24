package com.nightstalker.artic.network

import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.toAudioFileModel
import com.nightstalker.artic.network.net.ArtworksApi
import com.nightstalker.artic.network.net.AudioApi

/**
 * Класс получения данных с помощью [ArtworksApi]
 *
 * @property api    API
 * @author Tamerlan Mamukhov on 2022-09-16
 */
class AudioApiMapper(private val api: AudioApi) {
    suspend fun getAudioById(id: Int): AudioFileModel = api.getSoundById(id).data.toAudioFileModel()

    suspend fun getSoundByArtworkTitle(title: String): AudioFileModel = api.getSoundByArtworkTitle(title).data.first().toAudioFileModel()
}