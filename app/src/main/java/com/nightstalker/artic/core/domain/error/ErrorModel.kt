package com.nightstalker.artic.core.domain.error

import androidx.annotation.StringRes

data class ErrorModel(@StringRes val title: Int, @StringRes val description: Int)
