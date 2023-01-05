package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.ImageLinkCreator
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения деталей эскпоната
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : Fragment() {
    private val args: ArtworkDetailsFragmentArgs by navArgs()
    private var _binding: FragmentArtworkDetailsBinding? = null
    private val binding get() = _binding
    private val artworkViewModel by viewModel<ArtworkDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_artwork_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentArtworkDetailsBinding.bind(view)
        val id = args.posterId
        initObserver()

        artworkViewModel.getArtwork(id)
        artworkViewModel.getArtworkInformation(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserver() {
        with(artworkViewModel) {
            artworkContentState.observe(viewLifecycleOwner, ::handle)
            artworkDescriptionState.observe(viewLifecycleOwner, ::handle)
        }
    }

    private fun handle(contentResultState: ContentResultState) = when (contentResultState) {
        is ContentResultState.Content -> contentResultState.handle()
        is ContentResultState.Error -> contentResultState.handle()
        else -> {}
    }

    private fun ContentResultState.Content.handle() {
        when (content) {
            is Artwork -> {
                setArtworkViews(content)
            }
            is ArtworkInformation -> {
                setDescriptionView(content)
            }
        }
    }

    private fun ContentResultState.Error.handle() {
        Log.d(TAG, "handle: $error")
    }

    private fun setDescriptionView(artworkInformation: ArtworkInformation?) {
        with(binding) {
            this?.tvDescription?.text = artworkInformation?.description
        }
    }

    private fun setArtworkViews(artwork: Artwork) {
        with(binding) {
            val place = binding?.placeImage

            this?.titleTextView?.text = artwork.title
            this?.tvAuthor?.text = artwork.artist

            val imageUrl = artwork.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

            place?.load(imageUrl)

            place?.setOnClickListener {
                artwork.imageId?.let { it1 ->
                    ArtworkDetailsFragmentDirections.actionArtworkDetailsFragmentToArtworkFullViewFragment(
                        it1
                    )
                }
                    .run {
                        findNavController().navigate(this!!)
                    }
            }

        }
    }

    companion object {
        private const val TAG = "ArtworkDetailsFragment"
    }
}