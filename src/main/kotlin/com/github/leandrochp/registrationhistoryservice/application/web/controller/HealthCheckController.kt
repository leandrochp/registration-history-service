package com.github.leandrochp.registrationhistoryservice.application.web.controller

import com.github.leandrochp.registrationhistoryservice.application.web.response.HealthCheckResponse

class HealthCheckController {

  fun health() = HealthCheckResponse(status = "Ok", up = true)

}
