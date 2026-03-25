package hs.flensburg.marlin.business.api.userDevice.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserDevice(
    var id: Long,
    var fcmToken: String,
    var userId: Long,
) {
    companion object {
        fun from(userDevice: hs.flensburg.marlin.database.generated.tables.pojos.UserDevice): UserDevice {
            return UserDevice(
                id = userDevice.id!!,
                fcmToken = userDevice.fcmToken!!,
                userId= userDevice.userId!!
            )
        }
    }
}
