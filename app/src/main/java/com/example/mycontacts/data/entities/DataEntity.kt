package com.example.mycontacts.data.entities

import io.realm.RealmObject

open class DataEntity(
    var name: String = "",
    var surname: String = ""
) : RealmObject()
