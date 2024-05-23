package com.engie.eea_tech_interview.di.koin

import com.engie.eea_tech_interview.data.repository.MovieRepositoryImpl
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}