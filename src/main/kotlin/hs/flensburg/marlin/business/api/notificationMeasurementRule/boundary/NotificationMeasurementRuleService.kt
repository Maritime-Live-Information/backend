package hs.flensburg.marlin.business.api.notificationMeasurementRule.boundary

import de.lambda9.tailwind.core.KIO
import de.lambda9.tailwind.core.extensions.kio.onNullFail
import de.lambda9.tailwind.core.extensions.kio.orDie
import hs.flensburg.marlin.business.ApiError
import hs.flensburg.marlin.business.App
import hs.flensburg.marlin.business.ServiceLayerError
import hs.flensburg.marlin.business.api.notificationMeasurementRule.control.NotificationMeasurementRuleRepo
import hs.flensburg.marlin.business.api.notificationMeasurementRule.entity.CreateOrUpdateNotificationMeasurementRuleRequest
import hs.flensburg.marlin.business.api.notificationMeasurementRule.entity.NotificationMeasurementRuleDTO
import hs.flensburg.marlin.business.api.subscription.control.SubscriptionRepository
import hs.flensburg.marlin.business.api.users.boundary.UserService
import hs.flensburg.marlin.database.generated.enums.SubscriptionType
import hs.flensburg.marlin.database.generated.tables.pojos.NotificationMeasurementRule

object NotificationMeasurementRuleService {
    sealed class Error(private val message: String) : ServiceLayerError {
        object NotFound : Error("Notification measurement rule not found")
        object BadRequest : Error("Bad request")
        object SubscriptionRequired : Error("Active APP_NOTIFICATION subscription required")

        override fun toApiError(): ApiError {
            return when (this) {
                is NotFound -> ApiError.NotFound(message)
                is BadRequest -> ApiError.BadRequest(message)
                is SubscriptionRequired -> ApiError.Forbidden(message)
            }
        }
    }

    fun getNotificationMeasurementRule(userId: Long, ruleId: Long): App<Error, NotificationMeasurementRuleDTO> =
        KIO.comprehension {
            NotificationMeasurementRuleRepo.fetchById(userId, ruleId).orDie().onNullFail { Error.NotFound }
                .map { NotificationMeasurementRuleDTO.from(it) }
        }

    fun getAllNotificationMeasurementRulesByLocationId(locationId: Long): App<Error, List<NotificationMeasurementRuleDTO>> =
        KIO.comprehension {
            NotificationMeasurementRuleRepo.fetchAllByLocationId(locationId).orDie().onNullFail { Error.NotFound }
        }

    fun getAllNotificationMeasurementRulesFromUser(userId: Long): App<Error, List<NotificationMeasurementRuleDTO>> =
        KIO.comprehension {
            NotificationMeasurementRuleRepo.fetchAllByUserId(userId).orDie().onNullFail { Error.NotFound }
        }

    fun getAllNotificationMeasurementRuleByUserIdAndLocationId(
        userId: Long,
        locationId: Long
    ): App<Error, List<NotificationMeasurementRuleDTO>> = KIO.comprehension {
        NotificationMeasurementRuleRepo.fetchAllByUserIdAndLocationId(userId, locationId).orDie()
            .onNullFail { Error.NotFound }
    }

    fun getNotificationMeasurementRule(
        userId: Long,
        locationId: Long,
        measurementTypeId: Long
    ): App<Error, NotificationMeasurementRuleDTO> = KIO.comprehension {
        NotificationMeasurementRuleRepo.fetchByIds(userId, locationId, measurementTypeId).orDie()
            .onNullFail { Error.NotFound }.map { NotificationMeasurementRuleDTO.from(it) }
    }

    fun createRule(
        userId: Long,
        rule: CreateOrUpdateNotificationMeasurementRuleRequest
    ): App<Error, NotificationMeasurementRuleDTO> = KIO.comprehension {
        /*
            val hasSub = !SubscriptionRepository.hasActiveSubscription(rule.userId, SubscriptionType.APP_NOTIFICATION).orDie()
            !KIO.failOn(!hasSub) { Error.SubscriptionRequired }
        */
        NotificationMeasurementRuleRepo.insert(
            NotificationMeasurementRule(
                userId = userId,
                locationId = rule.locationId,
                measurementTypeId = rule.measurementTypeId,
                operator = rule.operator,
                measurementValue = rule.measurementValue,
                isActive = rule.isActive
            )
        ).orDie().map { NotificationMeasurementRuleDTO.from(it) }
    }

    fun updateRule(
        userId: Long,
        id: Long,
        rule: CreateOrUpdateNotificationMeasurementRuleRequest
    ): App<Error, NotificationMeasurementRuleDTO> = KIO.comprehension {
        NotificationMeasurementRuleRepo.update(
            id,
            NotificationMeasurementRule(
                userId = userId,
                locationId = rule.locationId,
                measurementTypeId = rule.measurementTypeId,
                operator = rule.operator,
                measurementValue = rule.measurementValue,
                isActive = rule.isActive,
                lastState = false
            )
        ).orDie().onNullFail { Error.NotFound }.map { NotificationMeasurementRuleDTO.from(it) }
    }

    fun deleteRule(userId: Long, ruleId: Long): App<UserService.Error, Unit> = KIO.comprehension {
        NotificationMeasurementRuleRepo.deleteById(userId, ruleId).orDie()
    }
}