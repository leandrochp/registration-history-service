package com.github.leandrochp.registrationhistoryservice.application.web.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.github.leandrochp.registrationhistoryservice.application.web.constant.APPLICATION_JSON_CHARSET_UTF_8
import com.github.leandrochp.registrationhistoryservice.application.web.constant.CONTENT_TYPE
import com.github.leandrochp.registrationhistoryservice.domain.exception.ApiError
import com.github.leandrochp.registrationhistoryservice.domain.exception.ApiException
import com.github.leandrochp.registrationhistoryservice.domain.exception.ErrorResponse
import com.github.leandrochp.registrationhistoryservice.domain.exception.InvalidRequestException
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object FailureHandlerConfig : KoinComponent {

    private val objectMapper: ObjectMapper by inject()

    fun handle(router: Router) {
        router.route().failureHandler {
            if (it.failed()) {
                when (val throwable = it.failure()) {
                    is ApiException -> buildApiExceptionResponse(it, throwable)
                    is MissingKotlinParameterException -> buildApiExceptionResponse(
                        it,
                        InvalidRequestException(
                            "Invalid request missing parameter in request.",
                            details = mapOf("error" to listOf(throwable.localizedMessage))
                        )
                    )
                    else -> buildDefaultErrorResponse(it, throwable)
                }
            }
        }
    }

    private fun buildApiExceptionResponse(routingContext: RoutingContext, apiException: ApiException) {
        routingContext.response()
            .setStatusCode(apiException.httpStatus())
            .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
            .end(objectMapper.writeValueAsString(apiException.createErrorResponse()))
    }

    private fun buildDefaultErrorResponse(routingContext: RoutingContext, throwable: Throwable) {
        val response = ErrorResponse(
            apiError = ApiError.INTERNAL_SERVER_ERROR,
            message = "An error occurred, please contact the server administrator.",
            details = mapOf("error" to listOf(throwable.localizedMessage))
        )

        routingContext.response()
            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
            .end(objectMapper.writeValueAsString(response))
    }
}
