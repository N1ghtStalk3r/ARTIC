package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.core.presentation.model.refreshPage
import com.nightstalker.artic.core.presentation.ui.LayoutErrorHandler
import com.nightstalker.artic.databinding.FragmentExhibitionsListBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка выставок
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ExhibitionsListFragment : Fragment() {

    private lateinit var adapter: ExhibitionsListAdapter
    private var binding: FragmentExhibitionsListBinding? = null
    private val viewModel by viewModel<ExhibitionsListViewModel>()

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

        initObserver()
        viewModel.getExhibitions()

        with(binding) {
            this?.rvExhibitions?.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ExhibitionsListAdapter { id -> onItemClicked(id) }
            this?.rvExhibitions?.adapter = adapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObserver() = with(viewModel) {
        exhibitionsContentState.observe(viewLifecycleOwner, ::setExhibitions)
    }

    private fun setExhibitions(contentResultState: ContentResultState) {
        contentResultState.refreshPage(
            binding?.rvExhibitions!!, binding?.progressBar!!, binding?.errorLayout!!
        )
        contentResultState.handleContents(
            onStateSuccess = {
                if (adapter.data.isEmpty()) {
                    adapter.setData(it as List<Exhibition>)
                    binding?.rvExhibitions?.adapter = adapter
                }
            },
            onStateError = {
                with(binding) {
                    LayoutErrorHandler(
                        this?.errorLayout!!,
                        { tryAgain() },
                        it,
                        this.rvExhibitions
                    )
                }
            }
        )
    }

    private fun onItemClicked(id: Int) {
        ExhibitionsListFragmentDirections
            .toExhibitionDetailsFragment(id)
            .run { findNavController().navigate(this) }
    }

    private fun tryAgain() {
        binding?.errorLayout?.root?.visibility = View.INVISIBLE
        viewModel.getExhibitions()
    }
}