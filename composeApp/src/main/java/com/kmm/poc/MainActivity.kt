package com.kmm.poc

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kmm.poc.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Note
import services.NoteDatabase
import viewmodels.NoteViewModel
import viewmodels.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private val database by lazy { NoteDatabase.getDatabase(applicationContext) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val factory = NoteViewModelFactory(database?.noteDao()!!)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        observeNotes()
        setEventListeners()
    }

    private fun setEventListeners() {
        binding.btnAddNote.setOnClickListener{
            val titleEt = binding.etTitle
            val descriptionEt = binding.etDescription

            if(!titleEt.text.isNullOrEmpty() && !descriptionEt.text.isNullOrEmpty()){
                val note = Note(title = titleEt.text.toString(), description = descriptionEt.text.toString())

                viewModel.addNote(note)

                titleEt.text.clear()
                descriptionEt.text.clear()
                titleEt.clearFocus()
                descriptionEt.clearFocus()
            }
        }
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            viewModel.notes.collect { notes ->
                if (notes.isNotEmpty()) {
                    binding.tvNotes.text = notes.joinToString(separator = "\n") { "${it.title}: ${it.description}" }
                } else {
                    binding.tvNotes.text = "No notes yet"
                }
            }
        }
        viewModel.getNotes()
    }
}