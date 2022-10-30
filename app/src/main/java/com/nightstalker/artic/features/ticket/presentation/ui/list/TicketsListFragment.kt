package com.nightstalker.artic.features.ticket.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightstalker.artic.databinding.FragmentTicketsListBinding
import com.nightstalker.artic.features.ticket.domain.Ticket
import com.nightstalker.artic.features.ticket.presentation.ui.TicketsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Фрагмент для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */

class TicketsListFragment: Fragment()   {
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

            initObserver()
            ticketsViewModel.getTickets()
        }
    }

    private fun onItemClicked(id: Long) {
        TicketsListFragmentDirections
            .toTicketDetailsFragment(id.toInt())
            .run { findNavController().navigate(this) }
    }

    private fun initObserver() {
        ticketsViewModel.ticketsLoaded.observe(viewLifecycleOwner, ::setData)
    }

    private fun setData(tickets: List<Ticket>) {
        adapter.setData(tickets)
    }

}