package com.example.madlevel7task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task2.databinding.FragmentBuildingBinding
import com.example.madlevel7task2.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BuildingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BuildingFragment : Fragment() {
    private var _binding: FragmentBuildingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBuildingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}