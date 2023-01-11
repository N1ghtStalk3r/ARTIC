package com.nightstalker.artic.features.artwork.presentation.ui.fullview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.nightstalker.artic.core.presentation.ViewBindingFragment
import com.nightstalker.artic.databinding.FragmentArtworkFullViewBinding
import com.nightstalker.artic.features.ImageLinkCreator
import kotlinx.android.synthetic.main.fragment_artwork_full_view.*

/**
 * Фрагмент для отображения полного изображения экспоната
 *
 * @author Tamerlan Mamukhov on 2023-01-06
 */
class ArtworkFullViewFragment : ViewBindingFragment<FragmentArtworkFullViewBinding>() {

    private val args: ArtworkFullViewFragmentArgs by navArgs()

    override val initBinding: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> FragmentArtworkFullViewBinding
        get() = FragmentArtworkFullViewBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage()
    }

    private fun loadImage() {
        val imageUrl = ImageLinkCreator.createImageHighQualityLink(args.imageId)
        ivFullArtwork.load(imageUrl)
    }

}