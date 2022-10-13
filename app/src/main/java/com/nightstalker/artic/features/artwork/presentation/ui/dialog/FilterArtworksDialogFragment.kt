package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksDialogBinding

/**
 * @author Tamerlan Mamukhov
 * @created 2022-10-06
 */
class FilterArtworksDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentFilterArtworksDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilterArtworksDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            btnClose.setOnClickListener {
                dialog?.dismiss()
            }
        }

        setViews()
    }

    override fun getTheme(): Int = R.style.DialogTheme

    private fun setViews() = with(binding) {
        recentAcquisition.tvTitle.text = getString(R.string.filter_content_recent)
        publicDomain.tvTitle.text = getString(R.string.filter_content_pub_dom)
        educationalResources.tvTitle.text = getString(R.string.filter_content_has_materials)
    }
}