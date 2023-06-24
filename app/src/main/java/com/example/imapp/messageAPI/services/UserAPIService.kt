package com.example.imapp.messageAPI.services

import com.example.imapp.MainUser
import com.example.imapp.global.BASE_URL
import com.example.imapp.global.serializeWithExceptions
import com.example.imapp.messageAPI.StatusCode
import com.example.imapp.messageAPI.dataClasses.UserAPIData
import com.example.imapp.messageAPI.getResponseCode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class UserAPIService {
    fun getUser(userID: String): StatusCode {
        val user: UserAPIData
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/get-user/$userID")
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val json = response.body!!.string()
        val typeToken = object : TypeToken<List<UserAPIData>>() {}.type
        val userList = Gson().fromJson(json, typeToken) as List<UserAPIData>
        user = userList.first()
        MainUser.userID = user.ID
        MainUser.username = user.Username
        return getResponseCode(response.code)
    }

    fun getUserSalt(username: String): Pair<StatusCode, String> {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/get-user-salt/$username")
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val code = getResponseCode(response.code)
        var salt = ""
        if (code == StatusCode.OK) {
            val json = response.body!!.string()
            val typeToken = object : TypeToken<List<String>>() {}.type
            val saltList = Gson().fromJson(json, typeToken) as List<String>
            salt = saltList[0]

            return Pair(code, salt)
        }
        return Pair(code, salt)
    }

    fun createUser(user: UserAPIData): Pair<StatusCode, String>{
        val client = OkHttpClient()

        val json = serializeWithExceptions(user, listOf("ID"))
        val requestBody = json.toRequestBody()

        val request = Request.Builder()
            .url("$BASE_URL/create-user")
            .post(requestBody)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val responseJson = response.body!!.string()
        data class ResponseData(val details: String)
        val idClass = Gson().fromJson(json, ResponseData::class.java)
        val code = getResponseCode(response.code)
        return Pair(code, idClass.details)
    }

    fun checkUserCredentials(user: UserAPIData): Pair<StatusCode, String> {
        val client = OkHttpClient()

        val postJson = serializeWithExceptions(user, listOf("ID", "Username"))
        val requestBody = postJson.toRequestBody()

        val request = Request.Builder()
            .url("$BASE_URL/check-credentials/${user.Username}")
            .post(requestBody)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val json = response.body!!.string()
        val userID = Gson().fromJson(json, String::class.java)
        return Pair(getResponseCode(response.code), userID)
    }

    fun deleteUser(userID: String){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/delete-user/$userID")
            .delete()
            .build()

        val call = client.newCall(request)
        val response = call.execute()
    }

}