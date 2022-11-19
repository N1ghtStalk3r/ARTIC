package com.nightstalker.artic.features.artwork.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.viewModelCall
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Вью модель для получения экспонатов
 *
 * @property useCase    юз кейс
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworkViewModel(
    private val useCase: ArtworksUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _artworksContentState = MutableLiveData<ContentResultState>()
    val artworksContentState get() = _artworksContentState

    private val _artworkContentState = MutableLiveData<ContentResultState>()
    val artworkContentState get() = _artworkContentState

    private val _artworkDescriptionState = MutableLiveData<ContentResultState>()
    val artworkDescriptionState get() = _artworkDescriptionState

    private var _searchedArtworksContentState = MutableLiveData<ContentResultState>()
    val searchedArtworksContentState get() = _searchedArtworksContentState

    fun getArtworks() =
        viewModelCall(call = { useCase.getArtworks() }, contentResultState = _artworksContentState)

    fun getArtwork(id: Int) =
        viewModelCall(
            call = { useCase.getArtworkById(id) },
            contentResultState = _artworkContentState
        )

    fun getArtworksByQuery(query: String) =
        viewModelCall(
            call = { useCase.getArtworksByQuery(query) },
            contentResultState = _artworksContentState
        )

    fun getArtworkInformation(id: Int) =
        viewModelCall(
            dispatcher,
            { useCase.getArtworkInformation(id) },
            _artworkDescriptionState
        )
}