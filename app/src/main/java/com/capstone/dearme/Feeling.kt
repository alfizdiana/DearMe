package com.capstone.dearme

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.dearme.databinding.FragmentFeelingBinding
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Feeling : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFeelingBinding
    private val dummyData = mutableListOf<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeelingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Variables to store scores
        var q1score = 0
        var q2score = 0
        var q3score = 0
        var q4score = 0
        var q5score = 0
        var q6score = 0

        // Listener for each RadioGroup
        binding.question1.setOnCheckedChangeListener { group, checkedId ->
            q1score = getScoreFromRadioButtonId(checkedId)
        }

        binding.question2.setOnCheckedChangeListener { group, checkedId ->
            q2score = getScoreFromRadioButtonId(checkedId)
        }

        binding.question3.setOnCheckedChangeListener { group, checkedId ->
            q3score = getScoreFromRadioButtonId(checkedId)
        }

        binding.question4.setOnCheckedChangeListener { group, checkedId ->
            q4score = getScoreFromRadioButtonId(checkedId)
        }

        binding.question5.setOnCheckedChangeListener { group, checkedId ->
            q5score = getScoreFromRadioButtonId(checkedId)
        }

        binding.question6.setOnCheckedChangeListener { group, checkedId ->
            q6score = getScoreFromRadioButtonId(checkedId)
        }

        binding.feelingButton.setOnClickListener {
            val total = ((q1score.toFloat() + q2score.toFloat() + q3score.toFloat() + q4score.toFloat() + q5score.toFloat() + q6score.toFloat()) / 24)
            val formatTotal = String.format("%.1f", total)
            val message = "Daily Score: $formatTotal/4.0"
            val duration = Toast.LENGTH_LONG
            Toast.makeText(requireContext(), message, duration).show()

            // Store data locally
            val dummyScore = hashMapOf(
                "score" to formatTotal,
                "date" to getCurrentDate(),
                "time" to getCurrentTime()
            )
            dummyData.add(dummyScore)

            // Print dummy data
            for (data in dummyData) {
                Log.d(ContentValues.TAG, "Dummy Data: $data")
            }
        }
    }

    private fun getScoreFromRadioButtonId(checkedId: Int): Int {
        return when (checkedId) {
            R.id.q11, R.id.q21, R.id.q31 -> 1
            R.id.q12, R.id.q22, R.id.q32 -> 2
            R.id.q13, R.id.q23, R.id.q33 -> 3
            R.id.q14, R.id.q24, R.id.q34 -> 4
            else -> 0 // If none selected
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm:ss")
        val currentTime = Date()
        return timeFormat.format(currentTime)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Feeling().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
