package com.imdvlpr.sobatdompet.activity.forgot

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.sobatdompet.model.Forgot
import com.imdvlpr.sobatdompet.model.OTP
import com.imdvlpr.weatherappp.helper.base.BasePresenter

class ForgotPresenter(private val context: Context) : BasePresenter<ForgotInterface> {

    var view: ForgotInterface? = null
    private var api: Api? = null
    private var fireStoreConnection: FireStoreConnection? = null
    private var dispatchGroup: DispatchGroup? = null

    override fun onAttach(view: ForgotInterface) {
        this.view = view
        if (api == null) api = Api(context)
        if (fireStoreConnection == null) fireStoreConnection = FireStoreConnection(context)
        if (dispatchGroup == null) {
            dispatchGroup = DispatchGroup()
            dispatchGroup?.notify { view.onFinishProgress() }
        }
    }

    override fun onDetach() { view = null }

    fun sendOtp(data: OTP) {
        view?.onProgress()
        dispatchGroup?.enter()

        api?.sendOtp(data) { response ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.postDelayed({
                when(response.isSuccess) {
                    true -> view?.onSuccessSendOtp(response)
                    else -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }, 2000)
        }
    }

    fun forgot(forgot: Forgot) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection?.forgot(forgot) { data, response ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.postDelayed({
                when (response.isSuccess) {
                    true -> view?.onSuccessForgot(data)
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }, 2000)
        }
    }
}