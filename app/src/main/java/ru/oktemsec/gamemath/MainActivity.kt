package ru.oktemsec.gamemath

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var answer:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val taskText:TextView = findViewById(R.id.math_task)
        taskText.text = generateTask()
    }

    fun generateTask():String {
        var firstNumber:Int
        var secondNumber:Int
        val operator:String = listOf("+", "-", "*")[Random.nextInt(0, 2)]
        if (operator == "*") {
            firstNumber = Random.nextInt(1,9)
            secondNumber = Random.nextInt(1,9)
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

        return "$firstNumber + $secondNumber ="
    }
}