package com.nightstalker.artic.features.exhibition.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.features.exhibition.domain.Exhibition
import com.nightstalker.artic.features.exhibition.domain.repo.ExhibitionsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * Вью модель для получения экспонатов
 *
 * @property repo   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionsViewModel(
    private val repo: ExhibitionsRepo,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _exhibitionLoaded = MutableLiveData<Exhibition>()
    val exhibitionLoaded: LiveData<Exhibition> get() = _exhibitionLoaded

    private var _exhibitionsLoaded = MutableLiveData<List<Exhibition>>()
    val exhibitionsLoaded: LiveData<List<Exhibition>> get() = _exhibitionsLoaded

    fun getExhibition(id: Int) {
        viewModelScope.launch(dispatcher) {
            _exhibitionLoaded.postValue(repo.getExhibitionById(id))
        }
    }

    fun getExhibitions() {
        viewModelScope.launch(dispatcher) {
            _exhibitionsLoaded.postValue(repo.getExhibitions())
        }
    }
}