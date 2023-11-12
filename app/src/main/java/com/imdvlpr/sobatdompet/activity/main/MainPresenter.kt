package com.imdvlpr.sobatdompet.activity.main

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.weatherappp.helper.base.BasePresenter

class MainPresenter(private val context: Context) : BasePresenter<MainInterface> {

    var view: MainInterface? = null
    private var api: Api? = null
    private var fireStoreConnection: FireStoreConnection? = null
    private var dispatchGroup: DispatchGroup? = null

    override fun onAttach(view: MainInterface) {
        this.view = view
        if (api == null) api = Api(context)
        if (fireStoreConnection == null) fireStoreConnection = FireStoreConnection(context)
        if (dispatchGroup == null) {
            dispatchGroup = DispatchGroup()
            dispatchGroup?.notify { view.onFinishProgress() }
        }
    }

    override fun onDetach() { view = null }

    fun logout() {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection?.logout { response ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.postDelayed({
                when (response.isSuccess) {
                    true -> view?.onSuccessLogout()
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }, 2000)
        }
    }
}