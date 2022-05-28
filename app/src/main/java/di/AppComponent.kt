package di

import android.content.Context
import base.ListingApplication
import core.di.CoreModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import userinfo.ui.di.SplashFragmentModule
import userinfo.ui.di.user_info.UserListFragmentModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class, CoreModule::class, MainActivityModule::class, SplashFragmentModule::class, UserListFragmentModule::class
])

@Singleton
interface AppComponent: AndroidInjector<ListingApplication> {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun setContext(context: Context): Builder
    }
}