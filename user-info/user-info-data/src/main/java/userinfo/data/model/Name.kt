package userinfo.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.SkipCallbackExecutor

data class Name(
    @SerializedName("first")
    val first: String? = "",
    @SerializedName("last")
    val last: String? = ""
)
