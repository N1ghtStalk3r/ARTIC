package com.nightstalker.artic.features.ticket.presentation.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nightstalker.artic.MainActivity
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.core.presentation.reformatIso8601
import com.nightstalker.artic.databinding.FragmentTicketDetailsBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.detail.ExhibitionDetailsViewModel
import com.nightstalker.artic.features.qrcode.QrCodeGenerator
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.artic.features.toCalendarEvent
import com.nightstalker.artic.features.toExhibitionTicket
import kotlinx.android.synthetic.main.fragment_ticket_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TicketDetailsFragment : Fragment() {
    private val args: TicketDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionDetailsViewModel>()
    private val ticketViewModel by viewModel<TicketDetailsViewModel>()
    private lateinit var binding: FragmentTicketDetailsBinding

    private lateinit var undoExhibitionTicket: ExhibitionTicket

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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    // 2023-01-10 Не могу пока разобраться с логикой здесь. Этот код писал не я
    // Разберу его обязательно...
    private fun initTicketDetailsFragment() {
        // Форма TicketDetailsFragment открывается при покупке билета и из списка билетов
        // При покупке билета нам известен только id выставки
        // Из списка билетов получаем id билета

        var exhibition_id: Int = -1   // заполняется в ExhibitionDetailsFragment
        var ticket_id: Long = -1L     // заполняется в TicketsListFragment

        // exhibition_id получаем из ExhibitionDetailsFragment через bundle
        arguments?.getInt(ApiConstants.BUNDLE_EXHIBITION_ID)?.let {
            if (it > 0) {
                exhibition_id = it
                exhibitionsViewModel.getExhibition(exhibition_id)
                initExhibitionObserver()
            }
        }

        // ticket_id получаем из TicketsListFragment как аргумент фрагмента
        args.ticketId.toLong().let {
            if (it > 0L) {
                ticket_id = it
                ticketViewModel.getTicket(it)
                initTicketObserver()
            }
        }
        Log.d("TicketDetails", " ExhibitionId  = ${exhibition_id}, ticket_id = ${ticket_id} ")


        // удаление билета
        binding.deleteTicketButton.setOnClickListener {
            Log.d("deleteTicketButton", " was clicked")

            ticketViewModel.deleteTicket(
                ticketId = ticket_id,
                exhibitionId = exhibition_id.toString()
            )

            this.deleteTicketButton.visibility = View.INVISIBLE
            this.undoTicketButton.visibility = View.VISIBLE
        }
        // восстановление билета
        binding.undoTicketButton.setOnClickListener {
            Log.d("undoTicketButton", " was clicked")

            ticketViewModel.saveTicket(undoExhibitionTicket)

            this.deleteTicketButton.visibility = View.VISIBLE
            this.undoTicketButton.visibility = View.INVISIBLE
        }
    }


    private fun initTicketObserver() {
        ticketViewModel.ticketContent.observe(viewLifecycleOwner, ::handleTicket)
    }

    private fun initExhibitionObserver() {
        exhibitionsViewModel.exhibitionContentState.observe(viewLifecycleOwner, ::handleTicket)
    }

    private fun handleTicket(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
                when (it) {
                    is Exhibition -> setViews(it)
                    is ExhibitionTicket -> setData(it)
                }
            },
            onStateError = {
                Log.d("Ticket ADF", "handle: $it")
            }
        )

    private fun setViews(exhibition: Exhibition?) {
        if (exhibition != null) {
            ticketViewModel.saveTicket(exhibition.toExhibitionTicket())
            setData(exhibition.toExhibitionTicket())
        }
    }

    private fun setData(ticket: ExhibitionTicket?) = with(binding) {

        Log.d(
            "TicketDetails setData",
            " ExhibitionId  = ${ticket?.exhibitionId}, ticket_id = ${ticket?.id} "
        )

        undoExhibitionTicket = ticket ?: ExhibitionTicket()

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