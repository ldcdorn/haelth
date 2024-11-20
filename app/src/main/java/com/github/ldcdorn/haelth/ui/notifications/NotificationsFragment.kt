package com.github.ldcdorn.haelth.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.ldcdorn.haelth.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Access input fields from the updated XML
        val exerciseNameEditText: EditText = binding.exerciseNameInput
        val weightEditText: EditText = binding.weightInput
        val repsEditText: EditText = binding.repsInput
        val setsEditText: EditText = binding.setsInput
        val saveButton = binding.saveButton

        // Handle Save button click
        saveButton.setOnClickListener {
            val exerciseName = exerciseNameEditText.text.toString()
            val weight = weightEditText.text.toString()
            val reps = repsEditText.text.toString()
            val sets = setsEditText.text.toString()

            // Validate input fields
            if (exerciseName.isEmpty() || weight.isEmpty() || reps.isEmpty() || sets.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Entered: Exercise: $exerciseName, Weight: $weight, Reps: $reps, Sets: $sets",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}