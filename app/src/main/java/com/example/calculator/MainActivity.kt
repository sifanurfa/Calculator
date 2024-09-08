package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewExpression: TextView
    private var currentInput: String = ""
    private var expression: String = ""
    private var firstValue: Double = 0.0
    private var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewExpression = findViewById(R.id.textView_expression)

        val listener = View.OnClickListener { v ->
            val button = v as Button
            val buttonText = button.text.toString()

            when {
                buttonText.matches(Regex("[0-9.]")) -> {
                    currentInput += buttonText
                    expression += buttonText
                    textViewExpression.text = expression
                }
                buttonText == "AC" -> resetCalculator()
                buttonText == "=" -> calculateResult()
                else -> {
                    if (currentInput.isNotEmpty()) {
                        firstValue = currentInput.toDouble()
                        currentInput = ""
                    }
                    operator = buttonText
                    expression += " $operator "
                    textViewExpression.text = expression
                }
            }
        }

        val buttonIds = listOf(
            R.id.btn_nol, R.id.btn_satu, R.id.btn_dua, R.id.btn_tiga, R.id.btn_empat,
            R.id.btn_lima, R.id.btn_enam, R.id.btn_tujuh, R.id.btn_delapan, R.id.btn_sembilan,
            R.id.btn_titik, R.id.btn_tambah, R.id.btn_kurang, R.id.btn_kali, R.id.btn_bagi,
            R.id.btn_samaDengan, R.id.btn_AC, R.id.btn_persen
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(listener)
        }
    }

    private fun resetCalculator() {
        currentInput = ""
        expression = ""
        firstValue = 0.0
        operator = ""
        textViewExpression.text = ""
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondValue = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "x" -> firstValue * secondValue
                "/" -> if (secondValue != 0.0) firstValue / secondValue else Double.NaN
                "%" -> firstValue / 100 * secondValue
                else -> 0.0
            }

            // Cek apakah hasil adalah bilangan bulat atau desimal
            val finalResult = if (result == result.toInt().toDouble()) {
                result.toInt().toString()  // Jika bilangan bulat, tampilkan sebagai int
            } else {
                result.toString()  // Jika desimal, tetap tampilkan sebagai double
            }

            expression += " = $finalResult"
            textViewExpression.text = expression
            currentInput = finalResult
            operator = ""
        }
    }

}
