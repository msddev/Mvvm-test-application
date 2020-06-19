package com.mkdev.cafebazaarandroidtest.di.module

import android.content.Context
import androidx.room.Room
import com.mkdev.cafebazaarandroidtest.db.TestAppDatabase
import com.mkdev.cafebazaarandroidtest.db.dao.VenueDetailDao
import com.mkdev.cafebazaarandroidtest.db.dao.VenuesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase(context: Context): TestAppDatabase {
        return Room.databaseBuilder(
            context,
            TestAppDatabase::class.java, "TestApp-DB"
        ).build()
    }

    @Provides
    @Singleton
    fun providesVenuesDao(db: TestAppDatabase): VenuesDao = db.venuesDao()

    @Provides
    @Singleton
    fun providesVenueDetailDao(db: TestAppDatabase): VenueDetailDao = db.venueDetailDao()
}
