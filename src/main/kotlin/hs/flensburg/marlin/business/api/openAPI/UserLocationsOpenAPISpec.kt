package hs.flensburg.marlin.business.api.openAPI

import hs.flensburg.marlin.business.api.userLocations.entity.UserLocationDTO
import hs.flensburg.marlin.business.api.userLocations.entity.CreateOrUpdateUserLocationRequest
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode

object UserLocationsOpenAPISpec {

    val getUserLocation: RouteConfig.() -> Unit = {
        description = "Get a user location by its ID "
        tags("user-locations")
        request {
            pathParameter<Long>("id") {
                description = "ID of the user location"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<UserLocationDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getUserLocationByUserIdAndLocationId: RouteConfig.() -> Unit = {
        tags("user-locations")
        description = "Get a user location for the authenticated user by location ID."
        request {
            pathParameter<Long>("locationId") {
                description = "ID of the location"
            }
        }
        response {
            HttpStatusCode.OK to {
                body<UserLocationDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val getAllUserLocationsFromUser: RouteConfig.() -> Unit = {
        tags("user-locations")
        description = "Get all user locations for the authenticated user."
        response {
            HttpStatusCode.OK to {
                body<List<UserLocationDTO>>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val createUserLocation: RouteConfig.() -> Unit = {
        tags("user-locations")
        description = "Create a user location."
        request {
            body<CreateOrUpdateUserLocationRequest>()
        }
        response {
            HttpStatusCode.Created to {
                body<UserLocationDTO>()
            }
            HttpStatusCode.BadRequest to {
                body<String>()
            }
        }
    }

    val updateUserLocation: RouteConfig.() -> Unit = {
        tags("user-locations")
        description = "Update a user location by its ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the user location"
            }
            body<CreateOrUpdateUserLocationRequest>()
        }
        response {
            HttpStatusCode.OK to {
                body<UserLocationDTO>()
            }
            HttpStatusCode.NotFound to {
                body<String>()
            }
        }
    }

    val deleteUserLocation: RouteConfig.() -> Unit = {
        tags("user-locations")
        description = "Delete a user location by ID."
        request {
            pathParameter<Long>("id") {
                description = "ID of the user location"
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
