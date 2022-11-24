package com.nightstalker.artic.features.audio.domain.repo

import com.nightstalker.artic.features.audio.domain.model.AudioFileModel

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
interface AudioRepo {
    suspend fun getAudioById(id: Int): AudioFileModel
    suspend fun getSoundByArtworkTitle(title: String): AudioFileModel
}