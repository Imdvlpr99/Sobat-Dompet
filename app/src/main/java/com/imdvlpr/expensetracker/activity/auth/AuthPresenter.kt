package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import com.imdvlpr.expensetracker.helper.utils.DispatchGroup
import com.imdvlpr.weatherappp.helper.base.BasePresenter

class AuthPresenter(private val context: Context) : BasePresenter<AuthInterface> {

    var view: AuthInterface? = null
    var dispatchGroup: DispatchGroup? = null

    override fun onAttach(view: AuthInterface) {
        TODO("Not yet implemented")
    }

    override fun onDetach() { view = null }

}