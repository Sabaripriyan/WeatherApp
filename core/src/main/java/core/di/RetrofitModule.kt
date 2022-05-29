package core.di

import com.example.mylibrary.BuildConfig
import core.kotlin.url.ApiUrls
import core.qualifiers.RetrofitLib
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @Provides
    @RetrofitLib
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClientBuilder = OkHttpClient.Builder()
        if(BuildConfig.DEBUG)
            httpClientBuilder.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(ApiUrls.userInfoApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }
}
