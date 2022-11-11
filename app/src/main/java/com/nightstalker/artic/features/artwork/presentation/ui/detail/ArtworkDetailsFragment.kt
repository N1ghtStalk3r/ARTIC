package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.utils.ImageLinkCreator
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import com.nightstalker.artic.features.audio.AudioPlayer
import com.nightstalker.artic.features.audio.utils.AudioFileLinkCreator
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
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_artwork_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArtworkDetailsBinding.bind(view)
        val id = args.posterId
        initObserver()

        viewModel.getArtwork(id)
        viewModel.getArtworkInformation(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObserver() = with(viewModel) {
        artworkContentState.observe(viewLifecycleOwner, ::handle)
        artworkDescriptionState.observe(viewLifecycleOwner, ::handle)
    }

    private fun handle(contentResultState: ContentResultState) = when (contentResultState) {
        is ContentResultState.Content -> contentResultState.handle()
        is ContentResultState.Error -> contentResultState.handle()
        else -> {}
    }

    private fun ContentResultState.Content.handle() {
        when (contentSingle) {
            is Artwork -> {
                Log.d("ADF", "handle: $contentSingle")
                setArtworkViews(contentSingle as Artwork)
            }
            is ArtworkInformation -> {
                setManViews(contentSingle as ArtworkInformation)
            }
        }
    }

    private fun ContentResultState.Error.handle() {
        Log.d("ADF", "handle: $error")
    }

    private fun setManViews(artworkInformation: ArtworkInformation?) {
        with(binding) {
            this?.tvDescription?.text = artworkInformation?.description
        }
    }

    private fun setArtworkViews(artwork: Artwork) {
        with(binding) {
            val player = AudioPlayer()
            this?.titleTextView?.text = artwork.title
            this?.tvAuthor?.text = artwork.artist
            val context = binding?.placeImage?.context
            val imageUrl = artwork.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

            val place = binding?.placeImage

            context?.let { place?.let { it1 -> Glide.with(it).load(imageUrl).into(it1) } }

            Log.d("FD", "setArtworkViews: ${artwork.audioUrl}")

            when (artwork.audioUrl) {
                null -> {
                    this?.audioGroup?.visibility = View.GONE
                    Toast.makeText(activity, "No audio", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(activity, "There's audio", Toast.LENGTH_SHORT).show()


                    this?.audioGroup?.visibility = View.VISIBLE
                    player.audioUrl =
                        artwork.audioUrl?.let { it1 -> AudioFileLinkCreator.create(it1) }

                    this?.btnPlay?.setOnClickListener {
                        player.startPlaying()
                    }

                    this?.btnStop?.setOnClickListener {
                        player.stopPlaying()
                    }
                }

            }
        }
    }
}