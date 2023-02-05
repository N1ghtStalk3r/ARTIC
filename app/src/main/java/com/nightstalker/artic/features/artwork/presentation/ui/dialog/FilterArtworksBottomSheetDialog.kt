package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для фильтра эксопнатов
 *
 * @author Tamerlan Mamukhov on 2022-11-15
 */
class FilterArtworksBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterArtworksBottomSheetDialogBinding
    private val filterArtworksViewModel by viewModel<FilterArtworksViewModel>()

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

        handleSearchArguments()
//        filterArtworksViewModel.getNumberOfArtworks("")
//        filterArtworksViewModel.numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)
    }

    private fun handleSearchArguments() {

        var searchQuery =
            SearchArtworksQueryConstructor.create("")

        if (place != NULL_ARG || type != NULL_ARG) {
            searchQuery =
                SearchArtworksQueryConstructor.create(
                    searchQuery = "",
                    place,
                    type
                )
        }
        filterArtworksViewModel.getNumberOfArtworks(searchQuery)
        filterArtworksViewModel.numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)

    }

    private fun handleFilterResult(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
                Log.d(TAG, "numberOfArts: $it")
                binding.btnApply.text = resources.getString(R.string.text_found_artworks, it)
            },
            onStateError = {

            },
            onStateLoading = {
                Toast.makeText(activity, "Найдено: ", Toast.LENGTH_SHORT).show()
            }
        )


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

    companion object {
        private const val TAG = "FilterArtworks"
        private var place = ""
        private var type = ""
        private const val NULL_ARG = "null"
    }

}