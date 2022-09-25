package com.example.mycontacts

import com.example.mycontacts.mock.ContactRepository
import com.example.mycontacts.mock.FakeContact
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testViewModel() {

        val contactRepository = ContactRepository()

        val contact = FakeContact(
            name = "FakeName",
            surname = "FakeSurname",
            number = "+79999999999"
        )

        val contact2 = FakeContact(
            name = "FakeName2",
            surname = "FakeSurname2",
            number = "+79999999992"
        )

        // Add the first fake contact
        contactRepository.addContact(contact)
        val list = contactRepository.getAllContacts()
        val lastContact = list.last()
        assertEquals(contact, lastContact)

        // Add the second fake contact
        contactRepository.addContact(contact2)
        val lastContact2 = list.last()
        assertEquals(contact2, lastContact2)

        contactRepository.getAllContacts()
        assertEquals(true, list.isNotEmpty())

        // Delete the contacts
        contactRepository.deleteContact(contact)
        contactRepository.deleteContact(contact2)
        assertEquals(0, list.size)

    }

}