package com.nightstalker.artic.features.exhibition.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.ext.viewModelCall
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase

/**
 * Вью модель для получения экспонатов
 *
 * @property useCase   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionsListViewModel(
    private val useCase: ExhibitionsUseCase
) : ViewModel() {

    private var _exhibitionsContentState = MutableLiveData<ContentResultState>()
    val exhibitionsContentState get() = _exhibitionsContentState

    fun getExhibitions() = viewModelCall(
        call = { useCase.getExhibitions() },
        contentResultState = _exhibitionsContentState
    )

}