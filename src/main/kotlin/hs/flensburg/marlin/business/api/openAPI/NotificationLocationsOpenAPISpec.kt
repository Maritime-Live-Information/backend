package hs.flensburg.marlin.business.api.openAPI

import hs.flensburg.marlin.business.api.notificationLocation.entity.CreateOrUpdateNotificationLocationRequest
import hs.flensburg.marlin.business.api.notificationLocation.entity.NotificationLocationDTO
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode

object NotificationLocationsOpenAPISpec {

    val getNotificationLocation: RouteConfig.() -> Unit = {
        tags("notification-locations")
        description = "Get a notification location by its ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the notification location"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<NotificationLocationDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getAllNotificationLocationsFromLocation: RouteConfig.() -> Unit = {
        tags("notification-locations")
        description = "Get all notification locations from a location by its ID."
        request {
            pathParameter<Long>("locationId") {
                description = "ID of the location"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<List<NotificationLocationDTO>>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val createNotificationLocation: RouteConfig.() -> Unit = {
        tags("notification-locations")
        description = "Create a notification location and send push notifications to subscribed users."
        request {
            body<CreateOrUpdateNotificationLocationRequest>()
        }
        response {
            HttpStatusCode.Created to {
                body<NotificationLocationDTO>()
            }
            HttpStatusCode.BadRequest to {
                body<String>()
            }
        }
    }

    val deleteNotificationLocation: RouteConfig.() -> Unit = {
        tags("notification-locations")
        description = "Delete a notification location by ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the notification location"
            }
        }
        response {
            HttpStatusCode.NoContent to {}
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }
}
