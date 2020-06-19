package com.mkdev.cafebazaarandroidtest.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenueDetail

@Dao
interface VenueDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplace(venue: VenueDetail)

    @Query("SELECT * FROM VenueDetail WHERE id=:id")
    fun getVenueDetailById(id: String): LiveData<VenueDetail>

    @Query("DELETE FROM VenueDetail")
    fun deleteVenuesDetail()
}