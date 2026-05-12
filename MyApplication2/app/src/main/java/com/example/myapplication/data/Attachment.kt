package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attachments",
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
data class Attachment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val songId: Long,
    val fileName: String,
    val filePath: String,
    val fileType: String, // audio, image, pdf, youtube_link
    val createdAt: Long = System.currentTimeMillis()
)
