package com.example.mycontacts.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.mycontacts.models.Contacts

class MyDiffUtil : DiffUtil.ItemCallback<Contacts>() {

    override fun areItemsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
        return oldItem.id == newItem.id
    }

}
