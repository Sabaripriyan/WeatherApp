package userinfo.data.di

import core.qualifiers.RetrofitLib
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import userinfo.data.api.WebService

@Module
class UserInfoApiModule {
    @Provides
    @Reusable
    fun provideWebService(@RetrofitLib retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
    }
}