package com.example.roomdatabase.RoomMethods

import com.example.roomdatabase.Room.Contact

data class ContactsState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: sortType = com.example.roomdatabase.RoomMethods.sortType.FIRST_NAME

)