package com.example.madlevel7task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task2.R
import com.example.madlevel7task2.databinding.FragmentQuizBinding
import com.example.madlevel7task2.model.Question
import com.example.madlevel7task2.model.QuestionViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null

    private val viewModel: QuestionViewModel by activityViewModels()

    private val questions = arrayListOf<Question>()

    private var currentQuestionId = -1

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeQuestions()

        viewModel.getQuiz()

        binding.btnConfirm.setOnClickListener {
            checkAnswer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeQuestions() {

        viewModel.errorText.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        viewModel.questions.observe(viewLifecycleOwner) {
            currentQuestionId = -1

            questions.clear()
            questions.addAll(it)

            currentQuestionId++
            nextBuilding()
        }
    }

    private fun nextBuilding() {
        val currentQuestion = questions[currentQuestionId]

        Log.i("current", currentQuestion.question.toString())
        binding.tvQuestion.text = currentQuestion.question

        if (currentQuestion.answerOptions != null) {
            val options = currentQuestion.answerOptions

            binding.rbAnswerA.text = options[0]
            binding.rbAnswerB.text = options[1]
            binding.rbAnswerC.text = options[2]
        }
    }

    private fun checkAnswer() {
        val userAnswer = binding.rgAnswers.checkedRadioButtonId

        val radio = binding.rgAnswers.findViewById<RadioButton>(userAnswer)

        if (userAnswer == -1) {
            Toast.makeText(requireContext(), "Please select an answer!", Toast.LENGTH_SHORT).show()
            return
        }

        if (radio.text.toString() == questions[currentQuestionId].correctAnswer) {
            correctAnswer()
        } else {
            Toast.makeText(requireContext(), "Incorrect!",Toast.LENGTH_LONG).show()
        }
    }

    private fun correctAnswer() {
        if (currentQuestionId == questions.size - 1) {
            Toast.makeText(requireContext(), "Quiz complete!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            currentQuestionId++
            nextBuilding()
        }
        binding.rgAnswers.clearCheck()
    }
}