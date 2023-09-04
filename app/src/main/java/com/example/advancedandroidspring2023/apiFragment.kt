package com.example.advancedandroidspring2023

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.advancedandroidspring2023.databinding.FragmentDataBinding

class ApiFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val root: View = binding.root
// navigate to another fragment, pass some parameter too
        binding.navigateButton.setOnClickListener {
            val action = apiFragmentDirections.actionApiFragmentToApiDetailFragment(123)
            it.findNavController().navigate(action)
        }
        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}