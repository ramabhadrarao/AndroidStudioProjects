package com.example.myapplication.ui

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Attachment
import com.example.myapplication.databinding.FragmentImportSelectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImportSelectionFragment : Fragment() {

    private var _binding: FragmentImportSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SongViewModel by viewModels()
    private var sharedUris: Array<Uri> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedUris = arguments?.getParcelableArray("uris")?.filterIsInstance<Uri>()?.toTypedArray() ?: emptyArray()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImportSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SongAdapter { song ->
            importFilesToSong(song.id)
        }
        binding.recyclerViewImportSongs.adapter = adapter
        binding.recyclerViewImportSongs.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allSongs.observe(viewLifecycleOwner) { songs ->
            adapter.submitList(songs)
        }

        binding.buttonCancelImport.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun importFilesToSong(songId: Long) {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val songDao = db.songDao()

            withContext(Dispatchers.IO) {
                sharedUris.forEach { uri ->
                    saveUriAsAttachment(uri, songId, songDao)
                }
            }
            
            Toast.makeText(requireContext(), "Files attached successfully!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun saveUriAsAttachment(uri: Uri, songId: Long, dao: com.example.myapplication.data.SongDao) {
        val contentResolver = requireContext().contentResolver
        var fileName = "imported_${System.currentTimeMillis()}"
        
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex)
            }
        }

        val attachmentsDir = File(requireContext().filesDir, "attachments/$songId")
        if (!attachmentsDir.exists()) attachmentsDir.mkdirs()

        val destFile = File(attachmentsDir, fileName)
        contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }

        val attachment = Attachment(
            songId = songId,
            fileName = fileName,
            filePath = destFile.absolutePath,
            fileType = contentResolver.getType(uri) ?: "unknown"
        )
        
        lifecycleScope.launch(Dispatchers.IO) {
            dao.insertAttachment(attachment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
