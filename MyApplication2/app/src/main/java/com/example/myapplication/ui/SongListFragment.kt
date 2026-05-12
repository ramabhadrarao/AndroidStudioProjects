package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSongListBinding

class SongListFragment : Fragment() {

    private var _binding: FragmentSongListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SongViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SongAdapter { song ->
            val bundle = bundleOf("songId" to song.id)
            findNavController().navigate(R.id.action_songListFragment_to_songDetailFragment, bundle)
        }
        binding.recyclerViewSongs.adapter = adapter
        binding.recyclerViewSongs.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allSongs.observe(viewLifecycleOwner) { songs ->
            adapter.submitList(songs)
            binding.textEmptyState.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
