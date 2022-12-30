package com.example.munch.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TokenDAO {
  @Insert
  suspend fun insert(token: Token)

  @Update
  suspend fun update(token: Token)

  @Delete
  suspend fun delete(token: Token)

  @Query("SELECT * FROM Token")
  suspend fun fetch(): List<Token>

  @Query("SELECT * FROM Token where users_id = :users_id")
  suspend fun fetch(users_id: ULong): Token?

}