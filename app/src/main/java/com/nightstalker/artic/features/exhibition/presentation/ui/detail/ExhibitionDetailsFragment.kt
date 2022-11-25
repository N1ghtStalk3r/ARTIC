package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.filterHtmlEncodedText
import com.nightstalker.artic.databinding.FragmentExhibitionDetailsBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import com.nightstalker.artic.network.ApiConstants
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

    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_exhibition_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExhibitionDetailsBinding.bind(view)

        val id = args.exhibitionId
        exhibitionsViewModel.getExhibition(id)
        initObserver()
        buyTicket()
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
        else -> {}
    }

    @SuppressLint("LongLogTag")
    private fun ContentResultState.Content.handle() {
        Log.d(TAG, "handle: $contentSingle")
        setViews(contentSingle as Exhibition)
    }

    @SuppressLint("LongLogTag")
    private fun ContentResultState.Error.handle() {
        Log.d(TAG, "handle: $error")
    }

    private fun setViews(exhibition: Exhibition) = with(binding) {
        this?.titleTextView?.text = exhibition.title.orEmpty()
        this?.tvDescription?.text = exhibition.shortDescription?.filterHtmlEncodedText()?.orEmpty()
        this?.tvStatus?.text = exhibition.status.orEmpty()

        val context = this?.ivImage?.context
        val imageUrl = exhibition.imageUrl.orEmpty()
        if (context != null) {
            this?.ivImage?.let { Glide.with(context).load(imageUrl).into(it) }
        }
    }

    private fun buyTicket() =
        binding?.buyTicketFloatingActionButton?.setOnClickListener {
            bundle.putInt(ApiConstants.BUNDLE_EXHIBITION_ID, args.exhibitionId)
            findNavController().navigate(R.id.ticketDetailsFragment, bundle)
        }

    companion object {
        const val TAG = "ExhibitionDetailFragment"
    }
}