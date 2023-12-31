package com.example.imapp.messageAPI

enum class StatusCode(val code: Int) {
    OK(200),
    Created(201),

    BadRequest(400),
    Unauthorized(401),
    NotFound(404),
    Conflict(409),
    UnprocessableEntity(422),

    InternalServerError(500),
    ServiceUnavailable(503),

    Unknown(0)
}

fun getResponseCode(responseCode: Int): StatusCode{
    return if(StatusCode.values().find { it.code == responseCode }!=null){
        StatusCode.values().first { it.code == responseCode }
    }else{
        StatusCode.Unknown
    }
}