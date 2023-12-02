package com.imdvlpr.sobatdompet.activity.forgot

import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.sobatdompet.model.Forgot
import com.imdvlpr.sobatdompet.model.OTP
import kotlinx.coroutines.launch
import net.ist.btn.shared.base.BaseCoPresenter

class ForgotPresenter(private val api: Api, private val fireStoreConnection: FireStoreConnection) : BaseCoPresenter<ForgotInterface>() {

    override fun onAttach(view: ForgotInterface) {
        super.onAttach(view)
        if (dispatchGroup == null) {
            dispatchGroup = DispatchGroup()
            dispatchGroup?.notify { view.onFinishProgress() }
        }
    }

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

    fun forgot(forgot: Forgot) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection.forgot(forgot) { data, response ->
            launch {
                when (response.isSuccess) {
                    true -> view?.onSuccessForgot(data)
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }

    fun updateCredential(forgot: Forgot) {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection.updateAccountCredential(forgot) { response ->
            launch {
                when (response.isSuccess) {
                    true -> view?.onSuccessUpdateAccount(response.message)
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }
}