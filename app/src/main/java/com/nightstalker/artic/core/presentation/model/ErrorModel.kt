package com.nightstalker.artic.core.presentation.model

import androidx.annotation.StringRes

/**
 * Класс ошибки
 *
 * @property title          заголовок
 * @property description    описание
 * @constructor Create empty Error model
 */
data class ErrorModel(@StringRes val title: Int, @StringRes val description: Int)
