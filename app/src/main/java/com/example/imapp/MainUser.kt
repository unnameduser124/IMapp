package com.example.imapp

class MainUser(
    val userID: String,
    val username: String,
    val password: String,
    val conversationIDs: List<Long>
){
    companion object{
        private val instance: MainUser? = null
        fun getInstance(): MainUser {
            return instance ?: MainUser(
                "",
                "",
                "",
                listOf()
            )
        }
    }
}
