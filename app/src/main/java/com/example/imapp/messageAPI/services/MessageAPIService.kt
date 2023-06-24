package com.example.imapp.messageAPI.services

import com.example.imapp.MainUser
import com.example.imapp.global.BASE_URL
import com.example.imapp.messageAPI.StatusCode
import com.example.imapp.messageAPI.dataClasses.MessageAPIData
import com.example.imapp.messageAPI.getResponseCode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class MessageAPIService {

    fun getConversationMessages(conversationID: String): Pair<StatusCode, List<MessageAPIData>> {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/get-conversation-messages/${MainUser.userID}/$conversationID")
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val code = getResponseCode(response.code)
        var messageList: List<MessageAPIData> = listOf()
        if (code == StatusCode.OK) {
            val json = response.body!!.string()
            val typeToken = object : TypeToken<List<MessageAPIData>>() {}.type
            messageList = Gson().fromJson(json, typeToken) as List<MessageAPIData>
        }
        return Pair(code, messageList)
    }

    fun createMessage(message: MessageAPIData, conversationID: String): StatusCode{
        val client = OkHttpClient()

        val json  = Gson().toJson(message)
        val request = Request.Builder()
            .url("$BASE_URL/create-message/${MainUser.userID}/$conversationID")
            .post(json.toRequestBody())
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        return getResponseCode(response.code)
    }

}
