package com.nightstalker.artic.core.presentation.model

typealias SuccessStateAction = (content: Any?) -> Unit
typealias ErrorStateAction = (error: ErrorModel) -> Unit
typealias LoadingStateAction = () -> Unit

/**
 * Функция для удобной работы с готовым [ContentResultState] в фрагментах
 *
 * @param onStateSuccess        действие при успехе
 * @param onStateError          действие при неудаче
 * @author Tamerlan Mamukhov on 2023-01-07
 */
fun ContentResultState.handleContents(
    onStateSuccess: SuccessStateAction,
    onStateError: ErrorStateAction,
    onStateLoading: LoadingStateAction? = null
) = when (this) {
    is ContentResultState.Content -> {
        onStateSuccess.invoke(this.content)
    }
    is ContentResultState.Error -> {
        onStateError.invoke(this.error)
    }
    is ContentResultState.Loading -> {
        onStateLoading?.invoke()
    }
}