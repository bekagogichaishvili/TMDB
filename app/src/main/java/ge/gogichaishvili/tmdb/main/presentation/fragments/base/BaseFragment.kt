package ge.gogichaishvili.tmdb.main.presentation.fragments.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import kotlin.reflect.KClass

open class BaseFragment<VM : BaseViewModel>(clazz: KClass<VM>) : Fragment() {

    protected val mViewModel: VM by if (isGlobal()) sharedViewModel(
        clazz = clazz,
        parameters = getParams(),
    ) else viewModel(
        clazz = clazz,
        parameters = getParams()
    )

    open fun bindLifeCycleToViewModel() = false

    open fun isGlobal() = false

    open fun getParams(): ParametersDefinition? = null

    open fun bindObservers() {}

    open fun bindViewActionListeners() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (bindLifeCycleToViewModel()) {
            if (mViewModel is LifecycleObserver)
                lifecycle.addObserver(mViewModel as LifecycleObserver)
            else
                throw Exception("View model must implement LifecycleObserver interface")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewActionListeners()
        bindObservers()
    }

}