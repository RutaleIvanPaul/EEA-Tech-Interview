package com.engie.eea_tech_interview

import androidx.multidex.MultiDexApplication
import com.engie.eea_tech_interview.di.koin.networkModule
import com.engie.eea_tech_interview.di.koin.repositoryModule
import com.engie.eea_tech_interview.di.koin.useCaseModule
import com.engie.eea_tech_interview.di.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

open class BaseApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(getDependencyModules())
        }
    }

    open fun getDependencyModules(): List<Module> {
        return listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }
}
