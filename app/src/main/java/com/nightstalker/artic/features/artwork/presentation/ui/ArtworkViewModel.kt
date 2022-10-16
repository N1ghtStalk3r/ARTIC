package com.nightstalker.artic.features.artwork.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.domain.ArtworkManifest
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * Вью модель для получения экспонатов
 *
 * @property repo   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworkViewModel(
    private val repo: ArtworkRepo,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    // private val dispatcher = Dispatchers.IO

    private var _artworkLoaded = MutableLiveData<Artwork>()
    val artworkLoaded: LiveData<Artwork> get() = _artworkLoaded

    private var _artworksLoaded = MutableLiveData<List<Artwork>>()
    val artworksLoaded: LiveData<List<Artwork>> get() = _artworksLoaded

    private var _artworkManifestLoaded = MutableLiveData<ArtworkManifest>()
    val artworkManifestLoaded: LiveData<ArtworkManifest> get() = _artworkManifestLoaded

    private var _searchedArtworksLoaded = MutableLiveData<List<Artwork>>()
    val searchedArtworksLoaded: LiveData<List<Artwork>> get() = _searchedArtworksLoaded

    fun getArtwork(id: Int) {
        viewModelScope.launch(dispatcher) {
            _artworkLoaded.postValue(repo.getArtworkById(id))
        }
    }

    fun getArtworks() {
        viewModelScope.launch(dispatcher) {
            _artworksLoaded.postValue(repo.getArtworks())
        }
    }

    fun getManifest(id: Int) {
        viewModelScope.launch(dispatcher) {
            _artworkManifestLoaded.postValue(repo.getArtworkManifest(id))
        }
    }

    fun getArtworksByQuery(search: String) {
        viewModelScope.launch(dispatcher) {
            _searchedArtworksLoaded.postValue(repo.getArtworksByQuery(search))
        }
    }
}