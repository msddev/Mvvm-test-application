package com.mkdev.cafebazaarandroidtest.core

object Constants {

    object NetworkService {
        const val BASE_URL = "https://api.foursquare.com/v2/"
        const val FOURSQUARE_CLIENT_ID = "25GXIKX302M31DLFOLW1SITLWLCBXJ3EIIHC2HEFWO50HVIC"
        const val FOURSQUARE_CLIENT_SECRET = "WIAGCMJRDDHHOXGQXFHIAB4W3NUUJSYS12BD5GO2OA4YS1L1"
        const val FOURSQUARE_CLIENT_VERSION = 20181123
        const val PAGE_LIMIT = 30
        const val RATE_LIMITER_TYPE = "data"
        const val LAT = "lat"
        const val LON = "lon"
    }

    object ServiceAction{
        const val MAIN_ACTION = "com.mkdev.cafebazaarandroidtest.action.main"
        const val START_ACTION = "com.mkdev.cafebazaarandroidtest.action.start"
        const val STOP_ACTION = "com.mkdev.cafebazaarandroidtest.action.stop"
    }
}
