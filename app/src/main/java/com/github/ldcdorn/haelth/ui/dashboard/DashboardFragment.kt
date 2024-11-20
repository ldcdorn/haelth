package com.github.ldcdorn.haelth.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.ldcdorn.haelth.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mealNameEditText: EditText = binding.nutritionCardNameMeal
        val proteinEditText: EditText = binding.editTextNumber2
        val fatEditText: EditText = binding.editTextNumber3
        val carbsEditText: EditText = binding.editTextNumber4
        val saveButton = binding.button

        saveButton.setOnClickListener {
            val mealName = mealNameEditText.text.toString()
            val protein = proteinEditText.text.toString()
            val fat = fatEditText.text.toString()
            val carbs = carbsEditText.text.toString()

            if (mealName.isEmpty() || protein.isEmpty() || fat.isEmpty() || carbs.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Entered: $mealName, Protein: $protein, Fat: $fat, Carbs: $carbs",
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