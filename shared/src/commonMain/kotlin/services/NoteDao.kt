package services

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getNotes(): Flow<List<Note>>

    @Upsert
    suspend fun insertNote(note: Note)
}