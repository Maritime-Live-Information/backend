package hs.flensburg.marlin.business.api.openAPI

import hs.flensburg.marlin.business.api.notificationMeasurementRule.entity.CreateOrUpdateNotificationMeasurementRuleRequest
import hs.flensburg.marlin.business.api.notificationMeasurementRule.entity.NotificationMeasurementRuleDTO
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode

object NotificationMeasurementRuleOpenAPISpec {

    val getNotificationMeasurementRule: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Get a notification measurement rule by its ID for the authenticated user."
        request {
            pathParameter<Long>("id") {
                description = "ID of the notification measurement rule"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<NotificationMeasurementRuleDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getAllNotificationMeasurementRulesFromUser: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Get all notification measurement rules for the authenticated user."
        response {
            HttpStatusCode.OK to {
                body<List<NotificationMeasurementRuleDTO>>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getAllNotificationMeasurementRulesByUserIdAndLocationId: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Get all notification measurement rules for the authenticated user by location ID."
        request {
            pathParameter<Long>("locationId") {
                description = "ID of the location"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<List<NotificationMeasurementRuleDTO>>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getNotificationMeasurementRuleByUserLocationAndType: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Get a notification measurement rule for the authenticated user by location ID and measurement type ID."
        request {
            pathParameter<Long>("locationId") {
                description = "ID of the location"
            }
            pathParameter<Long>("measurementTypeId") {
                description = "ID of the measurement type"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<NotificationMeasurementRuleDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val createNotificationMeasurementRule: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Create a notification measurement rule."
        request {
            body<CreateOrUpdateNotificationMeasurementRuleRequest>()
        }
        response {
            HttpStatusCode.Created to {
                body<NotificationMeasurementRuleDTO>()
            }
            HttpStatusCode.BadRequest to {
                body<String>()
            }
        }
    }

    val updateNotificationMeasurementRule: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Update a notification measurement rule by its ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the notification measurement rule"
            }
            body<CreateOrUpdateNotificationMeasurementRuleRequest>()
        }
        response {
            HttpStatusCode.OK to {
                body<NotificationMeasurementRuleDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val deleteNotificationMeasurementRule: RouteConfig.() -> Unit = {
        tags("notification-measurement-rules")
        description = "Delete a notification measurement rule by ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the notification measurement rule"
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
