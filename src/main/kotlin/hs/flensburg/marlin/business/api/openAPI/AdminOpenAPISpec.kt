package hs.flensburg.marlin.business.api.openAPI

import hs.flensburg.marlin.business.api.admin.entity.AssignLocationRequest
import hs.flensburg.marlin.business.api.admin.entity.DashboardInfo
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode

object AdminOpenAPISpec {

    val getDashboardInfo: RouteConfig.() -> Unit = {
        tags("admin")
        description = "Get dashboard information (Admin only)."
        response {
            HttpStatusCode.OK to {
                body<DashboardInfo>()
            }
        }
    }

    val assignLocation: RouteConfig.() -> Unit = {
        tags("admin")
        description = "Assign a location to a harbor master (Admin only)."
        request {
            body<AssignLocationRequest>()
        }
        response {
            HttpStatusCode.OK to {
                description = "Location successfully assigned to harbor master"
            }
            HttpStatusCode.BadRequest to {
                description = "Invalid request (user is not a harbor master)"
            }
            HttpStatusCode.NotFound to {
                description = "User or location not found"
            }
        }
    }
}
