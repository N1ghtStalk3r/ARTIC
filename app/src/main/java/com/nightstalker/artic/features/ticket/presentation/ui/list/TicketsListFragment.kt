package com.nightstalker.artic.features.ticket.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.databinding.FragmentTicketsListBinding
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Фрагмент для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */

class TicketsListFragment : Fragment() {
    private lateinit var adapter: TicketsListAdapter
    private val ticketsViewModel by viewModel<TicketsViewModel>()
    private lateinit var binding: FragmentTicketsListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvTickets.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = TicketsListAdapter { id -> onItemClicked(id) }
            rvTickets.adapter = adapter

            ticketsViewModel.getAllTickets()
            initObserver()
        }
    }

    private fun initObserver() {
        ticketsViewModel.ticketsContent.observe(viewLifecycleOwner, ::handleTickets)
    }

    private fun handleTickets(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
                adapter.setData(it as List<ExhibitionTicket>)
            },
            onStateError = {

            }
        )

    private fun onItemClicked(id: Long) {
        TicketsListFragmentDirections
            .toTicketDetailsFragment(id.toInt())
            .run { findNavController().navigate(this) }
    }

}