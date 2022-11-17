package com.nightstalker.artic.features.artwork.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.domain.ResultState
import com.nightstalker.artic.core.presentation.onResultStateError
import com.nightstalker.artic.core.presentation.onResultStateSuccess
import com.nightstalker.artic.core.presentation.viewModelCall
import com.nightstalker.artic.features.FeaturesConstants.TIMEOUT_1000
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

    private val _artworkDescriptionState = MutableLiveData<ContentResultState>()
    val artworkDescriptionState get() = _artworkDescriptionState

    private var m_artworkInformationContentState = MutableLiveData<ArtworkInformation>()
    val artworkInformationContentState: LiveData<ArtworkInformation> get() = m_artworkInformationContentState

    private var _searchedArtworksContentState = MutableLiveData<List<Artwork>>()
    val searchedArtworksContentState: LiveData<List<Artwork>> get() = _searchedArtworksContentState

    // Проверка связи Bottom Sheet Dialog и основного фрагмента через VM
    // Будет изменено
    private val _searchQuery = MutableStateFlow("")

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val networkOperationResult: Flow<String> =
        _searchQuery
            .debounce(1000)
            .mapLatest {
                if (it.isEmpty()) {
                    return@mapLatest ""
                } else {
                    Log.d("netResult", it)
                    return@mapLatest longNetworkOperation(it)
                }
            }

    private suspend fun longNetworkOperation(request: String): String =
        withContext(Dispatchers.Default) {
            delay(1000)
            getArtworksByPlace(request)
            "Pseudo network delay of 3s delay: $request"
        }

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

    fun getArtworksByType(type: String) =
        viewModelCall(
            call = { useCase.getArtworksByKind(type) },
            contentResultState = _artworksContentState
        )

    fun getArtworksByPlace(type: String) =
        viewModelCall(
            call = { useCase.getArtworksByPlace(type) },
            contentResultState = _artworksContentState
        )

    fun getArtworkInformation(id: Int) =
        viewModelCall(
            dispatcher,
            { useCase.getArtworkInformation(id) },
            _artworkDescriptionState
        )
}