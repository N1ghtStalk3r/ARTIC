package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentFilterArtworksDialogBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author Tamerlan Mamukhov
 * @created 2022-10-06
 */
class FilterArtworksDialogFragment : DialogFragment() {

    private val viewModel by sharedViewModel<ArtworkViewModel>()
    private lateinit var binding: FragmentFilterArtworksDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterArtworksDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViews()
        with(binding) {
            btnClose.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun getTheme(): Int = R.style.DialogTheme

    private fun setViews() = with(binding) {
        val places = resources.getStringArray(R.array.places_of_origin)
        val types = resources.getStringArray(R.array.artwork_types)

        val placesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, places)
        val typesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, types)

        applyForTextView(placesAdapter, countries, Color.GREEN)
        applyForTextView(typesAdapter, tvTypes, Color.GREEN)

        this.tvQuery.setOnClickListener {
            val query = this.countries.text.toString()
            viewModel.getArtworksByPlace(query)
            viewModel.artworksContentState.observe(viewLifecycleOwner, ::getCount)
        }

    }

    private fun getCount(contentResultState: ContentResultState?) = when (contentResultState) {
        is ContentResultState.Content -> {
            contentResultState.handle()
        }
        is ContentResultState.Error -> {
            contentResultState.handle()
        }
        else -> {}
    }


    private fun ContentResultState.Content.handle() {
        val array = contentsList as List<Artwork>
        // Для проверки того, что есть данные
        Log.d("FADF", "handle: ${array.count()}")

    }

    private fun ContentResultState.Error.handle() {
        with(binding) {
            // Для проверки того, что данных нет ...
            Log.d("FADF", "handle: данных нет...")
        }
    }

    private fun applyForTextView(
        adapter: ArrayAdapter<String>, textView: AutoCompleteTextView, color: Int
    ) = with(textView) {
        threshold = 1
        setAdapter(adapter)
        setTextColor(color)
    }


}