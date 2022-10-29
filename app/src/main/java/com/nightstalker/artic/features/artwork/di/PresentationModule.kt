package com.nightstalker.artic.features.artwork.di

import com.nightstalker.artic.core.utils.Constants.IO_DISP_NAME
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val artworkPresentationModule = module {
    viewModel { ArtworkViewModel(useCase = get(), dispatcher = get(named(IO_DISP_NAME))) }
}