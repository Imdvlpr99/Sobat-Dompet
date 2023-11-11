package com.imdvlpr.sobatdompet.activity.forgot

import android.content.Context
import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.weatherappp.helper.base.BasePresenter

class ForgotPresenter(private val context: Context) : BasePresenter<ForgotInterface> {

    var view: ForgotInterface? = null
    private var fireStoreConnection: FireStoreConnection? = null
    private var dispatchGroup: DispatchGroup? = null

    override fun onAttach(view: ForgotInterface) {
        this.view = view
        if (fireStoreConnection == null) fireStoreConnection = FireStoreConnection(context)
        if (dispatchGroup == null) {
            dispatchGroup = DispatchGroup()
            dispatchGroup?.notify { view.onFinishProgress() }
        }
    }

    override fun onDetach() { view = null }
}