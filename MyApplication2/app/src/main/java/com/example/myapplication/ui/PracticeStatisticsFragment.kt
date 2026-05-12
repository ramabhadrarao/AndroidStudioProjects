package com.example.myapplication.ui

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.PracticeSession
import com.example.myapplication.databinding.FragmentPracticeStatisticsBinding
import com.example.myapplication.databinding.ItemPracticeSessionBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

class PracticeStatisticsFragment : Fragment() {

    private var _binding: FragmentPracticeStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PracticeAdapter()
        binding.recyclerRecentPractice.adapter = adapter

        val songDao = AppDatabase.getDatabase(requireContext()).songDao()

        lifecycleScope.launch {
            songDao.getTotalPracticeTime().collectLatest { totalSeconds ->
                binding.textTotalPracticeTime.text = formatTime(totalSeconds ?: 0)
            }
        }

        lifecycleScope.launch {
            songDao.getAllPracticeSessions().collectLatest { sessions ->
                adapter.submitList(sessions)
            }
        }
    }

    private fun formatTime(secs: Long): String {
        val hours = secs / 3600
        val minutes = (secs % 3600) / 60
        val s = secs % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, s)
    }

    inner class PracticeAdapter : ListAdapter<PracticeSession, PracticeAdapter.ViewHolder>(DiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemPracticeSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val session = getItem(position)
            holder.bind(session)
        }

        inner class ViewHolder(private val binding: ItemPracticeSessionBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(session: PracticeSession) {
                binding.textSessionDuration.text = formatTime(session.durationSeconds)
                binding.textSessionDate.text = DateFormat.format("dd MMM yyyy, hh:mm a", session.timestamp)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PracticeSession>() {
        override fun areItemsTheSame(oldItem: PracticeSession, newItem: PracticeSession) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PracticeSession, newItem: PracticeSession) = oldItem == newItem
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
