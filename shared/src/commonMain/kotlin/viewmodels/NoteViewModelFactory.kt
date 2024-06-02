package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import services.NoteDao
import kotlin.reflect.KClass

class NoteViewModelFactory(
    private val noteDao: NoteDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return NoteViewModel(noteDao) as T
    }
}