package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.core.presentation.ui.LayoutErrorHandler
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.dialog.SearchArtworksQueryConstructor
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка эспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListFragment : Fragment() {

    private var binding: FragmentArtworksListBinding? = null
    private lateinit var adapter: ArtworksListAdapter
    private val viewModel by viewModel<ArtworksListViewModel>()

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

        getArgs()
        viewModel.getArtworks()
        initObservers()
        prepareSearch()
        prepareAdapter()
    }


    private fun prepareAdapter() {
        with(binding) {
            this?.rvArtworks?.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ArtworksListAdapter { id -> onItemClick(id) }
            this?.rvArtworks?.adapter = adapter
        }
    }

    private fun prepareSearch() {
        with(binding) {
            val textInput = this?.tilSearch
            textInput?.apply {
                editText?.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        var searchQuery =
                            SearchArtworksQueryConstructor.create(searchQuery = editText?.text.toString())

                        if (place != NULL_ARG || type != NULL_ARG) {
                            searchQuery =
                                SearchArtworksQueryConstructor.create(
                                    searchQuery = editText?.text.toString(),
                                    place,
                                    type
                                )
                        }

                        Log.d(TAG, place)
                        Log.d(TAG, type)
                        Log.d(TAG, searchQuery)

                        viewModel.getArtworksByQuery(searchQuery)

                        initObserversForSearched()
                    }
                    true
                }
                this?.setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_artworksListFragment_to_filterArtworksBottomSheetDialog)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObservers() =
        viewModel.artworksContentState.observe(viewLifecycleOwner, ::handleArtworks)

    private fun initObserversForSearched() =
        viewModel.searchedArtworksContentState.observe(viewLifecycleOwner, ::handleSearchedArtworks)

    private fun handleArtworks(contentResultState: ContentResultState?) {
        contentResultState?.handleContents(
            onStateSuccess = {
                if (adapter.data.isEmpty()) {
                    adapter.setData(it as List<Artwork>)
                    binding?.rvArtworks?.adapter = adapter
                }
            },
            onStateError = {
                with(binding) {
                    LayoutErrorHandler(
                        this?.errorLayout!!,
                        { tryAgain() },
                        it,
                        this.rvArtworks
                    )
                }
            },
            onStateLoading = {
                Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show()
            }
        )
    }


    private fun handleSearchedArtworks(contentResultState: ContentResultState?) {
        contentResultState?.handleContents(
            onStateSuccess = {
                adapter.setData(it as List<Artwork>)
                binding?.rvArtworks?.adapter = adapter
            },
            onStateError = {
                with(binding) {
                    LayoutErrorHandler(
                        this?.errorLayout!!,
                        { tryAgain() },
                        it,
                        this.rvArtworks
                    )
                }
            },
            onStateLoading = {
                Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun tryAgain() {
        viewModel.getArtworks()
        initObservers()
    }

    private fun onItemClick(id: Int) = ArtworksListFragmentDirections
        .toArtworkDetailsFragment(id)
        .run { findNavController().navigate(this) }

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
        with(sharedPref.edit()) {
            putString(SHARED_PREF_QUERY_KEY, query)
            apply()
        }
    }

    private fun getQueryFromPref(): String? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val defValue = "Statue"
        return sharedPref?.getString(SHARED_PREF_QUERY_KEY, defValue)
    }
}