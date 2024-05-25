package com.engie.eea_tech_interview.di.koin

import com.engie.eea_tech_interview.domain.usecases.GetGenresUseCase
import com.engie.eea_tech_interview.domain.usecases.GetMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoviesUseCase(get()) }
    single { GetGenresUseCase(get()) }
}