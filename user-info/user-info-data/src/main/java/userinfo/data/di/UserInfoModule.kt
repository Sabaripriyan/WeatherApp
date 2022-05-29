package userinfo.data.di

import dagger.Binds
import dagger.Module
import userinfo.data.repository_impl.UserInfoRepositoryImpl
import userinfo.data.usecase_impl.UserInfoUseCaseImpl
import userinfo.domain.repository.UserInfoRepository
import userinfo.domain.usecase.UserInfoUseCase

@Module
interface UserInfoUseCaseModule {
    @Binds
    fun bindUserInfoUseCase(
        userInfoUseCaseImpl: UserInfoUseCaseImpl
    ): UserInfoUseCase
}

@Module(includes = [UserInfoUseCaseModule::class])
interface UserInfoRepoModule {
    @Binds
    fun bindUserInfoRepo(userInfoRepositoryImpl: UserInfoRepositoryImpl): UserInfoRepository
}