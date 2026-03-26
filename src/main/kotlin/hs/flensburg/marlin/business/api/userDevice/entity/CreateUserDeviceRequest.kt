package hs.flensburg.marlin.business.api.userDevice.entity

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDeviceRequest(
    val fcmToken: String
) {
}
