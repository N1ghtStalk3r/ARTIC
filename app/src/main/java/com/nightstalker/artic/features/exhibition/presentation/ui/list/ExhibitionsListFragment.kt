package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.annotation.SuppressLint
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
    private lateinit var binding: FragmentExhibitionsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExhibitionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivFilterExh.tooltipText = getString(R.string.iv_filter)

            rvExhibitions.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ExhibitionsListAdapter { id -> onItemClicked(id) }
            rvExhibitions.adapter = adapter

            initObserver()
            exhibitionsViewModel.getExhibitions()
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