package userinfo.ui.di.user_info

import androidx.lifecycle.ViewModel
import core.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import userinfo.ui.view.user_info.UserInfoFragment
import userinfo.ui.viewmodel.user_info.UserInfoViewModel

@Module
interface UserInfoViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    fun bindUserInfoViewModel(viewModel: UserInfoViewModel): ViewModel
}

@Module
interface UserInfoFragmentModule {
    @ContributesAndroidInjector(modules = [UserInfoViewModelModule::class])
    fun userInfoFragment(): UserInfoFragment
}