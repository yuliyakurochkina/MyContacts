package com.example.mycontacts.data.entities

import io.realm.RealmObject

open class ContactsEntity(
    var id: Int = 0,
    var number: String = "",
    var data: DataEntity? = null
) : RealmObject()
