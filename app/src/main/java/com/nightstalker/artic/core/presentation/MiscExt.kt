package com.nightstalker.artic.core.presentation

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.nightstalker.artic.R

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-21
 */
fun EditText.onDone(callback: (query: String) -> Unit) = setOnEditorActionListener { _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        if (!text.isNullOrBlank()) {
            callback.invoke(text.trim().toString())
        }
        else {
            error = context.getString(R.string.enter_num_val)
        }
        return@setOnEditorActionListener false
    }
    false
}
