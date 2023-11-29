package net.ist.btn.shared.base

import com.imdvlpr.sobatdompet.helper.utils.DispatchGroup
import com.imdvlpr.weatherappp.helper.base.BaseView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseCoPresenter<V: BaseView>: CoroutineScope {
    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = supervisorJob + Dispatchers.Main
    protected var view: V? = null
    protected var dispatchGroup: DispatchGroup? = null

    open fun onAttach(view: V) {
        this.view = view
    }

    open fun onDetach() {
        this.view = null
        supervisorJob.cancelChildren()
    }
}