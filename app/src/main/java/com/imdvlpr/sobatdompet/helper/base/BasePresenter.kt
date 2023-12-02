package com.imdvlpr.sobatdompet.helper.base

interface BasePresenter<in T : BaseView> {

    fun onAttach(view: T)

    fun onDetach()
}