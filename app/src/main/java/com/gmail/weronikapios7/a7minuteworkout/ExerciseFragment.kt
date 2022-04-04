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

    private var exerciseProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var totalExerciseTime = 30

    private var restProgress = 0
    private var restTimer: CountDownTimer? = null
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
        resetRestTimer()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flCountdownRest?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flCountdownExercise?.visibility = View.VISIBLE
        resetExerciseTimer()
        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = totalRestTime - restProgress
                binding?.tvTimer?.text = (totalRestTime - restProgress).toString()
            }

            override fun onFinish() {
                setupExerciseView()
            }

        }.start()

    }

    private fun setExerciseProgressBar(){
        binding?.progressBar?.progress = restProgress

        exerciseTimer = object: CountDownTimer(30000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = totalExerciseTime - exerciseProgress
                binding?.tvTimerExercise?.text = (totalExerciseTime - exerciseProgress).toString()
            }

            override fun onFinish() {
                //TODO show rest view
            }

        }.start()

    }

    private fun resetRestTimer(){
        if(restTimer !=null){
            restTimer?.cancel()
            restProgress = 0
            totalRestTime = 0
        }
    }

    private fun resetExerciseTimer(){
        if(exerciseTimer !=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
            totalExerciseTime = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        resetRestTimer()
        resetExerciseTimer()
        binding = null
    }

}