package com.mkdev.cafebazaarandroidtest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mkdev.cafebazaarandroidtest.db.dao.VenueDetailDao
import com.mkdev.cafebazaarandroidtest.db.dao.VenuesDao
import com.mkdev.cafebazaarandroidtest.db.typeConverters.*
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenueDetail
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue

@Database(
    entities = [
        Venue::class,
        VenueDetail::class
    ],
    version = 1
)
@TypeConverters(
    VenueCategoryTypeConverter::class,
    VenueLocationTypeConverter::class,
    VenueContactTypeConverter::class,
    VenuePhotosTypeConverter::class
)
abstract class TestAppDatabase : RoomDatabase() {

    abstract fun venuesDao(): VenuesDao

    abstract fun venueDetailDao(): VenueDetailDao
}
