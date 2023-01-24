package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.filterHtmlEncodedText
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.databinding.FragmentExhibitionDetailsBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения деталей выставки
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ExhibitionDetailsFragment : Fragment() {
    private val args: ExhibitionDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel by viewModel<ExhibitionDetailsViewModel>()
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
        exhibitionsViewModel.exhibitionContentState.observe(viewLifecycleOwner, ::handleExhibition)
    }

    private fun handleExhibition(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
                setViews(it as Exhibition)
            },
            onStateError = {
                Log.d(TAG, "handle: $it")
            }
        )

    private fun setViews(exhibition: Exhibition) = with(binding) {
        this?.titleTextView?.text = exhibition.title.orEmpty()
        this?.tvDescription?.text = exhibition.shortDescription?.filterHtmlEncodedText()?.orEmpty()
        this?.tvStatus?.text = exhibition.status.orEmpty()

        val imageUrl = exhibition.imageUrl.orEmpty()
        this?.ivImage?.load(imageUrl)
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