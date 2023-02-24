package com.github.leandrochp.registrationhistoryservice.application.web.controllers

import com.github.leandrochp.registrationhistoryservice.application.web.responses.HealthCheckResponse

class HealthCheckController {

  fun health() = HealthCheckResponse(status = "Ok", up = true)

}
