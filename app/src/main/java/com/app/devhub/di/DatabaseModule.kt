package com.app.devhub.di

import android.content.Context
import com.app.devhub.data.local.room.AppDatabase
import com.app.devhub.data.local.room.GitProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideGitProfileDao(db: AppDatabase): GitProfileDao {
        return db.gitProfileDao()
    }
}