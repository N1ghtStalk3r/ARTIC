package com.nightstalker.artic.features.artwork.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.domain.ResultState
import com.nightstalker.artic.core.presentation.onResultStateError
import com.nightstalker.artic.core.presentation.onResultStateSuccess
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

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

    private var m_artworkInformationContentState = MutableLiveData<ArtworkInformation>()
    val artworkInformationContentState: LiveData<ArtworkInformation> get() = m_artworkInformationContentState

    private var _searchedArtworksContentState = MutableLiveData<List<Artwork>>()
    val searchedArtworksContentState: LiveData<List<Artwork>> get() = _searchedArtworksContentState

    fun getArtworks() = viewModelScope.launch(dispatcher) {
        when (val artworks = useCase.getArtworks()) {
            is ResultState.Success -> {
                onResultStateSuccess(contentsList = artworks.data, contentResultState = _artworksContentState)
            }
            is ResultState.Error -> {
                onResultStateError(isNetworkError = artworks.errorData, contentResultState = _artworksContentState)
            }
        }
    }

    fun getArtwork(id: Int) = viewModelScope.launch(dispatcher) {
        when (val artwork = useCase.getArtworkById(id)) {
            is ResultState.Success -> {
                onResultStateSuccess(contentSingle = artwork.data, contentResultState = _artworkContentState)
            }
            is ResultState.Error -> {
                onResultStateError(isNetworkError = artwork.errorData, contentResultState = _artworksContentState)
            }
        }
    }

    fun getArtworksByQuery(query: String) = viewModelScope.launch(dispatcher) {
        when (val artworks = useCase.getArtworksByQuery(query)) {
            is ResultState.Success -> {
                onResultStateSuccess(contentsList = artworks.data, contentResultState = _artworksContentState)
            }
            is ResultState.Error -> {
                onResultStateError(isNetworkError = artworks.errorData, contentResultState = _artworksContentState)
            }
        }
    }

    fun getManifest(id: Int) = viewModelScope.launch(dispatcher) {
        when (val manifest = useCase.getArtworkInformation(id)) {
            is ResultState.Success -> {
            }
            is ResultState.Error -> {
            }
        }
    }

    fun getArtworksByKind(type: String) = viewModelScope.launch(dispatcher) {
        when (val artworks = useCase.getArtworksByKind(type)) {
            is ResultState.Success -> {
                onResultStateSuccess(contentsList = artworks.data, contentResultState = _artworksContentState)
            }
            is ResultState.Error -> {
                onResultStateError(isNetworkError = artworks.errorData, contentResultState = _artworksContentState)
            }
        }
    }

    fun getArtworksByPlace(type: String) = viewModelScope.launch(dispatcher) {
        when (val artworks = useCase.getArtworksByPlace(type)) {
            is ResultState.Success -> {
                onResultStateSuccess(contentsList = artworks.data, contentResultState = _artworksContentState)
            }
            is ResultState.Error -> {
                onResultStateError(isNetworkError = artworks.errorData, contentResultState = _artworksContentState)
            }
        }
    }
}