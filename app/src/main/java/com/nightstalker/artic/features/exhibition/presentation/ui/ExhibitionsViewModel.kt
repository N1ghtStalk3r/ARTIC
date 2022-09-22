package com.nightstalker.artic.features.exhibition.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.features.exhibition.domain.Exhibition
import com.nightstalker.artic.features.exhibition.domain.repo.ExhibitionsRepo
import kotlinx.coroutines.launch

/**
 * Вью модель для получения экспонатов
 *
 * @property repo   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionsViewModel(
    private val repo: ExhibitionsRepo
) : ViewModel() {
    private var _exhibitionLoaded = MutableLiveData<Exhibition>()
    val exhibitionLoaded: LiveData<Exhibition> get() = _exhibitionLoaded

    fun getExhibition(id: Int) {
        viewModelScope.launch {
            _exhibitionLoaded.postValue(repo.getExhibitionById(id))
        }
    }
}