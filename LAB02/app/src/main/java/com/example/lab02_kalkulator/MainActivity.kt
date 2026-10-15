package com.example.lab02_kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var firstValue = 0.0
    private var currentOperator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // Jawnie zdefiniowana lista przycisków numerycznych
        val numberButtons = listOf(
            Pair(R.id.btn0, "0"),
            Pair(R.id.btn1, "1"),
            Pair(R.id.btn2, "2"),
            Pair(R.id.btn3, "3"),
            Pair(R.id.btn4, "4"),
            Pair(R.id.btn5, "5"),
            Pair(R.id.btn6, "6"),
            Pair(R.id.btn7, "7"),
            Pair(R.id.btn8, "8"),
            Pair(R.id.btn9, "9")
        )

        for ((id, digit) in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (isNewOperation) {
                    currentInput = ""
                    isNewOperation = false
                }
                currentInput += digit
                tvResult.text = currentInput
            }
        }

        // Przycisk czyszczenia (C)
        findViewById<Button>(R.id.btnC).setOnClickListener {
            currentInput = ""
            firstValue = 0.0
            currentOperator = ""
            tvResult.text = "0"
            isNewOperation = true
        }

        // Operatory arytmetyczne
        val operatorButtons = listOf(
            Pair(R.id.btnPlus, "+"),
            Pair(R.id.btnMultiply, "*"),
            Pair(R.id.btnDivide, "/"),
        )

        for ((id, op) in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    firstValue = currentInput.toDouble()
                    currentOperator = op
                    isNewOperation = true
                }
            }
        }

        // Przycisk równa się (=)
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
                val secondValue = currentInput.toDouble()
                val result = when (currentOperator) {
                    "+" -> firstValue + secondValue
                    "-" -> firstValue - secondValue
                    "*" -> firstValue * secondValue
                    "/" -> if (secondValue != 0.0) firstValue / secondValue else 0.0
                    else -> 0.0
                }
                tvResult.text = if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()
                currentInput = tvResult.text.toString()
                currentOperator = ""
                isNewOperation = true
            }
        }

        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
                val secondValue = currentInput.toDouble()
                val percentValue = (firstValue * secondValue) / 100.0

                currentInput = percentValue.toString()

                tvResult.text = if (percentValue % 1.0 == 0.0) percentValue.toInt().toString() else percentValue.toString()
            }
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if(currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)

                if (currentInput.isEmpty()) {
                    tvResult.text = "0"
                } else{
                    tvResult.text = currentInput
                }
            }
        }

        val btnPlus = findViewById<Button>(R.id.btnPlus)

        btnPlus.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                firstValue = currentInput.toDouble()
                currentOperator = "+"
                isNewOperation = true
            }
        }

        btnPlus.setOnLongClickListener{ view ->
            val popup = androidx.appcompat.widget.PopupMenu(this, view)
            popup.menu.add("+")
            popup.menu.add("-")

            popup.setOnMenuItemClickListener { item ->
                if (currentInput.isNotEmpty()) {
                    firstValue = currentInput.toDouble()
                    currentOperator = item.title.toString()
                    isNewOperation = true
                }
                true
            }

            popup.show()
            true
        }
    }
}