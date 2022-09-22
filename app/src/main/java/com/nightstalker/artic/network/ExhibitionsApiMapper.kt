package com.nightstalker.artic.network

import com.nightstalker.artic.core.network.BaseApiMapper
import com.nightstalker.artic.features.artwork.data.mappers.toDomain
import com.nightstalker.artic.network.net.ExhibitionsApi

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-16
 */
class ExhibitionsApiMapper(private val api: ExhibitionsApi) : BaseApiMapper() {
    suspend fun getExhibitionById(id: Int) = api.getExhibitionById(id).toDomain()
}