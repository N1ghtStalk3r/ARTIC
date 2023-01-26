package com.nightstalker.artic.core.presentation.ext.ui

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.nightstalker.artic.R

/**
 * Ext-функции для вьюшек
 * @author Tamerlan Mamukhov on 2022-11-21
 */

/**
 * Функция для [EditText] -- слушатель текста
 *
 * Выполняет поиск
 */
fun EditText.onDone(callback: (query: String) -> Unit) =
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!text.isNullOrBlank()) {
                callback.invoke(text.trim().toString())
            } else {
                error = context.getString(R.string.enter_num_val)
            }
            return@setOnEditorActionListener false
        }
        false
    }

/**
 * Выполняет функции при не/пустом адаптере
 *
 * @param onAdapterEmpty        действие при пустом адаптере
 * @param onAdapterNotEmpty     действие при непустом адаптере
 */
fun RecyclerView.Adapter<RecyclerView.ViewHolder>.handleErrorMessage(
    onAdapterEmpty: () -> Unit,
    onAdapterNotEmpty: () -> Unit
) =
    when (this.itemCount) {
        0 -> onAdapterEmpty.invoke()
        else -> onAdapterNotEmpty.invoke()
    }
