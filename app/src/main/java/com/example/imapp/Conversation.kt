package com.example.imapp

import com.example.imapp.messageAPI.dataClasses.ConversationAPIData

class Conversation(val otherUser: String, val ID: String = "") {

    companion object{
        fun fromAPIData(conversationData: ConversationAPIData): Conversation {
            if(conversationData.SecondUserUsername != ""){
                return Conversation(conversationData.SecondUserUsername, conversationData.ID)
            }
            return Conversation(conversationData.FirstUserUsername, conversationData.ID)
        }
    }
}