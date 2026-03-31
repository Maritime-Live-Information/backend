package hs.flensburg.marlin.business.api.userDevice.boundary

import hs.flensburg.marlin.business.api.auth.entity.LoggedInUser
import hs.flensburg.marlin.business.api.openAPI.UserDeviceOpenAPISpec
import hs.flensburg.marlin.business.api.userDevice.entity.CreateUserDeviceRequest
import hs.flensburg.marlin.plugins.Realm
import hs.flensburg.marlin.plugins.authenticate
import hs.flensburg.marlin.plugins.respondKIO
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.server.application.Application
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.routing.routing

fun Application.configureUserDevices() {
    routing {
        authenticate(Realm.COMMON) {
            get("/user-device", UserDeviceOpenAPISpec.getUserDevice) {
                val user = call.principal<LoggedInUser>()!!
                call.respondKIO(UserDeviceService.getUserDevice(user.id))
            }

            get("/user-device/all", UserDeviceOpenAPISpec.getAllUserDevices) {
                val user = call.principal<LoggedInUser>()!!
                call.respondKIO(UserDeviceService.getAllUserDevices(user.id))
            }

            post("/user-device", UserDeviceOpenAPISpec.createUserDevice) {
                val user = call.principal<LoggedInUser>()!!
                val request = call.receive<CreateUserDeviceRequest>()
                call.respondKIO(UserDeviceService.createDevice(user.id, request))
            }

            delete("/user-device/{id}", UserDeviceOpenAPISpec.deleteUserDevice) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(UserDeviceService.deleteUserDevice(user.id, id))
            }
        }
    }
}