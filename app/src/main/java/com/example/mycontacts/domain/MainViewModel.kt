package com.example.mycontacts.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycontacts.data.dao.ContactsDao
import com.example.mycontacts.models.Contacts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel() : ViewModel() {

    var lastId = 0

    //
    val operationLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val contactsList: MutableLiveData<List<Contacts>> by lazy {
        MutableLiveData<List<Contacts>>()
    }

    private val onDatabaseOperation = object : ContactsDao.OnDatabaseOperation {
        override fun onSuccess() {
            operationLiveData.postValue(true)
        }
    }

    private val contactsDao = ContactsDao(onDatabaseOperation)

    fun insertContact(contacts: Contacts) {
        viewModelScope.launch {
            contactsDao.insertContact(contacts)
        }
    }

    fun deleteContacts(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactsDao.deleteContacts(id)
            }
        }
    }

    fun updateContacts(contacts: Contacts) {
        viewModelScope.launch {
            contactsDao.updateContact(contacts)
        }
    }

    fun fetchAllContacts() {
        CoroutineScope(Dispatchers.IO).launch {
            contactsList.postValue(contactsDao.fetchAllContacts())
        }
    }

}