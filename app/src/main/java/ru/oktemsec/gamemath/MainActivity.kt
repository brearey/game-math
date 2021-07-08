package ru.oktemsec.gamemath

import android.animation.ObjectAnimator
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var answer:Int = 0
    private lateinit var errorSound:MediaPlayer
    private lateinit var successSound:MediaPlayer
    lateinit var messageText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messageText = findViewById(R.id.message_text)
        val taskText:TextView = findViewById(R.id.math_task)
        val answerET:EditText = findViewById(R.id.answer_et)
        val imageMath:ImageView = findViewById(R.id.image_math)

        //Fade in ImageView
        ObjectAnimator.ofFloat(imageMath, "alpha", 1f, 0f).apply {
            duration = 1000
            start()
        }

        //Sounds
        errorSound = MediaPlayer.create(this, R.raw.error)
        successSound = MediaPlayer.create(this, R.raw.success)

        messageText.text = "Попробуйте вычислить:"

        answerET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (answer in 1..9 && answerET.text.length == 1) {
                    messageText.text = checkAnswer(answerET.text.toString().trim())
                    answerET.text.clear()
                    taskText.text = generateTask()
                }
                else if (answer >= 10 && answerET.text.length > 1) {
                    messageText.text = checkAnswer(answerET.text.toString().trim())
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
        when (operator) {
            "*" -> {
                firstNumber = Random.nextInt(1,9)
                secondNumber = Random.nextInt(1,9)
            }
            "-" -> {
                firstNumber = Random.nextInt(24,48)
                secondNumber = Random.nextInt(1,23)
            }
            else -> {
                firstNumber = Random.nextInt(1,48)
                secondNumber = Random.nextInt(1,48)
            }
        }

        when (operator) {
            "+" -> answer = firstNumber + secondNumber
            "-" -> answer = firstNumber - secondNumber
            "*" -> answer = firstNumber * secondNumber
        }

        return "$firstNumber $operator $secondNumber ="
    }

    fun checkAnswer(ans:String):String {
        return if (ans == answer.toString()) {
            messageText.setTextColor(Color.GREEN)
            successSound.start()
            "Good"
        } else {
            messageText.setTextColor(Color.RED)
            errorSound.start()
            "Bad"
        }
    }
}