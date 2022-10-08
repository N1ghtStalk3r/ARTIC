package com.nightstalker.artic.features.artwork.presentation.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.domain.ArtworkManifest
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo
import kotlinx.coroutines.launch

/**
 * Вью модель для получения экспонатов
 *
 * @property repo   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworkViewModel(
    private val repo: ArtworkRepo
) : ViewModel() {
    private var _artworkLoaded = MutableLiveData<Artwork>()
    val artworkLoaded: LiveData<Artwork> get() = _artworkLoaded

    private var _artworksLoaded = MutableLiveData<List<Artwork>>()
    val artworksLoaded: LiveData<List<Artwork>> get() = _artworksLoaded

    private var _artworkManifestLoaded = MutableLiveData<ArtworkManifest>()
    val artworkManifestLoaded: LiveData<ArtworkManifest> get() = _artworkManifestLoaded

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

    fun getManifest(id: Int) {
        viewModelScope.launch() {
            _artworkManifestLoaded.postValue(repo.getArtworkManifest(id))
        }
    }
}