package hs.flensburg.marlin.business.api.notificationMeasurementRule.boundary

import hs.flensburg.marlin.business.api.auth.entity.LoggedInUser
import hs.flensburg.marlin.business.api.notificationMeasurementRule.entity.CreateOrUpdateNotificationMeasurementRuleRequest
import hs.flensburg.marlin.business.api.openAPI.NotificationMeasurementRuleOpenAPISpec
import hs.flensburg.marlin.plugins.Realm
import hs.flensburg.marlin.plugins.authenticate
import hs.flensburg.marlin.plugins.respondKIO
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.put
import io.ktor.server.application.Application
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.routing.routing

fun Application.configureNotificationMeasurementRules() {
    routing {
        authenticate(Realm.COMMON) {
            get(
                "/notification-measurement-rules/{id}",
                NotificationMeasurementRuleOpenAPISpec.getNotificationMeasurementRule
            ) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(NotificationMeasurementRuleService.getNotificationMeasurementRule(userId = user.id, ruleId = id))
            }

            get(
                "/notification-measurement-rules/user",
                NotificationMeasurementRuleOpenAPISpec.getAllNotificationMeasurementRulesFromUser
            ) {
                val user = call.principal<LoggedInUser>()!!
                call.respondKIO(NotificationMeasurementRuleService.getAllNotificationMeasurementRulesFromUser(user.id))
            }

            get(
                "/notification-measurement-rules/user/location/{locationId}",
                NotificationMeasurementRuleOpenAPISpec.getAllNotificationMeasurementRulesByUserIdAndLocationId
            ) {
                val user = call.principal<LoggedInUser>()!!
                val locationId = call.parameters["locationId"]!!.toLong()
                call.respondKIO(
                    NotificationMeasurementRuleService.getAllNotificationMeasurementRuleByUserIdAndLocationId(
                        user.id,
                        locationId
                    )
                )
            }

            get(
                "/notification-measurement-rules/user/location/{locationId}/measurementTypeId/{measurementTypeId}",
                NotificationMeasurementRuleOpenAPISpec.getNotificationMeasurementRuleByUserLocationAndType
            ) {
                val user = call.principal<LoggedInUser>()!!
                val locationId = call.parameters["locationId"]!!.toLong()
                val measurementTypeId = call.parameters["measurementTypeId"]!!.toLong()
                call.respondKIO(
                    NotificationMeasurementRuleService.getNotificationMeasurementRule(
                        user.id,
                        locationId,
                        measurementTypeId
                    )
                )
            }

            post(
                "/notification-measurement-rules",
                NotificationMeasurementRuleOpenAPISpec.createNotificationMeasurementRule
            ) {
                val user = call.principal<LoggedInUser>()!!
                val request = call.receive<CreateOrUpdateNotificationMeasurementRuleRequest>()
                call.respondKIO(NotificationMeasurementRuleService.createRule(user.id, request))
            }

            put(
                "/notification-measurement-rules/{id}",
                NotificationMeasurementRuleOpenAPISpec.updateNotificationMeasurementRule
            ) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                val request = call.receive<CreateOrUpdateNotificationMeasurementRuleRequest>()
                call.respondKIO(NotificationMeasurementRuleService.updateRule(user.id, id, request))
            }

            delete(
                "/notification-measurement-rules/{id}",
                NotificationMeasurementRuleOpenAPISpec.deleteNotificationMeasurementRule
            ) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(NotificationMeasurementRuleService.deleteRule(user.id, id))
            }
        }
    }
}