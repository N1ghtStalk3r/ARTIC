package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nightstalker.artic.core.utils.ImageLinkConstructor
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : Fragment() {
    private val args: ArtworkDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentArtworkDetailsBinding
    private val viewModel by sharedViewModel<ArtworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtworkDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.posterId
        viewModel.getArtwork(id)
        initObserver()
    }

    private fun initObserver() {
        viewModel.artworkLoaded.observe(viewLifecycleOwner, ::setData)
    }

    private fun setData(artwork: Artwork) {
        with(binding) {
            tvTitle.text = artwork.title
            val context = binding.placeImage.context
            val imageUrl = ImageLinkConstructor.createImageDefaultLink(artwork.imageId)
            Glide.with(context).load(imageUrl).into(placeImage)
        }
    }
}