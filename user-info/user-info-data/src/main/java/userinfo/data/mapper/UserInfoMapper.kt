package userinfo.data.mapper

import userinfo.data.model.user_info.*
import userinfo.domain.model.user_info.*


internal fun UserInfoApiResponse.toDomain() = UserInfoApiResponseData(
    results = results?.map { it.toDomain() }
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