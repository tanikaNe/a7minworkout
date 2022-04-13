package com.gmail.weronikapios7.a7minuteworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.gmail.weronikapios7.a7minuteworkout.databinding.FragmentExerciseBinding
import com.gmail.weronikapios7.a7minuteworkout.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    private var binding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding?.flStart?.setOnClickListener { it.findNavController().navigate(R.id.welcome_to_exercise) }
        binding?.flBMI?.setOnClickListener { it.findNavController().navigate(R.id.welcome_to_bmi) }
        return binding?.root
    }


}