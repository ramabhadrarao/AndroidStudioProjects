package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.data.Song
import com.example.myapplication.databinding.FragmentAddSongBinding

class AddSongFragment : Fragment() {

    private var _binding: FragmentAddSongBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SongViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            saveSong()
        }
    }

    private fun saveSong() {
        val titleEng = binding.editTitleEnglish.text.toString()
        val titleTel = binding.editTitleTelugu.text.toString()
        
        if (titleEng.isBlank() || titleTel.isBlank()) {
            Toast.makeText(requireContext(), "Please enter titles in both languages", Toast.LENGTH_SHORT).show()
            return
        }

        val difficulty = when (binding.radioDifficulty.checkedRadioButtonId) {
            binding.radioBeginner.id -> "Beginner"
            binding.radioIntermediate.id -> "Intermediate"
            binding.radioAdvanced.id -> "Advanced"
            else -> "Beginner"
        }

        val song = Song(
            titleEnglish = titleEng,
            titleTelugu = titleTel,
            ragamEnglish = binding.editRagamEnglish.text.toString(),
            ragamTelugu = binding.editRagamTelugu.text.toString(),
            talamEnglish = binding.editTalamEnglish.text.toString(),
            talamTelugu = binding.editTalamTelugu.text.toString(),
            composerEnglish = binding.editComposerEnglish.text.toString(),
            composerTelugu = binding.editComposerTelugu.text.toString(),
            lyricsEnglish = "",
            lyricsTelugu = binding.editLyricsTelugu.text.toString(),
            meaning = binding.editMeaning.text.toString(),
            difficultyLevel = difficulty,
            sourceType = "personal"
        )

        viewModel.insert(song)
        Toast.makeText(requireContext(), "Song saved!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
