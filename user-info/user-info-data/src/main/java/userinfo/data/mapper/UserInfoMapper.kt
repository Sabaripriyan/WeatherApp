package userinfo.data.mapper

import com.google.gson.Gson
import userinfo.data.model.user_info.*
import userinfo.domain.model.user_info.*


internal fun UserInfoApiResponse.toDomain() = UserInfoApiResponseData(
    results = results?.map { it.toDomain() },
    info = info?.toDomain()
)

internal fun UserInfo.toDomain() = UserInfoData(
    name = name?.toDomain(),
    location = location?.toDomain(),
    email = email,
    phone = phone,
    picture = picture?.toDomain()
)

internal fun Name.toDomain() = NameData(
    first = first,
    last = last)

internal fun Picture.toDomain() = PictureData(
    medium = medium,
    large = large,
    thumbnail = thumbnail
)

internal fun Location.toDomain() = LocationData(
    city = city,
    state = state,
    country = country,
    coordinates = coordinates?.toDomain()
)

internal fun Coordinates.toDomain() = CoordinatesData(
    latitude = latitude,
    longitude = longitude
)

internal fun Info.toDomain() = InfoData(
    results = results,
    page = page)

internal fun NameData.toData() = Name(
    first = first,
    last = last)

internal fun LocationData.toData() = Location(
    city = city,
    state = state,
    country = country,
    coordinates = coordinates?.toData()
)

internal fun PictureData.toData() = Picture(
    medium = medium,
    large = large,
    thumbnail = thumbnail
)

internal fun CoordinatesData.toData() = Coordinates(
    latitude = latitude,
    longitude = longitude)

internal fun UserInfoData.toDB() = app.database.table.UserInfo(
    name = Gson().toJson(name),
    location = Gson().toJson(location),
    email = email?:"",
    phone = phone?:"",
    picture = Gson().toJson(picture)
)