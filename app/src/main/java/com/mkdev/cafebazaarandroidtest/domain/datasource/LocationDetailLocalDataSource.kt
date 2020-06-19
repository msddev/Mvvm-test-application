package com.mkdev.cafebazaarandroidtest.domain.datasource

import com.mkdev.cafebazaarandroidtest.db.dao.VenueDetailDao
import com.mkdev.cafebazaarandroidtest.db.dao.VenuesDao
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenueDetail
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import javax.inject.Inject

class LocationDetailLocalDataSource @Inject constructor(private val venueDetailDao: VenueDetailDao) {

    fun getVenueDetailById(id: String) = venueDetailDao.getVenueDetailById(id)

    fun insertVenueDetail(venueDetail: VenueDetail) = venueDetailDao.insertReplace(venueDetail)
}
