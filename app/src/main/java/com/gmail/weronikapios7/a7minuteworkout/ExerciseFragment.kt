package com.gmail.weronikapios7.a7minuteworkout

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.weronikapios7.a7minuteworkout.databinding.FragmentExerciseBinding


class ExerciseFragment : Fragment() {
    private var binding: FragmentExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var totalRestTime = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        setupRestView()
        return binding?.root
    }

    private fun setupRestView(){
        resetTimer()
        setResProgressBar()
    }

    private fun setResProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = totalRestTime - restProgress
                binding?.tvTimer?.text = (totalRestTime - restProgress).toString()
            }

            override fun onFinish() {
                //TODO
            }

        }.start()

    }

    private fun resetTimer(){
        if(restTimer !=null){
            restTimer?.cancel()
            restProgress = 0
            totalRestTime = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        resetTimer()
        binding = null
    }


}