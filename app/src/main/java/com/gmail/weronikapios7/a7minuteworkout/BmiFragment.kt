package com.gmail.weronikapios7.a7minuteworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.weronikapios7.a7minuteworkout.databinding.FragmentBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode


class BmiFragment : Fragment() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private var binding: FragmentBmiBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBmiBinding.inflate(inflater, container, false)
        binding?.btnCalculate?.setOnClickListener {
            calculateUnits()
        }

        makeVisibleMetricUnitsView()
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.rb_metric_units){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }
        return binding?.root
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilWeight?.hint = "Weight (kg)"
        binding?.tilMetricHeight?.visibility = View.VISIBLE
        binding?.tilUsHeightFeet?.visibility = View.GONE
        binding?.tilUsHeightInch?.visibility = View.GONE

        clearResult()
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.tilWeight?.hint = "Weight (pounds)"
        binding?.tilMetricHeight?.visibility = View.GONE
        binding?.tilUsHeightFeet?.visibility = View.VISIBLE
        binding?.tilUsHeightInch?.visibility = View.VISIBLE

        clearResult()
    }

    private fun clearResult() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            binding?.etMetricHeight?.text?.clear()
            binding?.etWeight?.text?.clear()

        } else {
            binding?.etWeight?.text?.clear()
            binding?.etUsHeightFeet?.text?.clear()
            binding?.etUsHeightInch?.text?.clear()
        }

        binding?.llDisplayBmiResult?.visibility = View.INVISIBLE
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        when {
            binding?.etWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etMetricHeight?.text.toString().isEmpty() -> {
                isValid = false
            }

        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

    private fun calculateMetricBMI(): Float {
        val height =
            binding?.etMetricHeight?.text.toString()
                .toFloat() / 100 //divide by 100 to convert cm to m
        val weight = binding?.etWeight?.text.toString().toFloat()
        return weight / (height * height)

    }

    private fun calculateUSBMI(): Float {
        val height =
            binding?.etUsHeightInch?.text.toString().toFloat() +
                    binding?.etUsHeightFeet?.text.toString().toFloat() * 12

        val weight = binding?.etWeight?.text.toString().toFloat()

        return 703 * (weight / (height * height))
    }

    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNITS_VIEW){
            if (validateMetricUnits()) {
                val bmi = calculateMetricBMI()
                getResult(bmi)
            }else{
                Toast.makeText(context, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }else{
            if(validateUSUnits()){
                val bmi = calculateUSBMI()
                getResult(bmi)
            }else{
                Toast.makeText(context, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getResult(bmi: Float){
        binding?.tvBmiResult?.text = bmi.toString()
        setResultText(bmi)
        setVisibility()
    }
    private fun setVisibility() {
        binding?.llDisplayBmiResult?.visibility = View.VISIBLE
    }

    private fun setResultText(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        when (bmi) {
            in 0f..15f -> {
                bmiLabel = "Very severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat up!"
            }
            in 15f..16f -> {
                bmiLabel = "Severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat up!"
            }
            in 16f..18.5f -> {
                bmiLabel = "Underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat up!"
            }
            in 18.5f..25f -> {
                bmiLabel = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            in 25f..30f -> {
                bmiLabel = "Overweight"
                bmiDescription =
                    "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            in 30f..35f -> {
                bmiLabel = "Obese Class | (Moderately obese)"
                bmiDescription =
                    "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            in 35f..40f -> {
                bmiLabel = "Obese Class || (Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            else -> {
                bmiLabel = "Obese Class ||| (Very Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
        }
        BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            .also { binding?.tvBmiResult?.text = it }
        binding?.tvBmiType?.text = bmiLabel
        binding?.tvBmiDescription?.text = bmiDescription
    }


}