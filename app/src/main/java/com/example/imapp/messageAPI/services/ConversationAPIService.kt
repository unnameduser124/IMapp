package com.example.imapp.messageAPI.services

import com.example.imapp.MainUser
import com.example.imapp.global.BASE_URL
import com.example.imapp.global.serializeWithExceptions
import com.example.imapp.messageAPI.StatusCode
import com.example.imapp.messageAPI.dataClasses.ConversationAPIData
import com.example.imapp.messageAPI.getResponseCode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ConversationAPIService {

    fun getAllUserConversations(userID: String): Pair<StatusCode, List<ConversationAPIData>> {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/get-all-user-conversations/$userID")
            .build()
        request.url

        val call = client.newCall(request)
        val response = call.execute()
        val code = getResponseCode(response.code)
        var conversationList: List<ConversationAPIData> = listOf()
        if (code == StatusCode.OK) {
            val json = response.body!!.string()
            val typeToken = object : TypeToken<List<ConversationAPIData>>() {}.type
            conversationList = Gson().fromJson(json, typeToken) as List<ConversationAPIData>
        }
        return Pair(code, conversationList)
    }

    fun createConversation(otherUserUsername: String): StatusCode{
        val client = OkHttpClient()

        val conversation = ConversationAPIData(
            ID = "",
            FirstUserUsername = MainUser.username,
            SecondUserUsername = otherUserUsername
        )
        val json  = serializeWithExceptions(conversation, listOf("ID"))
        val body = json.toRequestBody()
        val request = Request.Builder()
            .url("$BASE_URL/create-conversation")
            .post(body)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        return getResponseCode(response.code)
    }

    fun deleteConversation(conversationID: String): StatusCode{
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/delete-conversation/${MainUser.userID}/$conversationID")
            .delete()
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        return getResponseCode(response.code)
    }
}