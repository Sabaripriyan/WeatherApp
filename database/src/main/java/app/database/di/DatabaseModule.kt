package app.database.di

import android.content.Context
import app.database.dao.UserInfoDao
import app.database.db.Database
import app.database.table.UserInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Database.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideUserInfoDao(db: Database): UserInfoDao {
        return db.userInfoDao()
    }
}