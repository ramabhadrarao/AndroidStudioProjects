package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

class SongRepository(private val songDao: SongDao) {
    val allSongs: Flow<List<Song>> = songDao.getAllSongs()

    suspend fun insert(song: Song): Long {
        return songDao.insert(song)
    }

    suspend fun getSongById(id: Long): Song? {
        return songDao.getSongById(id)
    }
}
