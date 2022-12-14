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
import kotlinx.android.synthetic.main.fragment_ticket_details.deleteTicketButton
import kotlinx.android.synthetic.main.fragment_ticket_details.undoTicketButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketDetailsFragment : Fragment() {
    private val args: TicketDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private val ticketsViewModel by viewModel<TicketsViewModel>()
    private val ticketViewModel by viewModel<TicketsViewModel>()
    private lateinit var binding: FragmentTicketDetailsBinding

    private lateinit var undoTicketUseCase: TicketUseCase

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

        initTicketDetailsFragment()
    }

    private fun initTicketDetailsFragment() {
        // ?????????? TicketDetailsFragment ?????????????????????? ?????? ?????????????? ???????????? ?? ???? ???????????? ??????????????
        // ?????? ?????????????? ???????????? ?????? ???????????????? ???????????? id ????????????????
        // ???? ???????????? ?????????????? ???????????????? id ????????????

        var exhibition_id: Int = -1   // ?????????????????????? ?? ExhibitionDetailsFragment
        var ticket_id: Long = -1L     // ?????????????????????? ?? TicketsListFragment

        // exhibition_id ???????????????? ???? ExhibitionDetailsFragment ?????????? bundle
        arguments?.getInt(ApiConstants.BUNDLE_EXHIBITION_ID)?.let {
            if (it > 0) {
                exhibition_id = it
                exhibitionsViewModel.getExhibition(exhibition_id)
                initExhibitionObserver()
            }
        }

        // ticket_id ???????????????? ???? TicketsListFragment ?????? ???????????????? ??????????????????
        args.ticketId.toLong().let {
            if (it > 0L) {
                ticket_id = it
                ticketsViewModel.getTicket(it)
                initTicketObserver()
            }
        }
        Log.d("TicketDetails", " ExhibitionId  = ${exhibition_id}, ticket_id = ${ticket_id} ")



        // ???????????????? ????????????
        binding.deleteTicketButton.setOnClickListener {
            Log.d("deleteTicketButton", " was clicked")

            ticketsViewModel.deleteTicket(
                ticketId = ticket_id,
                exhibitionId = exhibition_id.toString()
            )

            this.deleteTicketButton.visibility = View.INVISIBLE
            this.undoTicketButton.visibility = View.VISIBLE
        }
        // ???????????????????????????? ????????????
        binding.undoTicketButton.setOnClickListener {
            Log.d("undoTicketButton", " was clicked")

            ticketViewModel.saveTicket(undoTicketUseCase)

            this.deleteTicketButton.visibility = View.VISIBLE
            this.undoTicketButton.visibility = View.INVISIBLE
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
        else -> {}
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

        undoTicketUseCase = ticket?:TicketUseCase()

        this.titleTextView.text = ticket?.title.orEmpty()
        this.exhibitionIdTextView.text = ticket?.galleryTitle.toString()

        this.aicStartAtTextView.text =
            "${ApiConstants.LABEL_AICSTARTAT} : ${ticket?.aicStartAt.toString().reformatIso8601()}"
        this.aicEndAtTextView.text =
            "${ApiConstants.LABEL_AICENDAT} : ${ticket?.aicEndAt.toString().reformatIso8601()}"


        //QRCode
        this.qrCodeImageView.setImageBitmap(
            QrCodeGenerator().makeImage("???????? ?? ARTIC ????????????????????????: ${ticket?.title} ?? ${ticket?.galleryTitle}")
        )

        // ?????????????????????? ???????????? ?????????????? ?? ?????????????????? Google
        binding.addCalendarEventButton.setOnClickListener {
            Log.d("addCalendarEventButton", " was clicked")
            if (ticket != null)
                (activity as MainActivity).addCalendarEvent(ticket.toCalendarEvent())
        }
    }
}