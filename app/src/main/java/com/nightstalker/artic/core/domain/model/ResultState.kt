package com.nightstalker.artic.core.domain.model

/**
 * Класс, оборачивающий запрашиваемые данные из Сети
 *
 * @param R
 * @constructor Create empty Result state
 */
sealed class ResultState<out R> {
    data class Success<out S>(val data: S) : ResultState<S>()
    data class Error(val errorData: Boolean) : ResultState<Nothing>()
}

val <S>ResultState<S>.data: S?
    get() = (this as? ResultState.Success)?.data

val <S>ResultState<S>.errorDate: Boolean?
    get() = (this as? ResultState.Error)?.errorData