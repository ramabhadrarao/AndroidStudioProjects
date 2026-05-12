package com.example.myapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Song
import com.example.myapplication.data.SongRepository
import kotlinx.coroutines.launch

class SongViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SongRepository
    val allSongs: LiveData<List<Song>>

    init {
        val songDao = AppDatabase.getDatabase(application).songDao()
        repository = SongRepository(songDao)
        allSongs = repository.allSongs.asLiveData()
    }

    fun insert(song: Song) = viewModelScope.launch {
        repository.insert(song)
    }
}
