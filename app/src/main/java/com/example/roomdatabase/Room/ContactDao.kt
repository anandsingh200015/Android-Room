package com.example.roomdatabase.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("select * from contact order by firstName ASC")
    fun getContactByFirstName():Flow<List<Contact>>

    @Query("select * from contact order by lastName ASC")
    fun getContactByLastName():Flow<List<Contact>>

    @Query("select * from contact order by phoneNumber ASC")
    fun getContactByPhoneNumber():Flow<List<Contact>>

}