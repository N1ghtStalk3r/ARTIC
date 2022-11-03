package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения списка эспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListFragment : Fragment() {

    private var binding: FragmentArtworksListBinding? = null
    private lateinit var adapter: ArtworksListAdapter
    private val viewModel by sharedViewModel<ArtworkViewModel>()

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

        with(binding) {
            this?.rvArtworks?.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ArtworksListAdapter { id -> onItemClick(id) }
            this?.rvArtworks?.adapter = adapter

            initObservers()

            viewModel.getArtworks()

            setupSearchView()

            this?.ivFilterArts?.setOnClickListener {
                findNavController().navigate(R.id.filterArtworksDialogFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObservers() = with(viewModel) {
        searchedArtworksContentState.observe(viewLifecycleOwner, ::setSearchedData)
        artworksContentState.observe(viewLifecycleOwner, ::handle)
    }

    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding?.searchView?.clearFocus()
                searchByQuery(query.toString())
                Toast.makeText(activity, "$query", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
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

    private fun tryAgain() {
        binding?.errorLayout?.root?.visibility = View.INVISIBLE
        viewModel.getArtworks()
    }

    private fun onItemClick(id: Int) = ArtworksListFragmentDirections
        .toArtworkDetailsFragment(id)
        .run { findNavController().navigate(this) }

    private fun setSearchedData(list: List<Artwork>?) {
        list?.let { adapter.setData(it) }
    }

    private fun searchByQuery(query: String) = viewModel.getArtworksByQuery(query)

    private fun ContentResultState.Content.handle() {
        adapter.setData(contentsList as List<Artwork>)
        Log.d("Fragm", "handle: ${contentsList as List<Artwork>}")
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

}