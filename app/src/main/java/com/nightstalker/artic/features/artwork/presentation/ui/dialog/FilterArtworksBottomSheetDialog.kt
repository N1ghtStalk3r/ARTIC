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
    private val artworksViewModel by viewModel<ArtworkViewModel>()

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

        val places = resources.getStringArray(R.array.places_of_origin)
        val types = resources.getStringArray(R.array.artwork_types)

        val placesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, places)
        val typesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, types)

        applyForSpinner(placesAdapter, this.spCountries)
        applyForSpinner(typesAdapter, spTypes)

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

    private fun applyForSpinner(
        adapter: ArrayAdapter<String>, spinner: Spinner,
    ) = with(spinner) {
        setAdapter(adapter)
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity, selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}