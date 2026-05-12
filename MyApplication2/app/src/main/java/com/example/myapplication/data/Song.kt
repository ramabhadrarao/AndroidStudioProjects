package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titleEnglish: String,
    val titleTelugu: String,
    val ragamEnglish: String,
    val ragamTelugu: String,
    val talamEnglish: String,
    val talamTelugu: String,
    val composerEnglish: String,
    val composerTelugu: String,
    val lyricsEnglish: String?,
    val lyricsTelugu: String,
    val meaning: String?,
    val difficultyLevel: String, // Beginner, Intermediate, Advanced
    val sourceType: String, // personal, received_from_teacher, imported
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
