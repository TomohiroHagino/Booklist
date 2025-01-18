package com.enjoyhac.booklist.model

import AccessInfo
import SaleInfo
import SearchInfo

data class Item (

    val kind : String,
    val id : String,
    val etag : String,
    val selfLink : String,
    val volumeInfo : VolumeInfo,
    val saleInfo : SaleInfo,
    val accessInfo : AccessInfo,
    val searchInfo : SearchInfo
)