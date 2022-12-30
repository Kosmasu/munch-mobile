package com.example.munch.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Database(entities = [
  Token::class
], version = 1
)
abstract class AppDatabase: RoomDatabase() {
  abstract val TokenDAO: TokenDAO
  companion object {
    private var _database: AppDatabase? = null
    public val coroutine = CoroutineScope(Dispatchers.IO)

    fun build(context: Context): AppDatabase {
      if (_database == null) {
        _database =
          Room.databaseBuilder(context, AppDatabase::class.java, "db_munch").build()
      }
      return _database!!
    }
  }
}