package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterArtworksBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterArtworksBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_filter_artworks_bottom_sheet_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFilterArtworksBottomSheetDialogBinding.bind(view)
        setViews()
    }

    private fun setViews() = with(binding) {
        this.btnApply.setOnClickListener {
            sendArgs()
        }
    }

    private fun sendArgs() {
        Bundle().apply {
            with(binding) {
                putString("place", this.spCountries.selectedItem.toString())
                putString("type", this.spTypes.selectedItem.toString())
            }
        }.run {
            findNavController().navigate(
                R.id.action_filterArtworksBottomSheetDialog_to_artworksListFragment,
                this
            )
        }
    }

}