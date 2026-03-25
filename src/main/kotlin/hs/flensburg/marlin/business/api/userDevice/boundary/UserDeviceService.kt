package hs.flensburg.marlin.business.api.userDevice.boundary

import de.lambda9.tailwind.core.KIO
import de.lambda9.tailwind.core.extensions.kio.onNullFail
import de.lambda9.tailwind.core.extensions.kio.orDie
import hs.flensburg.marlin.business.ApiError
import hs.flensburg.marlin.business.App
import hs.flensburg.marlin.business.JEnv
import hs.flensburg.marlin.business.ServiceLayerError
import hs.flensburg.marlin.business.api.userDevice.control.UserDeviceRepo
import hs.flensburg.marlin.business.api.userDevice.entity.CreateUserDeviceRequest
import hs.flensburg.marlin.business.api.userDevice.entity.UserDevice

object UserDeviceService {
    sealed class Error(private val message: String) : ServiceLayerError {
        object NotFound : Error("User device not found")
        object BadRequest : Error("Bad request")

        override fun toApiError(): ApiError {
            return when (this) {
                is NotFound -> ApiError.NotFound(message)
                is BadRequest -> ApiError.BadRequest(message)
            }
        }
    }

    fun getUserDevice(userId: Long): App<Error, UserDevice> = KIO.comprehension {
        UserDeviceRepo.fetchById(userId).orDie().onNullFail { Error.NotFound }.map { UserDevice.from(it) }
    }

    fun getAllUserDevices(userId: Long): App<Error, List<UserDevice>> = KIO.comprehension {
        UserDeviceRepo.fetchAllByUserId(userId).orDie().onNullFail { Error.NotFound }
    }

    fun createDevice(
        userId: Long,
        userDevice: CreateUserDeviceRequest
    ): App<Error, UserDevice> = KIO.comprehension {
        UserDeviceRepo.insertDevice(userId, userDevice.fcmToken).orDie().map { UserDevice.from(it) }
    }

    fun deleteUserDevice(userId: Long, id: Long): App<Error, Unit> = KIO.comprehension {
        UserDeviceRepo.deleteById(userId, id).orDie()
    }
}