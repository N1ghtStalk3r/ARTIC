package com.nightstalker.artic.features.audio.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.viewModelCall
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.domain.usecase.AudioUseCase

/**
 * ВЬю-модель для получения [AudioFileModel], файла с данными о аудио экспоната
 *
 * @property audioUseCase   юз-кейс получения данных
 * @author Tamerlan Mamukhov on 2022-10-04
 */
class AudioViewModel(
    private val audioUseCase: AudioUseCase,
) : ViewModel() {
    private val _audioFileContentState = MutableLiveData<ContentResultState>()
    val audioFileContentState get() = _audioFileContentState

    fun getSoundById(id: Int) =
        viewModelCall(call = { audioUseCase.getAudioById(id) }, contentResultState = _audioFileContentState)

    fun getSoundByTitle(title: String) =
        viewModelCall(call = { audioUseCase.getSoundByArtworkTitle(title) },
            contentResultState = _audioFileContentState)
}