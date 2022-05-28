package userinfo.ui.di

import core.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import userinfo.ui.view.splash.SplashFragment
import userinfo.ui.viewmodel.splash.SplashViewModel

@Module
interface SplashViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}

@Module
interface SplashFragmentModule {
    @ContributesAndroidInjector(modules = [SplashViewModelModule::class])
    fun splashFragment(): SplashFragment
}