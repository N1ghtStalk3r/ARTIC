package com.nightstalker.artic.core.di

import com.nightstalker.artic.core.utils.Constants.IO_DISP_NAME
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @author Tamerlan Mamukhov
 * @created 2022-10-10
 */
val dispatchersModule = module {
    single(named(IO_DISP_NAME)) {
        Dispatchers.IO
    }
}