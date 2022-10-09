package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.core.utils.ImageLinkCreator
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.domain.ArtworkManifest
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения деталей эскпоната
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : Fragment() {
    private val args: ArtworkDetailsFragmentArgs by navArgs()
    private var binding: FragmentArtworkDetailsBinding? = null
    private val viewModel by sharedViewModel<ArtworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artwork_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArtworkDetailsBinding.bind(view)
        val id = args.posterId
        viewModel.getArtwork(id)
        viewModel.getManifest(id)
        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObserver() {
        viewModel.artworkLoaded.observe(viewLifecycleOwner, ::setArtworkViews)
        viewModel.artworkManifestLoaded.observe(viewLifecycleOwner, ::setManViews)
    }

    private fun setManViews(artworkManifest: ArtworkManifest?) {
        with(binding) {
            this?.tvDescription?.text = artworkManifest?.description
        }
    }

    private fun setArtworkViews(artwork: Artwork) {
        with(binding) {
            this?.tvTitle?.text = artwork.title
            this?.tvAuthor?.text = artwork.artist
            val context = binding?.placeImage?.context
            val imageUrl = artwork.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

            val place = binding?.placeImage

            context?.let { place?.let { it1 -> Glide.with(it).load(imageUrl).into(it1) } }
        }
    }
}