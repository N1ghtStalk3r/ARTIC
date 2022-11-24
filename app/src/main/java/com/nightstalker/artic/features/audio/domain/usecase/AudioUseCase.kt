package com.nightstalker.artic.features.audio.domain.usecase

import com.nightstalker.artic.core.domain.ResultState
import com.nightstalker.artic.features.artwork.domain.usecase.safeCall
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.domain.repo.AudioRepo

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
class AudioUseCase(private val repo: AudioRepo) {
    suspend fun getAudioById(id: Int): ResultState<AudioFileModel> = safeCall { repo.getAudioById(id) }
    suspend fun getSoundByArtworkTitle(title: String): ResultState<AudioFileModel> = safeCall { repo.getSoundByArtworkTitle(title) }
}