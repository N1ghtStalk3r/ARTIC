package com.nightstalker.artic.core.presentation.model

/**
 * Класс состояния содержимого в UI
 * @author Tamerlan Mamukhov
 * @created 2022-10-19
 */
sealed class ContentResultState {
    object Loading : ContentResultState()
    data class Error(val error: ErrorModel) : ContentResultState()
    data class Content(val content: Any? = null) :
        ContentResultState()
}