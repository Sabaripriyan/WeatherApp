package core.di

import android.content.Context
import base.ListingApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import userinfo.data.di.UserInfoApiModule
import userinfo.data.di.UserInfoRepoModule
import userinfo.ui.di.SplashFragmentModule
import userinfo.ui.di.user_info.UserInfoFragmentModule
import userinfo.ui.di.user_info.UserListFragmentModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class, CoreModule::class, RetrofitModule::class, UserInfoRepoModule::class, UserInfoApiModule::class, MainActivityModule::class,
    SplashFragmentModule::class, UserListFragmentModule::class,UserInfoFragmentModule::class
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