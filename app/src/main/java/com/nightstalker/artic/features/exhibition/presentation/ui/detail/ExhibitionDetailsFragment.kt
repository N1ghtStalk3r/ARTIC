package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentExhibitionDetailsBinding
import com.nightstalker.artic.features.exhibition.domain.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения деталей выставки
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ExhibitionDetailsFragment : Fragment() {
    private val args: ExhibitionDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private var binding: FragmentExhibitionDetailsBinding? = null

// to_Ticket
    private var buyTicketFloatingActionButton : FloatingActionButton? = null
    private var exhibitionState : Exhibition? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exhibition_details, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewQRcode(view)
        val id = args.exhibitionId
        exhibitionsViewModel.getExhibition(id)
        initObserver()
    }

    private fun initObserver() {
        // exhibitionsViewModel.exhibitionLoaded.observe(viewLifecycleOwner, ::setViews)
    }

    private fun setViews(exhibition: Exhibition?) = with(binding) {
        exhibitionState = exhibition
        titleTextView.text = exhibition?.title.orEmpty()
        tvDescription.text = exhibition?.shortDescription.orEmpty()
        tvStatus.text = exhibition?.status.orEmpty()
        this?.tvTitle?.text = exhibition?.title.orEmpty()
        this?.tvDescription?.text = exhibition?.shortDescription.orEmpty()
        this?.tvStatus?.text = exhibition?.status.orEmpty()

        val context = ivImage.context
        val imageUrl = exhibition?.imageUrl.orEmpty()
        if (context != null) {
            this?.ivImage?.let { Glide.with(context).load(imageUrl).into(it) }
        }
    }

    private fun setViewQRcode(view: View) {
        buyTicketFloatingActionButton =  view.findViewById(R.id.buyTicketFloatingActionButton)

        buyTicketFloatingActionButton?.setOnClickListener {
            ExhibitionDetailsFragmentDirections
                .toTicketDetailsFragment(args.exhibitionId)
                .run { findNavController().navigate(this) }
        }
    }

}