package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterArtworksBottomSheetDialog : BottomSheetDialogFragment() {
    private var binding: FragmentFilterArtworksBottomSheetDialogBinding? = null
    private val artworksViewModel by viewModel<ArtworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

        applyForTextView(placesAdapter, this!!.countries, Color.GREEN)
        applyForTextView(typesAdapter, tvTypes, Color.GREEN)

        // Проверка связи Bottom Sheet Dialog и основного фрагмента через VM
        this?.countries?.addTextChangedListener {
            artworksViewModel.setSearchQuery(it.toString())
        }



        this?.btnApply?.setOnClickListener {
            passArgs()
            lifecycleScope.launch {
                artworksViewModel.networkOperationResult.collect() {
                    Log.d("Afaf", "setViews: $it")
                }
            }
        }

    }

    private fun passArgs() {
        Bundle().apply {
            with(binding) {
                putString("place", this?.countries?.text?.toString())
                putString("type", this?.tvTypes?.text?.toString())
            }
        }.run {
            findNavController().navigate(
                R.id.action_filterArtworksBottomSheetDialog_to_artworksListFragment,
                this
            )
        }
    }

    private fun applyForTextView(
        adapter: ArrayAdapter<String>, textView: AutoCompleteTextView, color: Int,
    ) = with(textView) {
        threshold = 1
        setAdapter(adapter)
        setTextColor(color)
    }
}