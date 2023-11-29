package com.imdvlpr.sobatdompet.activity.auth

import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.sobatdompet.model.Login
import com.imdvlpr.sobatdompet.model.OTP
import com.imdvlpr.sobatdompet.model.Register
import kotlinx.coroutines.launch
import net.ist.btn.shared.base.BaseCoPresenter

class AuthPresenter(private val api: Api, private val fireStoreConnection: FireStoreConnection) : BaseCoPresenter<AuthInterface>() {

    override fun onAttach(view: AuthInterface) {
        super.onAttach(view)
        if (dispatchGroup == null) {
            dispatchGroup = DispatchGroup()
            dispatchGroup?.notify { view.onFinishProgress() }
        }
    }

    override fun onDetach() { view = null }

    fun sendOtp(data: OTP) {
        view?.onProgress()
        dispatchGroup?.enter()

        api.sendOtp(data) { response ->
            launch {
                when(response.isSuccess) {
                    true -> view?.onSuccessSendOtp(response)
                    else -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }

    fun sendOtpEmail(data: OTP) {
        view?.onProgress()
        dispatchGroup?.enter()

        api.sendOtpEmail(data) { response ->
            launch {
                when (response.isSuccess) {
                    true -> view?.onSuccessSendOtp(response)
                    else -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }

    fun verifyOtp(data: OTP) {
        view?.onProgress()
        dispatchGroup?.enter()

        api.verifyEmail(data) { response ->
            launch {
                when (response.isSuccess) {
                    true -> view?.onSuccessVerifyOtp(response)
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }

    fun checkUsers(register: Register) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection.checkUsers(register) { response ->
            launch {
                when(response.isSuccess) {
                    true -> view?.onSuccessCheckUsers(response)
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }

    fun registerUser(register: Register) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection.registerUser(register) { response ->
            launch {
                when(response.isSuccess) {
                    true -> view?.onSuccessRegister()
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }

    fun loginUsername(login: Login) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection.loginUsername(login) { data, statusResponse ->
            launch {
                when (statusResponse.isSuccess) {
                    true -> view?.onSuccessLogin(data)
                    false -> view?.onFailed(statusResponse.message)
                }
                dispatchGroup?.leave()
            }
        }
    }
}