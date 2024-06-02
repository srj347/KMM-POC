package services

import android.content.Context
import android.media.metrics.BundleSession
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

class NoteDatabase {

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if(INSTANCE == null) {
                val appContext = context.applicationContext
                val dbFile = appContext.getDatabasePath("notes.db")
                INSTANCE = Room.databaseBuilder<AppDatabase>(
                    context = appContext,
                    name = dbFile.absolutePath
                )
                    .setDriver(BundledSQLiteDriver())
                    .build()
            }
            return INSTANCE
        }
    }
}