package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentExhibitionsListBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка выставок
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ExhibitionsListFragment : Fragment() {

    private lateinit var adapter: ExhibitionsListAdapter
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private var binding: FragmentExhibitionsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_exhibitions_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExhibitionsListBinding.bind(view)

        with(binding) {

            this?.rvExhibitions?.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ExhibitionsListAdapter { id -> onItemClicked(id) }
            this?.rvExhibitions?.adapter = adapter

            initObserver()

            exhibitionsViewModel.getExhibitions()

            this?.ivFilterExh?.setOnClickListener {
                findNavController().navigate(R.id.filterExhibitionsDialogFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onItemClicked(id: Int) {
        ExhibitionsListFragmentDirections
            .toExhibitionDetailsFragment(id)
            .run { findNavController().navigate(this) }
    }

    private fun initObserver() = with(exhibitionsViewModel) {
        exhibitionsContentState.observe(viewLifecycleOwner, ::setList)
    }

    private fun setList(contentResultState: ContentResultState) = when (contentResultState) {
        is ContentResultState.Content -> contentResultState.handle()
        is ContentResultState.Error -> contentResultState.handle()
        else -> {}
    }

    private fun ContentResultState.Content.handle() {
        if (adapter.data.isEmpty()) {
            adapter.setData(contentsList as List<Exhibition>)
            binding?.rvExhibitions?.adapter = adapter
        }
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

    private fun tryAgain() {
        binding?.errorLayout?.root?.visibility = View.INVISIBLE
        exhibitionsViewModel.getExhibitions()
    }
}