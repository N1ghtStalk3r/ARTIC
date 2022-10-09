package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentExhibitionsListBinding
import com.nightstalker.artic.features.exhibition.domain.Exhibition
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
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_exhibitions_list, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            this?.rvExhibitions?.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ExhibitionsListAdapter { id -> onItemClicked(id) }
            this?.rvExhibitions?.adapter = adapter

            exhibitionsViewModel.getExhibitions()

            initObserver()

            this?.ivFilterExh?.setOnClickListener {
                findNavController().navigate(R.id.filterExhibitionsDialogFragment)
            }
        }
    }

    private fun onItemClicked(id: Int) {
        ExhibitionsListFragmentDirections
            .toExhibitionDetailsFragment(id)
            .run { findNavController().navigate(this) }
    }

    private fun initObserver() {
        exhibitionsViewModel.exhibitionsLoaded.observe(viewLifecycleOwner, ::setData)
    }

    private fun setData(exhibitions: List<Exhibition>) {
        adapter.setData(exhibitions)
    }
}