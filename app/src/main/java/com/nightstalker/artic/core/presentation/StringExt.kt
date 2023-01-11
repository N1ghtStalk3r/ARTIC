package com.nightstalker.artic.core.presentation

import android.os.Build
import android.text.Html

/**
 * Функция для строк. Убирает тэги [Html]
 * @author Tamerlan Mamukhov on 2022-11-21
 */
fun String.filterHtmlEncodedText(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(replace("\n", "<br>"), Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(replace("\n", "<br>")).toString()
    }.apply {
        replace("\\</br.*?>", "\\\n")
    }
}
