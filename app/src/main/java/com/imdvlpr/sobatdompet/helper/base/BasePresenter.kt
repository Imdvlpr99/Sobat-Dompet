package com.imdvlpr.weatherappp.helper.base

interface BasePresenter<in T : BaseView> {

    fun onAttach(view: T)

    fun onDetach()
}