package com.nightstalker.artic.features.exhibition.domain.repo

import com.nightstalker.artic.features.exhibition.domain.Exhibition

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-16
 */
interface ExhibitionsRepo {
   suspend fun getExhibitionById(id: Int): Exhibition
}