package com.example.roomdatabase.RoomMethods

import com.example.roomdatabase.Room.Contact

sealed interface ContactEvents {

    object saveContact : ContactEvents
    data class setFirstName(val firstName:String):ContactEvents
    data class setLastName(val lastName:String):ContactEvents
    data class setPhoneNumber(val phoneNumber:String):ContactEvents
    object showDialog:ContactEvents
    object hideDialog:ContactEvents
    //sortType is a enum class through we can sort our data
    data class sortContacts(val sortType:sortType):ContactEvents
    data class DeleteContacts(val contact: Contact):ContactEvents

}