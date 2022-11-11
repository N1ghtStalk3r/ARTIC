package com.nightstalker.artic.features.exhibition.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.domain.ResultState
import com.nightstalker.artic.core.presentation.onResultStateError
import com.nightstalker.artic.core.presentation.onResultStateSuccess
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * Вью модель для получения экспонатов
 *
 * @property repo   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionsViewModel(
    private val repo: ExhibitionsUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var _exhibitionContentState = MutableLiveData<ContentResultState>()
    val exhibitionContentState get() = _exhibitionContentState

    private var _exhibitionsContentState = MutableLiveData<ContentResultState>()
    val exhibitionsContentState get() = _exhibitionsContentState

    fun getExhibition(id: Int) = viewModelScope.launch(dispatcher) {
        when (val exhibition = repo.getExhibitionById(id)) {
            is ResultState.Success -> {
                onResultStateSuccess(
                    contentSingle = exhibition.data,
                    contentResultState = _exhibitionContentState
                )
            }
            is ResultState.Error -> {
                onResultStateError(
                    isNetworkError = exhibition.errorData,
                    contentResultState = _exhibitionContentState
                )
            }
        }
    }

    fun getExhibitions() = viewModelScope.launch(dispatcher) {
        when (val exhibitions = repo.getExhibitions()) {
            is ResultState.Success -> {
                onResultStateSuccess(
                    contentsList = exhibitions.data,
                    contentResultState = _exhibitionsContentState
                )
            }
            is ResultState.Error -> {
                onResultStateError(
                    isNetworkError = exhibitions.errorData,
                    contentResultState = _exhibitionsContentState
                )
            }
        }
    }
}