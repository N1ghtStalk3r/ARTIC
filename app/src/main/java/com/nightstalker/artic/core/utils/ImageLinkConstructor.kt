package com.nightstalker.artic.core.utils

/**
 * Конструктор ссылок на картинки
 *
 * Его приходится использовать, чтобы получать данные с другого API
 * @author Tamerlan Mamukhov
 * @created 2022-09-16
 */
object ImageLinkConstructor {
    fun createImageDefaultLink(imageId: String): String {
        return "https://www.artic.edu/iiif/2/$imageId/full/200,/0/default.jpg"
    }
}