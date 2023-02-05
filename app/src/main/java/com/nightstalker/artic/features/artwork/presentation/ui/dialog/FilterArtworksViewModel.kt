package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.ext.viewModelCall
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase

/**
 * [ViewModel] для поиска экспонатов
 *
 * @author Tamerlan Mamukhov on 2022-11-15
 */
class FilterArtworksViewModel(
    private val useCase: ArtworksUseCase
) : ViewModel() {
    private val _numberOfArtworks = MutableLiveData<ContentResultState>()
    val numberOfArtworks get() = _numberOfArtworks

    fun getNumberOfArtworks(query: String) =
        viewModelCall(
            call = { useCase.getNumber(query) },
            contentResultState = _numberOfArtworks
        )
}