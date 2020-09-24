package com.imran.cavista.application

import android.app.Application
import com.imran.cavista.factory.ImageListViewModelFactory
import com.imran.cavista.network.ApiRequestService
import com.imran.cavista.network.intercepter.HeaderInterceptor
import com.imran.cavista.network.intercepter.NetworkInterceptor
import com.imran.cavista.repository.ImageListRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by imran on 2020-09-24.
 */
class CavistaApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CavistaApplication))
        bind() from singleton { NetworkInterceptor(instance()) }
        bind() from singleton { HeaderInterceptor() }
        bind() from singleton { ApiRequestService(instance(), instance()) }
        bind() from singleton { ImageListRepository(instance()) }
        bind() from singleton { ImageListViewModelFactory(instance()) }
    }
}
