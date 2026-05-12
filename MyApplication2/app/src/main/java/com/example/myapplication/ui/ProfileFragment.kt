package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProfile()

        binding.buttonSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun loadProfile() {
        val prefs = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        binding.editProfileName.setText(prefs.getString("name", ""))
        binding.editProfileMobile.setText(prefs.getString("mobile", ""))
        
        val role = prefs.getString("role", "Student")
        if (role == "Teacher") {
            binding.radioTeacher.isChecked = true
        } else {
            binding.radioStudent.isChecked = true
        }
    }

    private fun saveProfile() {
        val name = binding.editProfileName.text.toString()
        val mobile = binding.editProfileMobile.text.toString()
        val role = if (binding.radioTeacher.isChecked) "Teacher" else "Student"

        requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            .edit()
            .putString("name", name)
            .putString("mobile", mobile)
            .putString("role", role)
            .apply()

        Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
