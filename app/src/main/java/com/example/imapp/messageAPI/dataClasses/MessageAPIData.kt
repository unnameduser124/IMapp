package com.example.imapp.messageAPI.dataClasses

import com.example.imapp.Message

class MessageAPIData(
    val Content: String,
    val Timestamp: Long,
    val Status: String,
    val Sender_Recipient: Int
)
{
    companion object{
        fun fromMessage(message: Message): MessageAPIData{
            return MessageAPIData(
                Content = message.content,
                Timestamp = message.timestamp.timeInMillis,
                Status = message.status.name,
                Sender_Recipient = if (message.own) 1 else 0
            )
        }
    }
}