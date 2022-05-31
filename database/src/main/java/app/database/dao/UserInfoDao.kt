package app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import app.database.table.UserInfo

@Dao
abstract class UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertUserInfo(userInfoList: List<UserInfo>)
}