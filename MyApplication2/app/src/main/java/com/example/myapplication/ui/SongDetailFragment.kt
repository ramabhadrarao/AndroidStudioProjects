package com.example.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Attachment
import com.example.myapplication.data.PracticeSession
import com.example.myapplication.data.Song
import com.example.myapplication.data.SongRepository
import com.example.myapplication.databinding.FragmentSongDetailBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale

class SongDetailFragment : Fragment() {

    private var _binding: FragmentSongDetailBinding? = null
    private val binding get() = _binding!!

    private var songId: Long = -1
    private var isPracticing = false
    private var seconds = 0L
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            seconds++
            updateTimerText()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songId = arguments?.getLong("songId") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        val songDao = db.songDao()
        val repository = SongRepository(songDao)

        lifecycleScope.launch {
            val song = repository.getSongById(songId)
            song?.let { displaySongDetails(it) }
            
            // Load and display attachments
            val attachments = songDao.getAttachmentsForSong(songId).first()
            displayAttachments(attachments)
        }

        binding.fabPractice.setOnClickListener {
            startPractice()
        }

        binding.buttonStopTimer.setOnClickListener {
            stopPractice()
        }
    }

    private fun displaySongDetails(song: Song) {
        binding.textDetailTitle.text = "${song.titleEnglish} / ${song.titleTelugu}"
        binding.textDetailInfo.text = "Ragam: ${song.ragamEnglish} | Talam: ${song.talamEnglish}\nComposer: ${song.composerEnglish}"
        binding.textDetailLyrics.text = song.lyricsTelugu
        binding.textDetailMeaning.text = song.meaning
    }

    private fun displayAttachments(attachments: List<Attachment>) {
        binding.chipGroupAttachments.removeAllViews()
        attachments.forEach { attachment ->
            val chip = Chip(requireContext()).apply {
                text = attachment.fileName
                setOnClickListener {
                    openAttachment(attachment)
                }
            }
            binding.chipGroupAttachments.addView(chip)
        }
    }

    private fun openAttachment(attachment: Attachment) {
        if (attachment.fileType == "youtube_link") {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(attachment.filePath))
            startContext(intent)
            return
        }

        val file = File(attachment.filePath)
        if (!file.exists()) {
            Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show()
            return
        }

        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, attachment.fileType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startContext(intent)
    }

    private fun startContext(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No app found to open this file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPractice() {
        isPracticing = true
        seconds = 0
        updateTimerText()
        binding.cardTimer.visibility = View.VISIBLE
        binding.fabPractice.visibility = View.GONE
        handler.postDelayed(runnable, 1000)
    }

    private fun stopPractice() {
        isPracticing = false
        handler.removeCallbacks(runnable)
        binding.cardTimer.visibility = View.GONE
        binding.fabPractice.visibility = View.VISIBLE

        savePracticeSession()
    }

    private fun savePracticeSession() {
        if (seconds < 5) return

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(requireContext())
            db.songDao().insertPracticeSession(
                PracticeSession(songId = songId, durationSeconds = seconds)
            )
            launch(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Practice session saved: ${formatTime(seconds)}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTimerText() {
        binding.textTimerClock.text = formatTime(seconds)
    }

    private fun formatTime(secs: Long): String {
        val hours = secs / 3600
        val minutes = (secs % 3600) / 60
        val s = secs % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, s)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null
    }
}
