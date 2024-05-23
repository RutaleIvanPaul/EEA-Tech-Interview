package com.engie.eea_tech_interview.di.koin

import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}