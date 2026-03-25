package hs.flensburg.marlin.business.api.openAPI

import hs.flensburg.marlin.business.api.userDevice.entity.CreateUserDeviceRequest
import hs.flensburg.marlin.business.api.userDevice.entity.UserDevice
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode

object UserDeviceOpenAPISpec {

    val getUserDevice: RouteConfig.() -> Unit = {
        tags("user-device")
        description = "Get a user device by its ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the user device"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<UserDevice>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getAllUserDevices: RouteConfig.() -> Unit = {
        tags("user-device")
        description = "Get all devices of a user by userId."
        request {
            pathParameter<Long>("userId") {
                description = "userId of the user"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<List<UserDevice>>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val createUserDevice: RouteConfig.() -> Unit = {
        tags("user-device")
        description = "Create a user device entry."
        request {
            body<CreateUserDeviceRequest>()
        }
        response {
            HttpStatusCode.Created to {
                body<UserDevice>()
            }
            HttpStatusCode.BadRequest to {
                body<String>()
            }
        }
    }

    val deleteUserDevice: RouteConfig.() -> Unit = {
        tags("user-device")
        description = "Delete a user's device by ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the user device entry"
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
