package com.nightstalker.artic.features.ticket.presentation.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nightstalker.artic.MainActivity
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentTicketDetailsBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import com.nightstalker.artic.features.qrcode.QrCodeGenerator
import com.nightstalker.artic.features.reformatIso8601
import com.nightstalker.artic.features.ticket.domain.TicketUseCase
import com.nightstalker.artic.features.ticket.presentation.ui.TicketsViewModel
import com.nightstalker.artic.features.ticket.presentation.ui.list.TicketsListAdapter
import com.nightstalker.artic.features.toCalendarEvent
import com.nightstalker.artic.features.toTicketUseCase
import com.nightstalker.artic.network.ApiConstants
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketDetailsFragment : Fragment() {
    private val args: TicketDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private val ticketsViewModel by viewModel<TicketsViewModel>()
    private val ticketViewModel by viewModel<TicketsViewModel>()
    private lateinit var binding: FragmentTicketDetailsBinding
    private lateinit var adapter: TicketsListAdapter

    // В форму DetailsFragment переходят из списка билетов  и  при покупке билета
    private var exhibition_id: Int = -1   // заполняется в ExhibitionDetailsFragment
    private var ticket_id: Long = -1L     // заполняется в TicketsListFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ticket_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTicketDetailsBinding.bind(view)

        // Из списка билетов получаем положительные args.ticketId,
        // при покупке билета получаем args.ticketId < 0

        exhibition_id = arguments?.getInt("ExhibitionId") ?: ApiConstants.DEFAULT_EXHIBITION_ID
        ticket_id = args.ticketId.toLong()
        Log.d("TicketDetails", " ExhibitionId  = ${exhibition_id}, ticket_id = ${ticket_id} ")

        when {
            exhibition_id > 0 -> {
                exhibitionsViewModel.getExhibition(exhibition_id)
                initExhibitionObserver()
            }
            ticket_id > 0 -> {
                ticketsViewModel.getTicket(ticket_id)
                initTicketObserver()
            }
        }

        // удаление билета
        binding.deleteTicketButton.setOnClickListener {
            Log.d("deleteTicketButton", " was clicked")
            ticketsViewModel.deleteTicket(
                ticketId = ticket_id,
                exhibitionId = exhibition_id.toString()
            )
            findNavController().navigate(R.id.ticketsListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initExhibitionObserver() {
        exhibitionsViewModel.exhibitionContentState.observe(viewLifecycleOwner, ::handle)
    }

    private fun initTicketObserver() {
        ticketViewModel.ticketLoaded.observe(viewLifecycleOwner, ::setData)
    }

    private fun handle(contentResultState: ContentResultState) = when (contentResultState) {
        is ContentResultState.Content -> contentResultState.handle()
        is ContentResultState.Error -> contentResultState.handle()
        ContentResultState.Loading -> TODO()
    }

    private fun ContentResultState.Content.handle() {
        Log.d("Ticket ADF", "handle: $contentSingle")
        setViews(contentSingle as Exhibition)
    }

    private fun ContentResultState.Error.handle() {
        Log.d("Ticket ADF", "handle: $error")
    }

    private fun setViews(exhibition: Exhibition?) = with(binding) {
        if (exhibition != null) {
            ticketViewModel.saveTicket(exhibition.toTicketUseCase())
            setData(exhibition.toTicketUseCase())
        }
    }

    private fun setData(ticket: TicketUseCase?) = with(binding) {

        Log.d(
            "TicketDetails setData",
            " ExhibitionId  = ${ticket?.exhibitionId}, ticket_id = ${ticket?.id} "
        )

        this.titleTextView.text = ticket?.title.orEmpty()
        this.exhibitionIdTextView.text = ticket?.galleryTitle.toString()

        this.aicStartAtTextView.text =
            "${ApiConstants.LABEL_AICSTARTAT} : ${ticket?.aicStartAt.toString().reformatIso8601()}"
        this.aicEndAtTextView.text =
            "${ApiConstants.LABEL_AICENDAT} : ${ticket?.aicEndAt.toString().reformatIso8601()}"


        //QRCode
        this.qrCodeImageView.setImageBitmap(
            QrCodeGenerator().makeImage("ЭПИК и ARTIC представляют: ${ticket?.title} в ${ticket?.galleryTitle}")
        )

        // Регистрация нового события в календаре Google
        binding.addCalendarEventButton.setOnClickListener {
            Log.d("addCalendarEventButton", " was clicked")
            if (ticket != null)
                (activity as MainActivity).addCalendarEvent(ticket.toCalendarEvent())
        }
    }
}