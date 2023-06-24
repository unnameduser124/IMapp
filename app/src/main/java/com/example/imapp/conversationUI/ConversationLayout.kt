package com.example.imapp.conversationUI

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Refresh
import com.example.imapp.Conversation
import com.example.imapp.Message
import com.example.imapp.MessageStatus
import java.util.Calendar

class ConversationLayout : ComponentActivity() {

    private lateinit var conversation: Conversation
    private lateinit var messages: List<Message>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val conversationID = intent.getLongExtra("CONVERSATION_ID", -1)
        val conversationName = intent.getStringExtra("OTHER_USER")
        conversation = Conversation(conversationName!!, conversationID)
        messages = generateMessages() //TODO("Temporary, replace with API call")
        setContent {
            GenerateLayout()
        }
    }

    @Composable
    fun GenerateLayout() {
        MessagesLazyColumn()
    }

    @Composable
    fun MessagesLazyColumn() {
        Column(modifier = Modifier.fillMaxSize()) {

            val headerModifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .weight(0.05f)
            ConversationHeader(headerModifier)
            LazyColumn(
                content = {
                    messages.forEach { message ->
                        item {
                            if (message.own) {
                                OwnMessageListItem(message = message)
                            } else {
                                ReceivedMessageListItem(message = message)
                            }
                        }
                    }
                },
                modifier = Modifier.weight(0.88f),
                reverseLayout = true
            )
            val rowModifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 10.dp, end = 10.dp, top = 3.dp)
                .weight(0.07f)
            MessageInputRow(rowModifier)
        }
    }

    @Composable
    fun ConversationHeader(modifier: Modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = conversation.otherUser,
                fontSize = 25.sp
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh messages",
                modifier = Modifier
                    .clickable { /* TODO("Refresh messages") */ }
                    .width(40.dp)
                    .height(40.dp)
            )
        }
    }

    @Composable
    fun OwnMessageListItem(message: Message) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp)
            ) {
                Text(
                    text = message.content,
                    Modifier
                        .widthIn(0.dp, 200.dp)
                        .padding(end = 10.dp, bottom = 5.dp, top = 5.dp, start = 10.dp),
                    softWrap = true,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    }

    @Composable
    fun ReceivedMessageListItem(message: Message) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp)
            ) {

                Text(
                    text = message.content,
                    Modifier
                        .widthIn(0.dp, 200.dp)
                        .padding(start = 10.dp, bottom = 5.dp, top = 5.dp, end = 10.dp),
                    softWrap = true,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MessageInputRow(modifier: Modifier) {
        Row(
            modifier = modifier
        ) {
            val messageText = remember { mutableStateOf("") }
            OutlinedTextField(
                value = messageText.value, onValueChange = { messageText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
            )
            Button(
                onClick = { messageText.value = "" }, modifier = Modifier
                    .weight(0.2f)
                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send message",
                    modifier = Modifier
                        .width(45.dp)
                        .height(45.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ConversationLayoutPreview() {
    ConversationLayout().GenerateLayout()
}

fun generateMessages(): MutableList<Message> {

    val messages = mutableListOf<Message>()
    for (i in 0..20) {
        messages.add(
            Message(
                MessageStatus.READ,
                "Long message that has to wrap or destroy the layout",
                Calendar.getInstance(),
                i % 2 == 0
            )
        )
    }
    messages.add(
        Message(
            MessageStatus.READ,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Calendar.getInstance(),
            false
        )
    )
    messages.add(
        Message(
            MessageStatus.READ,
            "short",
            Calendar.getInstance(),
            false
        )
    )
    return messages
}