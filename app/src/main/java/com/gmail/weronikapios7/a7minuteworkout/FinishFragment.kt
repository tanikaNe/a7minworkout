package com.gmail.weronikapios7.a7minuteworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gmail.weronikapios7.a7minuteworkout.databinding.FragmentFinishBinding


class FinishFragment : Fragment() {

    private var binding: FragmentFinishBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinishBinding.inflate(inflater, container, false)
        binding?.btnFinish?.setOnClickListener {
            findNavController().navigate(R.id.finish_to_welcome)
        }
        return binding?.root
    }


}