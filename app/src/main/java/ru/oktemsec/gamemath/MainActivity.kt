package ru.oktemsec.gamemath

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var answer:Int = 0
    private lateinit var errorSound:MediaPlayer
    private lateinit var successSound:MediaPlayer
    private lateinit var messageText:TextView
    private lateinit var taskText:TextView
    private lateinit var historyText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messageText = findViewById(R.id.message_text)
        historyText = findViewById(R.id.historyTV)
        taskText = findViewById(R.id.math_task)
        val answerET:EditText = findViewById(R.id.answer_et)

        //Sounds
        errorSound = MediaPlayer.create(this, R.raw.error)
        successSound = MediaPlayer.create(this, R.raw.success)

        messageText.text = "Попробуйте вычислить:"

        answerET.addTextChangedListener {
            if (answer in 1..9 && answerET.text.length == 1) {
                messageText.text = checkAnswer(answerET.text.toString().trim())

                historyText.setTextColor(messageText.textColors)
                historyText.text = getString(R.string.history_text, taskText.text, answerET.text)

                answerET.text.clear()
                taskText.text = generateTask()
            }
            else if (answer >= 10 && answerET.text.length > 1) {
                messageText.text = checkAnswer(answerET.text.toString().trim())

                historyText.setTextColor(messageText.textColors)
                historyText.text = getString(R.string.history_text, taskText.text, answerET.text)

                answerET.text.clear()
                taskText.text = generateTask()
            }
        }
        taskText.text = generateTask()
        historyText.text = ""
    }

    private fun generateTask():String {
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

    private fun checkAnswer(ans:String):String {
        return if (ans == answer.toString()) {
            messageText.setTextColor(getColor(R.color.green_message))
            successSound.start()
            animateHistoryText()
            listOf("Молодец", "Красавчик", "Ты лучший", "Правильно", "Так держать!", "Верно")[Random.nextInt(1, 6)]
        } else {
            messageText.setTextColor(getColor(R.color.red_message))
            errorSound.start()
            animateHistoryText()
            listOf("Вы ошиблись", "Ты идиот?", "Оо, боже", "Не правильно", "Иди в лес", "Нет")[Random.nextInt(1, 6)]
        }
    }
    private fun animateHistoryText() {
        //Fade out and move historyText
        val animAlpha = ObjectAnimator.ofFloat(historyText, "alpha", 0.8f, 0f)
        val animY = ObjectAnimator.ofFloat(historyText, "y", taskText.y + taskText.height, taskText.y + taskText.height + 100f)
        AnimatorSet().apply {
            duration = 1000
            playTogether(animAlpha, animY)
            start()
        }
    }
}