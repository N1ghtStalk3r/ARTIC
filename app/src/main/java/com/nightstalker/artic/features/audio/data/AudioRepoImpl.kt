package com.nightstalker.artic.features.audio.data

import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.domain.repo.AudioRepo
import com.nightstalker.artic.network.AudioApiMapper

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
class AudioRepoImpl(
    private val apiMapper: AudioApiMapper,
) : AudioRepo {
    override suspend fun getAudioById(id: Int): AudioFileModel = apiMapper.getAudioById(id)
}