package com.nightstalker.artic.features.ticket.presentation.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentTicketDetailsBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import com.nightstalker.artic.features.qrcode.QrCodeGenerator
import kotlinx.android.synthetic.main.fragment_ticket_details.qrGeneratorscannerButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketDetailsFragment : Fragment() {
    private val args: TicketDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private var binding: FragmentTicketDetailsBinding? = null

    // QR-code
    private var posterImageUrl = ""
    private val qrCode = QrCodeGenerator()


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

        val id = args.exhibitionId
        exhibitionsViewModel.getExhibition(id)
        initObserver()
        setViewQrCode()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObserver() {
        exhibitionsViewModel.exhibitionContentState.observe(viewLifecycleOwner, ::handle)
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
        this?.titleTextView?.text = exhibition?.title.orEmpty()
        this?.exhibitionIdTextView?.text = exhibition?.galleryTitle.toString()
        posterImageUrl = exhibition?.imageUrl.orEmpty()

        if (exhibition != null)
            qrCode.setDataForQRCode(exhibition)

        this?.qrCodeImageView?.setImageBitmap(
            qrCode.makeImage(qrCode.getTextQRCode())
        )

    }


    private fun setViewQrCode() {
        with(binding) {
            this?.qrGeneratorscannerButton?.apply {
                setText(R.string.qr_code_url)
                setOnClickListener {
                    qrCodeImageView.setImageBitmap(
                        qrCode.makeImage(qrCode.getTextQRCode())
                    )
                }
            }

            when (qrCode.getCounter() % 2) {
                GET_CODE_FROM_TITLE -> qrGeneratorscannerButton?.setText(R.string.qr_code_ticket)
                GET_CODE_FROM_URL_POSTER -> qrGeneratorscannerButton?.setText(R.string.qr_code_url)
                else -> {}
            }
        }
    }

    companion object {
        private const val GET_CODE_FROM_TITLE = 0
        private const val GET_CODE_FROM_URL_POSTER = 1
    }
}