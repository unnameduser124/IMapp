package com.example.imapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.imapp.conversationListUI.ConversationListLayout
import com.example.imapp.database.UserDatabaseService
import com.example.imapp.loginAndRegisterUI.LoginLayout
import com.example.imapp.messageAPI.StatusCode
import com.example.imapp.messageAPI.services.ConversationAPIService
import com.example.imapp.messageAPI.services.UserAPIService
import com.example.imapp.ui.theme.IMappTheme
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbService = UserDatabaseService(this)
        dbService.getUser()
        setContent {
            val conversations = mutableListOf<Conversation>()

            thread {
                if(MainUser.userID!=""){
                    UserAPIService().getUser(MainUser.userID)
                }
                else{
                    val intent = Intent(this, LoginLayout::class.java)
                    startActivity(intent)
                    return@thread
                }
                val conversationsRequest =
                    ConversationAPIService().getAllUserConversations(
                        MainUser.userID
                    )
                if (conversationsRequest.first == StatusCode.OK) {
                    val conversationList = conversationsRequest.second
                    val tempConversationList = mutableListOf<Conversation>()
                    conversationList.forEach {
                        tempConversationList.add(Conversation.fromAPIData(it))
                    }
                    conversations.clear()
                    conversations.addAll(tempConversationList)
                }
            }
            //for (i in 0..40) {
            //    conversations.add(
            //        Conversation(
            //            "Other User $i",
            //            i.toString()
            //        )
            //    )
            //}
            ConversationListLayout(conversations, this).GenerateLayout()
        }
    }
}