package com.example.mycontacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontacts.base.focusAndShowKeyboard
import com.example.mycontacts.databinding.ActivityAddContactBinding
import com.example.mycontacts.databinding.ActivityMainBinding
import com.example.mycontacts.domain.MainViewModel
import com.example.mycontacts.models.Contacts
import com.example.mycontacts.models.Data

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: ContactsAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupListeners()
        observeLiveData()
        viewModel.fetchAllContacts()

    }

    private fun setupListeners() {

        adapter.onButtonClicks = object : ContactsAdapter.OnButtonClicks {

            override fun onUpdateClicked(contacts: Contacts) {
                showInsertDialog(true, contacts)
            }

            override fun onDeleteClicked(id: Int) {
                viewModel.deleteContacts(id)
            }
        }

        binding.fabAddContact.setOnClickListener {
            showInsertDialog()
        }
    }

    private fun setupAdapter() {
        adapter = ContactsAdapter()
        with(
            binding.rvContacts
        ) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun showInsertDialog(isUpdate: Boolean = false, contacts: Contacts? = null) {
        val dialogViewBinding = ActivityAddContactBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this)

        dialogViewBinding.etName.focusAndShowKeyboard()

        alertDialog.apply {

            setTitle(if (isUpdate) R.string.update_contacts else R.string.add_contacts)

            setView(dialogViewBinding.root)
            if (isUpdate) contacts?.let { dialogViewBinding.populateForm(it) }
            setPositiveButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }

            setNegativeButton(if (isUpdate) R.string.update else R.string.add) { dialog, _ ->
                val id = contacts?.id ?: (viewModel.lastId + 1)
                setNegativeButtonAction(isUpdate, dialogViewBinding, id)
                dialog.cancel()
            }

        }.create().show()

    }

    private fun ActivityAddContactBinding.populateForm(contacts: Contacts) {
        etName.focusAndShowKeyboard()

        etName.setText(contacts.data.name)
        etSurname.setText(contacts.data.surname)
        etNumber.setText(contacts.number)
    }

    private fun ActivityAddContactBinding.getFormData(id: Int): Contacts {
        val data = Data(
            etName.text.toString(),
            etSurname.text.toString()
        )
        return Contacts(
            id,
            etNumber.text.toString(),
            data
        )
    }

    private fun setNegativeButtonAction(
        isUpdate: Boolean,
        dialogViewBinding: ActivityAddContactBinding,
        id: Int
    ) {
        val contacts = dialogViewBinding.getFormData(id)
        if (isUpdate) {
            viewModel.updateContacts(contacts)
        } else {
            viewModel.insertContact(contacts)
        }
    }

    private fun observeLiveData() {
        viewModel.operationLiveData.observe(this) {
            if (it) viewModel.fetchAllContacts()
        }

        viewModel.contactsList.observe(this) {
            viewModel.lastId = if (it.isNotEmpty()) it.last().id else 0
            adapter.submitList(it) // - отправление нового списка для сравнения и отображения
        }
    }

}