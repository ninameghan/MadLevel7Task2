package com.example.madlevel7task2.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madlevel7task2.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FIRESTORE"
    private val questionRepository = QuestionRepository()

    val questions: LiveData<List<Question>> = questionRepository.questions

    val createSuccess: LiveData<Boolean> = questionRepository.createSuccess

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String>
        get() = _errorText

    fun getQuiz() {
        viewModelScope.launch {
            try {
                questionRepository.getQuiz()
            } catch (ex: QuestionRepository.QuestionRetrievalError) {
                val errorMsg = "Something went wrong while retrieving question"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }
}