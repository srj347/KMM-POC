package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Note
import services.NoteDao

class NoteViewModel(
    private val noteDao: NoteDao
): ViewModel() {
    private val _notes = MutableStateFlow(emptyList<Note>())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(note: Note){
        viewModelScope.launch {
            noteDao.insertNote(note)
            getNotes()
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            noteDao.getNotes().collect {
                _notes.value = it
            }
        }
    }
}