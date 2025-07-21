package com.example.calculator // <-- Убедитесь, что это ваш пакет

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView

    // Переменные для хранения состояния калькулятора
    private var operand1: Double? = null
    private var pendingOperation = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        // Назначаем единый обработчик кликов для всех кнопок
        val listener = View.OnClickListener { v ->
            val button = v as Button
            when (button.id) {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
                R.id.button_8, R.id.button_9 -> onNumberClick(button)

                R.id.button_comma -> onCommaClick()

                R.id.button_add, R.id.button_subtract, R.id.button_multiply, R.id.button_divide -> onOperatorClick(button)

                R.id.button_equals -> onEqualsClick()

                R.id.button_ac -> onClearClick()

                R.id.button_percent -> onPercentClick()

                R.id.button_sign -> onSignClick()
            }
        }

        // Привязываем обработчик ко всем кнопкам
        findViewById<Button>(R.id.button_0).setOnClickListener(listener)
        findViewById<Button>(R.id.button_1).setOnClickListener(listener)
        findViewById<Button>(R.id.button_2).setOnClickListener(listener)
        findViewById<Button>(R.id.button_3).setOnClickListener(listener)
        findViewById<Button>(R.id.button_4).setOnClickListener(listener)
        findViewById<Button>(R.id.button_5).setOnClickListener(listener)
        findViewById<Button>(R.id.button_6).setOnClickListener(listener)
        findViewById<Button>(R.id.button_7).setOnClickListener(listener)
        findViewById<Button>(R.id.button_8).setOnClickListener(listener)
        findViewById<Button>(R.id.button_9).setOnClickListener(listener)
        findViewById<Button>(R.id.button_comma).setOnClickListener(listener)
        findViewById<Button>(R.id.button_add).setOnClickListener(listener)
        findViewById<Button>(R.id.button_subtract).setOnClickListener(listener)
        findViewById<Button>(R.id.button_multiply).setOnClickListener(listener)
        findViewById<Button>(R.id.button_divide).setOnClickListener(listener)
        findViewById<Button>(R.id.button_equals).setOnClickListener(listener)
        findViewById<Button>(R.id.button_ac).setOnClickListener(listener)
        findViewById<Button>(R.id.button_percent).setOnClickListener(listener)
        findViewById<Button>(R.id.button_sign).setOnClickListener(listener)
    }

    private fun onNumberClick(button: Button) {
        if (isNewOperation) {
            resultTextView.text = ""
            isNewOperation = false
        }
        if (resultTextView.text.toString() == "0") {
            resultTextView.text = button.text
        } else {
            resultTextView.append(button.text)
        }
    }

    private fun onCommaClick() {
        if (isNewOperation) {
            resultTextView.text = "0,"
            isNewOperation = false
        } else if (!resultTextView.text.contains(",")) {
            resultTextView.append(",")
        }
    }

    private fun onOperatorClick(button: Button) {
        val operation = when(button.text.toString()) {
            "÷" -> "/"
            "×" -> "*"
            else -> button.text.toString()
        }

        val currentText = resultTextView.text.toString()
        if (currentText != "Error" && operand1 == null) {
            operand1 = currentText.replace(',', '.').toDoubleOrNull()
        } else if (currentText != "Error") {
            onEqualsClick()
        }
        pendingOperation = operation
        isNewOperation = true
    }

    private fun onEqualsClick() {
        val operand2Text = resultTextView.text.toString().replace(',', '.')
        val operand2 = operand2Text.toDoubleOrNull()

        if (operand1 != null && operand2 != null && pendingOperation.isNotEmpty()) {
            val result = when (pendingOperation) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> {
                    if (operand2 == 0.0) {
                        resultTextView.text = "Error"
                        operand1 = null
                        pendingOperation = ""
                        isNewOperation = true
                        return
                    } else {
                        operand1!! / operand2
                    }
                }
                else -> return
            }
            displayResult(result)
            operand1 = result
            pendingOperation = ""
            isNewOperation = true
        }
    }

    private fun onClearClick() {
        resultTextView.text = "0"
        operand1 = null
        pendingOperation = ""
        isNewOperation = true
    }

    private fun onPercentClick() {
        if (resultTextView.text.toString() == "Error") return
        val value = resultTextView.text.toString().replace(',', '.').toDoubleOrNull()
        if (value != null) {
            displayResult(value / 100.0)
            isNewOperation = true
        }
    }

    private fun onSignClick() {
        if (resultTextView.text.toString() == "Error") return
        val value = resultTextView.text.toString().replace(',', '.').toDoubleOrNull()
        if (value != null) {
            displayResult(-value)
        }
    }

    private fun displayResult(number: Double) {
        val formatter = DecimalFormat("0.########")
        val formatted = formatter.format(number).replace('.', ',')
        resultTextView.text = formatted
    }
}