package com.example.myapplication.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Attachment
import com.example.myapplication.data.Song
import com.example.myapplication.databinding.FragmentImportPackageBinding
import com.example.myapplication.utils.FileHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ImportPackageFragment : DialogFragment() {

    private var _binding: FragmentImportPackageBinding? = null
    private val binding get() = _binding!!

    private var zipUri: Uri? = null
    private var extractedSong: Song? = null
    private var extractedAttachments: List<File> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zipUri = arguments?.getParcelable("zipUri")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImportPackageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        zipUri?.let { uri ->
            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) {
                    FileHelper.extractSongPackage(requireContext(), uri)
                }
                
                if (result != null) {
                    extractedSong = result.first
                    extractedAttachments = result.second
                    binding.textPackage_details.text = "Song: ${extractedSong?.titleEnglish}\n" +
                            "Composer: ${extractedSong?.composerEnglish}\n" +
                            "Attachments: ${extractedAttachments.size} files"
                } else {
                    Toast.makeText(requireContext(), "Invalid song package", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }

        binding.buttonAccept.setOnClickListener {
            saveImportedSong()
        }

        binding.buttonReject.setOnClickListener {
            dismiss()
        }
    }

    private fun saveImportedSong() {
        val song = extractedSong ?: return
        
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val songDao = db.songDao()
            
            val newSongId = withContext(Dispatchers.IO) {
                val id = songDao.insert(song)
                
                // Copy attachments to permanent storage
                val attachmentsDir = File(requireContext().filesDir, "attachments/$id")
                if (!attachmentsDir.exists()) attachmentsDir.mkdirs()
                
                extractedAttachments.forEach { file ->
                    val destFile = File(attachmentsDir, file.name)
                    file.copyTo(destFile)
                    
                    val attachment = Attachment(
                        songId = id,
                        fileName = file.name,
                        filePath = destFile.absolutePath,
                        fileType = "imported" // Should ideally detect mime type
                    )
                    songDao.insertAttachment(attachment)
                }
                id
            }
            
            Toast.makeText(requireContext(), "Song imported successfully!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
