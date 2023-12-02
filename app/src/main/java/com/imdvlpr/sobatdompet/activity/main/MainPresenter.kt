package com.imdvlpr.sobatdompet.activity.main

import com.imdvlpr.sobatdompet.helper.firebase.FireStoreConnection
import com.imdvlpr.sobatdompet.helper.network.Api
import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import kotlinx.coroutines.launch
import net.ist.btn.shared.base.BaseCoPresenter

class MainPresenter(private val api: Api, private val fireStoreConnection: FireStoreConnection) : BaseCoPresenter<MainInterface>() {

    override fun onAttach(view: MainInterface) {
        super.onAttach(view)
        if (dispatchGroup == null) {
            dispatchGroup = DispatchGroup()
            dispatchGroup?.notify { view.onFinishProgress() }
        }
    }

    fun logout() {
        view?.onProgress()
        dispatchGroup?.enter()

        fireStoreConnection.logout { response ->
            launch {
                when (response.isSuccess) {
                    true -> view?.onSuccessLogout()
                    false -> view?.onFailed(response.message)
                }
                dispatchGroup?.leave()
            }
        }
    }
}