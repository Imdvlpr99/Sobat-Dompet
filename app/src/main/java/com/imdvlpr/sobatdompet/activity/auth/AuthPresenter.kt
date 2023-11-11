package com.imdvlpr.sobatdompet.activity.auth

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.sobatdompet.model.Login
import com.imdvlpr.sobatdompet.model.OTP
import com.imdvlpr.sobatdompet.model.Register
import com.imdvlpr.weatherappp.helper.base.BasePresenter

class AuthPresenter(private val context: Context) : BasePresenter<AuthInterface> {

    var view: AuthInterface? = null
    private var api: Api? = null
    private var fireStoreConnection: FireStoreConnection? = null
    private var dispatchGroup: DispatchGroup? = null

    override fun onAttach(view: AuthInterface) {
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

    fun checkUsers(register: Register) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection?.checkUsers(register) { response ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.postDelayed({
                when(response.isSuccess) {
                    true -> view?.onSuccessCheckUsers(response)
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }, 2000)
        }
    }

    fun registerUser(register: Register) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection?.registerUser(register) { response ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.postDelayed({
                when(response.isSuccess) {
                    true -> view?.onSuccessRegister()
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }, 2000)
        }
    }

    fun loginUsername(login: Login) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection?.loginUsername(login) { login, statusResponse ->
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.postDelayed({
                when (statusResponse.isSuccess) {
                    true -> view?.onSuccessLogin(login)
                    false -> view?.onFailed(statusResponse.message)
                }
                dispatchGroup?.leave()
            }, 2000)
        }
    }
}