package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Insert
    suspend fun insert(song: Song): Long

    @Update
    suspend fun update(song: Song)

    @Delete
    suspend fun delete(song: Song)

    @Query("SELECT * FROM songs ORDER BY createdAt DESC")
    fun getAllSongs(): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getSongById(id: Long): Song?

    @Query("SELECT * FROM songs WHERE titleEnglish LIKE :query OR titleTelugu LIKE :query OR ragamEnglish LIKE :query OR composerEnglish LIKE :query")
    fun searchSongs(query: String): Flow<List<Song>>

    // Attachment methods
    @Insert
    suspend fun insertAttachment(attachment: Attachment): Long

    @Query("SELECT * FROM attachments WHERE songId = :songId")
    fun getAttachmentsForSong(songId: Long): Flow<List<Attachment>>

    @Delete
    suspend fun deleteAttachment(attachment: Attachment)
    
    // Practice History
    @Insert
    suspend fun insertPracticeSession(session: PracticeSession)
    
    @Query("SELECT * FROM practice_history ORDER BY timestamp DESC")
    fun getAllPracticeSessions(): Flow<List<PracticeSession>>

    @Query("SELECT SUM(durationSeconds) FROM practice_history")
    fun getTotalPracticeTime(): Flow<Long?>

    @Query("SELECT * FROM practice_history WHERE songId = :songId ORDER BY timestamp DESC")
    fun getPracticeHistoryForSong(songId: Long): Flow<List<PracticeSession>>
}
