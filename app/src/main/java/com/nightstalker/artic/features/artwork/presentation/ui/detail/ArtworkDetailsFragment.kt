package com.nightstalker.artic.features.artwork.presentation.ui.detail

// import kotlinx.android.synthetic.main.fragment_artwork_details.audioPlayer
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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения деталей эскпоната
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : Fragment() {
    private val args: ArtworkDetailsFragmentArgs by navArgs()
    private var _binding: FragmentArtworkDetailsBinding? = null
    private val binding get() = _binding
    private val artworkViewModel by sharedViewModel<ArtworkViewModel>()

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
        when (contentSingle) {
            is Artwork -> {
                setArtworkViews(contentSingle as Artwork)
            }
            is ArtworkInformation -> {
                setDescriptionView(contentSingle as ArtworkInformation)
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

            val context = binding?.placeImage?.context
            val place = binding?.placeImage
            val btnPlay = binding?.btnPlayAudio

            this?.titleTextView?.text = artwork.title
            this?.tvAuthor?.text = artwork.artist

            val imageUrl = artwork.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

            context?.let { place?.let { it1 -> Glide.with(it).load(imageUrl).into(it1) } }

            when (artwork.audioUrl) {
                null -> {
                    Toast.makeText(activity, "No audio", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(activity, "There's audio", Toast.LENGTH_SHORT).show()
                    btnPlay?.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            binding?.fragmentAudioBottom?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "ArtworkDetailsFragment"
    }
}