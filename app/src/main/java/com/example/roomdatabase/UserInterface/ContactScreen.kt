package com.example.roomdatabase.UserInterface

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomdatabase.RoomMethods.ContactEvents
import com.example.roomdatabase.RoomMethods.ContactsState
import com.example.roomdatabase.RoomMethods.sortType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plcoding.roomguideandroid.AddContactDialog

@Composable
fun ContactScreen(
    state:ContactsState,
    onEvent:(ContactEvents) -> Unit
){
Scaffold(
    floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(ContactEvents.showDialog)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
        }
    },
    modifier = Modifier.padding(16.dp)
) {
paddingValues ->

    if (state.isAddingContact){
        AddContactDialog(state = state, onEvent = onEvent)
    }

    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically) {

                sortType.values().forEach { 
                    sortType ->
                    Row (modifier = Modifier
                        .clickable { onEvent(ContactEvents.sortContacts(sortType)) },
                        verticalAlignment = CenterVertically){
                        
                        RadioButton(
                            selected = state.sortType == sortType,
                            onClick = {
                                onEvent(ContactEvents.sortContacts(sortType))
                            }
                        )
                        Text(text = sortType.name)
                    }
                }

            }
        }
        items(state.contacts){ contact->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column (
                        modifier = Modifier.weight(1f)
                    ) {
                            Text(
                                text = "${contact.firstName} ${contact.lastName}",
                                fontSize = 20.sp
                            )
                        Text(text = contact.phoneNumber, fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(ContactEvents.DeleteContacts(contact))
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Contact")
                    }
                }
        }
    }
}
}