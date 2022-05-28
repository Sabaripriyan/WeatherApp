package core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import core.enum.ViewModelScope
import core.view.BaseActivity
import core.view.BaseFragment
import java.lang.ClassCastException
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.javaType

class ViewModelDelegate<in R,T : ViewModel> constructor(
    private val scope: ViewModelScope = ViewModelScope.FRAGMENT
): ReadOnlyProperty<R,T> {

    private var viewModel: T? = null

    @OptIn(ExperimentalStdlibApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if(viewModel == null) {
            val type: KType = property.getter.returnType
            val javaType = type.javaType
            val javaClass = javaType as? Class<T>
                ?: throw ClassCastException (
                    "Can't be casted to ViewModel"
                        )
            if(thisRef is BaseActivity){
                viewModel =  createViewModel(thisRef,javaClass)
            } else if (thisRef is BaseFragment) {
                viewModel = createViewModel(thisRef,javaClass)
            }
        }
        return viewModel!!
    }

    private fun createViewModel(fragment: BaseFragment, javaClass: Class<T>): T {
        val factory = fragment.viewModelFactory
        return when(scope) {
            ViewModelScope.FRAGMENT -> ViewModelProvider(fragment,factory)
                .get(javaClass)

            ViewModelScope.PARENT_FRAGMENT -> ViewModelProvider(fragment.requireParentFragment(),factory)
                .get(javaClass)

            ViewModelScope.ACTIVITY -> ViewModelProvider(fragment.requireParentActivity(),factory)
                .get(javaClass)
        }

    }

    private fun createViewModel(activity: BaseActivity, javaClass: Class<T>): T {
        val factory = activity.viewModelFactory
        return ViewModelProvider(activity,factory)
            .get(javaClass)
    }

    private fun Fragment.requireParentActivity(): FragmentActivity {
        return this.activity ?: throw IllegalStateException(
            "${this.javaClass.simpleName} is not attached to activity"
        )
    }

}