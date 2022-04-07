package com.gmail.weronikapios7.a7minuteworkout

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.weronikapios7.a7minuteworkout.databinding.FragmentExerciseBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {
    private var binding: FragmentExerciseBinding? = null

    private var exerciseProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var totalExerciseTime = 30

    private var restProgress = 0
    private var restTimer: CountDownTimer? = null
    private var totalRestTime = 10

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(context, this)
        setupRestView()
        setupExerciseStatusRV()
        return binding?.root
    }


    private fun setupRestView(){

        try {
            val soundURI = Uri.parse(
                "android.resource://com.gmail.weronikapios7.a7minuteworkout/"
                        + R.raw.press_start)
            player = MediaPlayer.create(context, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
        resetRestTimer()
        setRestProgressBar()
        binding?.flCountdownRest?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flCountdownExercise?.visibility = View.INVISIBLE
        binding?.ivExercise?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvNextExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

    }

    private fun setupExerciseView(){
        resetExerciseTimer()
        setExerciseProgressBar()
        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.flCountdownRest?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flCountdownExercise?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.INVISIBLE

        binding?.ivExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
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
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseAdapter!!.notifyDataSetChanged()
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    findNavController().navigate(R.id.exercise_to_finish)
                }
            }

        }.start()

    }

    private fun resetRestTimer(){
        if(restTimer !=null){
            restTimer?.cancel()
            restProgress = 0
            totalRestTime = 10
        }
    }

    private fun resetExerciseTimer(){
        if(exerciseTimer !=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
            totalExerciseTime = 30
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        resetRestTimer()
        resetExerciseTimer()
        binding = null

        //show down text to speech on destroy
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(context, "Language is not supported", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "Initialization Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRV(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

}