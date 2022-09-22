package com.nightstalker.artic.features.exhibition.domain

/**
 * Класс выставки
 *
 * @property imageUrl           ссылка на главную картинку
 * @property galleryTitle       заголовок галереи
 * @property title              загаловок выставки
 * @property altImageIds        ид картинок
 * @property status             статус
 * @property shortDescription   краткое описание
 * @author Tamerlan Mamukhov
 */
data class Exhibition(
    val imageUrl: String,
    val galleryTitle: String,
    val title: String,
    val altImageIds: List<String>,
    val status: String,
    val shortDescription: String
)
