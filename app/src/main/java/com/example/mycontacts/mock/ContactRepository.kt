package com.example.mycontacts.mock

class ContactRepository {

    private val contactList = mutableListOf<FakeContact>()

    fun addContact(contact: FakeContact) {
        contactList.add(contact)
    }

    fun deleteContact(contact: FakeContact) {
        contactList.remove(contact)
    }

    fun getAllContacts(): MutableList<FakeContact> = contactList

}