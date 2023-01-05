package com.nightstalker.artic.features.artwork.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.viewModelCall
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase

/**
 * Вью модель для получения списка экспонатов
 *
 * @property useCase    юз кейс
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworksListViewModel(
    private val useCase: ArtworksUseCase
) : ViewModel() {

    private val _artworksContentState = MutableLiveData<ContentResultState>()
    val artworksContentState get() = _artworksContentState

    private var _searchedArtworksContentState = MutableLiveData<ContentResultState>()
    val searchedArtworksContentState get() = _searchedArtworksContentState

    fun getArtworks() =
        viewModelCall(call = { useCase.getArtworks() }, contentResultState = _artworksContentState)

    fun getArtworksByQuery(query: String) =
        viewModelCall(
            call = { useCase.getArtworksByQuery(query) },
            contentResultState = _artworksContentState
        )
}