package services

import androidx.room.Room
import androidx.room.RoomDatabase

class NoteDatabase {

    companion object{
        fun getDatabase(): RoomDatabase.Builder<AppDatabase> {
            val dbFilePath = NSHomeDirectory() + "/notes.db"
            return Room.databaseBuilder<AppDatabase>(
                name = dbFilePath,
                factory =  { AppDatabase::class.instantiateImpl() }
            )
        }
    }
}