package com.example.imapp

import java.util.Calendar

class Message(
    val status: MessageStatus,
    val content: String,
    val timestamp: Calendar,
    val own: Boolean,
    val ID: Long = -1
) {
}


enum class MessageStatus {
    SENT,
    RECEIVED,
    READ
}