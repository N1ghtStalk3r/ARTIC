package com.nightstalker.artic.core.presentation.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nightstalker.artic.core.presentation.ext.ui.handleErrorMessage
import com.nightstalker.artic.core.presentation.model.ErrorModel
import com.nightstalker.artic.databinding.LayoutErrorBinding

typealias TryAgainAction = () -> Unit

class LayoutErrorHandler(
    private val binding: LayoutErrorBinding,
    private val action: TryAgainAction,
    private val errorModel: ErrorModel,
    recyclerView: RecyclerView
) {
    init {
        recyclerView.adapter?.handleErrorMessage(
            onAdapterEmpty = {
                recyclerView.visibility = View.GONE
                binding.apply {
                    root.visibility = View.VISIBLE
                    btnErrorTryAgain.setOnClickListener { action }
                    textErrorDescription.setText(errorModel.description)
                }
            },
            onAdapterNotEmpty = {
                recyclerView.visibility = View.VISIBLE
                binding.apply {
                    root.visibility = View.GONE
                }
            }
        )

    }
}