package hs.flensburg.marlin.business.api.userLocations.entity

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrUpdateUserLocationRequest(
    var locationId: Long,
    var sentHarborNotifications: Boolean
)