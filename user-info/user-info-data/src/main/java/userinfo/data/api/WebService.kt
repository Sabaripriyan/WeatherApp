package userinfo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import userinfo.data.model.UserInfoApiResponse

interface WebService {

    @GET
    fun getUserInfo(
        @Url url: String,
        ): Single<UserInfoApiResponse>
}