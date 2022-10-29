package com.nightstalker.artic.features.ticket.presentation.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentTicketDetailsBinding
import com.nightstalker.artic.features.exhibition.domain.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import com.nightstalker.artic.features.qrcode.QRCodeGenerator
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketDetailsFragment: Fragment() {
    private val args: TicketDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private lateinit var binding: FragmentTicketDetailsBinding

    // QR-code
    private var qrCodeImageView: ImageView? = null
    private var bBuy: Button? = null
    private var bScanner: Button? = null
    private var cnt = 0
    private var posterImageUrl = ""
    private val qrCode = QRCodeGenerator()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.exhibitionId
        exhibitionsViewModel.getExhibition(id)
        initObserver()
        setViewQRcode(view)
    }

    private fun initObserver() {
        exhibitionsViewModel.exhibitionLoaded.observe(viewLifecycleOwner, ::setViews)
    }


    private fun setViews(exhibition: Exhibition?) = with(binding) {
        titleTextView.text = exhibition?.title.orEmpty()
//        exhibitionIdTextView.text = exhibition?.id.toString()
        exhibitionIdTextView.text = exhibition?.galleryTitle.toString()
        posterImageUrl = exhibition?.imageUrl.orEmpty()

        if (exhibition != null)
            qrCode.setBaseForText(exhibition)

        qrCodeImageView.setImageBitmap(
            qrCode.makeImage(qrCode.getTextQRCode())
        )

    }


    private fun setViewQRcode(view: View) {

        qrCodeImageView = binding.qrCodeImageView
        bBuy =  binding.qrGeneratorscannerButton
        bBuy?.setText(R.string.qr_code_url)

        bBuy?.setOnClickListener{
            qrCodeImageView?.setImageBitmap(
                qrCode.makeImage(qrCode.getTextQRCode())
            )
            when(qrCode.getCounter()%2){
                0 -> bBuy?.setText(R.string.qr_code_ticket)
                1 -> bBuy?.setText(R.string.qr_code_url)
            }
        }
    }


}