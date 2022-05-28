package userinfo.ui.di.user_info

import androidx.lifecycle.ViewModel
import core.ViewModelKey
import core.viewmodel.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import userinfo.ui.view.user_info.UserListFragment
import userinfo.ui.viewmodel.user_info.UserListViewModel

@Module
interface UserListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    fun bindUserListViewModel(viewModel: UserListViewModel): ViewModel
}

@Module
interface UserListFragmentModule {
    @ContributesAndroidInjector(modules = [UserListViewModelModule::class])
    fun userListFragment(): UserListFragment
}