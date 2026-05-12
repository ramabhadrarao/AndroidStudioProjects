package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "practice_history",
    foreignKeys = [
        ForeignKey(
            entity = Song::class,
            parentColumns = ["id"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("songId")]
)
data class PracticeSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val songId: Long,
    val durationSeconds: Long,
    val timestamp: Long = System.currentTimeMillis()
)
