package com.example.myapplication.utils

import android.content.Context
import android.net.Uri
import com.example.myapplication.data.Attachment
import com.example.myapplication.data.Song
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object FileHelper {

    fun createSongPackage(context: Context, song: Song, attachments: List<Attachment>): File? {
        val tempDir = File(context.cacheDir, "export_${song.id}")
        if (tempDir.exists()) tempDir.deleteRecursively()
        tempDir.mkdirs()

        val songJson = JSONObject().apply {
            put("titleEnglish", song.titleEnglish)
            put("titleTelugu", song.titleTelugu)
            put("ragamEnglish", song.ragamEnglish)
            put("ragamTelugu", song.ragamTelugu)
            put("talamEnglish", song.talamEnglish)
            put("talamTelugu", song.talamTelugu)
            put("composerEnglish", song.composerEnglish)
            put("composerTelugu", song.composerTelugu)
            put("lyricsEnglish", song.lyricsEnglish)
            put("lyricsTelugu", song.lyricsTelugu)
            put("meaning", song.meaning)
            put("difficultyLevel", song.difficultyLevel)
        }
        File(tempDir, "song.json").writeText(songJson.toString())

        val attachmentsDir = File(tempDir, "attachments")
        attachmentsDir.mkdirs()
        attachments.forEach { attachment ->
            val srcFile = File(attachment.filePath)
            if (srcFile.exists()) {
                srcFile.copyTo(File(attachmentsDir, attachment.fileName))
            }
        }

        val zipFile = File(context.cacheDir, "${song.titleEnglish.replace(" ", "_")}.zip")
        if (zipFile.exists()) zipFile.delete()

        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { zos ->
            tempDir.walkTopDown().forEach { file ->
                if (file.isFile) {
                    val entryName = file.relativeTo(tempDir).path
                    zos.putNextEntry(ZipEntry(entryName))
                    file.inputStream().use { it.copyTo(zos) }
                    zos.closeEntry()
                }
            }
        }

        tempDir.deleteRecursively()
        return zipFile
    }

    fun extractSongPackage(context: Context, zipUri: Uri): Pair<Song, List<File>>? {
        val tempDir = File(context.cacheDir, "import_${System.currentTimeMillis()}")
        tempDir.mkdirs()

        try {
            context.contentResolver.openInputStream(zipUri)?.use { input ->
                ZipInputStream(input).use { zis ->
                    var entry = zis.nextEntry
                    while (entry != null) {
                        val file = File(tempDir, entry.name)
                        if (entry.isDirectory) {
                            file.mkdirs()
                        } else {
                            file.parentFile?.mkdirs()
                            FileOutputStream(file).use { output ->
                                zis.copyTo(output)
                            }
                        }
                        zis.closeEntry()
                        entry = zis.nextEntry
                    }
                }
            }

            val songJsonFile = File(tempDir, "song.json")
            if (!songJsonFile.exists()) return null

            val json = JSONObject(songJsonFile.readText())
            val song = Song(
                titleEnglish = json.getString("titleEnglish"),
                titleTelugu = json.getString("titleTelugu"),
                ragamEnglish = json.getString("ragamEnglish"),
                ragamTelugu = json.getString("ragamTelugu"),
                talamEnglish = json.getString("talamEnglish"),
                talamTelugu = json.getString("talamTelugu"),
                composerEnglish = json.getString("composerEnglish"),
                composerTelugu = json.getString("composerTelugu"),
                lyricsEnglish = json.optString("lyricsEnglish"),
                lyricsTelugu = json.getString("lyricsTelugu"),
                meaning = json.optString("meaning"),
                difficultyLevel = json.getString("difficultyLevel"),
                sourceType = "imported"
            )

            val attachmentFiles = File(tempDir, "attachments").listFiles()?.toList() ?: emptyList()
            
            return Pair(song, attachmentFiles)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun createFullBackup(context: Context, songs: List<Song>, allAttachments: Map<Long, List<Attachment>>): File? {
        val tempDir = File(context.cacheDir, "full_backup")
        if (tempDir.exists()) tempDir.deleteRecursively()
        tempDir.mkdirs()

        val songsArray = JSONArray()
        songs.forEach { song ->
            val songJson = JSONObject().apply {
                put("id", song.id)
                put("titleEnglish", song.titleEnglish)
                put("titleTelugu", song.titleTelugu)
                put("ragamEnglish", song.ragamEnglish)
                put("ragamTelugu", song.ragamTelugu)
                put("talamEnglish", song.talamEnglish)
                put("talamTelugu", song.talamTelugu)
                put("composerEnglish", song.composerEnglish)
                put("composerTelugu", song.composerTelugu)
                put("lyricsEnglish", song.lyricsEnglish)
                put("lyricsTelugu", song.lyricsTelugu)
                put("meaning", song.meaning)
                put("difficultyLevel", song.difficultyLevel)
                put("sourceType", song.sourceType)
                put("createdAt", song.createdAt)
            }
            songsArray.put(songJson)

            // Copy song specific attachments
            val songAttachmentsDir = File(tempDir, "attachments/${song.id}")
            songAttachmentsDir.mkdirs()
            allAttachments[song.id]?.forEach { attachment ->
                val srcFile = File(attachment.filePath)
                if (srcFile.exists()) {
                    srcFile.copyTo(File(songAttachmentsDir, attachment.fileName))
                }
            }
        }
        File(tempDir, "backup_data.json").writeText(songsArray.toString())

        val zipFile = File(context.getExternalFilesDir(null), "SangeetaPathamBackup.zip")
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { zos ->
            tempDir.walkTopDown().forEach { file ->
                if (file.isFile) {
                    val entryName = file.relativeTo(tempDir).path
                    zos.putNextEntry(ZipEntry(entryName))
                    file.inputStream().use { it.copyTo(zos) }
                    zos.closeEntry()
                }
            }
        }
        tempDir.deleteRecursively()
        return zipFile
    }
}
