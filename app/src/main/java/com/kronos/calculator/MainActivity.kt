package com.kronos.calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.kronos.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var number: String? = null

    var firstNumber: Double = 0.0
    var lastNumber: Double = 0.0

    var status: String? = null
    var operator: Boolean = false

    var history: String = ""
    var current: String = ""

    var dotControl: Boolean = true
    var equalControl: Boolean = false

    var myformatMethod = DecimalFormat("######.######")

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.resultView.text = "0"


        binding.materialToolbar.setOnMenuItemClickListener { item ->

            when(item.itemId) {
                R.id.settings -> {
                    val intent = Intent(this@MainActivity, dark_modeActivity::class.java)
                    startActivity(intent)

                    return@setOnMenuItemClickListener true
                }else -> return@setOnMenuItemClickListener false
            }

        }


        binding.zero.setOnClickListener {
            onClickButton("0")
        }

        binding.btn1.setOnClickListener {
            onClickButton("1")
        }

        binding.btn2.setOnClickListener {
            onClickButton("2")
        }

        binding.btn3.setOnClickListener {
            onClickButton("3")
        }
        binding.btn4.setOnClickListener {
            onClickButton("4")
        }

        binding.btn5.setOnClickListener {
            onClickButton("5")
        }

        binding.btn6.setOnClickListener {
            onClickButton("6")
        }

        binding.btn7.setOnClickListener {
            onClickButton("7")
        }
        binding.btn8.setOnClickListener {
            onClickButton("8")
        }
        binding.btn9.setOnClickListener {
            onClickButton("9")
        }

        binding.btnAC.setOnClickListener {

            clearAll()

        }
        binding.btnDel.setOnClickListener {

            number?.let {
                if (it.length == 1) {
                    clearAll()
                }else {

                    number = it.substring(0, it.length-1)
                    binding.resultView.text = number
                    dotControl = !number!!.contains(".")

                }

            }

        }
        binding.btnAdd.setOnClickListener {
            history = binding.viewHistory.text.toString()
            current = binding.resultView.text.toString()
            binding.viewHistory.text = history.plus(current).plus("+")

            if (operator) {
                when(status) {
                    "addition" -> plus()
                    "subtraction" -> minus()
                    "multiplication" -> multiple()
                    "division" -> divide()
                    else -> firstNumber = binding.resultView.text.toString().toDouble()
                }
            }
            status = "addition"
            operator = false
            number = null
            dotControl = true

        }
        binding.btnminus.setOnClickListener {
            history = binding.viewHistory.text.toString()
            current = binding.resultView.text.toString()
            binding.viewHistory.text = history.plus(current).plus("-")
            if (operator) {
                when(status) {
                    "addition" -> plus()
                    "subtraction" -> minus()
                    "multiplication" -> multiple()
                    "division" -> divide()
                    else -> firstNumber = binding.resultView.text.toString().toDouble()
                }
            }
            status = "subtraction"
            operator = false
            number = null
            dotControl = true

        }
        binding.btnMultiply.setOnClickListener {

            history = binding.viewHistory.text.toString()
            current = binding.resultView.text.toString()
            binding.viewHistory.text = history.plus(current).plus("*")

            if (operator) {
                when(status) {
                    "addition" -> plus()
                    "subtraction" -> minus()
                    "multiplication" -> multiple()
                    "division" -> divide()
                    else -> firstNumber = binding.resultView.text.toString().toDouble()
                }
            }
            status = "multiplication"
            operator = false
            number = null
            dotControl = true

        }
        binding.btnDivide.setOnClickListener {
            history = binding.viewHistory.text.toString()
            current = binding.resultView.text.toString()
            binding.viewHistory.text = history.plus(current).plus("/")
            if (operator) {
                when(status) {
                    "addition" -> plus()
                    "subtraction" -> minus()
                    "multiplication" -> multiple()
                    "division" -> divide()
                    else -> firstNumber = binding.resultView.text.toString().toDouble()
                }
            }
            status = "division"
            operator = false
            number = null
            dotControl = true

        }
        binding.dot.setOnClickListener {

             if (dotControl) {
                number = if (number == null) {

                    "0."

                }else if (equalControl) {
                    if (binding.resultView.text.toString().contains(".")) {
                        binding.resultView.text.toString()
                    }else {
                        binding.resultView.text.toString().plus(".")
                    }
                }


                else {
                    "$number."
                }
                binding.resultView.text.toString()
            }
            dotControl = false
        }

        binding.equal.setOnClickListener {
            history = binding.viewHistory.text.toString()
            current = binding.resultView.text.toString()
            if (operator) {
                when(status){
                    "addition" -> plus()
                    "subtraction" -> minus()
                    "multiplication" -> multiple()
                    "division" -> divide()
                    else -> firstNumber = binding.resultView.text.toString().toDouble()
                }

                binding.viewHistory.text = history.plus(current).plus("=").plus(binding.resultView.text)
            }

            operator = false
            dotControl = true
            equalControl = true
        }


    }


    fun clearAll() {
        status = null
        number = null
        firstNumber = 0.0
        lastNumber = 0.0
        binding.resultView.text ="0"
        binding.viewHistory.text =""
        dotControl = false
        equalControl = false

    }

    fun onClickButton(numberClicked: String) {
        if (number == null) {
            number = numberClicked
        } else if (equalControl) {
            number = if (dotControl) {
                numberClicked
            } else {
                binding.resultView.text.toString().plus(numberClicked)
            }
            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            binding.viewHistory.text = ""

        }
        else {
            number += numberClicked
        }

        binding.resultView.text = number

        operator = true
        equalControl = false

    }

    fun plus() {
        lastNumber = binding.resultView.text.toString().toDouble()
        firstNumber += lastNumber
        binding.resultView.text = myformatMethod.format(firstNumber)

    }

    fun minus() {
        lastNumber = binding.resultView.text.toString().toDouble()
        firstNumber -= lastNumber
        binding.resultView.text = myformatMethod.format(firstNumber)

    }

    fun multiple() {
        lastNumber = binding.resultView.text.toString().toDouble()
        firstNumber *= lastNumber
        binding.resultView.text = myformatMethod.format(firstNumber)

    }

    fun divide() {
        lastNumber = binding.resultView.text.toString().toDouble()

        if (lastNumber == 0.0) {
            Toast.makeText(this, "divider can't be zero", Toast.LENGTH_SHORT).show()
        } else {
            firstNumber /= lastNumber
            binding.resultView.text = myformatMethod.format(firstNumber)
        }
    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = this.getSharedPreferences("darkMode", Context.MODE_PRIVATE)
        val darkMode = sharedPreferences.getBoolean("darkModeYes", false)
        if(darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }


    override fun onStart() {
        super.onStart()
        sharedPreferences = getSharedPreferences("Calculation",Context.MODE_PRIVATE)
        binding.resultView.text = sharedPreferences.getString("current", "")
        binding.viewHistory.text = sharedPreferences.getString("history", "")
        number = sharedPreferences.getString("number", null)
        status = sharedPreferences.getString("status", null)
        operator = sharedPreferences.getBoolean("operator", false)
        dotControl = sharedPreferences.getBoolean("dot", true)
        equalControl = sharedPreferences.getBoolean("equal", false)
        firstNumber = sharedPreferences.getString("first", "0.0")!!.toDouble()
        lastNumber = sharedPreferences.getString("second", "0.0")!!.toDouble()

    }


    override fun onPause() {
        super.onPause()

        sharedPreferences = getSharedPreferences("Calculation", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val saveCurrent = binding.resultView.text.toString()
        val saveHistory = binding.viewHistory.text.toString()
        val saveNumber = number
        val saveStatus = status
        val saveOperator = operator
        val saveDot = dotControl
        val saveEqual = equalControl
        val saveFirstNumber = firstNumber.toString()
        val saveLastNumber = lastNumber.toString()


        editor.putString("current", saveCurrent)
        editor.putString("history", saveHistory)
        editor.putString("number", saveNumber)
        editor.putString("status", saveStatus)
        editor.putBoolean("operator", saveOperator)
        editor.putBoolean("dot",saveDot)
        editor.putBoolean("equal", saveEqual)
        editor.putString("first", saveFirstNumber)
        editor.putString("second", saveLastNumber)

        editor.apply()
    }
}