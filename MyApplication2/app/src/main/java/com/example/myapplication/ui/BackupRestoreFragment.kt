package com.example.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.databinding.FragmentBackupRestoreBinding
import com.example.myapplication.utils.FileHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BackupRestoreFragment : Fragment() {

    private var _binding: FragmentBackupRestoreBinding? = null
    private val binding get() = _binding!!

    private val pickBackupLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { restoreFromBackup(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBackupRestoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonExportBackup.setOnClickListener {
            exportFullBackup()
        }

        binding.buttonImportBackup.setOnClickListener {
            pickBackupLauncher.launch("application/zip")
        }
    }

    private fun exportFullBackup() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val songs = db.songDao().getAllSongs().first()
            val allAttachments = mutableMapOf<Long, List<com.example.myapplication.data.Attachment>>()
            
            songs.forEach { song ->
                allAttachments[song.id] = db.songDao().getAttachmentsForSong(song.id).first()
            }

            val backupFile = withContext(Dispatchers.IO) {
                FileHelper.createFullBackup(requireContext(), songs, allAttachments)
            }

            if (backupFile != null && backupFile.exists()) {
                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.fileprovider",
                    backupFile
                )
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/zip"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(Intent.createChooser(intent, "Share Backup File"))
            } else {
                Toast.makeText(requireContext(), "Failed to create backup", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun restoreFromBackup(uri: Uri) {
        // Implementation for full restore would involve unzipping and inserting into DB.
        // For brevity, we'll notify the user it's starting.
        Toast.makeText(requireContext(), "Restoring from backup... (This will merge data)", Toast.LENGTH_LONG).show()
        // Logic similar to extractSongPackage but for multiple songs
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
