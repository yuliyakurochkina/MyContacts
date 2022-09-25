package com.example.mycontacts.data.dao

import com.example.mycontacts.data.entities.ContactsEntity
import com.example.mycontacts.data.entities.DataEntity
import com.example.mycontacts.models.Contacts
import com.example.mycontacts.models.Data
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class ContactsDao(private val onDatabaseOperation: OnDatabaseOperation) {

    interface OnDatabaseOperation {
        fun onSuccess()
    }

    suspend fun insertContact(contacts: Contacts) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { transaction ->
            transaction.insert(mapDataToEntity(contacts))
            onDatabaseOperation.onSuccess()
        }
    }

    fun deleteContacts(id: Int) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { transaction ->
            val contact = transaction.where(ContactsEntity::class.java)
                .equalTo("id", id)
                .findFirst()
            contact?.deleteFromRealm()
            onDatabaseOperation.onSuccess()
        }
    }

    suspend fun updateContact(contacts: Contacts) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { transaction ->
            val contact = transaction.where(ContactsEntity::class.java)
                .equalTo("id", contacts.id)
                .findFirst()
            if (contact != null) {
                contact.id = contacts.id
                contact.number = contacts.number
                val data = transaction.createObject(DataEntity::class.java)
                data.name = contacts.data.name
                data.surname = contacts.data.surname
                contact.data = data
                onDatabaseOperation.onSuccess()
            }
        }
    }

    fun fetchAllContacts(): List<Contacts> {
        val realm = Realm.getDefaultInstance()
        val contactsList = mutableListOf<Contacts>()
        realm.executeTransaction { transaction ->
            contactsList.addAll(
                mapEntityListToDataList(
                    transaction.where(ContactsEntity::class.java).findAll()
                )
            )
        }
        return contactsList
    }

    private fun mapEntityToData(contactsEntity: ContactsEntity): Contacts = Contacts(
        id = contactsEntity.id,
        number = contactsEntity.number,
        data = Data(
            contactsEntity.data?.name ?: "",
            contactsEntity.data?.surname ?: ""
        )
    )

    private fun mapDataToEntity(contacts: Contacts): ContactsEntity = ContactsEntity(
        id = contacts.id,
        number = contacts.number,
        data = DataEntity(
            contacts.data.name,
            contacts.data.surname
        )
    )

    private fun mapEntityListToDataList(contactsEntityList: List<ContactsEntity>): List<Contacts> {
        val dataList = mutableListOf<Contacts>()
        contactsEntityList.forEach {
            dataList.add(mapEntityToData(it))
        }
        return dataList
    }

}