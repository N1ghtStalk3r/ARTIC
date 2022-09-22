package com.nightstalker.artic.features.artwork.domain

/**
 * Класс произведения искусства
 *
 * @property id         ид
 * @property title      заголовок
 * @property imageId    ид картинки
 * @author Tamerlan Mamukhov
 */
data class Artwork(
    val id: Int,
    val title: String,
    val imageId: String
)
