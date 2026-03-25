package hs.flensburg.marlin.business.api.userLocations.boundary

import hs.flensburg.marlin.business.api.auth.entity.LoggedInUser
import hs.flensburg.marlin.business.api.openAPI.UserLocationsOpenAPISpec
import hs.flensburg.marlin.business.api.userLocations.entity.CreateOrUpdateUserLocationRequest
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

fun Application.configureUserLocations() {
    routing {
        authenticate(Realm.COMMON) {
            get("/user-locations/{id}", UserLocationsOpenAPISpec.getUserLocation) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(UserLocationsService.getUserLocation(user.id, id))
            }

            get("/user-locations/location/{locationId}", UserLocationsOpenAPISpec.getUserLocationByUserIdAndLocationId) {
                val user = call.principal<LoggedInUser>()!!
                val locationId = call.parameters["locationId"]!!.toLong()
                call.respondKIO(UserLocationsService.getUserLocationByUserIdAndLocationId(user.id, locationId))
            }

            get("/user-locations/user", UserLocationsOpenAPISpec.getAllUserLocationsFromUser) {
                val user = call.principal<LoggedInUser>()!!
                call.respondKIO(UserLocationsService.getAllUserLocationsFromUser(user.id))
            }

            post("/user-locations", UserLocationsOpenAPISpec.createUserLocation) {
                val user = call.principal<LoggedInUser>()!!
                val request = call.receive<CreateOrUpdateUserLocationRequest>()
                call.respondKIO(UserLocationsService.create(user.id, request))
            }

            put("/user-locations/{id}", UserLocationsOpenAPISpec.updateUserLocation) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                val request = call.receive<CreateOrUpdateUserLocationRequest>()
                call.respondKIO(UserLocationsService.update(user.id, id, request))
            }

            delete("/user-locations/{id}", UserLocationsOpenAPISpec.deleteUserLocation) {
                val user = call.principal<LoggedInUser>()!!
                val id = call.parameters["id"]!!.toLong()
                call.respondKIO(UserLocationsService.delete(user.id, id))
            }
        }
    }
}
