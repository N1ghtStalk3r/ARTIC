package com.nightstalker.artic.features.audio.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.viewModelCall
import com.nightstalker.artic.features.audio.domain.usecase.AudioUseCase

class AudioLookupViewModel(
    private val audioUseCase: AudioUseCase,
) : ViewModel() {
    private val _audioFileContentState = MutableLiveData<ContentResultState>()
    val audioFileContentState get() = _audioFileContentState

    fun getSoundById(id: Int) =
        viewModelCall(call = { audioUseCase.getAudioById(id) }, contentResultState = _audioFileContentState)
}