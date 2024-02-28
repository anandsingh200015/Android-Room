@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.roomdatabase.RoomMethods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.Room.Contact
import com.example.roomdatabase.Room.ContactDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel (private val dao: ContactDao):ViewModel(){


    private val _sortType = MutableStateFlow(sortType.FIRST_NAME)

    private val _contacts = _sortType.flatMapLatest { sortType->
        when(sortType){
            com.example.roomdatabase.RoomMethods.sortType.FIRST_NAME -> dao.getContactByFirstName()
            com.example.roomdatabase.RoomMethods.sortType.LAST_NAME -> dao.getContactByLastName()
            com.example.roomdatabase.RoomMethods.sortType.PHONE_NUMBER -> dao.getContactByPhoneNumber()
        }
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactsState())

    val state = combine(_state,_sortType,_contacts){
        state,sortType,contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactsState())

    fun onEvent(events: ContactEvents){
        when(events){
            is ContactEvents.DeleteContacts ->
                viewModelScope.launch {
                    dao.deleteContact(events.contact)
                }
            ContactEvents.hideDialog -> {
                _state.update { it.copy(isAddingContact = false) }
            }

            ContactEvents.saveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()){
                    return
                }
                val contact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    id = 0
                )
                viewModelScope.launch { dao.insertContact(contact) }

                _state.update { it.copy(
                    isAddingContact = false,
                    firstName = "",
                    lastName = "",
                    phoneNumber = ""
                ) }

            }
            is ContactEvents.setFirstName -> {
                _state.update { it.copy(firstName = events.firstName) }
            }
            is ContactEvents.setLastName -> {
                _state.update { it.copy(lastName = events.lastName) }
            }
            is ContactEvents.setPhoneNumber -> {
                _state.update { it.copy(phoneNumber = events.phoneNumber) }
            }
            ContactEvents.showDialog -> {
                _state.update { it.copy(isAddingContact = true) }
            }
            is ContactEvents.sortContacts -> {
                _sortType.value = events.sortType
            }
        }
    }

}