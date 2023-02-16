package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.refreshPage
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentExhibitionsListBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка выставок
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ExhibitionsListFragment : Fragment(R.layout.fragment_exhibitions_list) {

    private lateinit var adapter: ExhibitionsListAdapter
    private val binding: FragmentExhibitionsListBinding
            by viewBinding(FragmentExhibitionsListBinding::bind)
    private val viewModel by viewModel<ExhibitionsListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        viewModel.loadExhibitions()
        prepareAdapter()
    }

    private fun prepareAdapter() =
        with(binding) {
            rvExhibitions.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ExhibitionsListAdapter { id -> onItemClicked(id) }
            rvExhibitions.adapter = adapter

        }

    private fun initObserver() = with(viewModel) {
        exhibitionsContentState.observe(viewLifecycleOwner, ::setExhibitions)
    }

    private fun setExhibitions(contentResultState: ContentResultState) =
        contentResultState.refreshPage(
            viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                adapter.setData(it as List<Exhibition>)
                binding.rvExhibitions.adapter = adapter
            },
            errorLayout = binding.errorLayout,
            tryAgainAction = {
                tryAgain()
            }
        )

    private fun onItemClicked(id: Int) =
        ExhibitionsListFragmentDirections
            .toExhibitionDetailsFragment(id)
            .run { findNavController().navigate(this) }

    private fun tryAgain() {
        viewModel.getExhibitions()
    }
}