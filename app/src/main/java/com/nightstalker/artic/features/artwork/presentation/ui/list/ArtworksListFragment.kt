package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения списка эспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListFragment : Fragment() {

    private lateinit var binding: FragmentArtworksListBinding
    private lateinit var adapter: ArtworksListAdapter
    private val viewModel by sharedViewModel<ArtworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtworksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
                fabScanQr.tooltipText = getString(R.string.fab_scan_qr_tooltip)
                ivFilterArts.tooltipText = getString(R.string.iv_filter)
            rvArtworks.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ArtworksListAdapter { id -> onItemClick(id) }
            rvArtworks.adapter = adapter

            initObservers()
            viewModel.getArtworks()
        }
    }

    private fun onItemClick(id: Int) {
        ArtworksListFragmentDirections
            .toArtworkDetailsFragment(id)
            .run { findNavController().navigate(this) }
    }

    private fun initObservers() {
        viewModel.artworksLoaded.observe(viewLifecycleOwner, ::setData)
    }

    private fun setData(list: List<Artwork>) {
        adapter.setData(list)
    }
}