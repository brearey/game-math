package ru.oktemsec.gamemath

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.rangeTo
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var answer:Int = 0
    lateinit var messageText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messageText = findViewById(R.id.message_text)
        val taskText:TextView = findViewById(R.id.math_task)
        val answerET:EditText = findViewById(R.id.answer_et)

        answerET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (answer in 1..9 && answerET.text.length == 1) {
                    Log.d("AnswerCheck" ,checkAnswer(answerET.text.toString().trim()))
                    answerET.text.clear()
                    taskText.text = generateTask()
                }
                else if (answer >= 10 && answerET.text.length > 1) {
                    Log.d("AnswerCheck" ,checkAnswer(answerET.text.toString().trim()))
                    answerET.text.clear()
                    taskText.text = generateTask()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        taskText.text = generateTask()
    }

    fun generateTask():String {
        val firstNumber:Int
        val secondNumber:Int
        val operator:String = listOf("+", "-", "*")[Random.nextInt(1, 3)]
        if (operator == "*") {
            firstNumber = Random.nextInt(1,9)
            secondNumber = Random.nextInt(1,9)
        }
        else if (operator == "-") {
            firstNumber = Random.nextInt(24,48)
            secondNumber = Random.nextInt(1,23)
        }
        else {
            firstNumber = Random.nextInt(1,48)
            secondNumber = Random.nextInt(1,48)
        }

        when (operator) {
            "+" -> answer = firstNumber + secondNumber
            "-" -> answer = firstNumber - secondNumber
            "*" -> answer = firstNumber * secondNumber
        }

        messageText.text = answer.toString()
        return "$firstNumber $operator $secondNumber ="
    }

    fun checkAnswer(ans:String):String {
        if (ans == answer.toString()) {
            return "Good"
        }
        else {
            return "Bad"
        }
    }
}