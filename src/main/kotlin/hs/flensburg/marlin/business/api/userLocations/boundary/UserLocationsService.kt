import de.lambda9.tailwind.core.KIO
import de.lambda9.tailwind.core.extensions.kio.onNullFail
import de.lambda9.tailwind.core.extensions.kio.orDie
import hs.flensburg.marlin.business.ApiError
import hs.flensburg.marlin.business.App
import hs.flensburg.marlin.business.JEnv
import hs.flensburg.marlin.business.ServiceLayerError
import hs.flensburg.marlin.business.api.userLocations.entity.CreateOrUpdateUserLocationRequest
import hs.flensburg.marlin.business.api.userLocations.entity.UserLocationDTO
import hs.flensburg.marlin.business.api.users.boundary.UserService
import hs.flensburg.marlin.database.generated.tables.pojos.UserLocations

object UserLocationsService {
    sealed class Error(private val message: String) : ServiceLayerError {
        object NotFound : Error("User location not found")
        object BadRequest : Error("Bad request")

        override fun toApiError(): ApiError {
            return when (this) {
                is NotFound -> ApiError.NotFound(message)
                is BadRequest -> ApiError.BadRequest(message)
            }
        }
    }

    fun getUserLocation(userId: Long, id: Long): App<Error, UserLocationDTO> = KIO.comprehension {
        UserLocationsRepo.fetchById(userId, id).orDie().onNullFail { Error.NotFound }.map { UserLocationDTO.from(it) }
    }

    fun getAllUserLocationsFromUser(userId: Long): App<Error, List<UserLocationDTO>> =
        KIO.comprehension {
            UserLocationsRepo.fetchAllByUserId(userId).orDie()
                .onNullFail { Error.NotFound }
        }

    fun getUserLocationByUserIdAndLocationId(
        userId: Long,
        locationId: Long
    ): App<Error, UserLocationDTO> = KIO.comprehension {
        UserLocationsRepo.fetchByUserIdAndLocationId(userId, locationId).orDie()
            .onNullFail { Error.NotFound }.map { UserLocationDTO.from(it) }
    }

    fun create(
        userId: Long,
        userLocation: CreateOrUpdateUserLocationRequest
    ): App<Error, UserLocationDTO> = KIO.comprehension {
        UserLocationsRepo.insert(
            UserLocations(
                userId = userId,
                locationId = userLocation.locationId,
                sentHarborNotifications = userLocation.sentHarborNotifications,
            )
        ).orDie().map { UserLocationDTO.from(it) }
    }

    fun update(
        userId: Long,
        id: Long,
        userLocation: CreateOrUpdateUserLocationRequest
    ): App<Error, UserLocationDTO?> = KIO.comprehension {
        UserLocationsRepo.update(
            id,
            UserLocations(
                userId = userId,
                locationId = userLocation.locationId,
                sentHarborNotifications = userLocation.sentHarborNotifications,
            )
        ).orDie().onNullFail { Error.NotFound }.map { UserLocationDTO.from(it) }
    }

    fun delete(userId: Long, id: Long): App<UserService.Error, Unit> = KIO.comprehension {
        UserLocationsRepo.deleteById(userId, id).orDie()
    }
}