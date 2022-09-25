package com.example.mycontacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mycontacts.databinding.ItemContactBinding
import com.example.mycontacts.models.Contacts
import com.example.mycontacts.ui.MyDiffUtil
import com.example.mycontacts.ui.MyViewHolder

class ContactsAdapter : ListAdapter<Contacts, MyViewHolder>(MyDiffUtil()) {

    interface OnButtonClicks {
        fun onUpdateClicked(contacts: Contacts)
        fun onDeleteClicked(id: Int)
    }

    var onButtonClicks: OnButtonClicks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, onButtonClicks)
    }

}