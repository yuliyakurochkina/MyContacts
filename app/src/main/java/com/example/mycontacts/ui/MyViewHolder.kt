package com.example.mycontacts.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.mycontacts.ContactsAdapter
import com.example.mycontacts.databinding.ItemContactBinding
import com.example.mycontacts.models.Contacts

class MyViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contacts, onButtonClicks: ContactsAdapter.OnButtonClicks?) {

        val data = "${contact.data.name} ${contact.data.surname}"

        with(binding) {

            tvNameAndSurname.text = contact.number
            tvNumber.text = data

            ivDeleteContact.setOnClickListener {
                onButtonClicks?.onDeleteClicked(contact.id)
            }

            ivEditContact.setOnClickListener {
                onButtonClicks?.onUpdateClicked(contact)
            }

        }
    }
}