package com.imdvlpr.sobatdompet.di

import com.imdvlpr.sobatdompet.activity.auth.AuthPresenter
import com.imdvlpr.sobatdompet.activity.forgot.ForgotPresenter
import com.imdvlpr.sobatdompet.activity.main.MainPresenter
import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import org.koin.dsl.module

val appModule = module {
    single { Api(get()) }
    single { FireStoreConnection(get()) }
}

val mvpModule = module {
    factory { AuthPresenter(get(), get()) }
    factory { ForgotPresenter(get(), get()) }
    factory { MainPresenter(get(), get()) }
}