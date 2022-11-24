package com.nightstalker.artic.network

import com.nightstalker.artic.core.network.BaseApiMapper
import com.nightstalker.artic.features.toExhibition
import com.nightstalker.artic.features.toListOfExhibitions
import com.nightstalker.artic.network.net.ArtworksApi
import com.nightstalker.artic.network.net.ExhibitionsApi

/**
 * Класс получения данных с помощью [ArtworksApi]
 *
 * @property api    API
 * @author Tamerlan Mamukhov on 2022-09-16
 */
class ExhibitionsApiMapper(private val api: ExhibitionsApi) : BaseApiMapper() {
    suspend fun getExhibitionById(id: Int) = api.getExhibitionById(id).data.toExhibition()

    suspend fun getExhibitions() = api.getExhibitions().data.toListOfExhibitions()
}