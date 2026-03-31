package hs.flensburg.marlin.business.api.notificationLocation.boundary

import de.lambda9.tailwind.core.KIO.Companion.unsafeRunSync
import hs.flensburg.marlin.business.api.notificationLocation.entity.CreateOrUpdateNotificationLocationRequest
import hs.flensburg.marlin.business.api.notifications.boundary.NotificationSender
import hs.flensburg.marlin.business.api.openAPI.NotificationLocationsOpenAPISpec
import hs.flensburg.marlin.business.api.userDevice.control.UserDeviceRepo
import hs.flensburg.marlin.business.api.userDevice.entity.UserDevice
import hs.flensburg.marlin.database.generated.tables.pojos.UserLocations
import hs.flensburg.marlin.plugins.Realm
import hs.flensburg.marlin.plugins.authenticate
import hs.flensburg.marlin.plugins.kioEnv
import hs.flensburg.marlin.plugins.respondKIO
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.routing

private val logger = KotlinLogging.logger { }

fun Application.configureNotificationLocations() {
    routing {
        authenticate(Realm.HARBOUR_CONTROL) {
            get("/notification-locations/{id}", NotificationLocationsOpenAPISpec.getNotificationLocation) {
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(NotificationLocationsService.getNotificationLocation(id))
            }

            get(
                "/notification-locations/all/{locationId}",
                NotificationLocationsOpenAPISpec.getAllNotificationLocationsFromLocation
            ) {
                val locationId = call.parameters["locationId"]!!.toLong()
                call.respondKIO(NotificationLocationsService.getAllNotificationLocationsFromLocation(locationId))
            }

            post("/notification-locations", NotificationLocationsOpenAPISpec.createNotificationLocation) {
                val request = call.receive<CreateOrUpdateNotificationLocationRequest>()

                var allUserLocations: List<UserLocations?> =
                    UserLocationsRepo.fetchAllUserLocationsByLocationId(request.locationId).unsafeRunSync(call.kioEnv)
                        .fold(
                            onError = { listOf<UserLocations>() },
                            onSuccess = { it }
                        )
                allUserLocations =
                    allUserLocations.filter { userLocations -> userLocations!!.sentHarborNotifications == true }

                logger.info { "Broadcasting notification to ${allUserLocations.size} users for location ${request.locationId}" }
                allUserLocations.forEach { userLocation ->
                    val userId: Long = userLocation!!.userId!!
                    val allUserDevices: List<UserDevice?> =
                        UserDeviceRepo.fetchAllByUserId(userId).unsafeRunSync(call.kioEnv).fold(
                            onError = { listOf<UserDevice>() },
                            onSuccess = { it }
                        )

                    allUserDevices.forEach { device ->
                        NotificationSender.sendNotification(
                            expoToken = device!!.fcmToken,
                            title = request.notificationTitle,
                            body = request.notificationText
                        )
                    }
                }

                call.respondKIO(NotificationLocationsService.create(request))
            }

            delete("/notification-locations/{id}", NotificationLocationsOpenAPISpec.deleteNotificationLocation) {
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(NotificationLocationsService.delete(id))
            }
        }
    }
}