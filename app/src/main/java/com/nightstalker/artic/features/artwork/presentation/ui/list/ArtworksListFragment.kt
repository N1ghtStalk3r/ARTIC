package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import com.nightstalker.artic.features.artwork.presentation.ui.dialog.SearchArtworksQueryConstructor
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения списка эспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListFragment : Fragment() {

    private var binding: FragmentArtworksListBinding? = null
    private lateinit var adapter: ArtworksListAdapter
    private val artworkViewModel by sharedViewModel<ArtworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_artworks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtworksListBinding.bind(view)

        prepareSearchEt()

        getArgs()
        initObservers()
        initViews()
        artworkViewModel.getArtworks()
    }

    private fun prepareSearchEt() {
        val query = getQueryFromPref()
        binding?.tilSearch?.editText?.setText(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() {
        with(binding) {
            adapter = ArtworksListAdapter { id -> onItemClick(id) }
            this?.rvArtworks?.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this?.rvArtworks?.adapter = adapter

            val textInput = this?.tilSearch
            textInput?.apply {
                editText?.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        editText?.text?.toString()?.let { putQueryToPref(it) }

                        var searchQuery =
                            SearchArtworksQueryConstructor.create(searchQuery = editText?.text.toString())

                        if (place != NULL_ARG || type != NULL_ARG) {
                            searchQuery =
                                SearchArtworksQueryConstructor.create(searchQuery = editText?.text.toString(),
                                    place,
                                    type)
                        }

                        Log.d(TAG, place)
                        Log.d(TAG, type)
                        Log.d(TAG, searchQuery)

                        artworkViewModel.getArtworksByQuery(searchQuery)
                    }
                    true
                }

                artworkViewModel.searchedArtworksContentState.observe(viewLifecycleOwner, ::handleSearched)

                setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_artworksListFragment_to_filterArtworksBottomSheetDialog)
                }
            }
        }
    }

    private fun initObservers() = with(artworkViewModel) {
        artworksContentState.observe(viewLifecycleOwner, ::handle)
    }

    private fun handle(contentResultState: ContentResultState) = when (contentResultState) {
        is ContentResultState.Content -> {
            contentResultState.handle()
        }
        is ContentResultState.Error -> {
            contentResultState.handle()
        }
        else -> {}
    }

    private fun handleSearched(contentResultState: ContentResultState?) = when (contentResultState) {
        is ContentResultState.Content -> {
            contentResultState.handleSearched()
        }
        is ContentResultState.Error -> {
            contentResultState.handle()
        }
        else -> {}
    }

    private fun tryAgain() {
        binding?.errorLayout?.root?.visibility = View.INVISIBLE
        artworkViewModel.getArtworks()
    }

    private fun onItemClick(id: Int) = ArtworksListFragmentDirections
        .toArtworkDetailsFragment(id)
        .run { findNavController().navigate(this) }

    private fun ContentResultState.Content.handle() {
        adapter.data = (contentsList as MutableList<Artwork>)
        binding?.rvArtworks?.adapter = adapter
    }

    private fun ContentResultState.Content.handleSearched() {
        adapter.data = (contentsList as MutableList<Artwork>)
        binding?.rvArtworks?.adapter = adapter
    }

    private fun ContentResultState.Error.handle() {
        with(binding) {
            this?.errorLayout?.apply {
                root.visibility = View.VISIBLE
                btnErrorTryAgain.setOnClickListener { tryAgain() }
                textErrorDescription.setText(error.title)
                textErrorDescription.setText(error.description)
            }
        }
    }

    private fun getArgs() {
        arguments?.apply {
            place = getString("place").toString()
            type = getString("type").toString()

            Log.d(TAG, "getArgs: $place")
            Log.d(TAG, "getArgs: $type")
        }
    }

    companion object {
        private var place = ""
        private var type = ""
        private const val TAG = "ArtworksListFragment"
        private const val NULL_ARG = "null"
        private val SHARED_PREF_QUERY_KEY = "query"
    }

    private fun putQueryToPref(query: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(SHARED_PREF_QUERY_KEY, query)
            apply()
        }
    }

    private fun getQueryFromPref() : String?{
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val defValue = "Statue"
        return sharedPref?.getString(SHARED_PREF_QUERY_KEY, defValue)
    }
}