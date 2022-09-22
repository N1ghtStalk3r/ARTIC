package com.nightstalker.artic.features.exhibition.data

import com.nightstalker.artic.features.exhibition.domain.Exhibition
import com.nightstalker.artic.features.exhibition.domain.repo.ExhibitionsRepo
import com.nightstalker.artic.network.ExhibitionsApiMapper

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-17
 */
class ExhibitionsRepoImpl(
    private val apiMapper: ExhibitionsApiMapper
) : ExhibitionsRepo {
    override suspend fun getExhibitionById(id: Int): Exhibition =
        apiMapper.getExhibitionById(id)
}