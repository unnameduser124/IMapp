package com.example.imapp.conversationListUI

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.imapp.Conversation
import com.example.imapp.conversationUI.ConversationLayout

class ConversationListLayout(val conversations: List<Conversation>, val context: Context) {

    @Composable
    fun GenerateLayout() {
        ConversationList()
    }

    @Composable
    fun ConversationList() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                conversations.forEach { conversation ->
                    item {
                        ConversationListItem(conversation)
                    }
                }
            })
        }
        AddConversationButton()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ConversationListItem(conversation: Conversation) {
        val deleteDialogOpen = remember { mutableStateOf(false) }
        Row(modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    val intent = Intent(context, ConversationLayout::class.java)
                    intent.putExtra("CONVERSATION_ID", conversation.ID)
                    intent.putExtra("OTHER_USER", conversation.otherUser)
                    context.startActivity(intent)
                },
                onLongClick = {
                    deleteDialogOpen.value = true
                }
            )
        ) {
            Text(
                text = conversation.otherUser,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
        if (deleteDialogOpen.value) {
            DeleteConversationDialog(deleteDialogOpen)
        }
    }

    @Composable
    fun AddConversationButton() {
        val dialogOpen = remember { mutableStateOf(false) }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = {
                        dialogOpen.value = true
                    },
                    containerColor = Color.LightGray,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 25.dp, end = 25.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Conversation",
                        tint = Color.Black
                    )
                }
            }
        }
        if (dialogOpen.value) {
            AddNewConversationDialog(dialogOpen)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddNewConversationDialog(dialogOpen: MutableState<Boolean>) {
        val newConversationUsername = remember { mutableStateOf("") }
        Dialog(onDismissRequest = { dialogOpen.value = false }) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add New Conversation",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(10.dp)
                )
                OutlinedTextField(
                    value = newConversationUsername.value,
                    onValueChange = { newConversationUsername.value = it },
                    label = { Text(text = "Username") },
                    modifier = Modifier
                        .padding(10.dp)
                        .width(250.dp)
                )
                Button(
                    onClick = {
                        if (newConversationUsername.value != "") {
                            //TODO("Send conversation request to API, refresh conversation list")
                        }
                        dialogOpen.value = false
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Add")
                }
            }
        }
    }

    @Composable
    fun DeleteConversationDialog(dialogOpen: MutableState<Boolean>) {
        Dialog(onDismissRequest = { dialogOpen.value = false }) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Delete conversation?",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            //TODO("Send delete conversation request to API, refresh conversation list")
                            dialogOpen.value = false
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    Button(
                        onClick = {
                            dialogOpen.value = false
                        }
                    ) {
                        Text(text = "No")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewConversationListLayout() {
    val conversations = mutableListOf<Conversation>()
    for (i in 0..40) {
        conversations.add(
            Conversation(
                "Other User $i",
                i.toLong()
            )
        )
    }
    ConversationListLayout(
        conversations = conversations,
        context = LocalContext.current
    ).GenerateLayout()
}
