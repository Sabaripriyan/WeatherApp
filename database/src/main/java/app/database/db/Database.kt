package app.database.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.database.dao.UserInfoDao
import app.database.table.UserInfo

@androidx.room.Database(
    entities = [UserInfo::class],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao

    companion object {

        private const val DATABASE_NAME = "ListingApp.db"

        @Volatile
        private var dbInstance: Database? = null

        fun getInstance(context: Context): Database {
            return dbInstance ?: synchronized(this) {
                dbInstance ?: buildDatabase(context).also { dbInstance = it }
            }
        }

        private fun buildDatabase(context: Context): Database {
            return Room.databaseBuilder(
                context,
                Database::class.java,
                DATABASE_NAME
            ).build()
        }

    }
}