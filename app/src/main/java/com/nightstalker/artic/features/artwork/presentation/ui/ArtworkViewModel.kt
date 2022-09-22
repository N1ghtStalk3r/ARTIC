package com.nightstalker.artic.features.artwork.presentation.ui

import androidx.lifecycle.*
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.domain.repo.ArtWorkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Вью модель для получения экспонатов
 *
 * @property repo   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworkViewModel(
    private val repo: ArtWorkRepo
) : ViewModel() {
    private var _artworkLoaded = MutableLiveData<Artwork>()
    val artworkLoaded: LiveData<Artwork> get() = _artworkLoaded

    private var _artworksLoaded = MutableLiveData<List<Artwork>>()
    val artworksLoaded: LiveData<List<Artwork>> get() = _artworksLoaded

    fun getArtwork(id: Int) {
        viewModelScope.launch {
            _artworkLoaded.postValue(repo.getArtworkById(id))
        }
    }

    fun getArtworks() {
        viewModelScope.launch() {
            _artworksLoaded.postValue(repo.getArtworks())
        }
    }
}