package com.example.imapp

import com.example.imapp.messageAPI.dataClasses.MessageAPIData
import java.util.Calendar

class Message(
    val status: MessageStatus,
    val content: String,
    val timestamp: Calendar,
    val own: Boolean,
    val ID: Long = -1
) {
    companion object{
        fun fromAPIData(data: MessageAPIData): Message{
            return Message(
                status = MessageStatus.valueOf(data.Status),
                content = data.Content,
                timestamp = Calendar.getInstance().apply { timeInMillis = data.Timestamp },
                own = data.Sender_Recipient==1
            )
        }
    }
}


enum class MessageStatus {
    Sent,
    Received,
    Read
}