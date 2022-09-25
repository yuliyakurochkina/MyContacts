package com.example.mycontacts

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    private val realmVersion = 1L

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
            .schemaVersion(realmVersion)
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)
    }

}