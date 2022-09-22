package com.nightstalker.artic.core.utils

/**
 * Создатель ссылок на картинки
 *
 * Его приходится использовать, чтобы получать данные с другого API (IIIF)
 * @author Tamerlan Mamukhov
 * @created 2022-09-16
 */
object ImageLinkCreator {
    fun createImageDefaultLink(imageId: String): String {
        return "https://www.artic.edu/iiif/2/$imageId/full/200,/0/default.jpg"
    }
}