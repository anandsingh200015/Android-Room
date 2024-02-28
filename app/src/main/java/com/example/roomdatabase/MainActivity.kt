package com.example.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.roomdatabase.Room.ContactDatabase
import com.example.roomdatabase.RoomMethods.ContactViewModel
import com.example.roomdatabase.UserInterface.ContactScreen

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            ContactScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}
