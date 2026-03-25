package hs.flensburg.marlin.business.api.openAPI

import hs.flensburg.marlin.business.api.notifications.entity.TestNotificationRequest
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode

object NotificationsOpenAPISpec {

    val testNotification: RouteConfig.() -> Unit = {
        tags("test-notification")
        description = "Send a test push notification to a device via FCM."
        request {
            body<TestNotificationRequest>()
        }
        response {
            HttpStatusCode.OK to {
                body<TestNotificationRequest>()
            }
            HttpStatusCode.BadRequest to {
                body<String>()
            }
        }
    }
}
