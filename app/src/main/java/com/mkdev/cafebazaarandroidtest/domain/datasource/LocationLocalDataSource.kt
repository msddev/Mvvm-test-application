package com.mkdev.cafebazaarandroidtest.domain.datasource

import com.mkdev.cafebazaarandroidtest.db.dao.VenuesDao
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import javax.inject.Inject

class LocationLocalDataSource @Inject constructor(private val venuesDao: VenuesDao) {

    fun getVenues(userLatLng: String) = venuesDao.getVenueByUserLatLng(userLatLng)

    fun insertVenues(venues: List<Venue>) = venuesDao.insertReplaceVenues(venues)
}
