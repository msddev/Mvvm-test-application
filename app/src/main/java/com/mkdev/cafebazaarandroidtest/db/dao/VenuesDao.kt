package com.mkdev.cafebazaarandroidtest.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import io.reactivex.Single

@Dao
interface VenuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplaceVenues(venues: List<Venue>)

    @Query("SELECT * FROM Venue")
    fun getVenues(): LiveData<List<Venue>>

    @Query("SELECT * FROM Venue WHERE userLatLng=:userLatLng")
    fun getVenueByUserLatLng(userLatLng: String): LiveData<List<Venue>>

    @Transaction
    fun deleteAndInsertVenues(venues: List<Venue>) {
        deleteVenues()
        insertReplaceVenues(venues)
    }

    @Query("DELETE FROM Venue")
    fun deleteVenues()
}