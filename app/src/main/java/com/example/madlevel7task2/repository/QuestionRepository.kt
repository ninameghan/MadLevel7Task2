package com.example.madlevel7task2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel7task2.model.Question
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class QuestionRepository {
    private var firestore = FirebaseFirestore.getInstance()
    private var quiz = firestore.collection("questions")

    private val _questions = MutableLiveData<List<Question>>()

    val questions: LiveData<List<Question>>
        get() = _questions

    //the QuizFragment can use this to see if creation succeeded
    private val _createSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val createSuccess: LiveData<Boolean>
        get() = _createSuccess

    suspend fun getQuiz() {
        val questions = arrayListOf<Question>()

        try {
            //firestore has support for coroutines via the extra dependency we've added :)
            withTimeout(30_000) {
                val data = quiz
                    .get()
                    .await().documents

                for (question in data) {
                    questions.add(question.toObject()!!)
                }

                _questions.value = questions
            }
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
            throw QuestionRetrievalError("Retrieval-firebase-task was unsuccessful")
        }
    }

    class QuestionRetrievalError(message: String) : Exception(message)
}