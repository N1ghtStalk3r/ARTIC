package com.nightstalker.artic.core.presentation

import androidx.lifecycle.MutableLiveData
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.domain.error.parseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Функции для работы с [ContentResultState] в вью-моделях
 *
 * @author Tamerlan Mamukhov
 * @created 2022-10-24
 */
suspend fun onResultStateSuccess(
    contentsList: List<Any> = emptyList(),
    contentSingle: Any? = null,
    contentResultState: MutableLiveData<ContentResultState>,
) = withContext(Dispatchers.Main) {
    if (contentsList.isNotEmpty()) {
        contentResultState.postValue(ContentResultState.Content(contentsList = contentsList))
    }
    if (contentSingle != null) {
        contentResultState.postValue(ContentResultState.Content(contentSingle = contentSingle))
    }
}

suspend fun onResultStateError(isNetworkError: Boolean, contentResultState: MutableLiveData<ContentResultState>) =
    withContext(Dispatchers.Main) {
        contentResultState.value = ContentResultState.Error(error = isNetworkError.parseError())
    }