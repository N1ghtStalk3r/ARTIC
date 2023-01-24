package com.nightstalker.artic.features.artwork.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.viewModelCall
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase

/**
 * Вью модель для получения деталей экспоната
 *
 * @property useCase    юз кейс
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworkDetailsViewModel(
    private val useCase: ArtworksUseCase,
) : ViewModel() {

    private val _artworkContentState = MutableLiveData<ContentResultState>()
    val artworkContentState get() = _artworkContentState

    private val _artworkDescriptionState = MutableLiveData<ContentResultState>()
    val artworkDescriptionState get() = _artworkDescriptionState

    fun getArtwork(id: Int) =
        viewModelCall(
            call = { useCase.getArtworkById(id) },
            contentResultState = _artworkContentState
        )

    fun getArtworkInformation(id: Int) =
        viewModelCall(
            call = { useCase.getArtworkInformation(id) },
            contentResultState = _artworkDescriptionState
        )
}