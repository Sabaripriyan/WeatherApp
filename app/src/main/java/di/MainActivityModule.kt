package di

import androidx.lifecycle.ViewModel
import core.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import view.MainActivity
import viewmodel.MainActivityViewModel

@Module
interface MainActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}
@Module
interface MainActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityViewModelModule::class])
    fun mainActivity(): MainActivity
}