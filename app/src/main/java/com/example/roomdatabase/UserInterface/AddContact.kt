package com.plcoding.roomguideandroid



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomdatabase.RoomMethods.ContactEvents
import com.example.roomdatabase.RoomMethods.ContactsState

@Composable
fun AddContactDialog(
    state: ContactsState,
    onEvent: (ContactEvents) -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        modifier= modifier,
        onDismissRequest = {
                           onEvent(ContactEvents.hideDialog)
        },
        title = { Text(text = "Add Contact")},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(value = state.firstName, onValueChange = {
                    onEvent(ContactEvents.setFirstName(it))
                },
                    placeholder = {
                        Text(text = "First Name")
                    }
                )

                TextField(value = state.lastName, onValueChange = {
                    onEvent(ContactEvents.setLastName(it))
                },
                    placeholder = {
                        Text(text = "Last Name")
                    }
                )

                TextField(value = state.phoneNumber, onValueChange = {
                    onEvent(ContactEvents.setPhoneNumber(it))
                },
                    placeholder = {
                        Text(text = "Phone Number")
                    }
                )
            }
        },
        buttons = {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Button(onClick = {
                    onEvent(ContactEvents.saveContact)
                }) {
                        Text(text = "Save")
                }
            }
        }
        )

}


